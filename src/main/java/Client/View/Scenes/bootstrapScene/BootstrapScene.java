package Client.View.Scenes.bootstrapScene;

import Client.View.Config.ViewConfig;
import Client.View.Scenes.Behaviors.Exit.ExcitableLayer;
import imgui.ImGui;
import imgui.type.ImInt;

public class BootstrapScene implements  ExcitableLayer
{
    private final BootstrapSceneView bootstrapSceneView;
    private float[] color = new float[3];

    public BootstrapScene(BootstrapSceneView bootstrapSceneView) {
        this.bootstrapSceneView = bootstrapSceneView;
    }

    public void layer()
    {
        renderSetColorPopup();
        renderExitButton();
        mainLayer();
    }

    public void mainLayer()
    {
        ViewConfig.setColorSchema();
        ImGui.setNextWindowPos(ViewConfig.MainWindow.Position.x(), ViewConfig.MainWindow.Position.y());
        ImGui.begin("Welcome!",  ViewConfig.MainWindow.WindowFlag);
        ImGui.setWindowSize(300, 400);

        ImGui.sameLine(50);
        if (ImGui.button("Login", 200, 100))
            bootstrapSceneView.onLogInButtonClick();
        
        ImGui.newLine();
        
        ImGui.sameLine(50);
        if (ImGui.button("Sing up", 200, 100))
            bootstrapSceneView.onSignUpButtonClick();

        ImGui.end();
    }

    public void renderSetColorPopup()
    {
        ImGui.setNextWindowPos(ViewConfig.ExitButton.Position.x() + 200, ViewConfig.ExitButton.Position.y());
        ImGui.begin("Установить цвет",  ViewConfig.MainWindow.WindowFlag);
        ImGui.setWindowSize(300, 100);

        ImInt imInt = new ImInt(ViewConfig.COLOR_SCHEMA);
        if (ImGui.combo(
                "Схема цвета",
                imInt,
                new String[]{
                        "styleColorsLight",
                        "styleColorsDark",
                        "styleColorsClassic"
                }))
        {
            if (imInt.get() == 0) ViewConfig.COLOR_SCHEMA = 0;
            if (imInt.get() == 1) ViewConfig.COLOR_SCHEMA = 1;
            if (imInt.get() == 2) ViewConfig.COLOR_SCHEMA = 2;
        }

        ImGui.newLine();
        if(ImGui.colorEdit3("Color", color))
        {
            color[0] /= 225;
            color[1] /= 225;
            color[2] /= 225;

            bootstrapSceneView.onSetColorButtonDown(color);
        }
        ImGui.end();
    }

    public void renderExitButton()
    {
        ImGui.setNextWindowPos(ViewConfig.ExitButton.Position.x(), ViewConfig.ExitButton.Position.y());
        ImGui.begin("Выйти",10);
        ImGui.setWindowSize(100, 110);
        
        if (ImGui.button("Выйти", ViewConfig.ExitButton.Size.x(), ViewConfig.ExitButton.Size.y()))
            bootstrapSceneView.onButtonExit();
        ImGui.end();
    }
}
