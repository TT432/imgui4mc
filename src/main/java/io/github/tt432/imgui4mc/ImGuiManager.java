package io.github.tt432.imgui4mc;

import io.github.tt432.imgui4mc.config.ImGuiConfig;
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
    private static final List<ImGuiWindowElements> elements = new ArrayList<>();
    private static Window window;

    @EventBusSubscriber(Dist.CLIENT)
    public static final class ForgeEvents {
        @SubscribeEvent
        public static void onEvent(RenderFrameEvent.Post event) {
            if (window != null && ImGuiConfig.CONFIG.openImGui.get()) window.runFrame();
        }
    }

    public static void loadWindows(long windowId) {
        window = new Window(windowId) {
            @Override
            public void process() {
                for (ImGuiWindowElements element : elements) {
                    element.process(this);
                }
            }
        };

        window.preInit();

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
                        if (ImGuiWindowElements.class.isAssignableFrom(clazz)) {
                            ImGuiWindowElements element = (ImGuiWindowElements) clazz.getConstructor().newInstance();
                            element.init(window, windowId);
                            elements.add(element);
                        }
                    } catch (ReflectiveOperationException | LinkageError e) {
                        log.error("Failed to load: {}, the class must be {} and have a <init>()V", className, ImGuiWindowElements.class.getName(), e);
                    }
                }
            }
        }

        window.postInit();
    }
}
