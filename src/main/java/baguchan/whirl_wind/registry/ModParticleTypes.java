package baguchan.whirl_wind.registry;

import baguchan.whirl_wind.WhirlWindMod;
import baguchan.whirl_wind.client.particle.WindParticle;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

@EventBusSubscriber(modid = WhirlWindMod.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModParticleTypes {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, WhirlWindMod.MODID);

    public static final Supplier<SimpleParticleType> WIND = PARTICLE_TYPES.register("wind", () -> new SimpleParticleType(false));

    @SubscribeEvent
    public static void registerFactories(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticleTypes.WIND.get(), WindParticle.Factory::new);
    }
}