package io.github.tt432.imgui4mc;

import io.github.tt432.imgui4mc.config.ImGuiConfig;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = ImGui4MC.MOD_ID, dist = Dist.CLIENT)
public class ImGui4MC {
    public static final String MOD_ID = "imgui4mc";

    public ImGui4MC(ModContainer container) {
        container.registerConfig(ModConfig.Type.CLIENT, ImGuiConfig.CONFIG_SPEC);
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
}
