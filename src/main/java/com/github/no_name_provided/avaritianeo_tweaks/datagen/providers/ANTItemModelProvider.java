package com.github.no_name_provided.avaritianeo_tweaks.datagen.providers;

import com.github.no_name_provided.avaritianeo_tweaks.common.blocks.ANTBlocks;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ANTItemModelProvider extends ItemModelProvider {
    public ANTItemModelProvider(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }
    
    @Override
    protected void registerModels() {
        this.simpleBlockItem(ANTBlocks.FAST_NEUTRONIUM_COMPRESSOR.get());
    }
}
