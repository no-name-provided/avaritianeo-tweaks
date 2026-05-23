package com.github.no_name_provided.avaritianeo_tweaks.common.capabilities;

import net.byAqua3.avaritia.tile.TileExtremeCraftingTable;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault @MethodsReturnNonnullByDefault
public class ExtremeCraftingTableCapability implements IItemHandler {
    TileExtremeCraftingTable be;
    Direction side;
    boolean usingBottom;
    
    public ExtremeCraftingTableCapability(TileExtremeCraftingTable be, @Nullable Direction side) {
        this.be = be;
        this.side = side != null ? side : Direction.DOWN;
        usingBottom = this.side == Direction.DOWN;
        
    }
    
    @Override
    public int getSlots() {
        
        return side != Direction.DOWN ? 9 * 9 : 1;
    }
    
    @Override
    public ItemStack getStackInSlot(int slot) {
        
        return usingBottom ? be.result.getItem(slot) : be.matrix.getItem(slot);
    }
    
    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        ItemStack copy = stack.copy();
        if (!usingBottom) {
            if (getStackInSlot(slot).isEmpty()) {
                if (!simulate) {
                    be.matrix.setItem(slot, copy);
                }
                
                return ItemStack.EMPTY;
            } else {
                ItemStack current = be.matrix.getItem(slot);
                if (current.is(stack.getItem())) {
                    int newSize = Mth.clamp(
                            current.getCount() + copy.getCount(),
                            0,
                            Math.min(getSlotLimit(slot), current.getMaxStackSize())
                    );
                    if (!simulate) {
                        be.matrix.setItem(slot, copy.copyWithCount(newSize));
                    }
                    
                    return copy.copyWithCount(copy.getCount() + current.getCount() - newSize);
                }
                
                return copy;
            }
        } else {
            
            return copy;
        }
    }
    
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        ItemStack current = usingBottom ? be.matrix.getItem(slot) : be.result.getItem(slot);
        int toTransfer = Math.min(amount, current.getCount());
        if (!simulate) {
            if (!usingBottom) {
                be.matrix.setItem(slot, current.copyWithCount(current.getCount() - toTransfer));
            } else {
                be.result.setItem(slot, current.copyWithCount(current.getCount() - toTransfer));
            }
        }
        
        return current.copyWithCount(toTransfer);
    }
    
    @Override
    public int getSlotLimit(int slot) {
        
        return be.matrix.getMaxStackSize();
    }
    
    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        
        return !usingBottom;
    }
}
