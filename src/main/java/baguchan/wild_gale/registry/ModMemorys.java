package baguchan.wild_gale.registry;

import baguchan.wild_gale.WhirlWindMod;
import com.mojang.serialization.Codec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Optional;
import java.util.function.Supplier;

public class ModMemorys {
    public static final DeferredRegister<MemoryModuleType<?>> MEMORY_REGISTER = DeferredRegister.create(BuiltInRegistries.MEMORY_MODULE_TYPE, WhirlWindMod.MODID);

    public static final Supplier<MemoryModuleType<Integer>> BREEZE_SHOOT_REMAIN = MEMORY_REGISTER.register("breeze_shoot_remain", () -> new MemoryModuleType<>(Optional.of(Codec.INT)));
    public static final Supplier<MemoryModuleType<Integer>> BREEZE_SHOOT_REMAIN_COOLDOWN = MEMORY_REGISTER.register("breeze_shoot_remain_cooldown", () -> new MemoryModuleType<>(Optional.of(Codec.INT)));

}