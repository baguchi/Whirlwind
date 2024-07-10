package baguchan.whirl_wind.entity.behavior;

import baguchan.whirl_wind.registry.ModMemorys;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.monster.breeze.Breeze;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.function.Predicate;

public class GroundAttack extends Behavior<Breeze> {
    private static final int ATTACK_RANGE_MIN_SQRT = 4;
    private static final int ATTACK_RANGE_MAX_SQRT = 256;
    private static final int UNCERTAINTY_BASE = 5;
    private static final int UNCERTAINTY_MULTIPLIER = 4;
    private static final float PROJECTILE_MOVEMENT_SCALE = 0.7F;
    private static final int DELAY_TICKS = Math.round(37.0F);
    private static final int GROUND_TICKS = Math.round(120.0F);

    @VisibleForTesting
    public GroundAttack() {
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
                        MemoryStatus.VALUE_ABSENT,
                        MemoryModuleType.WALK_TARGET,
                        MemoryStatus.VALUE_ABSENT,
                        MemoryModuleType.BREEZE_JUMP_TARGET,
                        MemoryStatus.VALUE_ABSENT,
                        ModMemorys.BREEZE_GROUND_ATTACK_COOLDOWN.get(),
                        MemoryStatus.VALUE_ABSENT
                ),
                DELAY_TICKS
        );
    }

    protected boolean checkExtraStartConditions(ServerLevel p_312041_, Breeze p_312169_) {
        return p_312169_.getPose() != Pose.STANDING
                ? false
                : p_312169_.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).map(p_312632_ -> isTargetWithinRange(p_312169_, p_312632_)).map(p_312737_ -> {

            return p_312737_;
        }).orElse(false);
    }

    protected boolean canStillUse(ServerLevel p_312535_, Breeze p_312174_, long p_311812_) {
        return !p_312174_.getBrain().hasMemoryValue(ModMemorys.BREEZE_GROUND_ATTACK_COOLDOWN.get());
    }

    protected void start(ServerLevel serverLevel, Breeze breeze, long p_311781_) {
        serverLevel.broadcastEntityEvent(breeze, (byte) 61);
        breeze.playSound(SoundEvents.BREEZE_INHALE, 1.0F, 1.0F);
        breeze.getBrain().setMemoryWithExpiry(ModMemorys.BREEZE_GROUND_ATTACK.get(), Unit.INSTANCE, DELAY_TICKS);
    }

    protected void stop(ServerLevel serverLevel, Breeze breeze, long p_312309_) {

        if (breeze.onGround()) {

            serverLevel.playSound((Player) null, breeze.getX(), breeze.getY(), breeze.getZ(), SoundEvents.MACE_SMASH_GROUND_HEAVY, breeze.getSoundSource(), 1.0F, 1.0F);
        }

        knockback(serverLevel, breeze, breeze);
        breeze.getBrain().setMemoryWithExpiry(ModMemorys.BREEZE_GROUND_ATTACK_COOLDOWN.get(), Unit.INSTANCE, (long) GROUND_TICKS);
    }

    protected void tick(ServerLevel serverLevel, Breeze breeze, long p_312804_) {
        Brain<Breeze> brain = breeze.getBrain();
        LivingEntity livingentity = brain.getMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);
        if (livingentity != null) {
            breeze.lookAt(EntityAnchorArgument.Anchor.EYES, livingentity.position());

        }
    }

    private static double getKnockbackPower(Breeze p_338265_, LivingEntity p_338630_, Vec3 p_338866_) {
        return (3.5 - p_338866_.length()) * 0.699999988079071 * (double) (2) * (1.0 - p_338630_.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
    }

    private static Predicate<LivingEntity> knockbackPredicate(Breeze p_338613_, Entity p_338698_) {
        return (p_344407_) -> {
            boolean flag;
            boolean flag1;
            boolean flag2;
            boolean flag6;
            label62:
            {
                flag = !p_344407_.isSpectator();
                flag1 = p_344407_ != p_338613_ && p_344407_ != p_338698_;
                flag2 = !p_338613_.isAlliedTo(p_344407_);
                if (p_344407_ instanceof TamableAnimal tamableanimal) {
                    if (tamableanimal.isTame() && p_338613_.getUUID().equals(tamableanimal.getOwnerUUID())) {
                        flag6 = true;
                        break label62;
                    }
                }

                flag6 = false;
            }

            boolean flag3;
            label55:
            {
                flag3 = !flag6;
                if (p_344407_ instanceof ArmorStand armorstand) {
                    if (armorstand.isMarker()) {
                        flag6 = false;
                        break label55;
                    }
                }

                flag6 = true;
            }

            boolean flag4 = flag6;
            boolean flag5 = p_338698_.distanceToSqr(p_344407_) <= Math.pow(3.5, 2.0);
            return flag && flag1 && flag2 && flag3 && flag4 && flag5;
        };
    }

    private static void knockback(Level p_335716_, Breeze breeze, Entity p_335810_) {
        p_335716_.levelEvent(2013, p_335810_.getOnPos(), 750);
        p_335716_.getEntitiesOfClass(LivingEntity.class, p_335810_.getBoundingBox().inflate(3.5), knockbackPredicate(breeze, p_335810_)).forEach((pushedEntity) -> {
            Vec3 vec3 = pushedEntity.position().subtract(p_335810_.position());
            double d0 = getKnockbackPower(breeze, pushedEntity, vec3);
            Vec3 vec31 = vec3.normalize().scale(d0);
            if (d0 > 0.0 && breeze != pushedEntity) {

                pushedEntity.hurt(breeze.damageSources().mobAttack(breeze), Mth.floor(Mth.clamp(3.5F - vec3.length(), 0F, 3.5F) * 6.0F));
                pushedEntity.push(vec31.x, 0.699999988079071, vec31.z);
                if (pushedEntity instanceof ServerPlayer) {
                    ServerPlayer serverplayer = (ServerPlayer) pushedEntity;
                    serverplayer.connection.send(new ClientboundSetEntityMotionPacket(serverplayer));
                }
            }

        });
    }

    private static boolean isTargetWithinRange(Breeze p_312114_, LivingEntity p_312647_) {
        double d0 = p_312114_.position().distanceToSqr(p_312647_.position());
        return d0 < 5 * 5;
    }
}
