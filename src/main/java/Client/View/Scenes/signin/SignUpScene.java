package Client.View.Scenes.signin;

import Client.View.Config.ViewConfig;
import Client.View.Scenes.Behaviors.Notification.NotificationLayer;
import Client.View.Scenes.Behaviors.UnDo.ReturnableLayer;
import Client.View.Scenes.Layer;
import Server.Entities.StaticEnumerable.Role;
import imgui.ImGui;
import imgui.flag.ImGuiInputTextFlags;
import imgui.type.ImInt;
import imgui.type.ImString;

public class SignUpScene extends NotificationLayer implements Layer, ReturnableLayer
{
    private static final int ITEM_WIDTH = 200;
    private final SignUpSceneView signUpSceneView;
    private final Container container;

    public SignUpScene(SignUpSceneView signUpSceneView)
    {
        this.signUpSceneView = signUpSceneView;
        container = new Container();
    }

    @Override
    public void layer()
    {
        renderReturnButton();
        renderNotificationPopup();
        mainLayer();
    }

    public void mainLayer()
    {
        ImGui.setNextWindowPos(ViewConfig.MainWindow.Position.x(), ViewConfig.MainWindow.Position.y());
        ImGui.begin("Sign up", 10);
        ImGui.setWindowSize(330, 500);

        ImGui.setNextItemWidth(ITEM_WIDTH);
        ImGui.inputText("Login", container.Login, ImGuiInputTextFlags.CharsNoBlank);

        ImGui.newLine();
        ImGui.setNextItemWidth(ITEM_WIDTH);
        ImGui.inputText("Password", container.Password, ImGuiInputTextFlags.Password);

        ImGui.newLine();
        ImGui.newLine();

        ImGui.setNextItemWidth(ITEM_WIDTH);
        ImGui.inputText("First Name", container.FirstName, ImGuiInputTextFlags.CharsNoBlank);

        ImGui.newLine();
        ImGui.setNextItemWidth(ITEM_WIDTH);
        ImGui.inputText("Last Name", container.LastName, ImGuiInputTextFlags.CharsNoBlank);

        ImGui.newLine();
        ImGui.setNextItemWidth(ITEM_WIDTH);
        ImGui.inputText("Date of Birth (yyyy-mm-dd)", container.DateOfBirth, ImGuiInputTextFlags.CharsNoBlank);
        
        ImGui.newLine();
        ImGui.setNextItemWidth(ITEM_WIDTH);
        ImGui.inputText("Email", container.Email, ImGuiInputTextFlags.CharsNoBlank);
        
        ImGui.newLine();
        ImGui.setNextItemWidth(ITEM_WIDTH);
        ImGui.inputText("Position", container.Position, ImGuiInputTextFlags.CharsNoBlank);


        ImGui.newLine();
        ImGui.setNextItemWidth(ITEM_WIDTH);
        if (ImGui.combo("Уровень доступа", container.selectedPosition,
                new String[]{Role.GUEST.toString(), Role.EMPLOYEE.toString(), Role.ADMIN.toString()}))
        {
            if (container.selectedPosition.get() == 0) container.Role = Role.GUEST;
            if (container.selectedPosition.get() == 1) container.Role = Role.EMPLOYEE;
            if (container.selectedPosition.get() == 2) container.Role = Role.ADMIN;
        }

        ImGui.newLine();
        if (ImGui.button("enter", 200, 70))
            signUpSceneView.onRegistrationCompletedButtonClick(container, this);

        ImGui.end();
    }
    
    @Override
    public void renderReturnButton()
    {
        ImGui.setNextWindowPos(ViewConfig.ReturnButton.Position.x(), ViewConfig.ReturnButton.Position.y());
        ImGui.begin("Return",ViewConfig.ReturnButton.WindowFlag);
        if (ImGui.button("Return", ViewConfig.ReturnButton.Size.x(), ViewConfig.ReturnButton.Size.y()))
            signUpSceneView.onReturnButtonClick();
        ImGui.end();
    }

    public static class Container
    {
        public ImString Login = new ImString(20);
        public ImString Password = new ImString(20);
        public ImString FirstName = new ImString(20);
        public ImString LastName = new ImString(20);
        public ImString DateOfBirth = new ImString();
        public ImString Email = new ImString();
        public ImString Position = new ImString();
        public Role Role = Server.Entities.StaticEnumerable.Role.GUEST;
        public ImInt selectedPosition = new ImInt(0);
    }
}
