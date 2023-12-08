package baguchan.wild_gale.client.render.layer;

import baguchan.wild_gale.WhirlWindMod;
import baguchan.wild_gale.client.ModModelLayers;
import baguchan.wild_gale.client.render.model.WindModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class WindLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
    private static final ResourceLocation LOCATION = new ResourceLocation(WhirlWindMod.MODID, "textures/entity/whirl_wind_wind.png");
    private final WindModel<T> windModel;
    public WindLayer(RenderLayerParent<T, M> p_174493_, EntityModelSet p_174494_) {
        super(p_174493_);
        this.windModel = new WindModel<T>(p_174494_.bakeLayer(ModModelLayers.WIND));
    }

    public void render(
            PoseStack p_116951_,
            MultiBufferSource p_116952_,
            int p_116953_,
            T p_116954_,
            float p_116955_,
            float p_116956_,
            float p_116957_,
            float p_116958_,
            float p_116959_,
            float p_116960_
    ) {
        float f = (float) p_116954_.tickCount + p_116957_;
        p_116951_.pushPose();
        this.getParentModel().copyPropertiesTo(this.windModel);
        this.windModel.setupAnim(p_116954_, p_116955_, p_116956_, p_116958_, p_116959_, p_116960_);
        VertexConsumer vertexconsumer = p_116952_.getBuffer(RenderType.breezeWind(getTextureLocation(p_116954_), this.xOffset(f) % 1.0F, 0.0F));
        this.windModel.renderToBuffer(p_116951_, vertexconsumer, p_116953_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        p_116951_.popPose();

    }

    private float xOffset(float p_312086_) {
        return p_312086_ * 0.04F;
    }

    @Override
    protected ResourceLocation getTextureLocation(T p_117348_) {
        return LOCATION;
    }
}
