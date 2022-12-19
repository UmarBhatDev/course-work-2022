package Client.View.Scenes.login;

import Client.View.Scenes.Behaviors.Notification.NotificationLayer;
import Client.View.Scenes.Behaviors.UnDo.ReturnableLayer;
import Client.View.Scenes.Layer;
import imgui.ImGui;
import imgui.flag.ImGuiInputTextFlags;
import imgui.type.ImString;

import static Client.View.Config.ViewConfig.*;

public class LogInScene extends NotificationLayer implements Layer, ReturnableLayer
{
    private static final int ITEM_WIDTH = 200;

    private final LogInSceneView signInSceneView;
    private final Container container;

    public LogInScene(LogInSceneView signInSceneView)
    {
        this.signInSceneView = signInSceneView;
        this.container = new Container();
    }

    public void layer()
    {
        renderReturnButton();
        renderNotificationPopup();
        renderMainLayer();
    }

    public void renderMainLayer()
    {
        setColorSchema();
        ImGui.setNextWindowPos(MainWindow.Position.x(), MainWindow.Position.y());
        ImGui.begin("Login", 10);
        ImGui.setWindowSize(300, 200);

        ImGui.setNextItemWidth(ITEM_WIDTH);
        ImGui.inputText("Login", container.Login, ImGuiInputTextFlags.CharsNoBlank);

        ImGui.newLine();
        ImGui.setNextItemWidth(ITEM_WIDTH);
        ImGui.inputText("Password", container.Password, ImGuiInputTextFlags.Password);

        ImGui.newLine();
        if (ImGui.button("Done", 200, 70)) 
            signInSceneView.onLoginEndedButtonClick(container, this);

        ImGui.end();
    }

    public void renderReturnButton()
    {
        ImGui.setNextWindowPos(ReturnButton.Position.x(), ReturnButton.Position.y());
        ImGui.begin("Return", ReturnButton.WindowFlag);
        if (ImGui.button("Return", ReturnButton.Size.x(), ReturnButton.Size.y()))
            signInSceneView.onReturnButtonClick();
        ImGui.end();
    }

    public static class Container
    {
        public ImString Login = new ImString(20);
        public ImString Password = new ImString(20);
    }
}
