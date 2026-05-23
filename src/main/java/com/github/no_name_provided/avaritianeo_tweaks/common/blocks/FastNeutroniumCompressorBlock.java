package com.github.no_name_provided.avaritianeo_tweaks.common.blocks;

import com.github.no_name_provided.avaritianeo_tweaks.common.block_entities.FastNeutroniumCompressorBlockEntity;
import com.mojang.serialization.MapCodec;
import net.byAqua3.avaritia.block.BlockMachine;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault @ParametersAreNonnullByDefault
public class FastNeutroniumCompressorBlock extends BaseEntityBlock {
    
    protected FastNeutroniumCompressorBlock(Properties properties) {
        super(properties);
    }
    
    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return MapCodec.unit(this);
    }
    
    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        
        return new FastNeutroniumCompressorBlockEntity(pos, state);
    }
    
    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide()) {
            
            return InteractionResult.SUCCESS;
        } else {
            MenuProvider menuProvider = getMenuProvider(state, level, pos);
            if (menuProvider instanceof FastNeutroniumCompressorBlockEntity) {
                player.openMenu(menuProvider, pos);
                
                return InteractionResult.SUCCESS;
            }
            
            return InteractionResult.FAIL;
        }
    }
    
    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        
        return defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite());
    }
    
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockMachine.ACTIVE, BlockStateProperties.HORIZONTAL_FACING);
    }
    
    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }
    
    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if (!level.isClientSide()) {
            
            return (level1, pos, state1, blockEntity) -> {
                if (blockEntity instanceof FastNeutroniumCompressorBlockEntity fastNeutroniumCompressorBlockEntity) {
                    FastNeutroniumCompressorBlockEntity.tick(level1, pos, state1, fastNeutroniumCompressorBlockEntity);
                }
            };
        } else {
            
            return null;
        }
    }
}
