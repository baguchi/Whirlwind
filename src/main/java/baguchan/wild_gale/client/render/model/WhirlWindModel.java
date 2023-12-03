package baguchan.wild_gale.client.render.model;// Made with Blockbench 4.8.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import baguchan.wild_gale.client.render.animation.WhirlWindAnimation;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

public class WhirlWindModel<T extends Entity> extends HierarchicalModel<T> {
	private final ModelPart realroot;
	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart rod_bone;
	private final ModelPart rod_bone2;

	public WhirlWindModel(ModelPart root) {
		this.realroot = root;
		this.root = root.getChild("root");
		this.head = this.root.getChild("head");
		this.rod_bone = this.root.getChild("rod_bone");
		this.rod_bone2 = this.root.getChild("rod_bone2");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(1.0F, 6.0F, -1.0F));

		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(0, 22).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.offset(-1.0F, -12.0F, 1.0F));

		PartDefinition rod_bone = root.addOrReplaceChild("rod_bone", CubeListBuilder.create(), PartPose.offset(-1.0F, 8.0F, 1.0F));

		PartDefinition rod4 = rod_bone.addOrReplaceChild("rod4", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, -12.0F, -3.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

		PartDefinition rod3 = rod_bone.addOrReplaceChild("rod3", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, -12.0F, 1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1745F, 0.0F, 0.0F));

		PartDefinition rod2 = rod_bone.addOrReplaceChild("rod2", CubeListBuilder.create().texOffs(0, 16).addBox(-3.0F, -12.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1745F));

		PartDefinition rod1 = rod_bone.addOrReplaceChild("rod1", CubeListBuilder.create().texOffs(0, 16).addBox(1.0F, -12.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1745F));

		PartDefinition rod_bone2 = root.addOrReplaceChild("rod_bone2", CubeListBuilder.create(), PartPose.offset(-1.0F, 8.0F, 1.0F));

		PartDefinition rod5 = rod_bone2.addOrReplaceChild("rod5", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, -12.0F, -11.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

		PartDefinition rod6 = rod_bone2.addOrReplaceChild("rod6", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, -12.0F, 8.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1745F, 0.0F, 0.0F));

		PartDefinition rod7 = rod_bone2.addOrReplaceChild("rod7", CubeListBuilder.create().texOffs(0, 16).addBox(-11.0F, -12.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1745F));

		PartDefinition rod8 = rod_bone2.addOrReplaceChild("rod8", CubeListBuilder.create().texOffs(0, 16).addBox(10.0F, -12.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1745F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.realroot.getAllParts().forEach(ModelPart::resetPose);
		this.head.xRot = headPitch * (float) (Math.PI / 180.0) - limbSwingAmount * 22.5F * (float) (Math.PI / 180.0);
		this.head.yRot = netHeadYaw * (float) (Math.PI / 180.0);
		this.head.z = -3F * limbSwingAmount;
		this.root.xRot = limbSwingAmount * 22.5F * (float) (Math.PI / 180.0);
		this.animateWalk(WhirlWindAnimation.IDLE, ageInTicks, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public ModelPart root() {
		return this.realroot;
	}
}