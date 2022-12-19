package Client.View.Scenes.Admin;

import Client.View.Config.ViewConfig;
import Client.View.Scenes.Behaviors.Notification.NotificationLayer;
import Client.View.Scenes.Behaviors.UnDo.ReturnableLayer;
import Client.View.Scenes.Layer;
import Server.Entities.StaticEnumerable.Role;
import imgui.ImGui;

public class AdminScene extends NotificationLayer implements Layer, ReturnableLayer
{
    private final AdminSceneView adminSceneView;
    private final boolean isMiddle;
    private final boolean isAdmin;

    public AdminScene(AdminSceneView adminSceneView, Role position)
    {
        this.adminSceneView = adminSceneView;
        isAdmin = position.equals(Role.ADMIN);
        isMiddle =  (position.equals(Role.ADMIN) || position.equals(Role.EMPLOYEE));
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
        ImGui.begin("Welcome admin",  ViewConfig.MainWindow.WindowFlag);
        ImGui.setWindowSize(600, 600);

        ImGui.newLine();
        ImGui.sameLine(50);
        if (ImGui.button("Account Settings", 200, 100))
            adminSceneView.onMyAccountButtonClick(this);


        ImGui.sameLine(300);
        if (ImGui.button("Account info", 200, 100))
            adminSceneView.onAccountInfoButtonClick();
        

        if(isAdmin)
        {
            ImGui.newLine();
            ImGui.sameLine(50);
            if (ImGui.button("Requests to join company", 200, 100))
                adminSceneView.onJoinRequestButtonClick(this);


            ImGui.sameLine(300);
            if (ImGui.button("All Employees info", 200, 100))
                adminSceneView.onGetAllEmployeesInfoButtonClick(this);

            ImGui.sameLine(300);
            if (ImGui.button("Set position", 200, 100))
                adminSceneView.onSetPosition(this);

        }
        if(isMiddle)
        {
            ImGui.newLine();
            ImGui.sameLine(50);
            if (ImGui.button("add transaction", 200, 100))
                adminSceneView.onBoardsButtonClicked();
        }

        ImGui.newLine();
        ImGui.sameLine(50);
        if (ImGui.button("write budget data in file", 200, 100))
            adminSceneView.onViewReportsButtonClicked(this);

        ImGui.end();
    }

    @Override
    public void renderReturnButton()
    {
        ImGui.setNextWindowPos(ViewConfig.ReturnButton.Position.x(), ViewConfig.ReturnButton.Position.y());
        ImGui.begin("Return",ViewConfig.ReturnButton.WindowFlag);
        if (ImGui.button("Return", ViewConfig.ReturnButton.Size.x(), ViewConfig.ReturnButton.Size.y()))
            adminSceneView.onReturnButtonClick();
        ImGui.end();
    }
}
