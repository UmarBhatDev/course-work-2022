package Client.View.Scenes.Admin.Scenes;

import Client.View.Scenes.Admin.AdminSceneView;
import imgui.ImGui;

public class AdminScene extends EmployeeScene
{
    public AdminScene(AdminSceneView adminSceneView)
    {
        super(adminSceneView);
    }
    
    @Override
    public void GlobalWindowOptions()
    {
        super.GlobalWindowOptions();
        PrivateWindowOptions();
    }

    private void PrivateWindowOptions()
    {
        ImGui.newLine();
        ImGui.sameLine(50);
        if (ImGui.button("Accounts", 200, 100))
            adminSceneView.onAccountsEditButtonClicked();
        
        ImGui.newLine();
        ImGui.sameLine(50);
        if (ImGui.button("Boards", 200, 100))
            adminSceneView.onBoardsEditButtonClicked();
    }
}
