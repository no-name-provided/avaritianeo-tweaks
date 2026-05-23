package com.github.no_name_provided.avaritianeo_tweaks.common.capabilities;

import net.byAqua3.avaritia.loader.AvaritiaRecipes;
import net.byAqua3.avaritia.recipe.RecipeExtremeCrafting;
import net.byAqua3.avaritia.tile.TileExtremeCraftingTable;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.IItemHandler;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;

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
        ItemStack current = !usingBottom ? be.matrix.getItem(slot) : be.result.getItem(slot);
        int toTransfer = Math.min(amount, current.getCount());
        if (!usingBottom) {
            if (!simulate) {
                be.matrix.setItem(slot, current.copyWithCount(current.getCount() - toTransfer));
            }
        } else if (be.getLevel() instanceof Level level) {
            // Crafting is done in the menu, so the result slot isn't actually populated.
            RecipeManager manager = level.getRecipeManager();
            Optional<RecipeHolder<RecipeExtremeCrafting>> maybeRecipe = manager.getRecipeFor(AvaritiaRecipes.EXTREME_CRAFTING.get(), asPositionedCraftInput(be.matrix), level);
            if (maybeRecipe.isPresent()) {
                current = maybeRecipe.get().value().assemble(asPositionedCraftInput(be.matrix), level.registryAccess());
                toTransfer = current.getCount();
                
                if (!simulate) {
                    for (int index = 0; index < be.matrix.getContainerSize(); index++) {
                        be.matrix.setItem(index, be.matrix.getItem(index).copyWithCount(be.matrix.getItem(index).getCount() - 1));
                    }
                }
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
    
    private CraftingInput asPositionedCraftInput(SimpleContainer container) {
        return CraftingInput.ofPositioned(9, 9, container.getItems()).input();
    }
}
