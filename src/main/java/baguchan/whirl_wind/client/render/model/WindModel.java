package baguchan.whirl_wind.client.render.model;// Made with Blockbench 4.8.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import baguchan.whirl_wind.client.render.state.WhirlWindRenderState;
import net.minecraft.client.model.geom.ModelPart;

public class WindModel<T extends WhirlWindRenderState> extends WhirlWindModel<T> {

	public WindModel(ModelPart root) {
		super(root);
	}

	@Override
	public void setupAnim(T entity) {
		super.setupAnim(entity);
		this.wind_swirls.visible = true;
		this.body.visible = false;
		this.head.visible = false;
	}
}