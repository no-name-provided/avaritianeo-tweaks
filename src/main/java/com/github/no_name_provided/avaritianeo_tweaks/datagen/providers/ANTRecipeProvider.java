package com.github.no_name_provided.avaritianeo_tweaks.datagen.providers;

import com.github.no_name_provided.avaritianeo_tweaks.common.items.ANTItems;
import net.byAqua3.avaritia.loader.AvaritiaBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.concurrent.CompletableFuture;

@ParametersAreNonnullByDefault
public class ANTRecipeProvider extends RecipeProvider {
    public ANTRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }
    
    @Override
    protected void buildRecipes(RecipeOutput output, HolderLookup.Provider holderLookup) {
        new ShapelessRecipeBuilder(RecipeCategory.MISC, ANTItems.FAST_NEUTRONIUM_COMPRESSOR.get().getDefaultInstance())
                .requires(AvaritiaBlocks.COMPRESSOR_ITEM.get())
                .unlockedBy("has_slow_comp", has(AvaritiaBlocks.COMPRESSOR_ITEM.get()))
                .save(output, "upgrade");
        new ShapelessRecipeBuilder(RecipeCategory.MISC, AvaritiaBlocks.COMPRESSOR_ITEM.get().getDefaultInstance())
                .requires(ANTItems.FAST_NEUTRONIUM_COMPRESSOR.get())
                .unlockedBy("has_fast_comp", has(ANTItems.FAST_NEUTRONIUM_COMPRESSOR.get()))
                .save(output, "downgrade");
    }
}
