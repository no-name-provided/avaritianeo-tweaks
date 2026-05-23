package com.github.no_name_provided.avaritianeo_tweaks.datagen.providers;

import com.github.no_name_provided.avaritianeo_tweaks.common.blocks.ANTBlocks;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ANTLanguageProvider extends LanguageProvider {
    public ANTLanguageProvider(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }
    
    @Override
    protected void addTranslations() {
        add(ANTBlocks.FAST_NEUTRONIUM_COMPRESSOR.get(), "Fast Neutronium Compressor");
        add("container.fast_neutronium_compressor", "Fast Neutronium Compressor");
    }
}