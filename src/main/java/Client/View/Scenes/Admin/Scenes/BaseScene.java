package Client.View.Scenes.Admin.Scenes;

import Client.View.Config.ViewConfig;
import Client.View.Scenes.Admin.AdminSceneView;
import Client.View.Scenes.Behaviors.Notification.NotificationLayer;
import Client.View.Scenes.Behaviors.UnDo.ReturnableLayer;
import Client.View.Scenes.Layer;
import imgui.ImGui;

public class BaseScene extends NotificationLayer implements Layer, ReturnableLayer
{
    protected final AdminSceneView adminSceneView;

    public BaseScene(AdminSceneView adminSceneView)
    {
        this.adminSceneView = adminSceneView;
    }

    @Override
    public void renderReturnButton()
    {
        ImGui.setNextWindowPos(ViewConfig.ReturnButton.Position.x(), ViewConfig.ReturnButton.Position.y());
        ImGui.begin("Выйти",ViewConfig.ReturnButton.WindowFlag);
        if (ImGui.button("Выйти", ViewConfig.ReturnButton.Size.x(), ViewConfig.ReturnButton.Size.y()))
            adminSceneView.onReturnButtonClick();
        ImGui.end();
    }

    @Override
    public void layer()
    {
        mainLayer();
        renderNotificationPopup();
        renderReturnButton();
    }

    public void mainLayer()
    {
        ViewConfig.setColorSchema();
        ImGui.setNextWindowPos(ViewConfig.MainWindow.Position.x(), ViewConfig.MainWindow.Position.y() - 100);
        ImGui.begin("Добро пожаловать",  ViewConfig.MainWindow.WindowFlag);
        ImGui.setWindowSize(600, 600);
        
        GlobalWindowOptions();

        ImGui.end();
    }
    
    protected void GlobalWindowOptions()
    {
        ImGui.newLine();
        ImGui.sameLine(50);
        if (ImGui.button("Мой аккаунт", 200, 100))
            adminSceneView.onMyAccountButtonClick(this);

        ImGui.newLine();
        ImGui.sameLine(50);
        if (ImGui.button("Отчеты", 200, 100))
            adminSceneView.onViewReportsButtonClicked(this);
    }
}
