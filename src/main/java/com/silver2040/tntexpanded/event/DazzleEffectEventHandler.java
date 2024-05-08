package com.silver2040.tntexpanded.event;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.logging.LogUtils;
import com.silver2040.tntexpanded.TntExpanded;
import com.silver2040.tntexpanded.effects.DazzleEffect;
import com.silver2040.tntexpanded.registry.TntEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
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
import org.joml.Matrix4f;

import java.awt.*;

@Mod.EventBusSubscriber(modid = TntExpanded.MODID, value = Dist.CLIENT)
public class DazzleEffectEventHandler {
    static Window w;
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
        RenderSystem.setShaderColor(256f, 256f, 256f, calculateAlpha(durationTicks));
        w = Minecraft.getInstance().getWindow();
        int screenWidth = w.getGuiScaledWidth();
        int screenHeight = w.getGuiScaledHeight();
        drawRect(ps, 0, 0, screenWidth, screenHeight);

        RenderSystem.disableBlend();

    }
    private static float calculateAlpha(int duration) {
        final int fadeOutTime = 100;
        return Math.min(duration / (float) fadeOutTime, 1.0f);
    }
    public static void drawRect(PoseStack poseStack, int x1, int y1, int x2, int y2) {
        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        BufferBuilder buffer = Tesselator.getInstance().getBuilder();
        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        Matrix4f matrix = poseStack.last().pose();

        buffer.vertex(matrix, x1, y2, 0.0F).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        buffer.vertex(matrix, x2, y2, 0.0F).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        buffer.vertex(matrix, x2, y1, 0.0F).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        buffer.vertex(matrix, x1, y1, 0.0F).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();

        Tesselator.getInstance().end();
    }


}
