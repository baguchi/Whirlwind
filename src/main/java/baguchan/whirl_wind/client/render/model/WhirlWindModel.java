package baguchan.whirl_wind.client.render.model;// Made with Blockbench 4.8.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import baguchan.whirl_wind.client.render.animation.WhirlWindAnimation;
import baguchan.whirl_wind.client.render.state.WhirlWindRenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class WhirlWindModel<T extends WhirlWindRenderState> extends EntityModel<T> {
	public final ModelPart whirl_wind;
	public final ModelPart head;
	public final ModelPart body;
	public final ModelPart wind_swirls;

	private final ModelPart windTop;
	private final ModelPart windMid;
	private final ModelPart windBottom;

	public WhirlWindModel(ModelPart whirl_wind) {
		super(whirl_wind);
		this.whirl_wind = whirl_wind.getChild("whirl_wind");
		this.body = this.whirl_wind.getChild("body");
		this.head = this.body.getChild("head");
		this.wind_swirls = this.whirl_wind.getChild("wind_swirls");
		this.windBottom = this.wind_swirls.getChild("wind_bottom");
		this.windMid = this.wind_swirls.getChild("wind_middle");
		this.windTop = this.wind_swirls.getChild("wind_top");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition whirl_wind = partdefinition.addOrReplaceChild("whirl_wind", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body = whirl_wind.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 25).addBox(-4.0F, -8.5F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F))
				.texOffs(26, 7).addBox(-5.0F, -5.5F, -4.5F, 10.0F, 4.0F, 5.0F, new CubeDeformation(0.5F))
				.texOffs(0, 8).addBox(-4.0F, -9.5F, -4.0F, 8.0F, 9.0F, 8.0F, new CubeDeformation(0.75F))
				.texOffs(1, 44).addBox(-4.0F, -8.3F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.75F)), PartPose.offset(0.0F, -26.5F, 0.0F));

		PartDefinition torso = body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(56, 0).addBox(-3.0F, -5.0F, -1.0F, 4.0F, 21.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.offset(1.0F, -19.0F, -1.0F));

		PartDefinition rods = torso.addOrReplaceChild("rods", CubeListBuilder.create(), PartPose.offset(-1.0F, 1.0F, 1.0F));

		PartDefinition rod3_r1 = rods.addOrReplaceChild("rod3_r1", CubeListBuilder.create().texOffs(34, 26).addBox(-3.0F, -6.0F, -1.0F, 6.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0622F, 3.0F, 3.5F, -2.8362F, 1.0472F, 3.1416F));

		PartDefinition rod2_r1 = rods.addOrReplaceChild("rod2_r1", CubeListBuilder.create().texOffs(34, 26).addBox(-3.0F, -6.0F, -1.0F, 6.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0622F, 3.0F, 3.5F, -2.8362F, -1.0472F, 3.1416F));

		PartDefinition rod1_r1 = rods.addOrReplaceChild("rod1_r1", CubeListBuilder.create().texOffs(34, 26).addBox(-3.0F, -6.0F, -1.0F, 6.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, -7.0F, 0.3054F, 0.0F, 0.0F));

		PartDefinition wind_swirls = whirl_wind.addOrReplaceChild("wind_swirls", CubeListBuilder.create(), PartPose.offset(1.0F, -18.0F, -1.0F));

		PartDefinition wind_top = wind_swirls.addOrReplaceChild("wind_top", CubeListBuilder.create().texOffs(40, 25).addBox(-12.0F, -9.0F, -10.0F, 22.0F, 14.0F, 22.0F, new CubeDeformation(0.5F))
				.texOffs(64, 62).addBox(-9.0F, -9.0F, -7.0F, 16.0F, 14.0F, 16.0F, new CubeDeformation(0.5F))
				.texOffs(92, 93).addBox(-5.5F, -9.0F, -3.5F, 9.0F, 14.0F, 9.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition wind_middle = wind_swirls.addOrReplaceChild("wind_middle", CubeListBuilder.create().texOffs(0, 62).addBox(-9.0F, 5.0F, -7.0F, 16.0F, 7.0F, 16.0F, new CubeDeformation(0.5F))
				.texOffs(0, 87).addBox(-7.0F, 5.0F, -5.0F, 12.0F, 7.0F, 12.0F, new CubeDeformation(0.5F))
				.texOffs(0, 108).addBox(-5.5F, 5.0F, -3.5F, 9.0F, 7.0F, 9.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition wind_bottom = wind_swirls.addOrReplaceChild("wind_bottom", CubeListBuilder.create().texOffs(80, 2).addBox(-7.0F, 12.0F, -5.0F, 12.0F, 6.0F, 12.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity) {
		super.setupAnim(entity);
		this.wind_swirls.visible = false;
		this.head.xRot = entity.xRot * (float) (Math.PI / 180.0);
		this.head.yRot = entity.yRot * (float) (Math.PI / 180.0);
		float f = entity.ageInTicks * (float) Math.PI * -0.1F;
		this.windTop.x = Mth.cos(f) * 1.0F * 0.6F;
		this.windTop.z = Mth.sin(f) * 1.0F * 0.6F;
		this.windMid.x = Mth.sin(f) * 0.5F * 0.8F;
		this.windMid.z = Mth.cos(f) * 0.8F;
		this.windBottom.x = Mth.cos(f) * -0.25F * 1.0F;
		this.windBottom.z = Mth.sin(f) * -0.25F * 1.0F;
		this.animate(entity.slide, WhirlWindAnimation.SLIDE, entity.ageInTicks);
		this.animate(entity.longJump, WhirlWindAnimation.JUMP, entity.ageInTicks, 0.25F);
		this.animate(entity.shoot, WhirlWindAnimation.SHOOT, entity.ageInTicks);
		this.animate(entity.groundAttackAnimationState, WhirlWindAnimation.GROUND_ATTACK, entity.ageInTicks);
		this.animateWalk(WhirlWindAnimation.IDLE, entity.ageInTicks, 1.0F, 1.0F, 1.0F);
	}
}