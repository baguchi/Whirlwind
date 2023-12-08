package baguchan.wild_gale.client.render.animation;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

public class WhirlWindAnimation {
    public static final AnimationDefinition SLIDE = AnimationDefinition.Builder.withLength(1.0F)
            .addAnimation(
                    "body",
                    new AnimationChannel(
                            AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.2F, KeyframeAnimations.posVec(0.0F, 0.0F, -6.0F), AnimationChannel.Interpolations.LINEAR)
                    )
            )
            .build();
    public static final AnimationDefinition IDLE = AnimationDefinition.Builder.withLength(1f).looping()
            .addAnimation("rod_bone",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 90f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 180f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 270f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 360f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .addAnimation("rod_bone2",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, -90f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, -180f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, -270f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1f, KeyframeAnimations.degreeVec(0f, -360f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM))).build();
}
