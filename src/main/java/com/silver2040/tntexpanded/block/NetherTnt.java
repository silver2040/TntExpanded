package com.silver2040.tntexpanded.block;

import com.mojang.logging.LogUtils;
import com.silver2040.tntexpanded.entity.blocks.NetherTntEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.gameevent.GameEvent;

import javax.annotation.Nullable;

public class NetherTnt extends BaseTntBlock{
    NetherTntEntity NetherTnt;
    NetherTntEntity primedtnt;
    public static BooleanProperty LIT = BooleanProperty.create("lit");

    public NetherTnt() {
        super();
        this.registerDefaultState(this.stateDefinition.any().setValue(LIT, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT);
        super.createBlockStateDefinition(builder);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext p_48689_) {
        return (BlockState)this.defaultBlockState().setValue(FACING, p_48689_.getHorizontalDirection().getOpposite()).setValue(LIT, true);
    }

    @Override
    public void onCaughtFire(BlockState state, Level world, BlockPos pos, @org.jetbrains.annotations.Nullable Direction face, @org.jetbrains.annotations.Nullable LivingEntity igniter) {
        world.setBlock(pos, world.getBlockState(pos).setValue(LIT, true), 3);
        explode(world, pos, igniter);
    }


    public void explode(Level world, BlockPos pos, @Nullable LivingEntity p_57439_) {
        if (!world.isClientSide) {
            world.setBlock(pos, world.getBlockState(pos).setValue(LIT, true), 3);
            NetherTnt = new NetherTntEntity(world, (double)pos.getX() + 0.5, (double)pos.getY(), (double)pos.getZ() + 0.5, p_57439_);
            world.addFreshEntity(NetherTnt);
            world.playSound((Player)null, NetherTnt.getX(), NetherTnt.getY(), NetherTnt.getZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
            world.gameEvent(p_57439_, GameEvent.PRIME_FUSE, pos);
        }

    }
    @Override
    public void wasExploded(Level world, BlockPos pos, Explosion p_57443_) {
        if (!world.isClientSide) {
            primedtnt = new NetherTntEntity(world, (double)pos.getX() + 0.5, (double)pos.getY(), (double)pos.getZ() + 0.5, p_57443_.getIndirectSourceEntity());
            int i = primedtnt.getFuse();
            primedtnt.setFuse((short)(world.random.nextInt(i / 4) + i / 8));
            world.addFreshEntity(primedtnt);
        }
    }

    @Override
    public BlockState rotate(BlockState p_54125_, Rotation p_54126_) {
        return super.rotate(p_54125_, p_54126_);
    }

    @Override
    public BlockState mirror(BlockState p_54122_, Mirror p_54123_) {
        return super.mirror(p_54122_, p_54123_);
    }
}


