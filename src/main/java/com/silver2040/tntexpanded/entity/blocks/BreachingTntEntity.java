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
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.Nullable;



public class BreachingTntEntity extends Entity implements TraceableEntity {

    private static final EntityDataAccessor<Integer> DATA_FUSE_ID;
    @javax.annotation.Nullable
    private LivingEntity owner;
    private boolean wasExploded;
    public Direction d;


    @Override
    public Vec2 getRotationVector() {
        return super.getRotationVector();
    }

    public BreachingTntEntity(Level p_32079_, double p_32080_, double p_32081_, double p_32082_, @Nullable LivingEntity p_32083_, Direction d) {
        this(TntEntities.PRIMED_BREACHING_TNT.get(), p_32079_);
        this.setPos(p_32080_, p_32081_, p_32082_);
        double $$5 = p_32079_.random.nextDouble() * 6.2831854820251465;
        this.setDeltaMovement(-Math.sin($$5) * 0.02, 0.20000000298023224, -Math.cos($$5) * 0.02);
        this.setFuse(10);
        this.xo = p_32080_;
        this.yo = p_32081_;
        this.zo = p_32082_;
        this.owner = p_32083_;
        this.d = d;
        //LogUtils.getLogger().info(getDirection().getName());
        LogUtils.getLogger().info(d.getName());

    }

    public BreachingTntEntity(EntityType<BreachingTntEntity> breachingTntEntityEntityType, Level level) {
        super(breachingTntEntityEntityType, level);
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
                wasExploded = true;

            }
        } else {
            this.updateInWaterStateAndDoFluidPushing();
            if (this.level().isClientSide) {
                this.level().addParticle(ParticleTypes.SMOKE, this.getX(), this.getY() + 0.5, this.getZ(), 0.0, 0.0, 0.0);
            }
        }

    }

    protected void explode() {
        float range = 2.9f;
        float explosionPower = 6.5f;
        float debuff = 6f;
        switch (d.getName()){
            case "north":{
                this.level().explode(this, this.getX(), this.getY(0.0625), this.getZ(), explosionPower-debuff, Level.ExplosionInteraction.BLOCK);
                this.level().explode(this, this.getX(), this.getY(0.0625), this.getZ(), explosionPower-debuff, Level.ExplosionInteraction.BLOCK);
                this.level().explode(this, this.getX(), this.getY(0.0625), this.getZ(), explosionPower-debuff, Level.ExplosionInteraction.BLOCK);
                this.level().explode(this, this.getX(), this.getY(0.0625), this.getZ()+range, explosionPower, Level.ExplosionInteraction.BLOCK);
                break;
            }
            case "south":{
                this.level().explode(this, this.getX(), this.getY(0.0625), this.getZ(), explosionPower-debuff, Level.ExplosionInteraction.BLOCK);
                this.level().explode(this, this.getX(), this.getY(0.0625), this.getZ(), explosionPower-debuff, Level.ExplosionInteraction.BLOCK);
                this.level().explode(this, this.getX(), this.getY(0.0625), this.getZ(), explosionPower-debuff, Level.ExplosionInteraction.BLOCK);
                this.level().explode(this, this.getX(), this.getY(0.0625), this.getZ()-range, explosionPower, Level.ExplosionInteraction.BLOCK);
                break;
            }
            case "east":{
                this.level().explode(this, this.getX(), this.getY(0.0625), this.getZ(), explosionPower-debuff, Level.ExplosionInteraction.BLOCK);
                this.level().explode(this, this.getX(), this.getY(0.0625), this.getZ(), explosionPower-debuff, Level.ExplosionInteraction.BLOCK);
                this.level().explode(this, this.getX(), this.getY(0.0625), this.getZ(), explosionPower-debuff, Level.ExplosionInteraction.BLOCK);
                this.level().explode(this, this.getX()-range, this.getY(0.0625), this.getZ(), explosionPower, Level.ExplosionInteraction.BLOCK);
                break;
            }
            case "west":{
                this.level().explode(this, this.getX(), this.getY(0.0625), this.getZ(), explosionPower-debuff, Level.ExplosionInteraction.BLOCK);
                this.level().explode(this, this.getX(), this.getY(0.0625), this.getZ(), explosionPower-debuff, Level.ExplosionInteraction.BLOCK);
                this.level().explode(this, this.getX(), this.getY(0.0625), this.getZ(), explosionPower-debuff, Level.ExplosionInteraction.BLOCK);
                this.level().explode(this, this.getX()+range, this.getY(0.0625), this.getZ(), explosionPower, Level.ExplosionInteraction.BLOCK);
                break;
            }

        }
       // this.level().explode(this, this.getX(), this.getY(0.0625), this.getZ(), 1.0F, Level.ExplosionInteraction.BLOCK);
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
        DATA_FUSE_ID = SynchedEntityData.defineId(BreachingTntEntity.class, EntityDataSerializers.INT);
    }

    @Nullable
    @Override
    public Entity getOwner() {
        return null;
    }
}
