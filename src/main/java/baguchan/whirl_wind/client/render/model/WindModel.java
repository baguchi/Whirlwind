package baguchan.whirl_wind.client.render.model;// Made with Blockbench 4.8.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import baguchan.whirl_wind.entity.WhirlWind;
import net.minecraft.client.model.geom.ModelPart;

public class WindModel<T extends WhirlWind> extends WhirlWindModel<T> {

	public WindModel(ModelPart root) {
		super(root);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		this.wind_swirls.visible = true;
		this.body.visible = false;
		this.head.visible = false;
	}
}