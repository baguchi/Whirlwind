package baguchan.wild_gale.client.render.layer;

import baguchan.wild_gale.WhirlWindMod;
import baguchan.wild_gale.client.ModModelLayers;
import baguchan.wild_gale.client.render.model.WindModel;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.Util;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Matrix4f;

public class WindLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
    private static final ResourceLocation LOCATION = new ResourceLocation(WhirlWindMod.MODID, "textures/entity/whirl_wind_wind.png");
    private final WindModel<T> windModel;
    protected static final RenderStateShard.ShaderStateShard RENDERTYPE_ENERGY_SWIRL_SHADER = new RenderStateShard.ShaderStateShard(GameRenderer::getRendertypeEnergySwirlShader);

    protected static final RenderStateShard.TransparencyStateShard ADDITIVE_TRANSPARENCY = new RenderStateShard.TransparencyStateShard("additive_transparency", () -> {
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
    }, () -> {
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
    });
    protected static final RenderStateShard.CullStateShard NO_CULL = new RenderStateShard.CullStateShard(false);
    protected static final RenderStateShard.TexturingStateShard ENTITY_GLINT_TEXTURING = new RenderStateShard.TexturingStateShard("entity_glint_texturing", () -> {
        setupGlintTexturing(1F);
    }, () -> {
        RenderSystem.resetTextureMatrix();
    });

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

        p_116951_.pushPose();
        this.getParentModel().copyPropertiesTo(this.windModel);
        this.windModel.setupAnim(p_116954_, p_116955_, p_116956_, p_116958_, p_116959_, p_116960_);
        VertexConsumer vertexconsumer = p_116952_.getBuffer(wind(getTextureLocation(p_116954_)));
        this.windModel.renderToBuffer(p_116951_, vertexconsumer, p_116953_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        p_116951_.popPose();

    }

    @Override
    protected ResourceLocation getTextureLocation(T p_117348_) {
        return LOCATION;
    }

    private static void setupGlintTexturing(float p_110187_) {
        long i = Util.getMillis() * 8L;
        float f = (float) (i % 10000L) / 10000.0F;
        float f1 = (float) (i % 5000L) / 5000.0F;
        Matrix4f matrix4f = (new Matrix4f()).translation(-f, 0, 0.0F);
        matrix4f.scale(p_110187_);
        RenderSystem.setTextureMatrix(matrix4f);
    }

    public static RenderType wind(ResourceLocation resourceLocation) {
        return RenderType.create("wind_effect", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, false, true, RenderType.CompositeState.builder().setShaderState(RENDERTYPE_ENERGY_SWIRL_SHADER).setTextureState(new RenderStateShard.TextureStateShard(resourceLocation, false, false)).setTransparencyState(ADDITIVE_TRANSPARENCY).setCullState(NO_CULL).setTexturingState(ENTITY_GLINT_TEXTURING).createCompositeState(false));
    }
}
