package com.silver2040.tntexpanded.entity.blocks;

import com.mojang.logging.LogUtils;
import com.silver2040.tntexpanded.registry.TntEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class VegetationTntEntity extends Entity implements TraceableEntity {

    private static final EntityDataAccessor<Integer> DATA_FUSE_ID;
    @javax.annotation.Nullable
    private LivingEntity owner;
    private boolean wasExploded;


    public VegetationTntEntity(Level p_32079_, double p_32080_, double p_32081_, double p_32082_, @Nullable LivingEntity p_32083_) {
        this(TntEntities.PRIMED_VEGETATION_TNT.get(), p_32079_);
        this.setPos(p_32080_, p_32081_, p_32082_);
        double $$5 = p_32079_.random.nextDouble() * 6.2831854820251465;
        this.setDeltaMovement(-Math.sin($$5) * 0.02, 0.20000000298023224, -Math.cos($$5) * 0.02);
        this.xo = p_32080_;
        this.yo = p_32081_;
        this.zo = p_32082_;
        this.owner = p_32083_;
        setFuse(20);
    }

    public VegetationTntEntity(EntityType<VegetationTntEntity> TntEntityEntityType, Level level) {
        super(TntEntityEntityType, level);
        this.blocksBuilding = true;
    }
    protected MovementEmission getMovementEmission() {
        return MovementEmission.NONE;
    }
    public boolean isPickable() {
        return !this.isRemoved();
    }

    public void tick() {
        if (!this.isNoGravity()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.04, 0.0));
        }

        this.move(MoverType.SELF, this.getDeltaMovement());
        this.setDeltaMovement(this.getDeltaMovement().scale(0.98));
        if (this.onGround()) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.7, -0.5, 0.7));
        }

        int $$0 = this.getFuse() - 1;
        this.setFuse($$0);
        if ($$0 <= 0) {
            this.discard();
            if (!this.level().isClientSide) {
                this.explode();
            }
        } else {
            this.updateInWaterStateAndDoFluidPushing();
            if (this.level().isClientSide) {
                this.level().addParticle(ParticleTypes.SMOKE, this.getX(), this.getY() + 0.5, this.getZ(), 0.0, 0.0, 0.0);
            }
        }

    }

    protected void explode() {
        this.level().explode(this, this.getX(), this.getY(0.0625), this.getZ(), 0.00001F, Level.ExplosionInteraction.BLOCK);
        wasExploded = true;
        BlockPos center = this.blockPosition();
        int radius = 6;
        for (BlockPos pos : BlockPos.betweenClosed(center.offset(-radius, -radius, -radius), center.offset(radius, radius, radius))) {
            if (level().isLoaded(pos)) {
                BlockState state = level().getBlockState(pos);
                if (state.getBlock() instanceof GrassBlock || state.getBlock() instanceof CropBlock) {
                    state.randomTick((ServerLevel) this.level(), pos, level().random);
                    ((BonemealableBlock) state.getBlock()).performBonemeal((ServerLevel) level(), level().random, pos, state);
                    if (level().random.nextFloat() < 0.25f){
                        if (level().getBlockState(pos.above()).canBeReplaced()) {
                            level().setBlock(pos.above(), Blocks.OAK_SAPLING.defaultBlockState(), 3);
                        }
                    }
                }

                if (state.getBlock() instanceof SaplingBlock || state.getBlock() instanceof CropBlock) {
                    for (int i = 0; i < 200; i++) {
                        if (state.getBlock() instanceof SaplingBlock)
                            ((SaplingBlock) state.getBlock()).performBonemeal((ServerLevel) level(), level().random, pos, state);
                        if (state.getBlock() instanceof CropBlock)
                            ((CropBlock) state.getBlock()).performBonemeal((ServerLevel) level(), level().random, pos, state);
                    }
                }

                if (state.getBlock() == Blocks.FIRE) {
                    level().removeBlock(pos, false);
                }
            }
        }
    }
    public void setFuse(int p_32086_) {
        this.entityData.set(DATA_FUSE_ID, p_32086_);
    }
    public int getFuse() {
        return (Integer)this.entityData.get(DATA_FUSE_ID);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DATA_FUSE_ID, 20);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
    }


    static{
        DATA_FUSE_ID = SynchedEntityData.defineId(VegetationTntEntity.class, EntityDataSerializers.INT);
    }

    @Nullable
    @Override
    public Entity getOwner() {
        return null;
    }
}
