package baguchan.wild_gale.client.render;

import baguchan.wild_gale.WildGaleMod;
import baguchan.wild_gale.client.ModModelLayers;
import baguchan.wild_gale.client.render.layer.WindLayer;
import baguchan.wild_gale.client.render.model.WildGaleModel;
import baguchan.wild_gale.entity.WildGale;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class WildGaleRender<T extends WildGale> extends MobRenderer<T, WildGaleModel<T>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(WildGaleMod.MODID, "textures/entity/wild_gale.png");

    public WildGaleRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new WildGaleModel<>(renderManagerIn.bakeLayer(ModModelLayers.WILD_GALE)), 0.5F);
        this.addLayer(new WindLayer<>(this, renderManagerIn.getModelSet()));
    }

    @Override
    public ResourceLocation getTextureLocation(T p_110775_1_) {
        return TEXTURE;
    }
}