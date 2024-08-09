package io.github.tt432.imgui4mc;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderFrameEvent;
import net.neoforged.neoforgespi.language.ModFileScanData;
import org.objectweb.asm.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author TT432
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class ImGuiManager {
    private static final List<Window> windows = new ArrayList<>();

    @EventBusSubscriber(Dist.CLIENT)
    public static final class ForgeEvents {
        @SubscribeEvent
        public static void onEvent(RenderFrameEvent.Post event) {
            if (gui != null) gui.runFrame();

            for (Window window : windows) {
                window.runFrame();
            }
        }
    }

    public static void loadWindows(long windowId) {
        Type annotationType = Type.getType(RegisterImGui.class);
        List<ModFileScanData> allScanData = ModList.get().getAllScanData();

        for (ModFileScanData scanData : allScanData) {
            Iterable<ModFileScanData.AnnotationData> annotations = scanData.getAnnotations();

            for (ModFileScanData.AnnotationData a : annotations) {
                if (Objects.equals(a.annotationType(), annotationType)) {
                    String className = a.clazz().getClassName();

                    try {
                        Class<?> clazz = Class.forName(className, false,
                                ImGuiManager.class.getClassLoader());
                        if (Window.class.isAssignableFrom(clazz)) {
                            Window window = (Window) clazz.getConstructor(long.class).newInstance(windowId);
                            window.init();
                            windows.add(window);
                        }
                    } catch (ReflectiveOperationException | LinkageError e) {
                        log.error("Failed to load: {}, the class must be {} and have a <init>(J)V", className, Window.class.getName(), e);
                    }
                }
            }
        }
    }

    private static TestImGui gui;

    public static void showTestUI(long windowId) {
        gui = new TestImGui(windowId);
        gui.init();
    }
}
