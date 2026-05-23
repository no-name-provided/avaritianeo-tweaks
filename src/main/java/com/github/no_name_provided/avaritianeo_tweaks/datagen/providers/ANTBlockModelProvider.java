package com.github.no_name_provided.avaritianeo_tweaks.datagen.providers;

import com.github.no_name_provided.avaritianeo_tweaks.common.blocks.ANTBlocks;
import net.byAqua3.avaritia.loader.AvaritiaBlocks;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ANTBlockModelProvider extends BlockModelProvider {
    public ANTBlockModelProvider(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }
    
    @Override
    protected void registerModels() {
        withExistingParent(
                ANTBlocks.FAST_NEUTRONIUM_COMPRESSOR.getRegisteredName(),
                AvaritiaBlocks.COMPRESSOR.getRegisteredName()
        );
    }
}
