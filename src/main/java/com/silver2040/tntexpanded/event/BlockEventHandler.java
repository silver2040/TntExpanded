package com.silver2040.tntexpanded.event;

import com.silver2040.tntexpanded.block.BaseTntBlock;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.TntBlock;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class BlockEventHandler {
    @SubscribeEvent
    public static void onBlockPlaced(BlockEvent.EntityPlaceEvent event) {
        if(event.getPlacedBlock().getBlock() instanceof BaseTntBlock){
            if (event.getEntity() instanceof Player){

            }
        }


    }
}
