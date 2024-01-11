package com.silver2040.tntexpanded.entity.item;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;

public class LifespanArrowEntity extends Arrow {
    private int lifespan;

    public LifespanArrowEntity(EntityType<? extends Arrow> entityType, Level world) {
        super(entityType, world);
        this.lifespan = 1200;
        pickup = Pickup.DISALLOWED;
    }


    public void setLifespan(int ticks) {
        this.lifespan = ticks;
    }


    @Override
    public void tick() {
        super.tick();

        if (lifespan > 0) {
            lifespan--;
        }

        if (lifespan <= 0) {
            this.remove(RemovalReason.DISCARDED);
        }
    }
}
