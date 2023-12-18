package baguchan.wild_gale.registry;

import baguchan.wild_gale.WhirlWindMod;
import baguchan.wild_gale.entity.ChargePotion;
import baguchan.wild_gale.entity.WhirlWind;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.flag.FeatureFlags;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = WhirlWindMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES_REGISTRY = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, WhirlWindMod.MODID);


    public static final Supplier<EntityType<WhirlWind>> WHIRLWIND = ENTITIES_REGISTRY.register("whirl_wind", () -> EntityType.Builder.of(WhirlWind::new, MobCategory.MONSTER).sized(0.8F, 2.2F).clientTrackingRange(8).requiredFeatures(FeatureFlags.UPDATE_1_21).build(prefix("whirl_wind")));
    public static final Supplier<EntityType<ChargePotion>> CHARGE_POTION = ENTITIES_REGISTRY.register("charge_potion", () -> EntityType.Builder.<ChargePotion>of(ChargePotion::new, MobCategory.MISC).sized(0.3F, 0.3F).clientTrackingRange(10).requiredFeatures(FeatureFlags.UPDATE_1_21).build(prefix("charge_potion")));

    @SubscribeEvent
    public static void registerEntityAttribute(EntityAttributeCreationEvent event) {
        event.put(WHIRLWIND.get(), WhirlWind.createAttributes().build());
    }

    private static String prefix(String path) {
        return WhirlWindMod.MODID + "." + path;
    }
}
