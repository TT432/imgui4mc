package io.github.tt432.imgui4mc.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

/**
 * @author TT432
 */
public class ImGuiConfig {
    //Define a field to keep the config and spec for later
    public static final ImGuiConfig CONFIG;
    public static final ModConfigSpec CONFIG_SPEC;

    public final ModConfigSpec.ConfigValue<Boolean> openImGui;

    public ImGuiConfig(ModConfigSpec.Builder builder) {
        openImGui = builder.define("openImGui", false);
    }

    //CONFIG and CONFIG_SPEC are both built from the same builder, so we use a static block to seperate the properties
    static {
        Pair<ImGuiConfig, ModConfigSpec> pair =
                new ModConfigSpec.Builder().configure(ImGuiConfig::new);

        //Store the resulting values
        CONFIG = pair.getLeft();
        CONFIG_SPEC = pair.getRight();
    }
}
