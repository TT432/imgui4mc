package io.github.tt432.imgui4mc;

import imgui.ImGui;

/**
 * @author TT432
 */
public class TestImGui extends Window{
    public TestImGui(long handle) {
        super(handle);
    }

    @Override
    public void process() {
        ImGui.text("Hello, world!"); // 添加文本
    }
}
