package baguchan.wild_gale.client.render.model;// Made with Blockbench 4.8.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import baguchan.wild_gale.client.render.animation.WhirlWindAnimation;
import baguchan.wild_gale.entity.WhirlWind;
import net.minecraft.client.animation.definitions.BreezeAnimation;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class WhirlWindModel<T extends WhirlWind> extends HierarchicalModel<T> {
	private final ModelPart realroot;
	private final ModelPart root;
	private final ModelPart head;

	public WhirlWindModel(ModelPart root) {
		this.realroot = root;
		this.root = root.getChild("root");
		this.head = this.root.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(1.0F, 6.0F, -1.0F));

		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(0, 22).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.offset(-1.0F, -12.0F, 1.0F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition rod_bone2 = body.addOrReplaceChild("rod_bone2", CubeListBuilder.create(), PartPose.offset(-1.0F, 8.0F, 1.0F));

		PartDefinition rod5 = rod_bone2.addOrReplaceChild("rod5", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, -12.0F, -11.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

		PartDefinition rod6 = rod_bone2.addOrReplaceChild("rod6", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, -12.0F, 8.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1745F, 0.0F, 0.0F));

		PartDefinition rod7 = rod_bone2.addOrReplaceChild("rod7", CubeListBuilder.create().texOffs(0, 16).addBox(-11.0F, -12.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1745F));

		PartDefinition rod8 = rod_bone2.addOrReplaceChild("rod8", CubeListBuilder.create().texOffs(0, 16).addBox(10.0F, -12.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1745F));

		PartDefinition rod_bone = body.addOrReplaceChild("rod_bone", CubeListBuilder.create(), PartPose.offset(-1.0F, 8.0F, 1.0F));

		PartDefinition rod4 = rod_bone.addOrReplaceChild("rod4", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, -12.0F, -3.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

		PartDefinition rod3 = rod_bone.addOrReplaceChild("rod3", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, -12.0F, 1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1745F, 0.0F, 0.0F));

		PartDefinition rod2 = rod_bone.addOrReplaceChild("rod2", CubeListBuilder.create().texOffs(0, 16).addBox(-3.0F, -12.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1745F));

		PartDefinition rod1 = rod_bone.addOrReplaceChild("rod1", CubeListBuilder.create().texOffs(0, 16).addBox(1.0F, -12.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1745F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.realroot.getAllParts().forEach(ModelPart::resetPose);
		this.animate(entity.slide, WhirlWindAnimation.SLIDE, ageInTicks);
		this.animate(entity.longJump, BreezeAnimation.JUMP, ageInTicks, 0.25F);
		this.animate(entity.shoot, BreezeAnimation.SHOOT, ageInTicks);
		this.animateWalk(WhirlWindAnimation.IDLE, ageInTicks, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public ModelPart root() {
		return this.realroot;
	}
}