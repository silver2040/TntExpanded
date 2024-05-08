package com.silver2040.tntexpanded.event;

import com.silver2040.tntexpanded.registry.TntEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.SmokeParticle;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static com.silver2040.tntexpanded.TntExpanded.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ParticleEventBus {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        Minecraft.getInstance().particleEngine.register(TntEntities.GREEN_PARTICLE.get(), SmokeParticle.Provider::new);
    }
}
