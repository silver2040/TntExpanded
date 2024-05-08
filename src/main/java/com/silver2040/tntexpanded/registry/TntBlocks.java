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
    public static final RegistryObject<Block> BREACHING_TNT = BLOCKS.register("breaching_tnt_block", BreachingTnt::new);
    public static final RegistryObject<Block> NETHER_TNT = BLOCKS.register("nether_tnt_block", NetherTnt::new);
    public static final RegistryObject<Block> CLUSTER_TNT = BLOCKS.register("cluster_tnt_block", ClusterTnt::new);
    public static final RegistryObject<Block> BOMBLET_TNT = BLOCKS.register("cluster_bomblet", BombletTnt::new);
    public static final RegistryObject<Block> CONCUSSIVE_TNT = BLOCKS.register("concussive_tnt_block", ConcussiveTnt::new);

    public static final RegistryObject<Block> VEGETATION_TNT = BLOCKS.register("vegetation_tnt_block", VegetationTnt::new);
    public static final RegistryObject<Block> THERMOBARIC_TNT = BLOCKS.register("thermobaric_tnt_block", ThermobaricTnt::new);
    public static final RegistryObject<Block> REVERSE_TNT = BLOCKS.register("reverse_tnt_block", ReverseTnt::new);







    //blockitems
    //public static final RegistryObject<BlockItem> TEST_TNT_ITEM = ITEMS.register("tester_tnt_block_item", () -> new BlockItem(TEST_TNT.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> COMPACT_TNT_ITEM = ITEMS.register("compact_tnt_block", () -> new BlockItem(COMPACT_TNT.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> TOXIC_TNT_ITEM = ITEMS.register("toxic_tnt_block", () -> new BlockItem(TOXIC_TNT.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> INCENDIARY_TNT_ITEM = ITEMS.register("incendiary_tnt_block", () -> new BlockItem(INCENDIARY_TNT.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> SHRAPNEL_TNT_ITEM = ITEMS.register("shrapnel_tnt_block", () -> new BlockItem(SHRAPNEL_TNT.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> STUN_TNT_ITEM = ITEMS.register("stun_tnt_block", () -> new BlockItem(STUN_TNT.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> CHEMICAL_TNT_ITEM = ITEMS.register("chemical_tnt_block", () -> new BlockItem(CHEMICAL_TNT.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> BREACHING_TNT_ITEM = ITEMS.register("breaching_tnt_block", () -> new BlockItem(BREACHING_TNT.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> NETHER_TNT_ITEM = ITEMS.register("nether_tnt_block", () -> new BlockItem(NETHER_TNT.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> CLUSTER_TNT_ITEM = ITEMS.register("cluster_tnt_block", () -> new BlockItem(CLUSTER_TNT.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> BOMBLET_TNT_ITEM = ITEMS.register("cluster_bomblet", () -> new BlockItem(BOMBLET_TNT.get(), new Item.Properties()));

    public static final RegistryObject<BlockItem> CONCUSSIVE_TNT_ITEM = ITEMS.register("concussive_tnt_block", () -> new BlockItem(CONCUSSIVE_TNT.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> VEGETATION_TNT_ITEM = ITEMS.register("vegetation_tnt_block", () -> new BlockItem(VEGETATION_TNT.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> THERMOBARIC_TNT_ITEM = ITEMS.register("thermobaric_tnt_block", () -> new BlockItem(THERMOBARIC_TNT.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> REVERSE_TNT_ITEM = ITEMS.register("reverse_tnt_block", () -> new BlockItem(REVERSE_TNT.get(), new Item.Properties()));

    public static void register(IEventBus bus){
        BLOCKS.register(bus);
        ITEMS.register(bus);
    }

}
