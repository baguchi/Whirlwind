package baguchan.whirl_wind.entity;

import baguchan.whirl_wind.entity.behavior.GroundAttack;
import baguchan.whirl_wind.entity.behavior.ShootGust;
import baguchan.whirl_wind.entity.behavior.WhirlShootWhenStuck;
import baguchan.whirl_wind.entity.behavior.WhirlWindLongJump;
import baguchan.whirl_wind.registry.ModMemorys;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.monster.breeze.Breeze;
import net.minecraft.world.entity.monster.breeze.BreezeAi;
import net.minecraft.world.entity.monster.breeze.Slide;
import net.minecraft.world.entity.schedule.Activity;

import java.util.List;
import java.util.Set;

public class WhirlWindAi {
    public static final float SPEED_MULTIPLIER_WHEN_SLIDING = 0.6F;
    public static final float JUMP_CIRCLE_INNER_RADIUS = 4.0F;
    public static final float JUMP_CIRCLE_MIDDLE_RADIUS = 8.0F;
    public static final float JUMP_CIRCLE_OUTER_RADIUS = 20.0F;

    static final List<SensorType<? extends Sensor<? super Breeze>>> SENSOR_TYPES = ImmutableList.of(
            SensorType.NEAREST_LIVING_ENTITIES, SensorType.HURT_BY, SensorType.NEAREST_PLAYERS, SensorType.BREEZE_ATTACK_ENTITY_SENSOR
    );
    static final List<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
            MemoryModuleType.NEAREST_ATTACKABLE,
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryModuleType.ATTACK_TARGET,
            MemoryModuleType.WALK_TARGET,
            MemoryModuleType.BREEZE_JUMP_COOLDOWN,
            MemoryModuleType.BREEZE_JUMP_INHALING,
            MemoryModuleType.BREEZE_SHOOT,
            MemoryModuleType.BREEZE_SHOOT_CHARGING,
            MemoryModuleType.BREEZE_SHOOT_RECOVERING,
            MemoryModuleType.BREEZE_SHOOT_COOLDOWN,
            MemoryModuleType.BREEZE_JUMP_TARGET,
            ModMemorys.BREEZE_SHOOT_REMAIN.get(),
            ModMemorys.BREEZE_SHOOT_REMAIN_COOLDOWN.get(),
            ModMemorys.BREEZE_GROUND_ATTACK_COOLDOWN.get(),
            ModMemorys.BREEZE_GROUND_ATTACK.get(),
            MemoryModuleType.BREEZE_LEAVING_WATER,
            MemoryModuleType.HURT_BY,
            MemoryModuleType.HURT_BY_ENTITY,
            MemoryModuleType.PATH
    );

    protected static Brain<?> makeBrain(Brain<WhirlWind> p_312887_) {
        initCoreActivity(p_312887_);
        initIdleActivity(p_312887_);
        initFightActivity(p_312887_);
        p_312887_.setCoreActivities(Set.of(Activity.CORE));
        p_312887_.setDefaultActivity(Activity.FIGHT);
        p_312887_.useDefaultActivity();
        return p_312887_;
    }

    public static void updateActivity(WhirlWind p_316353_) {
        p_316353_.getBrain().setActiveActivityToFirstValid(ImmutableList.of(Activity.FIGHT, Activity.IDLE));
    }

    private static void initCoreActivity(Brain<WhirlWind> p_312774_) {
        p_312774_.addActivity(Activity.CORE, 0, ImmutableList.of(new Swim(0.8F), new LookAtTargetSink(45, 90), new SlideToTargetSink(20, 100)));
    }

    private static void initIdleActivity(Brain<WhirlWind> p_316741_) {
        p_316741_.addActivity(
                Activity.IDLE,
                ImmutableList.of(
                        Pair.of(0, StartAttacking.create(p_376863_ -> p_376863_.getBrain().getMemory(MemoryModuleType.NEAREST_ATTACKABLE))),
                        Pair.of(1, StartAttacking.create(p_375898_ -> p_375898_.getHurtBy())),
                        Pair.of(2, new BreezeAi.SlideToTargetSink(20, 40)),
                        Pair.of(3, new RunOne<>(ImmutableList.of(Pair.of(new DoNothing(20, 100), 1), Pair.of(RandomStroll.stroll(0.6F), 2))))
                )
        );
    }

    private static void initFightActivity(Brain<WhirlWind> p_312350_) {
        p_312350_.addActivityWithConditions(
                Activity.FIGHT,
                ImmutableList.of(
                        Pair.of(0, StartAttacking.create(p_312881_ -> p_312881_.getBrain().getMemory(MemoryModuleType.NEAREST_ATTACKABLE))),
                        Pair.of(1, StopAttackingIfTargetInvalid.create()),
                        Pair.of(2, new ShootGust()),
                        Pair.of(3, new WhirlShootWhenStuck()),
                        Pair.of(4, new WhirlWindLongJump()),
                        Pair.of(5, new GroundAttack()),
                        Pair.of(6, new Slide()),
                        Pair.of(7, new RunOne<>(ImmutableList.of(Pair.of(new DoNothing(20, 100), 1), Pair.of(RandomStroll.stroll(0.6F), 2))))
                ),
                Set.of()
        );
    }

    public static class SlideToTargetSink extends MoveToTargetSink {
        @VisibleForTesting
        public SlideToTargetSink(int p_311828_, int p_312532_) {
            super(p_311828_, p_312532_);
        }

        @Override
        protected void start(ServerLevel p_312732_, Mob p_312543_, long p_312612_) {
            super.start(p_312732_, p_312543_, p_312612_);
            p_312543_.playSound(SoundEvents.BREEZE_SLIDE);
            p_312543_.setPose(Pose.SLIDING);
        }

        @Override
        protected void stop(ServerLevel p_312932_, Mob p_311871_, long p_312594_) {
            super.stop(p_312932_, p_311871_, p_312594_);
            p_311871_.setPose(Pose.STANDING);
            if (p_311871_.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_TARGET)) {
                p_311871_.getBrain().setMemoryWithExpiry(MemoryModuleType.BREEZE_SHOOT, Unit.INSTANCE, 120L);
            }
        }
    }
}
