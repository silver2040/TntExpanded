package com.silver2040.tntexpanded.event;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import com.silver2040.tntexpanded.TntExpanded;
import com.silver2040.tntexpanded.effects.DazzleEffect;
import com.silver2040.tntexpanded.registry.TntEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TntExpanded.MODID, value = Dist.CLIENT)
public class DazzleEffectEventHandler {
    private static final ResourceLocation resLoc = new ResourceLocation(TntExpanded.MODID,"textures/screen/flash_screen.png");



    @SubscribeEvent
    public static void playSoundEvent(PlaySoundEvent pse){
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.hasEffect(TntEntities.DAZZLE_EFFECT.get())){
            pse.setSound(null);
        }
    }
    public static void onKeyInput(MovementInputUpdateEvent e){
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.hasEffect(TntEntities.DAZZLE_EFFECT.get())){

        }
    }
    public static void consumeEvent(PlayerInteractEvent.RightClickItem pie){

    }
    @SubscribeEvent
    public static void potionRemoveEffect(MobEffectEvent.Remove mee){
    if (mee.getEffect() != null && mee.getEffect().equals(TntEntities.DAZZLE_EFFECT.get())){
            mee.setCanceled(true);
        }
    }
    @SubscribeEvent
    public static void potionAddEvent(MobEffectEvent.Added e){
        if (e.getEffectInstance().getEffect().equals(TntEntities.DAZZLE_EFFECT.get())){
            if (Minecraft.getInstance().level != null) {
            }
        }
    }
    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiOverlayEvent e){
        if (Minecraft.getInstance().level != null) {
            if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.hasEffect(TntEntities.DAZZLE_EFFECT.get())) {
                renderOverlay(e.getGuiGraphics().pose(), e.getGuiGraphics(), Minecraft.getInstance().player.getEffect(TntEntities.DAZZLE_EFFECT.get()).getDuration());
            }
        }
    }

    public static void renderOverlay(PoseStack ps, GuiGraphics g, int durationTicks){
        Minecraft mc = Minecraft.getInstance();
        mc.getTextureManager().bindForSetup(resLoc);
        RenderSystem.enableBlend();
        g.blit(resLoc,0, 0, 0, 0, mc.getWindow().getGuiScaledWidth(), mc.getWindow().getGuiScaledHeight(), 256, 256);
        RenderSystem.disableBlend();

    }
    private static float calculateAlpha(int duration) {
        final int fadeOutTime = 40;
        return Math.min(duration / (float) fadeOutTime, 1.0f);
    }


}
