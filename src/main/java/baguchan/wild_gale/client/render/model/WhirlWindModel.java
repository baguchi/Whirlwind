package baguchan.wild_gale.client.render.model;// Made with Blockbench 4.8.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import baguchan.wild_gale.client.render.animation.WhirlWindAnimation;
import baguchan.wild_gale.entity.WhirlWind;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class WhirlWindModel<T extends WhirlWind> extends HierarchicalModel<T> {
	private final ModelPart realroot;
	public final ModelPart whirl_wind;
	public final ModelPart head;
	public final ModelPart body;
	public final ModelPart wind_swirls;

	private final ModelPart windTop;
	private final ModelPart windMid;
	private final ModelPart windBottom;

	public WhirlWindModel(ModelPart whirl_wind) {
		this.realroot = whirl_wind;
		this.whirl_wind = whirl_wind.getChild("whirl_wind");
		this.head = this.whirl_wind.getChild("head");
		this.body = this.whirl_wind.getChild("body");
		this.wind_swirls = this.whirl_wind.getChild("wind_swirls");
		this.windBottom = this.wind_swirls.getChild("wind_bottom");
		this.windMid = this.windBottom.getChild("wind_middle");
		this.windTop = this.windMid.getChild("wind_top");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition whirl_wind = partdefinition.addOrReplaceChild("whirl_wind", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition head = whirl_wind.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 25).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F))
				.texOffs(27, 8).addBox(-5.0F, -1.0F, -4.25F, 10.0F, 3.0F, 4.0F, new CubeDeformation(0.5F))
				.texOffs(0, 8).addBox(-4.0F, -5.0F, -4.0F, 8.0F, 9.0F, 8.0F, new CubeDeformation(0.75F)), PartPose.offset(0.0F, -31.0F, 0.0F));

		PartDefinition body = whirl_wind.addOrReplaceChild("body", CubeListBuilder.create().texOffs(56, 0).addBox(-3.0F, -6.0F, -1.0F, 4.0F, 16.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.offset(1.0F, -18.0F, -1.0F));

		PartDefinition rods = body.addOrReplaceChild("rods", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition rod4_r1 = rods.addOrReplaceChild("rod4_r1", CubeListBuilder.create().texOffs(36, 26).addBox(-1.0F, -4.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.2618F));

		PartDefinition rod3_r1 = rods.addOrReplaceChild("rod3_r1", CubeListBuilder.create().texOffs(36, 26).addBox(-1.0F, -4.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.0F, 2.0F, 1.0F, 0.0F, 0.0F, -0.2618F));

		PartDefinition rod2_r1 = rods.addOrReplaceChild("rod2_r1", CubeListBuilder.create().texOffs(36, 26).addBox(-1.0F, -4.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 2.0F, 9.0F, -0.2618F, 0.0F, 0.0F));

		PartDefinition rod1_r1 = rods.addOrReplaceChild("rod1_r1", CubeListBuilder.create().texOffs(36, 26).addBox(-1.0F, -4.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 2.0F, -7.0F, 0.2618F, 0.0F, 0.0F));

		PartDefinition wind_swirls = whirl_wind.addOrReplaceChild("wind_swirls", CubeListBuilder.create(), PartPose.offset(1.0F, -18.0F, -1.0F));

		PartDefinition wind_bottom = wind_swirls.addOrReplaceChild("wind_bottom", CubeListBuilder.create().texOffs(80, 2).addBox(-7.0F, 12.0F, -5.0F, 12.0F, 6.0F, 12.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition wind_middle = wind_bottom.addOrReplaceChild("wind_middle", CubeListBuilder.create().texOffs(0, 62).addBox(-9.0F, 5.0F, -7.0F, 16.0F, 7.0F, 16.0F, new CubeDeformation(0.5F))
				.texOffs(0, 87).addBox(-7.0F, 5.0F, -5.0F, 12.0F, 7.0F, 12.0F, new CubeDeformation(0.5F))
				.texOffs(0, 108).addBox(-5.5F, 5.0F, -3.5F, 9.0F, 7.0F, 9.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition wind_top = wind_middle.addOrReplaceChild("wind_top", CubeListBuilder.create().texOffs(40, 25).addBox(-12.0F, -9.0F, -10.0F, 22.0F, 14.0F, 22.0F, new CubeDeformation(0.0F))
				.texOffs(64, 62).addBox(-9.0F, -9.0F, -7.0F, 16.0F, 14.0F, 16.0F, new CubeDeformation(0.5F))
				.texOffs(92, 93).addBox(-5.5F, -9.0F, -3.5F, 9.0F, 14.0F, 9.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.wind_swirls.visible = false;
		this.realroot.getAllParts().forEach(ModelPart::resetPose);
		this.head.xRot = headPitch * (float) (Math.PI / 180.0);
		this.head.yRot = netHeadYaw * (float) (Math.PI / 180.0);
		float f = ageInTicks * (float) Math.PI * -0.1F;
		this.windTop.x = Mth.cos(f) * 1.0F * 0.6F;
		this.windTop.z = Mth.sin(f) * 1.0F * 0.6F;
		this.windMid.x = Mth.sin(f) * 0.5F * 0.8F;
		this.windMid.z = Mth.cos(f) * 0.8F;
		this.windBottom.x = Mth.cos(f) * -0.25F * 1.0F;
		this.windBottom.z = Mth.sin(f) * -0.25F * 1.0F;
		this.animate(entity.slide, WhirlWindAnimation.SLIDE, ageInTicks);
		this.animate(entity.longJump, WhirlWindAnimation.JUMP, ageInTicks, 0.25F);
		this.animate(entity.shoot, WhirlWindAnimation.SHOOT, ageInTicks);
		this.animateWalk(WhirlWindAnimation.IDLE, ageInTicks, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public ModelPart root() {
		return this.realroot;
	}
}