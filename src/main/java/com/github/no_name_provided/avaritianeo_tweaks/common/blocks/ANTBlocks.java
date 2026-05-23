package com.github.no_name_provided.avaritianeo_tweaks.common.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.github.no_name_provided.avaritianeo_tweaks.AvaritiaNeoTweaks.MODID;

public class ANTBlocks {
    public static DeferredRegister<Block> BLOCKS = DeferredRegister.createBlocks(MODID);
    
    public static DeferredHolder<Block, FastNeutroniumCompressorBlock> FAST_NEUTRONIUM_COMPRESSOR = BLOCKS.register(
            "fast_neutronium_compressor",
            () -> new FastNeutroniumCompressorBlock(
                    BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).requiresCorrectToolForDrops().strength(20.0F).sound(SoundType.GLASS)
            )
    );
}
