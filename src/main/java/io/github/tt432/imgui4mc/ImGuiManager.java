package io.github.tt432.imgui4mc;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.TickEvent;

/**
 * @author TT432
 */
@Mod.EventBusSubscriber(Dist.CLIENT)
public class ImGuiManager {
    static boolean init;
    static TestImGui gui;

    private static void init() {
        final String outputFolder = "./.natives";
        IOUtil.extractResource("imgui-java64.dll", outputFolder);
        IOUtil.extractResource("libimgui-java64.dylib", outputFolder);
        IOUtil.extractResource("libimgui-java64.so", outputFolder);
        System.setProperty("imgui.library.path", outputFolder);

        IOUtil.extractResource("SourceHanSans-Normal.ttc", outputFolder);

        long windowId = Minecraft.getInstance().getWindow().getWindow();
        gui = new TestImGui(windowId);
        gui.init();
    }

    @SubscribeEvent
    public static void onEvent(TickEvent.RenderTickEvent event) {
        if (!init) {
            init = true;
            init();
        }

        gui.runFrame();
    }
}
