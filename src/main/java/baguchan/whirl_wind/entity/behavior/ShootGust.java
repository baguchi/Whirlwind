package baguchan.whirl_wind.entity.behavior;

import baguchan.whirl_wind.registry.ModMemorys;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.monster.breeze.Breeze;
import net.minecraft.world.entity.projectile.windcharge.BreezeWindCharge;
import net.minecraft.world.phys.Vec3;

public class ShootGust extends Behavior<Breeze> {
    private static final int ATTACK_RANGE_MIN_SQRT = 4;
    private static final int ATTACK_RANGE_MAX_SQRT = 256;
    private static final int UNCERTAINTY_BASE = 5;
    private static final int UNCERTAINTY_MULTIPLIER = 4;
    private static final float PROJECTILE_MOVEMENT_SCALE = 0.7F;
    private static final int SHOOT_INITIAL_DELAY_TICKS = Math.round(15.0F);
    private static final int SHOOT_RECOVER_DELAY_TICKS = Math.round(10.0F);
    private static final int SHOOT_COOLDOWN_TICKS = Math.round(55.0F);
    private static final int SHOOT_REMAIN_DELAY_TICKS = Math.round(60.0F);

    @VisibleForTesting
    public ShootGust() {
        super(
                ImmutableMap.of(
                        MemoryModuleType.ATTACK_TARGET,
                        MemoryStatus.VALUE_PRESENT,
                        MemoryModuleType.BREEZE_SHOOT_COOLDOWN,
                        MemoryStatus.VALUE_ABSENT,
                        MemoryModuleType.BREEZE_SHOOT_CHARGING,
                        MemoryStatus.VALUE_ABSENT,
                        MemoryModuleType.BREEZE_SHOOT_RECOVERING,
                        MemoryStatus.VALUE_ABSENT,
                        MemoryModuleType.BREEZE_SHOOT,
                        MemoryStatus.VALUE_PRESENT,
                        MemoryModuleType.WALK_TARGET,
                        MemoryStatus.VALUE_ABSENT,
                        MemoryModuleType.BREEZE_JUMP_TARGET,
                        MemoryStatus.VALUE_ABSENT,
                        ModMemorys.BREEZE_GROUND_ATTACK.get(),
                        MemoryStatus.VALUE_ABSENT
                ),
                SHOOT_INITIAL_DELAY_TICKS + 1 + SHOOT_RECOVER_DELAY_TICKS
        );
    }

    protected boolean checkExtraStartConditions(ServerLevel serverLevel, Breeze breeze) {
        return breeze.getPose() != Pose.STANDING
                ? false
                : breeze.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).map(p_312632_ -> isTargetWithinRange(breeze, p_312632_)).map(p_312737_ -> {
            if (!p_312737_) {
                breeze.getBrain().eraseMemory(MemoryModuleType.BREEZE_SHOOT);
            }

            return p_312737_;
        }).orElse(false);
    }

    protected boolean canStillUse(ServerLevel serverLevel, Breeze breeze, long p_311812_) {
        return breeze.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_TARGET) && breeze.getBrain().hasMemoryValue(MemoryModuleType.BREEZE_SHOOT);
    }

    protected void start(ServerLevel serverLevel, Breeze breeze, long p_311781_) {
        breeze.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).ifPresent(p_312833_ -> breeze.setPose(Pose.SHOOTING));
        breeze.getBrain().setMemoryWithExpiry(MemoryModuleType.BREEZE_SHOOT_CHARGING, Unit.INSTANCE, (long) SHOOT_INITIAL_DELAY_TICKS);
        breeze.getBrain().setMemory(ModMemorys.BREEZE_SHOOT_REMAIN.get(), 2);
        breeze.playSound(SoundEvents.BREEZE_INHALE, 1.0F, 1.0F);
    }

    protected void stop(ServerLevel serverLevel, Breeze breeze, long p_312309_) {
        if (breeze.getPose() == Pose.SHOOTING) {
            breeze.setPose(Pose.STANDING);
        }

        breeze.getBrain().setMemoryWithExpiry(MemoryModuleType.BREEZE_SHOOT_COOLDOWN, Unit.INSTANCE, (long) SHOOT_COOLDOWN_TICKS);
        breeze.getBrain().eraseMemory(MemoryModuleType.BREEZE_SHOOT);
    }

    protected void tick(ServerLevel serverLevel, Breeze breeze, long p_312804_) {
        Brain<Breeze> brain = breeze.getBrain();
        LivingEntity livingentity = brain.getMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);
        if (livingentity != null) {
            breeze.lookAt(EntityAnchorArgument.Anchor.EYES, livingentity.position());
            if (!brain.getMemory(MemoryModuleType.BREEZE_SHOOT_CHARGING).isPresent() && !brain.getMemory(MemoryModuleType.BREEZE_SHOOT_RECOVERING).isPresent()) {


                if (isFacingTarget(breeze, livingentity)) {
                    if (!brain.hasMemoryValue(ModMemorys.BREEZE_SHOOT_REMAIN_COOLDOWN.get())) {
                        if (brain.getMemory(ModMemorys.BREEZE_SHOOT_REMAIN.get()).get() > 0) {
                            double d0 = livingentity.getX() - breeze.getX();
                            double d1 = livingentity.getY(0.3) - breeze.getY(0.5);
                            double d2 = livingentity.getZ() - breeze.getZ();
                            BreezeWindCharge windcharge = new BreezeWindCharge(EntityType.BREEZE_WIND_CHARGE, serverLevel);
                            breeze.playSound(SoundEvents.BREEZE_SHOOT, 1.5F, 1.0F);
                            windcharge.setOwner(breeze);
                            windcharge.setPos(breeze.getEyePosition());
                            windcharge.shoot(d0, d1, d2, 0.4F + brain.getMemory(ModMemorys.BREEZE_SHOOT_REMAIN.get()).get() * 0.1F, (float) (5 - serverLevel.getDifficulty().getId() * 4));
                            serverLevel.addFreshEntity(windcharge);
                            if (brain.getMemory(ModMemorys.BREEZE_SHOOT_REMAIN.get()).get() <= 0) {
                                brain.setMemoryWithExpiry(MemoryModuleType.BREEZE_SHOOT_RECOVERING, Unit.INSTANCE, (long) SHOOT_RECOVER_DELAY_TICKS);

                            } else {
                                brain.setMemory(ModMemorys.BREEZE_SHOOT_REMAIN.get(), brain.getMemory(ModMemorys.BREEZE_SHOOT_REMAIN.get()).get() - 1);
                                brain.setMemoryWithExpiry(ModMemorys.BREEZE_SHOOT_REMAIN_COOLDOWN.get(), Unit.INSTANCE, (long) Math.round(4F));

                            }
                        }
                    }
                }

            }
        }
    }

    @VisibleForTesting
    public static boolean isFacingTarget(Breeze p_311845_, LivingEntity p_312453_) {
        Vec3 vec3 = p_311845_.getViewVector(1.0F);
        Vec3 vec31 = p_312453_.position().subtract(p_311845_.position()).normalize();
        return vec3.dot(vec31) > 0.5;
    }

    private static boolean isTargetWithinRange(Breeze p_312114_, LivingEntity p_312647_) {
        double d0 = p_312114_.position().distanceToSqr(p_312647_.position());
        return d0 > 4.0 && d0 < 256.0;
    }
}
