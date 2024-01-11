package com.silver2040.tntexpanded.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

public class BaseTntBlock extends TntBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;;
    public BaseTntBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.TNT));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));

    }


    @Override
    public BlockState getStateForPlacement(BlockPlaceContext p_48689_) {
        return (BlockState)this.defaultBlockState().setValue(FACING, p_48689_.getHorizontalDirection().getOpposite());
    }


    @Override
    public void onPlace(BlockState p_57466_, Level p_57467_, BlockPos p_57468_, BlockState p_57469_, boolean p_57470_) {
        super.onPlace(p_57466_, p_57467_, p_57468_, p_57469_, p_57470_);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }
    @Override
    public BlockState rotate(BlockState p_54125_, Rotation p_54126_) {
        return (BlockState)p_54125_.setValue(FACING, p_54126_.rotate((Direction)p_54125_.getValue(FACING)));
    }
    @Override
    public BlockState mirror(BlockState p_54122_, Mirror p_54123_) {
        return p_54122_.rotate(p_54123_.getRotation((Direction)p_54122_.getValue(FACING)));
    }


}
