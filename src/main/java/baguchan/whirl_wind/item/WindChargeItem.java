package baguchan.whirl_wind.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.WindCharge;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class WindChargeItem extends Item {
    public WindChargeItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand p_41434_) {
        ItemStack itemstack = player.getItemInHand(p_41434_);
        if (!level.isClientSide) {
            WindCharge windCharge = new WindCharge(EntityType.WIND_CHARGE, level);
            windCharge.setOwner(player);
            windCharge.setPos(player.getX(), player.getEyeY(), player.getZ());
            Vec3 vec3 = player.getViewVector(1.0F);
            windCharge.xPower = 0.05F * vec3.x;
            windCharge.yPower = 0.05F * vec3.y;
            windCharge.zPower = 0.05F * vec3.z;
            windCharge.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
            level.addFreshEntity(windCharge);
        }
        if (!player.getAbilities().instabuild) {
            itemstack.shrink(1);
        }
        player.playSound(SoundEvents.BREEZE_SHOOT);
        player.fallDistance = 0;
        player.getCooldowns().addCooldown(this, 6);
        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }
}
