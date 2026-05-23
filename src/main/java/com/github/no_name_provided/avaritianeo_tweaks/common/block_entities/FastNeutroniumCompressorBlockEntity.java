package com.github.no_name_provided.avaritianeo_tweaks.common.block_entities;

import com.github.no_name_provided.avaritianeo_tweaks.common.menus.FastNeutroniumCompressorMenu;
import com.mojang.logging.annotations.MethodsReturnNonnullByDefault;
import net.byAqua3.avaritia.recipe.RecipeCompressor;
import net.byAqua3.avaritia.util.RecipeUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.logging.Logger;

import static com.github.no_name_provided.avaritianeo_tweaks.AvaritiaNeoTweaks.MODID;

@MethodsReturnNonnullByDefault @ParametersAreNonnullByDefault
public class FastNeutroniumCompressorBlockEntity extends BaseContainerBlockEntity {
    public static final int INVENTORY_SIZE = 2;
    public static final SimpleContainer INVENTORY = new SimpleContainer(INVENTORY_SIZE);
    
    public int amountAbsorbed = 0;
    public RecipeCompressor currentRecipe = null;
    public ItemLike itemCompressing = ItemStack.EMPTY.getItem();
    
    public FastNeutroniumCompressorBlockEntity(BlockPos pos, BlockState blockState) {
        super(ANTBlockEntities.FAST_NEUTRONIUM_COMPRESSOR.get(), pos, blockState);
    }
    
    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        amountAbsorbed = tag.getInt("amountAbsorbed");
        itemCompressing = BuiltInRegistries.ITEM.byNameCodec().decode(NbtOps.INSTANCE, tag.get("itemCompressing")).getOrThrow().getFirst();
        getItems().clear();
        ContainerHelper.loadAllItems(tag, getItems(), registries);
    }
    
    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("amountAbsorbed", amountAbsorbed);
        tag.put("itemCompressing", BuiltInRegistries.ITEM.byNameCodec().encodeStart(NbtOps.INSTANCE, itemCompressing.asItem()).getOrThrow());
        ContainerHelper.saveAllItems(tag, getItems(), registries);
    }
    
    @Override
    protected Component getDefaultName() {
        
        return Component.translatable("container.fast_neutronium_compressor");
    }
    
    @Override
    protected NonNullList<ItemStack> getItems() {
        
        return INVENTORY.getItems();
    }
    
    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = super.getUpdateTag(registries);
        saveAdditional(tag, registries);
        
        return tag;
    }
    
    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        super.handleUpdateTag(tag, lookupProvider);
        loadAdditional(tag, lookupProvider);
    }
    
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        
        return ClientboundBlockEntityDataPacket.create(this);
    }
    
    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider lookupProvider) {
        super.onDataPacket(net, pkt, lookupProvider);
        handleUpdateTag(pkt.getTag(), lookupProvider);
    }
    
    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        if (items.size() != INVENTORY_SIZE) {
            Logger.getLogger(MODID).warning("Error setting contents of " + getDefaultName() + " at " + getBlockPos());
            Logger.getLogger(MODID).warning("Returning early - inventory is unchanged.");
            
            return;
        }
        for (int i = 0; i < getContainerSize(); i++) {
            INVENTORY.setItem(i, items.get(i));
        }
    }
    
    @SuppressWarnings("NullableProblems") // compiler being weird; this is nullable the whole way down
    @Override
    protected @Nullable AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        
        return new FastNeutroniumCompressorMenu(
                containerId, inventory,
                getBlockPos(),
                new ItemStackHandler(this.getItems()),
                new ContainerData() {
                    @Override
                    public int get(int index) {
                        
                        return switch (index) {
                            case 0 -> amountAbsorbed;
                            case 1 -> currentRecipe != null ? currentRecipe.getCost() : -1;
                            case 2 ->
                                    currentRecipe != null ? BuiltInRegistries.ITEM.getId(currentRecipe.getIngredients().getFirst().getItems()[0].getItem()) : -1;
                            case 3 ->
                                    currentRecipe != null ? BuiltInRegistries.ITEM.getId(currentRecipe.result.getItem()) : -1;
                            default -> throw new IndexOutOfBoundsException();
                        };
                    }
                    
                    @Override
                    public void set(int index, int value) {
                        switch (index) {
                            case 0 -> amountAbsorbed = value;
                            case 1, 2, 3 ->
                                    throw new IndexOutOfBoundsException("This value can't be set via DataSlots; it's automatically pulled from the current recipe.");
                            default -> throw new IndexOutOfBoundsException();
                        }
                    }
                    
                    @Override
                    public int getCount() {
                        
                        return 4;
                    }
                }
        );
    }
    
    @Override
    public int getContainerSize() {
        
        return INVENTORY_SIZE;
    }
    
    public static void tick(Level level, BlockPos pos, BlockState state, FastNeutroniumCompressorBlockEntity be) {
        ItemStack input = be.getItem(0).copy();
        if (be.currentRecipe == null && (!input.isEmpty())) {
            be.currentRecipe = RecipeUtils.getCompressorRecipe(level, input);
            if (be.currentRecipe != null) {
                setChanged(level, pos, state);
            }
        }
        if (be.currentRecipe != null) {
            if (input.is(be.currentRecipe.getIngredients().getFirst().getItems()[0].getItem())) {
                int toAbsorb = Math.min(input.getCount(), be.currentRecipe.getCost() - be.amountAbsorbed);
                input.shrink(toAbsorb);
                be.setItem(0, input);
                be.amountAbsorbed += toAbsorb;
                if (be.amountAbsorbed == be.currentRecipe.getCost() && (be.currentRecipe.result.getItem() == be.getItem(1).getItem() || be.getItem(1).isEmpty())) {
                    be.setItem(1, be.currentRecipe.result.copyWithCount(be.getItem(1).getCount() + 1));
                    be.currentRecipe = null;
                    be.amountAbsorbed = 0;
                }
                setChanged(level, pos, state);
            }
        }
    }
}