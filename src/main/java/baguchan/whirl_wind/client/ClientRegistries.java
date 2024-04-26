package baguchan.whirl_wind.client;

import baguchan.whirl_wind.WhirlWindMod;
import baguchan.whirl_wind.client.render.WhirlWindRender;
import baguchan.whirl_wind.client.render.model.WhirlWindModel;
import baguchan.whirl_wind.registry.ModEntities;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = WhirlWindMod.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ClientRegistries {
    @SubscribeEvent
    public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.WHIRLWIND.get(), WhirlWindRender::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.WHIRLWIND, WhirlWindModel::createBodyLayer);
    }
}