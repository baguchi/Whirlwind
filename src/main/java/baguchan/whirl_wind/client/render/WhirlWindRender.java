package baguchan.whirl_wind.client.render;

import baguchan.whirl_wind.WhirlWindMod;
import baguchan.whirl_wind.client.ModModelLayers;
import baguchan.whirl_wind.client.render.layer.WindLayer;
import baguchan.whirl_wind.client.render.model.WhirlWindModel;
import baguchan.whirl_wind.entity.WhirlWind;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;

public class WhirlWindRender<T extends WhirlWind> extends MobRenderer<T, WhirlWindModel<T>> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(WhirlWindMod.MODID, "textures/entity/whirl_wind.png");
    private static final RenderType EYES = RenderType.eyes(ResourceLocation.fromNamespaceAndPath(WhirlWindMod.MODID, "textures/entity/whirl_wind_eye.png"));

    public WhirlWindRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new WhirlWindModel<>(renderManagerIn.bakeLayer(ModModelLayers.WHIRLWIND)), 0.5F);
        this.addLayer(new WindLayer<>(this, renderManagerIn.getModelSet()));
        this.addLayer(new EyesLayer<T, WhirlWindModel<T>>(this) {
            @Override
            public RenderType renderType() {
                return EYES;
            }
        });
    }

    @Override
    public ResourceLocation getTextureLocation(T p_110775_1_) {
        return TEXTURE;
    }
}