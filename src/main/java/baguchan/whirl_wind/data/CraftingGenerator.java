package baguchan.whirl_wind.data;

import baguchan.whirl_wind.registry.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class CraftingGenerator extends RecipeProvider {
    public CraftingGenerator(PackOutput p_248933_, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(p_248933_, lookupProvider);
    }

    @Override
    protected void buildRecipes(RecipeOutput p_301172_) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.WIND_CHARGE.get())
                .requires(ModItems.BREEZE_POWDER.get(), 3)
                .requires(Items.REDSTONE)
                .unlockedBy("has_item", has(ModItems.BREEZE_POWDER.get()))
                .save(p_301172_);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BREEZE_POWDER.get(), 2)
                .requires(ModItems.BREEZE_ROD.get())
                .unlockedBy("has_item", has(ModItems.BREEZE_ROD.get()))
                .save(p_301172_);
    }
}
