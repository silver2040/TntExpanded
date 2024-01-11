package com.silver2040.tntexpanded.registry;

import com.silver2040.tntexpanded.TntExpanded;
import com.silver2040.tntexpanded.block.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TntBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, TntExpanded.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TntExpanded.MODID);


    //blocks
    //public static final RegistryObject<Block> TEST_TNT = BLOCKS.register("tester_tnt_block", TestTnt::new);
    public static final RegistryObject<Block> COMPACT_TNT = BLOCKS.register("compact_tnt_block", CompactTnt::new);
    public static final RegistryObject<Block> TOXIC_TNT = BLOCKS.register("toxic_tnt_block", ToxicTnt::new);
    public static final RegistryObject<Block> INCENDIARY_TNT = BLOCKS.register("incendiary_tnt_block", IncendiaryTnt::new);
    public static final RegistryObject<Block> SHRAPNEL_TNT = BLOCKS.register("shrapnel_tnt_block", ShrapnelTnt::new);
    public static final RegistryObject<Block> STUN_TNT = BLOCKS.register("stun_tnt_block", StunTnt::new);
    public static final RegistryObject<Block> CHEMICAL_TNT = BLOCKS.register("chemical_tnt_block", ChemicalTnt::new);







    //blockitems
    //public static final RegistryObject<BlockItem> TEST_TNT_ITEM = ITEMS.register("tester_tnt_block_item", () -> new BlockItem(TEST_TNT.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> COMPACT_TNT_ITEM = ITEMS.register("compact_tnt_block", () -> new BlockItem(COMPACT_TNT.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> TOXIC_TNT_ITEM = ITEMS.register("toxic_tnt_block", () -> new BlockItem(TOXIC_TNT.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> INCENDIARY_TNT_ITEM = ITEMS.register("incendiary_tnt_block", () -> new BlockItem(INCENDIARY_TNT.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> SHRAPNEL_TNT_ITEM = ITEMS.register("shrapnel_tnt_block", () -> new BlockItem(SHRAPNEL_TNT.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> STUN_TNT_ITEM = ITEMS.register("stun_tnt_block", () -> new BlockItem(STUN_TNT.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> CHEMICAL_TNT_ITEM = ITEMS.register("chemical_tnt_block", () -> new BlockItem(CHEMICAL_TNT.get(), new Item.Properties()));


    public static void register(IEventBus bus){
        BLOCKS.register(bus);
        ITEMS.register(bus);
    }

}
