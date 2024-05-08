package com.silver2040.tntexpanded.entity.blocks;

import com.silver2040.tntexpanded.block.BombletTnt;
import com.silver2040.tntexpanded.registry.TntEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

import static com.silver2040.tntexpanded.registry.TntEntities.PRIMED_SHRAPNEL_TNT;

public class ClusterTntEntity extends Entity implements TraceableEntity {

    private static final EntityDataAccessor<Integer> DATA_FUSE_ID;
    public static int numberOfTnt = 40;
    static Random r = new Random();
    @javax.annotation.Nullable
    private LivingEntity owner;

    public ClusterTntEntity(Level p_32079_, double p_32080_, double p_32081_, double p_32082_, @Nullable LivingEntity p_32083_) {
        this(TntEntities.PRIMED_CLUSTER_TNT.get(), p_32079_);
        this.setPos(p_32080_, p_32081_, p_32082_);
        double $$5 = p_32079_.random.nextDouble() * 6.2831854820251465;
        //this.setDeltaMovement(-Math.sin($$5) * 0.02, 0.20000000298023224, -Math.cos($$5) * 0.02);
        this.xo = p_32080_;
        this.yo = p_32081_;
        this.zo = p_32082_;
        this.owner = p_32083_;
        setFuse(80);
    }

    public ClusterTntEntity(EntityType<ClusterTntEntity> clusterTntEntityEntityType, Level level) {
        super(clusterTntEntityEntityType, level);
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
        //this.setDeltaMovement(this.getDeltaMovement().scale(0.98));
        if (this.onGround()) {
            //this.setDeltaMovement(this.getDeltaMovement().multiply(0.7, -0.5, 0.7));
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
        launchTnt(level(), new BlockPos((int) this.getX(), (int) this.getY(), (int) this.getZ()));
        this.level().explode(this, this.getX(), this.getY(0.0625), this.getZ(), 4.0F, Level.ExplosionInteraction.BLOCK);

    }

    public static void launchTnt(Level world, BlockPos launchPoint) {
        for (int i = 0; i < numberOfTnt; i++) {
            double yaw = r.nextDouble(1) * 360.0;
            double pitch = r.nextDouble(1) * 180.0 - 90.0;
            Vec3 direction = Vec3.directionFromRotation((float) pitch, (float) yaw);
            spawnTnt(world, launchPoint, direction);
        }
    }
    private static void spawnTnt(Level world, BlockPos pos, Vec3 direction) {
        BombletTntEntity tnt = new BombletTntEntity(world, pos.getX(), pos.getY()+1, pos.getZ(), null);
        tnt.setDeltaMovement(direction.scale(.7));
        world.addFreshEntity(tnt);
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
        DATA_FUSE_ID = SynchedEntityData.defineId(ClusterTntEntity.class, EntityDataSerializers.INT);
    }

    @Nullable
    @Override
    public Entity getOwner() {
        return null;
    }
}
