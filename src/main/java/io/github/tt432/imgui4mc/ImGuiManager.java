package io.github.tt432.imgui4mc;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @author TT432
 */
@Mod.EventBusSubscriber(Dist.CLIENT)
public class ImGuiManager {
    static boolean init;
    static TestImGui gui;

    private static void init() {
        new SharedLibraryLoader().load("imgui-java");

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
