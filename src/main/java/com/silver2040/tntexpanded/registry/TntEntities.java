package com.silver2040.tntexpanded.registry;


import com.silver2040.tntexpanded.effects.DazzleEffect;
import com.silver2040.tntexpanded.entity.blocks.*;
import com.silver2040.tntexpanded.entity.item.LifespanArrowEntity;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.silver2040.tntexpanded.TntExpanded.MODID;

public class TntEntities {

   public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MODID);
   public static final DeferredRegister<MobEffect> POTION_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, MODID);
   public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MODID);

   public static final RegistryObject<EntityType<CompactTntEntity>> PRIMED_COMPACT_TNT =
           ENTITY_TYPES.register("primed_compact_tnt", () -> EntityType.Builder.<CompactTntEntity>of(CompactTntEntity::new, MobCategory.MISC)
                   .sized(1f, 1f).build("primed_compact_tnt"));
   public static final RegistryObject<EntityType<ToxicTntEntity>> PRIMED_TOXIC_TNT =
           ENTITY_TYPES.register("primed_toxic_tnt", () -> EntityType.Builder.<ToxicTntEntity>of(ToxicTntEntity::new, MobCategory.MISC)
                   .sized(1f, 1f).build("primed_toxic_tnt"));
   public static final RegistryObject<EntityType<IncendiaryTntEntity>> PRIMED_INCENDIARY_TNT =
           ENTITY_TYPES.register("primed_incendiary_tnt", () -> EntityType.Builder.<IncendiaryTntEntity>of(IncendiaryTntEntity::new, MobCategory.MISC)
                   .sized(1f, 1f).build("primed_incendiary_tnt"));
   public static final RegistryObject<EntityType<ShrapnelTntEntity>> PRIMED_SHRAPNEL_TNT =
           ENTITY_TYPES.register("primed_shrapnel_tnt", () -> EntityType.Builder.<ShrapnelTntEntity>of(ShrapnelTntEntity::new, MobCategory.MISC)
                   .sized(1f, 1f).build("primed_shrapnel_tnt"));
   public static final RegistryObject<EntityType<StunTntEntity>> PRIMED_STUN_TNT =
           ENTITY_TYPES.register("primed_stun_tnt", () -> EntityType.Builder.<StunTntEntity>of(StunTntEntity::new, MobCategory.MISC)
                   .sized(1f, 1f).build("primed_stun_tnt"));
   public static final RegistryObject<EntityType<LifespanArrowEntity>> LIFESPAN_ARROW_ENTITY = ENTITY_TYPES.register("lifespan_arrow_entity", () -> EntityType.Builder.<LifespanArrowEntity>of(LifespanArrowEntity::new, MobCategory.MISC)
           .sized(0.5F, 0.5F)
           .setTrackingRange(4)
           .setUpdateInterval(20)
           .setShouldReceiveVelocityUpdates(true)
           .build("lifespan_arrow_entity"));
   public static final RegistryObject<MobEffect> DAZZLE_EFFECT = POTION_EFFECTS.register("dazzle_effect", () -> new DazzleEffect(MobEffectCategory.HARMFUL, 0xE0E0DC));
   public static final RegistryObject<EntityType<ChemicalTntEntity>> PRIMED_CHEMICAL_TNT =
           ENTITY_TYPES.register("primed_chemical_tnt", () -> EntityType.Builder.<ChemicalTntEntity>of(ChemicalTntEntity::new, MobCategory.MISC)
                   .sized(1f, 1f).build("primed_chemical_tnt"));
   public static final RegistryObject<EntityType<BreachingTntEntity>> PRIMED_BREACHING_TNT =
           ENTITY_TYPES.register("primed_breaching_tnt", () -> EntityType.Builder.<BreachingTntEntity>of(BreachingTntEntity::new, MobCategory.MISC)
                   .sized(1f, 1f).build("primed_breaching_tnt"));
   public static final RegistryObject<EntityType<NetherTntEntity>> PRIMED_NETHER_TNT =
           ENTITY_TYPES.register("primed_nether_tnt", () -> EntityType.Builder.<NetherTntEntity>of(NetherTntEntity::new, MobCategory.MISC)
                   .sized(1f, 1f).build("primed_nether_tnt"));
   public static final RegistryObject<EntityType<ClusterTntEntity>> PRIMED_CLUSTER_TNT =
           ENTITY_TYPES.register("primed_cluster_tnt", () -> EntityType.Builder.<ClusterTntEntity>of(ClusterTntEntity::new, MobCategory.MISC)
                   .sized(1f, 1f).build("primed_cluster_tnt"));
   public static final RegistryObject<EntityType<BombletTntEntity>> PRIMED_BOMBLET_TNT =
           ENTITY_TYPES.register("primed_bomblet_tnt", () -> EntityType.Builder.<BombletTntEntity>of(BombletTntEntity::new, MobCategory.MISC)
                   .sized(.5f, .5f).build("primed_bomblet_tnt"));
   public static final RegistryObject<EntityType<ConcussiveTntEntity>> PRIMED_CONCUSSIVE_TNT =
           ENTITY_TYPES.register("primed_concussive_tnt", () -> EntityType.Builder.<ConcussiveTntEntity>of(ConcussiveTntEntity::new, MobCategory.MISC)
                   .sized(1f, 1f).build("primed_concussive_tnt"));
   public static final RegistryObject<EntityType<VegetationTntEntity>> PRIMED_VEGETATION_TNT =
           ENTITY_TYPES.register("primed_vegetation_tnt", () -> EntityType.Builder.<VegetationTntEntity>of(VegetationTntEntity::new, MobCategory.MISC)
                   .sized(1f, 1f).build("primed_vegetation_tnt"));
   public static final RegistryObject<EntityType<ThermobaricTntEntity>> PRIMED_THERMOBARIC_TNT =
           ENTITY_TYPES.register("primed_thermobaric_tnt", () -> EntityType.Builder.<ThermobaricTntEntity>of(ThermobaricTntEntity::new, MobCategory.MISC)
                   .sized(1f, 1f).build("primed_thermobaric_tnt"));
   public static final RegistryObject<EntityType<ReverseTntEntity>> PRIMED_REVERSE_TNT =
           ENTITY_TYPES.register("primed_reverse_tnt", () -> EntityType.Builder.<ReverseTntEntity>of(ReverseTntEntity::new, MobCategory.MISC)
                   .sized(1f, 1f).build("primed_reverse_tnt"));


   public static final RegistryObject<SimpleParticleType> GREEN_PARTICLE = PARTICLE_TYPES.register("green_particle", () -> new SimpleParticleType(true));
   public static void register(IEventBus bus){
      ENTITY_TYPES.register(bus);
      POTION_EFFECTS.register(bus);
      PARTICLE_TYPES.register(bus);
   }
}
