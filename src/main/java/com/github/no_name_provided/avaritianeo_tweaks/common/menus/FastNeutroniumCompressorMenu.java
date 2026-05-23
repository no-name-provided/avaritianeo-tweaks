package com.github.no_name_provided.avaritianeo_tweaks.common.menus;

import com.github.no_name_provided.avaritianeo_tweaks.common.block_entities.FastNeutroniumCompressorBlockEntity;
import com.mojang.logging.annotations.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault @ParametersAreNonnullByDefault
public class FastNeutroniumCompressorMenu extends AbstractContainerMenu {
    public BlockPos POS;
    // We need this because the dataslots are stupidly encapsulated
    public ContainerData SYNCED_DATA;
    
    public FastNeutroniumCompressorMenu(int id, Inventory playerInventory, BlockPos pos) {
        this(id, playerInventory, pos, new ItemStackHandler(FastNeutroniumCompressorBlockEntity.INVENTORY_SIZE), new SimpleContainerData(4));
    }
    
    public FastNeutroniumCompressorMenu(int id, Inventory playerInventory, BlockPos pos, ItemStackHandler machineInventory, ContainerData syncedData) {
        super(ANTMenus.FAST_NEUTRONIUM_COMPRESSOR.get(), id);
        POS = pos;
        SYNCED_DATA = syncedData;
        
        addSlot(new SlotItemHandler(machineInventory, 0, 39, 35));
        addSlot(new SlotItemHandler(machineInventory, 1, 117, 35) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }
        });
        addPlayerInventorySlots(playerInventory);
        addDataSlots(syncedData);
    }
    
    private void addPlayerInventorySlots(Inventory playerInv) {
        int playerInvOffset = 2 * 18;
        
        int row_index;
        int column_index;
        
        for(row_index = 0; row_index < 3; ++row_index) {
            for(column_index = 0; column_index < 9; column_index++) {
                this.addSlot(new Slot(playerInv, column_index + row_index * 9 + 9, 8 + column_index * 18, 48 + row_index * 18 + playerInvOffset));
            }
        }
        
        for(column_index = 0; column_index < 9; column_index++) {
            this.addSlot(new Slot(playerInv, column_index, 8 + column_index * 18, 106 + playerInvOffset));
        }
    }
    
    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot source = slots.get(index);
        ItemStack original = source.getItem();
        ItemStack remainder = original.copy();
        ItemStack moved = ItemStack.EMPTY;
        
        if (source.hasItem()) {
            boolean sourceIsMachine = index < 2;
            boolean couldMove = moveItemStackTo(
                    remainder,
                    sourceIsMachine ? 2 : 0,
                    sourceIsMachine ? slots.size() - 1 : 1,
                    false
            );
            if (!couldMove) {
                
                return ItemStack.EMPTY;
            }
            
            if (remainder.isEmpty()) {
                source.set(ItemStack.EMPTY);
            } else {
                source.setChanged();
            }
            if (original.getCount() == moved.getCount()) {
                
                return ItemStack.EMPTY;
            }
            source.onTake(player, remainder);
        }
        
        return remainder;
    }
    
    @Override
    public boolean stillValid(Player player) {
        if (player.level().isClientSide()) {
            
            return true;
        } else {
            
            return player.entityInteractionRange() + 3 >= player.position().distanceTo(POS.getCenter());
        }
    }
    
    public int getAmountAbsorbed() {
        
        return SYNCED_DATA.get(0);
    }
    
    public int getAmountRequired() {
        
        return SYNCED_DATA.get(1);
    }
    
    public Item getRecipeInput() {
        
        return SYNCED_DATA.get(2) != -1 ? BuiltInRegistries.ITEM.byId(SYNCED_DATA.get(2)) : Items.AIR;
    }
    
    public Item getRecipeOutput() {
        
        return SYNCED_DATA.get(3) != -1 ? BuiltInRegistries.ITEM.byId(SYNCED_DATA.get(3)) : Items.AIR;
    }
}
