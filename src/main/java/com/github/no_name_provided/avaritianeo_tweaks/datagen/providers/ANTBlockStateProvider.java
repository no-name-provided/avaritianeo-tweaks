package com.github.no_name_provided.avaritianeo_tweaks.datagen.providers;

import com.github.no_name_provided.avaritianeo_tweaks.common.blocks.ANTBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import static com.github.no_name_provided.avaritianeo_tweaks.AvaritiaNeoTweaks.MODID;
import static net.byAqua3.avaritia.block.BlockMachine.ACTIVE;

public class ANTBlockStateProvider extends BlockStateProvider {
    
    public ANTBlockStateProvider(PackOutput output, ExistingFileHelper exFileHurter) {
        super(output, MODID, exFileHurter);
    }
    
    @Override
    protected void registerStatesAndModels() {
//        this.directionalBlock(
//                ANTBlocks.FAST_NEUTRONIUM_COMPRESSOR.get(),
////                Minecraft.getInstance().getModelManager().getModel(ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath("avaritia", "compressor")))
//                modelProvider.getExistingFile(ResourceLocation.fromNamespaceAndPath("avaritia", "compressor"))
//        );
        
        ModelFile active = models().getExistingFile(ResourceLocation.parse(ANTBlocks.FAST_NEUTRONIUM_COMPRESSOR.getRegisteredName()));
        
//        directionalBlock(
//                ANTBlocks.FAST_NEUTRONIUM_COMPRESSOR.get(),
//                state -> {
//
//                    return state.getValue(ACTIVE) ? active : active;
//                },
//                90);
        horizontalBlock(
                ANTBlocks.FAST_NEUTRONIUM_COMPRESSOR.get(),
                state -> {

                    return state.getValue(ACTIVE) ? active : active;
                }
        );
        
//        ModelFile c_inactive = models().cubeAll("c_inactive", blockTexture(ANTBlocks.FAST_NEUTRONIUM_COMPRESSOR.get()));
//        ModelFile c_active = models().cubeAll("c_active", blockTexture(ANTBlocks.FAST_NEUTRONIUM_COMPRESSOR.get()));
        
//        this.getVariantBuilder(ANTBlocks.FAST_NEUTRONIUM_COMPRESSOR.get())
//                .partialState().with(ACTIVE, false).addModels(new ConfiguredModel(c_inactive))
//                .partialState().with(ACTIVE, true).addModels(new ConfiguredModel(c_active));
    }
}
