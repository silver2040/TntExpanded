package com.silver2040.tntexpanded.entity.blocks;

import com.silver2040.tntexpanded.entity.item.LifespanArrowEntity;
import com.silver2040.tntexpanded.registry.TntEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.TntBlock;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ShrapnelTntEntity extends Entity implements TraceableEntity {

    private static final EntityDataAccessor<Integer> DATA_FUSE_ID;
    @javax.annotation.Nullable
    private LivingEntity owner;
    private final Map<Arrow, Integer> arrowMap = new HashMap<Arrow, Integer>();
    private boolean wasExploded;



    public ShrapnelTntEntity(Level p_32079_, double p_32080_, double p_32081_, double p_32082_, @Nullable LivingEntity p_32083_) {
        this(TntEntities.PRIMED_SHRAPNEL_TNT.get(), p_32079_);
        this.setPos(p_32080_, p_32081_, p_32082_);
        double $$5 = p_32079_.random.nextDouble() * 6.2831854820251465;
        this.setDeltaMovement(-Math.sin($$5) * 0.02, 0.20000000298023224, -Math.cos($$5) * 0.02);
        this.setFuse(80);
        this.xo = p_32080_;
        this.yo = p_32081_;
        this.zo = p_32082_;
        this.owner = p_32083_;
    }

    public ShrapnelTntEntity(EntityType<ShrapnelTntEntity> shrapnelTntEntityEntityType, Level level) {
        super(shrapnelTntEntityEntityType, level);
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
        this.level().explode(this, this.getX(), this.getY(0.0625), this.getZ(), 1.0F, Level.ExplosionInteraction.BLOCK);
        launchArrows(level(), this.blockPosition());
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
    public void launchArrows(Level world, BlockPos center) {
        final int numberOfArrows = 90;
        final double speed = 15.0;

        for (int i = 0; i < numberOfArrows; i++) {
            double phi = Math.acos(-1.0 + (2.0 * i) / numberOfArrows);
            double theta = Math.PI * (1 + Math.pow(5, 0.5)) * i;

            double x = Math.sin(phi) * Math.cos(theta);
            double y = Math.sin(phi) * Math.sin(theta);
            double z = Math.cos(phi);

            double magnitude = Math.sqrt(x * x + y * y + z * z);
            x = (x / magnitude) * speed;
            y = (y / magnitude) * speed;
            z = (z / magnitude) * speed;

            //LifespanArrowEntity arrow = new LifespanArrowEntity(TntEntities.LIFESPAN_ARROW_ENTITY.get(), world);
            Arrow arrow = new Arrow(EntityType.ARROW, world);
            arrow.setPos(center.getX() + 0.5, center.getY() + 0.5, center.getZ() + 0.5);
            arrow.setDeltaMovement(x, y, z);
            arrow.addTag("ShrapnelArrow");
            arrowMap.put(arrow, (int) world.getGameTime());
            world.addFreshEntity(arrow);

        }
    }



    static{
        DATA_FUSE_ID = SynchedEntityData.defineId(ShrapnelTntEntity.class, EntityDataSerializers.INT);
    }

    @Nullable
    @Override
    public Entity getOwner() {
        return null;
    }
}
