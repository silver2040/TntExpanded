package com.silver2040.tntexpanded;

import com.mojang.logging.LogUtils;
import com.silver2040.tntexpanded.entity.blocks.ToxicTntEntity;
import com.silver2040.tntexpanded.entity.client.*;
import com.silver2040.tntexpanded.entity.item.LifespanArrowEntity;
import com.silver2040.tntexpanded.registry.TntBlocks;
import com.silver2040.tntexpanded.registry.TntEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import static com.silver2040.tntexpanded.registry.TntBlocks.COMPACT_TNT;
import static com.silver2040.tntexpanded.registry.TntBlocks.COMPACT_TNT_ITEM;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TntExpanded.MODID)
public class TntExpanded
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "tntexpanded";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    // Create a Deferred Register to hold Items which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    // Creates a new Block with the id "examplemod:example_block", combining the namespace and path
    public static final RegistryObject<Block> EXAMPLE_BLOCK = BLOCKS.register("example_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)));
    // Creates a new BlockItem with the id "examplemod:example_block", combining the namespace and path
    public static final RegistryObject<Item> EXAMPLE_BLOCK_ITEM = ITEMS.register("example_block", () -> new BlockItem(EXAMPLE_BLOCK.get(), new Item.Properties()));

    // Creates a new food item with the id "examplemod:example_id", nutrition 1 and saturation 2
    public static final RegistryObject<Item> EXAMPLE_ITEM = ITEMS.register("example_item", () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .alwaysEat().nutrition(1).saturationMod(2f).build())));

    // Creates a creative tab with the id "examplemod:example_tab" for the example item, that is placed after the combat tab

    public static final RegistryObject<CreativeModeTab> TNT_TAB = CREATIVE_MODE_TABS.register("tnt_tab", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> COMPACT_TNT_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(EXAMPLE_ITEM.get());
                output.accept(COMPACT_TNT.get());
                output.accept(TntBlocks.TOXIC_TNT.get());
                output.accept(TntBlocks.INCENDIARY_TNT.get());
                output.accept(TntBlocks.SHRAPNEL_TNT.get());
                output.accept(TntBlocks.STUN_TNT.get());
                output.accept(TntBlocks.CHEMICAL_TNT.get());
                output.accept(TntBlocks.BREACHING_TNT.get());
                output.accept(TntBlocks.NETHER_TNT.get());
                output.accept(TntBlocks.CLUSTER_TNT.get());
                output.accept(TntBlocks.CONCUSSIVE_TNT.get());
                output.accept(TntBlocks.VEGETATION_TNT.get());
                output.accept(TntBlocks.THERMOBARIC_TNT.get());

                // Add the example item to the tab. For your own tabs, this method is preferred over the event
            }).build());

    public TntExpanded()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        TntBlocks.register(modEventBus);
        TntEntities.register(modEventBus);
        // Register the Deferred Register to the mod event bus so blocks get registered
        BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ITEMS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        CREATIVE_MODE_TABS.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS)
            event.accept(EXAMPLE_BLOCK_ITEM);
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {

            EntityRenderers.register(TntEntities.PRIMED_COMPACT_TNT.get(), CompactTntRenderer::new);
            EntityRenderers.register(TntEntities.PRIMED_TOXIC_TNT.get(), ToxicTntRenderer::new);
            EntityRenderers.register(TntEntities.PRIMED_INCENDIARY_TNT.get(), IncendiaryTntRenderer::new);
            EntityRenderers.register(TntEntities.PRIMED_SHRAPNEL_TNT.get(), ShrapnelTntRenderer::new);
            EntityRenderers.register(TntEntities.LIFESPAN_ARROW_ENTITY.get(), LifeSpanArrowRenderer::new);
            EntityRenderers.register(TntEntities.PRIMED_STUN_TNT.get(), StunTntRenderer::new);
            EntityRenderers.register(TntEntities.PRIMED_CHEMICAL_TNT.get(), ChemicalTntRenderer::new);
            EntityRenderers.register(TntEntities.PRIMED_BREACHING_TNT.get(), BreachingTntRenderer::new);
            EntityRenderers.register(TntEntities.PRIMED_NETHER_TNT.get(), NetherTntRenderer::new);
            EntityRenderers.register(TntEntities.PRIMED_CLUSTER_TNT.get(), ClusterTntRenderer::new);
            EntityRenderers.register(TntEntities.PRIMED_BOMBLET_TNT.get(), BombletTntRenderer::new);
            EntityRenderers.register(TntEntities.PRIMED_CONCUSSIVE_TNT.get(), ConcussiveTntRenderer::new);
            EntityRenderers.register(TntEntities.PRIMED_VEGETATION_TNT.get(), VegetationTntRenderer::new);
            EntityRenderers.register(TntEntities.PRIMED_THERMOBARIC_TNT.get(), ThermobaricTntRenderer::new);
            EntityRenderers.register(TntEntities.PRIMED_REVERSE_TNT.get(), ReverseTntRenderer::new);


            //EntityRenderers.register(EntityType.ARROW, LifeSpanArrowRenderer::new);


            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
