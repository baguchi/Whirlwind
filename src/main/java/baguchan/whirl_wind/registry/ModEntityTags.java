package baguchan.whirl_wind.registry;

import baguchan.whirl_wind.WhirlWindMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public interface ModEntityTags {
    TagKey<EntityType<?>> NON_AFFECT_WIND = create("non_affect_wind");

    private static TagKey<EntityType<?>> create(String p_203849_) {
        return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(WhirlWindMod.MODID, p_203849_));
    }
}
