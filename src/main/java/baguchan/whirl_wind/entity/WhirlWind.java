package baguchan.whirl_wind.entity;

import baguchan.whirl_wind.registry.ModEntities;
import baguchan.whirl_wind.registry.ModEntityTags;
import baguchan.whirl_wind.registry.ModParticleTypes;
import com.mojang.serialization.Dynamic;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.breeze.Breeze;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class WhirlWind extends Breeze {
    public WhirlWind(EntityType<? extends WhirlWind> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
        this.xpReward = 20;
    }

    @Override
    public boolean canAttack(LivingEntity p_312275_) {
        return p_312275_.getType() != ModEntities.WHIRLWIND.get() && super.canAttack(p_312275_);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.625F).add(Attributes.MAX_HEALTH, 70F);
    }

    @Override
    protected Brain<?> makeBrain(Dynamic<?> p_312201_) {
        return WhirlWindAi.makeBrain(this.whirlBrainProvider().makeBrain(p_312201_));
    }

    @Override
    public boolean canSpawnSprintParticle() {
        return true;
    }


    @Override
    public void aiStep() {
        if (this.level().isClientSide()) {
            if (this.getPose() == Pose.INHALING) {
                this.spawnCloudParticles();
            }
        }

        super.aiStep();

        if (this.getPose() == Pose.INHALING) {
            // This code is used to move other entities around the Whirlwind.
            // Thanks aether Team. from Aether's Whirl Wind code
            List<Entity> entityList = this.level().getEntities(this, this.getBoundingBox().inflate(6, 4, 6))
                    .stream().filter((entity -> !entity.getType().is(ModEntityTags.NON_AFFECT_WIND))).toList();
            for (Entity entity : entityList) {
                double x = (float) entity.getX();
                double y = (float) entity.getY();
                double z = (float) entity.getZ();
                double distance = this.distanceTo(entity);
                double d1 = y - this.getY();

                double d3 = Math.atan2(this.getX() - x, this.getZ() - z) / 0.0175;
                entity.setDeltaMovement(entity.getDeltaMovement().add(Math.sin(0.0175 * d3) * 0.055, 0, Math.cos(0.0175 * d3) * 0.055));
            }
        }
    }

    public void spawnCloudParticles() {
        for (int i = 0; i < 2; i++) {
            float moveX = (float) ((this.getRandom().nextDouble() - this.getRandom().nextDouble()) * 3);
            float moveY = (float) ((this.getRandom().nextDouble() - this.getRandom().nextDouble()) * 3);
            float moveZ = (float) ((this.getRandom().nextDouble() - this.getRandom().nextDouble()) * 3);
            double d1 = this.getX() + moveX;
            double d4 = this.getY() + moveY;
            double d7 = this.getZ() + moveZ;
            this.level().addParticle(ModParticleTypes.WIND.get(), d1, d4, d7, 0F, 0F, 0F);
        }
    }

    protected void spawnSprintParticle() {
        BlockPos blockpos = this.getOnPosLegacy();
        BlockState blockstate = this.level().getBlockState(blockpos);
        if (!blockstate.addRunningEffects(this.level(), blockpos, this))
            if (blockstate.getRenderShape() != RenderShape.INVISIBLE) {
                Vec3 vec3 = this.getDeltaMovement();
                BlockPos blockpos1 = this.blockPosition();
                for (int i = 0; i < 2; i++) {
                    double d0 = this.getX() + (this.random.nextDouble() - 0.5) * (double) this.getDimensions(this.getPose()).width() * 2;
                    double d1 = this.getZ() + (this.random.nextDouble() - 0.5) * (double) this.getDimensions(this.getPose()).width() * 2;
                    if (blockpos1.getX() != blockpos.getX()) {
                        d0 = Mth.clamp(d0, (double) blockpos.getX(), (double) blockpos.getX() + 1.0);
                    }

                    if (blockpos1.getZ() != blockpos.getZ()) {
                        d1 = Mth.clamp(d1, (double) blockpos.getZ(), (double) blockpos.getZ() + 1.0);
                    }

                    this.level()
                            .addParticle(
                                    new BlockParticleOption(ParticleTypes.BLOCK, blockstate).setPos(blockpos), d0, this.getY() + 0.1, d1, vec3.x * -4.0, 2, vec3.z * -4.0
                            );
                }
            }
    }

    @Override
    public boolean ignoreExplosion(Explosion p_312868_) {
        return p_312868_.getDirectSourceEntity() == this;
    }



    protected Brain.Provider<WhirlWind> whirlBrainProvider() {
        return Brain.provider(WhirlWindAi.MEMORY_TYPES, WhirlWindAi.SENSOR_TYPES);
    }
}
