package io.github.tt432.imgui4mc;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * @author TT432
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImGuiManager {
    @SubscribeEvent
    public static void onEvent(FMLClientSetupEvent event) {
        final String outputFolder = "./.natives";
        IOUtil.extractResource("imgui-java64.dll", outputFolder);
        IOUtil.extractResource("libimgui-java64.dylib", outputFolder);
        IOUtil.extractResource("libimgui-java64.so", outputFolder);
        IOUtil.extractResource("libimgui-javaarm64.dylib", outputFolder);
        System.setProperty("imgui.library.path", outputFolder);

        IOUtil.extractResource("SourceHanSans-Normal.ttc", outputFolder);
    }

    private static TestImGui gui;

    public static void showTestUI() {
        long windowId = Minecraft.getInstance().getWindow().getWindow();
        gui = new TestImGui(windowId);
        gui.init();
        MinecraftForge.EVENT_BUS.addListener(ImGuiManager::onRenderTick);
    }

    public static void onRenderTick(TickEvent.RenderTickEvent event) {
        if (gui != null) gui.runFrame();
    }
}
