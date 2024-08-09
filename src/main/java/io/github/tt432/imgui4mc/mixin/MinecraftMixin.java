package io.github.tt432.imgui4mc.mixin;

import com.mojang.blaze3d.platform.Window;
import io.github.tt432.imgui4mc.IOUtil;
import io.github.tt432.imgui4mc.ImGuiManager;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author TT432
 */
@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Shadow
    @Final
    private Window window;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void imgui4mc$init(CallbackInfo ci) {
        final String outputFolder = "./.natives";
        IOUtil.extractResource("imgui-java64.dll", outputFolder);
        IOUtil.extractResource("libimgui-java64.dylib", outputFolder);
        IOUtil.extractResource("libimgui-java64.so", outputFolder);
        IOUtil.extractResource("libimgui-javaarm64.dylib", outputFolder);
        System.setProperty("imgui.library.path", outputFolder);

        IOUtil.extractResource("SourceHanSans-Normal.ttc", outputFolder);

        ImGuiManager.loadWindows(window.getWindow());
    }
}
