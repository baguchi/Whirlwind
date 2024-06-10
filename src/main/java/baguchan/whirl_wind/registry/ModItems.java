package baguchan.whirl_wind.registry;

import baguchan.whirl_wind.WhirlWindMod;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister<Item> ITEM_REGISTRY = DeferredRegister.create(BuiltInRegistries.ITEM, WhirlWindMod.MODID);
    public static final Supplier<Item> WHIRL_WIND_SPAWNEGG = ITEM_REGISTRY.register("whirl_wind_spawn_egg", () -> new DeferredSpawnEggItem(ModEntities.WHIRLWIND, 11506911, 9529055, (new Item.Properties())));
    public static void dispenserInit() {
    }
}
