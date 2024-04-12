package baguchan.whirl_wind.event;

import baguchan.whirl_wind.WhirlWindMod;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;

import javax.annotation.Nullable;
import java.util.*;

@Mod.EventBusSubscriber(modid = WhirlWindMod.MODID)
public class ModVillagerTradeEvent {
    private static final Map<VillagerProfession, Int2ObjectMap<VillagerTrades.ItemListing[]>> VANILLA_TRADES = new HashMap<>();

    private static Int2ObjectMap<VillagerTrades.ItemListing[]> gatAsIntMap(ImmutableMap<Integer, VillagerTrades.ItemListing[]> p_221238_0_) {
        return new Int2ObjectOpenHashMap<>(p_221238_0_);
    }

    @SubscribeEvent
    public static void tradeEvent(VillagerTradesEvent event) {
        List<VillagerTrades.ItemListing> trades3 = event.getTrades().get(3);
        trades3.add(new TrialMapForEmeralds(12, "filled_map.trial_chambers", MapDecoration.Type.TARGET_POINT, 12, 10));


    }


    public static void loadTrades(ServerAboutToStartEvent event) {
        Map<VillagerProfession, Int2ObjectMap<VillagerTrades.ItemListing[]>> originalTrade = VillagerTrades.EXPERIMENTAL_TRADES;
        Int2ObjectMap<VillagerTrades.ItemListing[]> trades = VillagerTrades.EXPERIMENTAL_TRADES.getOrDefault(VillagerProfession.CARTOGRAPHER, new Int2ObjectOpenHashMap<>());
        Int2ObjectMap<List<VillagerTrades.ItemListing>> mutableTrades = new Int2ObjectOpenHashMap<>();
        for (int i = 1; i < 6; i++) {
            mutableTrades.put(i, NonNullList.create());
        }

        mutableTrades.get(3).add(new TrialMapForEmeralds(12, "filled_map.trial_chambers", MapDecoration.Type.TARGET_POINT, 12, 10));

        trades.int2ObjectEntrySet().forEach(e -> {
            Arrays.stream(e.getValue()).forEach(mutableTrades.get(e.getIntKey())::add);
        });
        Int2ObjectMap<VillagerTrades.ItemListing[]> newTrades = new Int2ObjectOpenHashMap<>();

        mutableTrades.int2ObjectEntrySet().forEach(e -> newTrades.put(e.getIntKey(), e.getValue().toArray(new VillagerTrades.ItemListing[0])));

        Map<VillagerProfession, Int2ObjectMap<VillagerTrades.ItemListing[]>> map = new HashMap<>();
        map.putAll(originalTrade);
        map.put(VillagerProfession.CARTOGRAPHER, newTrades);

        VillagerTrades.EXPERIMENTAL_TRADES = map;

    }

    static class TrialMapForEmeralds implements VillagerTrades.ItemListing {
        private final int emeraldCost;
        private final String displayName;
        private final MapDecoration.Type destinationType;
        private final int maxUses;
        private final int villagerXp;

        public TrialMapForEmeralds(int p_207767_, String p_207769_, MapDecoration.Type p_207770_, int p_207771_, int p_207772_) {
            this.emeraldCost = p_207767_;
            this.displayName = p_207769_;
            this.destinationType = p_207770_;
            this.maxUses = p_207771_;
            this.villagerXp = p_207772_;
        }

        @Nullable
        public MerchantOffer getOffer(Entity p_219708_, RandomSource p_219709_) {
            if (!(p_219708_.level() instanceof ServerLevel)) {
                return null;
            } else {
                ServerLevel serverlevel = (ServerLevel) p_219708_.level();
                Registry<Structure> registry = serverlevel.registryAccess().registryOrThrow(Registries.STRUCTURE);

                Optional<Holder.Reference<Structure>> structure = registry.getHolder(BuiltinStructures.TRIAL_CHAMBERS);
                if (structure.isEmpty()) {
                    return null;
                }


                Pair<BlockPos, Holder<Structure>> blockpos = serverlevel.getChunkSource().getGenerator().findNearestMapStructure(serverlevel, HolderSet.direct(registry.getHolder(BuiltinStructures.TRIAL_CHAMBERS).get()), p_219708_.blockPosition(), 100, true);

                if (blockpos != null) {
                    ItemStack itemstack = MapItem.create(serverlevel, blockpos.getFirst().getX(), blockpos.getFirst().getZ(), (byte) 2, true, true);
                    MapItem.renderBiomePreviewMap(serverlevel, itemstack);
                    MapItemSavedData.addTargetDecoration(itemstack, blockpos.getFirst(), "+", this.destinationType);
                    itemstack.setHoverName(Component.translatable(this.displayName));
                    return new MerchantOffer(new ItemStack(Items.EMERALD, this.emeraldCost), new ItemStack(Items.COMPASS), itemstack, this.maxUses, this.villagerXp, 0.2F);
                } else {
                    return null;
                }
            }
        }
    }
}