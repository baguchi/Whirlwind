package baguchan.whirl_wind.entity;

import baguchan.whirl_wind.registry.ModEntities;
import baguchan.whirl_wind.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCandleBlock;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.List;
import java.util.function.Predicate;

public class ChargePotion extends ThrowableItemProjectile implements ItemSupplier {
    public final PotionAirExplosionDamageCalculator EXPLOSION_DAMAGE_CALCULATOR = new PotionAirExplosionDamageCalculator();

    public static final double SPLASH_RANGE = 4.0;
    private static final double SPLASH_RANGE_SQ = 16.0;
    public static final Predicate<LivingEntity> WATER_SENSITIVE_OR_ON_FIRE = p_308783_ -> p_308783_.isSensitiveToWater() || p_308783_.isOnFire();

    public ChargePotion(EntityType<? extends ChargePotion> p_37527_, Level p_37528_) {
        super(p_37527_, p_37528_);
    }

    public ChargePotion(Level p_37535_, LivingEntity p_37536_) {
        super(ModEntities.CHARGE_POTION.get(), p_37536_, p_37535_);
    }

    public ChargePotion(Level p_37530_, double p_37531_, double p_37532_, double p_37533_) {
        super(ModEntities.CHARGE_POTION.get(), p_37531_, p_37532_, p_37533_, p_37530_);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.CHARGE_POTION.get();
    }

    @Override
    protected float getGravity() {
        return 0.05F;
    }

    @Override
    protected void onHitBlock(BlockHitResult p_37541_) {
        super.onHitBlock(p_37541_);
        if (!this.level().isClientSide) {
            ItemStack itemstack = this.getItem();
            Potion potion = PotionUtils.getPotion(itemstack);
            List<MobEffectInstance> list = PotionUtils.getMobEffects(itemstack);
            boolean flag = potion == Potions.WATER && list.isEmpty();
            Direction direction = p_37541_.getDirection();
            BlockPos blockpos = p_37541_.getBlockPos();
            BlockPos blockpos1 = blockpos.relative(direction);
            if (flag) {
                this.dowseFire(blockpos1);
                this.dowseFire(blockpos1.relative(direction.getOpposite()));

                for (Direction direction1 : Direction.Plane.HORIZONTAL) {
                    this.dowseFire(blockpos1.relative(direction1));
                }
            }
        }
    }

    @Override
    protected void onHit(HitResult p_37543_) {
        super.onHit(p_37543_);
        if (!this.level().isClientSide) {
            ItemStack itemstack = this.getItem();
            Potion potion = PotionUtils.getPotion(itemstack);
            List<MobEffectInstance> list = PotionUtils.getMobEffects(itemstack);
            this.level()
                    .explode(
                            this,
                            null,
                            EXPLOSION_DAMAGE_CALCULATOR,
                            this.getX(),
                            this.getY(),
                            this.getZ(),
                            (float) (3),
                            false,
                            Level.ExplosionInteraction.BLOW,
                            ParticleTypes.GUST,
                            ParticleTypes.GUST_EMITTER,
                            SoundEvents.WIND_BURST
                    );

            int i = potion.hasInstantEffects() ? 2007 : 2002;
            this.level().levelEvent(i, this.blockPosition(), PotionUtils.getColor(itemstack));
            this.discard();
        }
    }

    private void dowseFire(BlockPos p_150193_) {
        BlockState blockstate = this.level().getBlockState(p_150193_);
        if (blockstate.is(BlockTags.FIRE)) {
            this.level().destroyBlock(p_150193_, false, this);
        } else if (AbstractCandleBlock.isLit(blockstate)) {
            AbstractCandleBlock.extinguish(null, blockstate, this.level(), p_150193_);
        } else if (CampfireBlock.isLitCampfire(blockstate)) {
            this.level().levelEvent(null, 1009, p_150193_, 0);
            CampfireBlock.dowse(this.getOwner(), this.level(), p_150193_, blockstate);
            this.level().setBlockAndUpdate(p_150193_, blockstate.setValue(CampfireBlock.LIT, Boolean.valueOf(false)));
        }
    }

    public class PotionAirExplosionDamageCalculator extends ExplosionDamageCalculator {
        @Override
        public boolean shouldDamageEntity(Explosion p_314513_, Entity p_314456_) {
            ItemStack itemstack = ChargePotion.this.getItem();
            Potion potion = PotionUtils.getPotion(itemstack);
            List<MobEffectInstance> list = PotionUtils.getMobEffects(itemstack);
            boolean flag = potion == Potions.WATER && list.isEmpty();
            if (flag) {
                if (p_314456_ instanceof LivingEntity livingentity) {
                    float f = p_314513_.radius() * 2.0F;
                    double d0 = Math.sqrt(ChargePotion.this.distanceToSqr(livingentity)) / (double) f;
                    double d1 = (1.0 - d0);
                    if (livingentity.isSensitiveToWater()) {
                        livingentity.hurt(ChargePotion.this.damageSources().indirectMagic(ChargePotion.this, ChargePotion.this.getOwner()), (float) d1 * 3);
                    }

                    if (livingentity.isOnFire() && livingentity.isAlive()) {
                        livingentity.extinguishFire();
                    }
                }
            } else {
                if (p_314456_ instanceof LivingEntity livingentity) {
                    if (livingentity.isAffectedByPotions()) {
                        for (MobEffectInstance mobeffectinstance : list) {
                            Entity entity = ChargePotion.this.getEffectSource();
                            float f = p_314513_.radius() * 2.0F;
                            double d0 = Math.sqrt(ChargePotion.this.distanceToSqr(livingentity)) / (double) f;
                            double d1 = (1.0 - d0);

                            MobEffect mobeffect = mobeffectinstance.getEffect();
                            if (mobeffect.isInstantenous()) {
                                mobeffect.applyInstantenousEffect(ChargePotion.this, ChargePotion.this.getOwner(), livingentity, mobeffectinstance.getAmplifier(), d1);
                            } else {
                                int i = mobeffectinstance.mapDuration(p_267930_ -> (int) (d1 * (double) p_267930_ + 0.5));
                                MobEffectInstance mobeffectinstance1 = new MobEffectInstance(
                                        mobeffect, i, mobeffectinstance.getAmplifier(), mobeffectinstance.isAmbient(), mobeffectinstance.isVisible()
                                );
                                if (!mobeffectinstance1.endsWithin(20)) {
                                    livingentity.addEffect(mobeffectinstance1, entity);
                                }
                            }
                        }
                    }
                }
            }
            return false;
        }


    }
}
