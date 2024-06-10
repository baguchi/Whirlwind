package baguchan.whirl_wind.registry;

import baguchan.whirl_wind.WhirlWindMod;
import baguchan.whirl_wind.entity.WhirlWind;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

@EventBusSubscriber(modid = WhirlWindMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES_REGISTRY = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, WhirlWindMod.MODID);


    public static final Supplier<EntityType<WhirlWind>> WHIRLWIND = ENTITIES_REGISTRY.register("whirl_wind", () -> EntityType.Builder.of(WhirlWind::new, MobCategory.MONSTER).sized(0.8F, 2.2F).clientTrackingRange(8).build(prefix("whirl_wind")));

    @SubscribeEvent
    public static void registerEntityAttribute(EntityAttributeCreationEvent event) {
        event.put(WHIRLWIND.get(), WhirlWind.createAttributes().build());
    }

    private static String prefix(String path) {
        return WhirlWindMod.MODID + "." + path;
    }
}
