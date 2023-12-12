package baguchan.wild_gale.data;

import baguchan.wild_gale.WhirlWindMod;
import baguchan.wild_gale.registry.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.loaders.ItemLayerModelBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.function.Supplier;

import static baguchan.wild_gale.WhirlWindMod.prefix;

public class ItemModelGenerator extends ItemModelProvider {
    public ItemModelGenerator(PackOutput generator, ExistingFileHelper existingFileHelper) {
        super(generator, WhirlWindMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        this.egg(ModItems.WHIRL_WIND_SPAWNEGG);
        this.singleTex(ModItems.WIND_CHARGE);
        this.singleTex(ModItems.BREEZE_POWDER);
        this.singleTex(ModItems.BREEZE_ROD);
    }

    private ItemModelBuilder generated(String name, ResourceLocation... layers) {
        return buildItem(name, "item/generated", 0, layers);
    }

    private ItemModelBuilder buildItem(String name, String parent, int emissivity, ResourceLocation... layers) {
        ItemModelBuilder builder = withExistingParent(name, parent);
        for (int i = 0; i < layers.length; i++) {
            builder = builder.texture("layer" + i, layers[i]);
        }
        if (emissivity > 0)
            builder = builder.customLoader(ItemLayerModelBuilder::begin).emissive(emissivity, emissivity, 0).renderType("minecraft:translucent", 0).end();
        return builder;
    }

    public ItemModelBuilder egg(Supplier<? extends Item> item) {
        return withExistingParent(BuiltInRegistries.ITEM.getKey(item.get()).getPath(), mcLoc("item/template_spawn_egg"));
    }

    private ItemModelBuilder toolRod(String name, ResourceLocation... layers) {
        return buildItem(name, "item/handheld_rod", 0, layers);
    }

    public ResourceLocation itemPath(Supplier<? extends ItemLike> item) {
        return BuiltInRegistries.ITEM.getKey(item.get().asItem());
    }

    private ItemModelBuilder singleTex(Supplier<? extends ItemLike> item) {
        return generated(itemPath(item).getPath(), prefix("item/" + itemPath(item).getPath()));
    }
}
