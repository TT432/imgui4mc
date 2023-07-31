package io.github.tt432.imgui4mc;

import imgui.ImFont;
import imgui.ImFontAtlas;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.type.ImBoolean;
import imgui.type.ImString;

import java.awt.*;

/**
 * @author TT432
 */
public class TestImGui extends Window {
    public TestImGui(long handle) {
        super(handle);
    }

    private boolean bool;

    ImBoolean imBoolean = new ImBoolean(bool);

    Color color = new Color(0, 0, 0);

    float[] col = new float[]{color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f};

    String string = "测试AAA";

    ImString imString = new ImString(string, 100);

    ImFont font1;

    ImFont font2;

    @Override
    protected void initImGui() {
        super.initImGui();
        ImGuiIO io = ImGui.getIO();
        ImFontAtlas fonts = io.getFonts();
        font1 = fonts.addFontFromFileTTF("./.natives/SourceHanSans-Normal.ttc", 20.0f, ImFontAtlasFix.getGlyphRangesAll());
        font2 = fonts.addFontFromFileTTF("./.natives/SourceHanSans-Normal.ttc", 25.0f, ImFontAtlasFix.getGlyphRangesAll());
    }

    @Override
    public void process() {
        ImGui.textWrapped("叠席是房间里供人坐或卧的一种家具。");
        ImGui.textWrapped("畳（たたみ）は、日本で利用されている伝統的な床材。");
        ImGui.textWrapped("다다미(일본어: 畳 다타미[*])는 일본에서 사용되는 전통식 바닥재를 말한다.");
        ImGui.pushFont(font2);
        ImGui.textWrapped("Тата́ми (яп. 畳, дословно «складывание; то, что складывается») — маты, которыми в Японии застилают полы домов (традиционного типа)[1].");
        ImGui.popFont();
        ImGui.textWrapped("A tatami (畳) is a type of mat used as a flooring material in traditional Japanese-style rooms.");
        if (ImGui.checkbox("bool", imBoolean)) {
            bool = imBoolean.get();
            System.out.println(bool);
        }
        if (ImGui.colorEdit3("color", col)) {
            color = new Color(col[0], col[1], col[2]);
            System.out.println(color);
        }
        if (ImGui.inputText("text", imString)) {
            string = imString.get();
            System.out.println(string);
        }
    }
}
