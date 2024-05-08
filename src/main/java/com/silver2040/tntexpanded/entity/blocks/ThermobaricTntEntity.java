package com.silver2040.tntexpanded.entity.blocks;

import com.mojang.logging.LogUtils;
import com.silver2040.tntexpanded.registry.TntEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class ThermobaricTntEntity extends Entity implements TraceableEntity {

    private static final EntityDataAccessor<Integer> DATA_FUSE_ID;
    @javax.annotation.Nullable
    private LivingEntity owner;
    private boolean wasExploded;


    public ThermobaricTntEntity(Level p_32079_, double p_32080_, double p_32081_, double p_32082_, @Nullable LivingEntity p_32083_) {
        this(TntEntities.PRIMED_THERMOBARIC_TNT.get(), p_32079_);
        this.setPos(p_32080_, p_32081_, p_32082_);
        double $$5 = p_32079_.random.nextDouble() * 6.2831854820251465;
        this.setDeltaMovement(-Math.sin($$5) * 0.02, 0.20000000298023224, -Math.cos($$5) * 0.02);
        this.xo = p_32080_;
        this.yo = p_32081_;
        this.zo = p_32082_;
        this.owner = p_32083_;
        setFuse(100);
    }

    public ThermobaricTntEntity(EntityType<ThermobaricTntEntity> TntEntityEntityType, Level level) {
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
        this.level().explode(this, this.getX(), this.getY(0.0625), this.getZ(), 50f, Level.ExplosionInteraction.BLOCK);
        wasExploded = true;
        if (!level().isClientSide){
            backtrackAir(level(), this.getOnPos(), 100);
        }

    }
    public void backtrackAir(Level world, BlockPos startPos, int maxRadius) {
        Set<BlockPos> visited = new HashSet<>();
        Queue<BlockPos> toVisit = new LinkedList<>();
        int maxDamage = 40;

        toVisit.add(startPos);

        while (!toVisit.isEmpty()) {
            BlockPos current = toVisit.poll();
            if (!visited.contains(current) && current.distManhattan(startPos) <= maxRadius) {
                visited.add(current);
                BlockState state = world.getBlockState(current);

                if (state.isAir()) {
                    affectEntitiesAt(world, current, startPos, maxDamage);

                    for (Direction dir : Direction.values()) {
                        BlockPos next = current.relative(dir);
                        if (!visited.contains(next) && next.distManhattan(startPos) <= maxRadius) {
                            toVisit.add(next);
                        }
                    }
                }
            }
        }
    }
    private void affectEntitiesAt(Level world, BlockPos pos, BlockPos startPos, int maxDamage) {
        AABB area = new AABB(pos, pos.offset(1, 1, 1));
        for (Entity entity : world.getEntitiesOfClass(Entity.class, area)) {
            int distance = pos.distManhattan(startPos);
            float damage = Math.max(maxDamage - distance/2, 0);
            entity.hurt(damageSources().generic(), damage);
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
        DATA_FUSE_ID = SynchedEntityData.defineId(ThermobaricTntEntity.class, EntityDataSerializers.INT);
    }

    @Nullable
    @Override
    public Entity getOwner() {
        return null;
    }
}
