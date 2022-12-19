package Client.View.Scenes.Admin.Scenes;

import Client.View.Scenes.Admin.AdminSceneView;
import imgui.ImGui;

public class EmployeeScene extends BaseScene
{
    public EmployeeScene(AdminSceneView adminSceneView)
    {
        super(adminSceneView);
    }
    
    @Override 
    public void GlobalWindowOptions()
    {
        super.GlobalWindowOptions();
        LocalWindowOptions();
    }
    
    public void LocalWindowOptions()
    {
        ImGui.newLine();
        ImGui.sameLine(50);
        if (ImGui.button("Доски", 200, 100))
            adminSceneView.onBoardsButtonClicked();
    }
}
