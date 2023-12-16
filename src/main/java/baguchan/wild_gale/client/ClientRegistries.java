package baguchan.wild_gale.client;

import baguchan.wild_gale.WhirlWindMod;
import baguchan.wild_gale.client.render.WhirlWindRender;
import baguchan.wild_gale.client.render.model.WhirlWindModel;
import baguchan.wild_gale.registry.ModEntities;
import baguchan.wild_gale.registry.ModItems;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

@Mod.EventBusSubscriber(modid = WhirlWindMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientRegistries {
    @SubscribeEvent
    public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.WHIRLWIND.get(), WhirlWindRender::new);
        event.registerEntityRenderer(ModEntities.CHARGE_POTION.get(), ThrownItemRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.WHIRLWIND, WhirlWindModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerColor(RegisterColorHandlersEvent.Item event) {
        event.register((stack, layer) -> layer > 0 ? -1 : PotionUtils.getColor(stack), ModItems.CHARGE_POTION.get());
    }
}