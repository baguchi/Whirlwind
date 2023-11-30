package baguchan.wild_gale.registry;

import baguchan.wild_gale.WildGaleMod;
import baguchan.wild_gale.entity.WildGale;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = WildGaleMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES_REGISTRY = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, WildGaleMod.MODID);


    public static final Supplier<EntityType<WildGale>> WILD_GALE = ENTITIES_REGISTRY.register("wild_wind", () -> EntityType.Builder.of(WildGale::new, MobCategory.MONSTER).sized(0.8F, 2.0F).clientTrackingRange(8).build(prefix("wild_wind")));

    @SubscribeEvent
    public static void registerEntityAttribute(EntityAttributeCreationEvent event) {
        event.put(WILD_GALE.get(), WildGale.createAttributes().build());
    }

    private static String prefix(String path) {
        return WildGaleMod.MODID + "." + path;
    }
}
