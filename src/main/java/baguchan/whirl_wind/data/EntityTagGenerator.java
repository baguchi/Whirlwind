package baguchan.whirl_wind.data;

import baguchan.whirl_wind.WhirlWindMod;
import baguchan.whirl_wind.registry.ModEntities;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class EntityTagGenerator extends EntityTypeTagsProvider {
    public EntityTagGenerator(PackOutput p_256095_, CompletableFuture<HolderLookup.Provider> p_256572_, ExistingFileHelper exFileHelper) {
        super(p_256095_, p_256572_, WhirlWindMod.MODID, exFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.Provider p_255894_) {
        this.tag(EntityTypeTags.FALL_DAMAGE_IMMUNE).add(ModEntities.WHIRLWIND.get());
        this.tag(EntityTypeTags.DEFLECTS_PROJECTILES).add(ModEntities.WHIRLWIND.get());
        this.tag(EntityTypeTags.CAN_TURN_IN_BOATS).add(ModEntities.WHIRLWIND.get());
        this.tag(EntityTypeTags.NO_ANGER_FROM_WIND_CHARGE)
                .add(
                        ModEntities.WHIRLWIND.get()
                );
    }
}
