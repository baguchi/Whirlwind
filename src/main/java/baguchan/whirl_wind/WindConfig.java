package baguchan.whirl_wind;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class WindConfig {
    public static final Common COMMON;
    public static final ModConfigSpec COMMON_SPEC;

    static {
        Pair<Common, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }

    public static class Common {
        public final ModConfigSpec.BooleanValue whirlWindAttackIfHurt;

        public Common(ModConfigSpec.Builder builder) {
            whirlWindAttackIfHurt = builder
                    .comment("Whirl Wind do Attack If Hurt by Mob")
                    .define("WhirlWind Attack If Hurt"
                            , true);
        }
    }

}