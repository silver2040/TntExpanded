package com.silver2040.tntexpanded.block;

import com.silver2040.tntexpanded.entity.blocks.BreachingTntEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.gameevent.GameEvent;

import javax.annotation.Nullable;

public class BreachingTnt extends BaseTntBlock{
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    static BreachingTntEntity primedBreachExp;
    static BreachingTntEntity primedtnt;
    Direction d;

    public BreachingTnt() {
        super();
        this.d = this.asBlock().defaultBlockState().getValue(HorizontalDirectionalBlock.FACING);
    }

    @Override
    public void onCaughtFire(BlockState state, Level world, BlockPos pos, @org.jetbrains.annotations.Nullable Direction face, @org.jetbrains.annotations.Nullable LivingEntity igniter) {
        explode(world, pos, igniter, state.getValue(HorizontalDirectionalBlock.FACING));

    }


    public static void explode(Level p_57437_, BlockPos p_57438_, @Nullable LivingEntity p_57439_, Direction d) {
        if (!p_57437_.isClientSide) {
            primedBreachExp = new BreachingTntEntity(p_57437_, (double)p_57438_.getX() + 0.5, (double)p_57438_.getY(), (double)p_57438_.getZ() + 0.5, p_57439_, d);
            p_57437_.addFreshEntity(primedBreachExp);
            p_57437_.playSound((Player)null, primedBreachExp.getX(), primedBreachExp.getY(), primedBreachExp.getZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
            p_57437_.gameEvent(p_57439_, GameEvent.PRIME_FUSE, p_57438_);
            
        }

    }


    @Override
    public void wasExploded(Level world, BlockPos pos, Explosion p_57443_) {
        if (!world.isClientSide) {
            primedtnt = new BreachingTntEntity(world, (double)pos.getX() + 0.5, (double)pos.getY(), (double)pos.getZ() + 0.5, p_57443_.getIndirectSourceEntity(), d);
            int i = primedtnt.getFuse();
            primedtnt.setFuse((short)(world.random.nextInt(i / 4) + i / 8));
            world.addFreshEntity(primedtnt);
        }

    }
}
