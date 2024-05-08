package com.silver2040.tntexpanded.entity.blocks;

import com.mojang.logging.LogUtils;
import com.silver2040.tntexpanded.registry.TntEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SpongeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class NetherTntEntity extends Entity implements TraceableEntity {

    private static final EntityDataAccessor<Integer> DATA_FUSE_ID;
    @javax.annotation.Nullable
    private LivingEntity owner;
    private int effectDurationTicks;
    private boolean wasExploded;
    private Level level;
    private int effectInterval = 20;
    private int duration = 200;
    Random r = new Random();

    public NetherTntEntity(Level p_32079_, double p_32080_, double p_32081_, double p_32082_, @Nullable LivingEntity p_32083_) {
        this(TntEntities.PRIMED_NETHER_TNT.get(), p_32079_);
        this.setPos(p_32080_, p_32081_, p_32082_);
        double $$5 = p_32079_.random.nextDouble() * 6.2831854820251465;
        this.setDeltaMovement(-Math.sin($$5) * 0.02, 0.20000000298023224, -Math.cos($$5) * 0.02);
        this.setFuse(80);
        this.xo = p_32080_;
        this.yo = p_32081_;
        this.zo = p_32082_;
        this.owner = p_32083_;
        this.effectDurationTicks = 200;

    }

    public NetherTntEntity(EntityType<NetherTntEntity> compactTntEntityEntityType, Level level) {
        super(compactTntEntityEntityType, level);
        this.blocksBuilding = true;
        this.level = level;
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
                this.wasExploded = true;
            }
        } else {
            this.updateInWaterStateAndDoFluidPushing();
            if (this.level().isClientSide) {
                this.level().addParticle(ParticleTypes.SMOKE, this.getX(), this.getY() + 0.5, this.getZ(), 0.0, 0.0, 0.0);
            }
        }
        if (wasExploded){
            int radius = 5;
            if (!level().isClientSide) {
                BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
                BlockPos center = this.getOnPos();

                radius = 9;
                for (int x = -radius; x <= radius; x++) {
                    for (int y = -radius; y <= radius; y++) {
                        for (int z = -radius; z <= radius; z++) {
                            if (center.distSqr(center.offset(x, y, z)) <= radius * radius) {
                                applyRandomEffect(level(), center.offset(x, y, z));
                            }
                        }
                    }
                }
            }

            if (effectDurationTicks > 0) {

                AABB effectArea =
                        new AABB(this.getX() - radius, this.getY() - radius, this.getZ() - radius,
                                this.getX() + radius, this.getY() + radius, this.getZ() + radius);

                new Thread(() -> {
                    int timeLeft = duration;
                    while (timeLeft > 0) {
                        List<Entity> entitiesWithinRadius = level.getEntities(null, effectArea);
                        for (Entity entity : entitiesWithinRadius) {
                            if (entity instanceof LivingEntity) {
                                LivingEntity livingEntity = (LivingEntity) entity;
                                livingEntity.setRemainingFireTicks(200);

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
    private void applyRandomEffect(Level world, BlockPos pos) {
        BlockState currentState = world.getBlockState(pos);
        double chance = r.nextDouble(1);
       // LogUtils.getLogger().info(String.valueOf(chance));
        if (chance < 1.0 / 500.0) {
            world.setBlock(pos, Blocks.LAVA.defaultBlockState(), 3);
        }
        if (currentState.is(Blocks.GRASS_BLOCK) && chance < 1.0 / 3.0) {
            world.setBlock(pos, Blocks.DIRT.defaultBlockState(), 3);
        }
        if ((currentState.is(Blocks.DIRT) || currentState.is(Blocks.PODZOL) ||
                currentState.is(Blocks.DIRT_PATH) || currentState.is(Blocks.MYCELIUM)) && chance < 1) {
            world.setBlock(pos, Blocks.NETHERRACK.defaultBlockState(), 3);
        }
        if (currentState.is(Blocks.SAND) && chance < 1.0/2.0) {
            if (chance < 1.0/4.0){
                world.setBlock(pos, Blocks.SOUL_SAND.defaultBlockState(), 3);
            }else{
            world.setBlock(pos, Blocks.GLASS.defaultBlockState(), 3);
            }
        }
        if (currentState.is(Blocks.COBBLESTONE) && chance < 1.0/2.0) {
            world.setBlock(pos, Blocks.STONE.defaultBlockState(), 3);
        }
        if ((currentState.is(Blocks.STONE) || currentState.is(Blocks.DIORITE) || currentState.is(Blocks.ANDESITE) || currentState.is(Blocks.GRANITE))&& chance < 0.5){
            if (chance < 1.0/3.0){
                world.setBlock(pos, Blocks.BLACKSTONE.defaultBlockState(), 3);
            }else{
                world.setBlock(pos, Blocks.MAGMA_BLOCK.defaultBlockState(), 3);
            }
        }
        if (currentState.is(Blocks.WATER) ) {
            world.removeBlock(pos, false);
            world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
        }

        if (currentState.is(Blocks.ICE) || currentState.is(Blocks.PACKED_ICE) || currentState.is(Blocks.BLUE_ICE) || currentState.is(Blocks.FROSTED_ICE)){
            world.setBlock(pos, Blocks.WATER.defaultBlockState(), 3);
        }
    }

    protected void explode() {
        this.level().explode(this, this.getX(), this.getY(0.0625), this.getZ(), 7.0F,true, Level.ExplosionInteraction.BLOCK);

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
        DATA_FUSE_ID = SynchedEntityData.defineId(NetherTntEntity.class, EntityDataSerializers.INT);
    }

    @Nullable
    @Override
    public Entity getOwner() {
        return null;
    }
}
