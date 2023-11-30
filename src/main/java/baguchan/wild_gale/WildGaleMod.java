package baguchan.wild_gale;


import baguchan.wild_gale.registry.ModEntities;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(WildGaleMod.MODID)
public class WildGaleMod
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "wild_gale";

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public WildGaleMod(IEventBus modEventBus)
    {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        // Register the Deferred Register to the mod event bus so items get registered
        ModEntities.ENTITIES_REGISTRY.register(modEventBus);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);}

    private void commonSetup(final FMLCommonSetupEvent event)
    {
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {

    }
}
