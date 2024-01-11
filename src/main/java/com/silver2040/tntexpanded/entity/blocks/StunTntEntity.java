package com.silver2040.tntexpanded.entity.blocks;


import com.silver2040.tntexpanded.registry.TntEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class StunTntEntity extends Entity implements TraceableEntity {

    private static final EntityDataAccessor<Integer> DATA_FUSE_ID;
    @javax.annotation.Nullable
    private LivingEntity owner;
    private int effectDurationTicks;
    private boolean wasExploded;
    private Level level;
    private int effectInterval = 20;
    private int duration = 20;

    public StunTntEntity(Level p_32079_, double p_32080_, double p_32081_, double p_32082_, @Nullable LivingEntity p_32083_) {
        this(TntEntities.PRIMED_STUN_TNT.get(), p_32079_);
        this.setPos(p_32080_, p_32081_, p_32082_);
        double $$5 = p_32079_.random.nextDouble() * 6.2831854820251465;
        this.setDeltaMovement(-Math.sin($$5) * 0.02, 0.20000000298023224, -Math.cos($$5) * 0.02);
        this.setFuse(40);
        this.xo = p_32080_;
        this.yo = p_32081_;
        this.zo = p_32082_;
        this.owner = p_32083_;
        this.effectDurationTicks = 100;
    }

    public StunTntEntity(EntityType<StunTntEntity> stunTntEntityEntityType, Level level) {
        super(stunTntEntityEntityType, level);
        this.blocksBuilding = true;
    }
    protected Entity.MovementEmission getMovementEmission() {
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
        if (wasExploded) {
            if (effectDurationTicks > 0) {

                int maxRad = 7;
                for (double radius = 1; radius < maxRad; radius++) {
                    AABB effectArea =
                            new AABB(this.getX() - radius, this.getY() - radius, this.getZ() - radius,
                                    this.getX() + radius, this.getY() + radius, this.getZ() + radius);

                    double finalRadius = radius;
                    new Thread(() -> {
                        int timeLeft = duration;
                        while (timeLeft > 0) {
                            List<Entity> entitiesWithinRadius = level().getEntities(null, effectArea);
                            for (Entity entity : entitiesWithinRadius) {
                                if (entity instanceof LivingEntity) {
                                    LivingEntity livingEntity = (LivingEntity) entity;
                                    livingEntity.addEffect(new MobEffectInstance(TntEntities.DAZZLE_EFFECT.get(), (int) (duration * (Math.abs(finalRadius - 7)))));
                                }
                            }


                            try {
                                Thread.sleep(effectInterval * 50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            timeLeft -= effectInterval;
                        }
                    }).start();

                    effectDurationTicks--;
                }
            }
        }

    }

    protected void explode() {
        this.level().explode(this, this.getX(), this.getY(0.0625), this.getZ(), 5.0F, Level.ExplosionInteraction.BLOCK);
        wasExploded = true;
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
        DATA_FUSE_ID = SynchedEntityData.defineId(StunTntEntity.class, EntityDataSerializers.INT);
    }

    @Nullable
    @Override
    public Entity getOwner() {
        return null;
    }
}
