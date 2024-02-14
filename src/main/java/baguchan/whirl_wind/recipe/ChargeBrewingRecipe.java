package baguchan.whirl_wind.recipe;

import baguchan.whirl_wind.registry.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.neoforged.neoforge.common.brewing.IBrewingRecipe;

public class ChargeBrewingRecipe implements IBrewingRecipe {

    /**
     * Code adapted from TileEntityBrewingStand.isItemValidForSlot(int index, ItemStack stack)
     */
    @Override
    public boolean isInput(ItemStack stack) {
        Item item = stack.getItem();
        return item == Items.POTION;
    }

    /**
     * Code adapted from TileEntityBrewingStand.isItemValidForSlot(int index, ItemStack stack)
     */
    @Override
    public boolean isIngredient(ItemStack stack) {
        return stack.is(ModItems.WIND_CHARGE.get());
    }

    /**
     * Code copied from TileEntityBrewingStand.brewPotions()
     * It brews the potion by doing the bit-shifting magic and then checking if the new PotionEffect list is different to the old one,
     * or if the new potion is a splash potion when the old one wasn't.
     */
    @Override
    public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
        if (isInput(input) && !ingredient.isEmpty() && isIngredient(ingredient)) {
            ItemStack result = PotionUtils.setPotion(new ItemStack(ModItems.CHARGE_POTION.get()), PotionUtils.getPotion(input));
            if (result != input) {
                return result;
            }
            return ItemStack.EMPTY;
        }

        return ItemStack.EMPTY;
    }
}
