package baguchan.whirl_wind.registry;

import baguchan.whirl_wind.WhirlWindMod;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEM_REGISTRY = DeferredRegister.createItems(WhirlWindMod.MODID);
    public static final DeferredItem<SpawnEggItem> WHIRL_WIND_SPAWNEGG = ITEM_REGISTRY.registerItem("whirl_wind_spawn_egg", (properties) -> new SpawnEggItem(ModEntities.WHIRLWIND.get(), (properties)));
    public static void dispenserInit() {
    }
}
