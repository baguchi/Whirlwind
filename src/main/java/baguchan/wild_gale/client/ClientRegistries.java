package baguchan.wild_gale.client;

import baguchan.wild_gale.WildGaleMod;
import baguchan.wild_gale.client.render.model.WildGaleModel;
import baguchan.wild_gale.client.render.WildGaleRender;
import baguchan.wild_gale.client.render.model.WindModel;
import baguchan.wild_gale.registry.ModEntities;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@Mod.EventBusSubscriber(modid = WildGaleMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientRegistries {
    @SubscribeEvent
    public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.WILD_GALE.get(), WildGaleRender::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.WILD_GALE, WildGaleModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.WIND, WindModel::createBodyLayer);

    }
}