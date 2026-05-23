package com.github.no_name_provided.avaritianeo_tweaks.common.block_entities;

import com.github.no_name_provided.avaritianeo_tweaks.common.blocks.ANTBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.github.no_name_provided.avaritianeo_tweaks.AvaritiaNeoTweaks.MODID;

public class ANTBlockEntities {
    public static DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(
            Registries.BLOCK_ENTITY_TYPE,
            MODID
    );
    
    @SuppressWarnings("DataFlowIssue")
    public static DeferredHolder<BlockEntityType<? extends BlockEntity>, BlockEntityType<FastNeutroniumCompressorBlockEntity>> FAST_NEUTRONIUM_COMPRESSOR = BLOCK_ENTITY_TYPES.register(
        "fast_neutronium_compressor",
            () -> BlockEntityType.Builder.of(
                            FastNeutroniumCompressorBlockEntity::new,
                            ANTBlocks.FAST_NEUTRONIUM_COMPRESSOR.get()
                    )
                    // Docs say this should be null
                    .build(null)
    );
}
