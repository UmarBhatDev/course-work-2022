package Client.View.Scenes.AccountSettings;

import Client.View.Config.ViewConfig;
import Client.View.Scenes.Behaviors.Notification.NotificationLayer;
import Client.View.Scenes.Behaviors.UnDo.ReturnableLayer;
import Client.View.Scenes.Layer;
import imgui.ImGui;
import imgui.flag.ImGuiInputTextFlags;
import imgui.type.ImString;

public class AccountSettingsLayer extends NotificationLayer implements Layer, ReturnableLayer
{
    private final AccountSettingsView accountSettingsView;
    private final Container container;

    public AccountSettingsLayer(AccountSettingsView accountSettingsView)
    {
        this.accountSettingsView = accountSettingsView;
        container = new Container();
    }

    public void layer()
    {
        mainLayer();
        renderNotificationPopup();
        renderReturnButton();
    }

    public void mainLayer()
    {
        ViewConfig.setColorSchema();
        ImGui.setNextWindowPos(ViewConfig.MainWindow.Position.x(), ViewConfig.MainWindow.Position.y());
        ImGui.begin("Account Settings",  ViewConfig.MainWindow.WindowFlag);
        ImGui.setWindowSize(300, 300);

        ImGui.sameLine(50);
        if (ImGui.button("Change password", 200, 100))
            container.passFlag = true;
        ImGui.newLine();
        ImGui.sameLine(50);
        if (ImGui.button("Change login", 200, 100))
            container.loginFlag = true;
        ImGui.newLine();
        ImGui.sameLine(50);
        if (ImGui.button("Change age", 200, 100))
            container.ageFlag = true;
        ImGui.newLine();
        ImGui.sameLine(50);
        if (ImGui.button("Change name", 200, 100))
            container.nameFlag = true;


        if(container.loginFlag) loginEditLayer();
        if(container.passFlag) passwordEditLayer();
        if(container.ageFlag) dateOfBirthEditLayer();
        if(container.nameFlag) nameEditLayer();

        ImGui.end();
    }

    public void loginEditLayer()
    {
        ImGui.setNextWindowPos(ViewConfig.MainWindow.Position.x(), ViewConfig.MainWindow.Position.y());
        ImGui.begin("Изменение логина",  ViewConfig.MainWindow.WindowFlag);
        ImGui.setWindowSize(300, 300);

        ImGui.setNextItemWidth(100);
        ImGui.inputText("Новый логин", container.Login, ImGuiInputTextFlags.CharsNoBlank);

        ImGui.newLine();
        if (ImGui.button("Готово", 200, 70))
            accountSettingsView.onEditLoginButtonClick(container, this);

        ImGui.end();
    }

    public void passwordEditLayer()
    {
        ImGui.setNextWindowPos(ViewConfig.MainWindow.Position.x(), ViewConfig.MainWindow.Position.y());
        ImGui.begin("Изменение пароля",  ViewConfig.MainWindow.WindowFlag);
        ImGui.setWindowSize(300, 300);

        ImGui.setNextItemWidth(100);
        ImGui.inputText("Новый пароль", container.Password, ImGuiInputTextFlags.Password);

        ImGui.newLine();
        if (ImGui.button("Готово", 200, 70))
            accountSettingsView.onEditPasswordButtonClick(container, this);

        ImGui.end();
    }

    public void dateOfBirthEditLayer()
    {
        ImGui.setNextWindowPos(ViewConfig.MainWindow.Position.x(), ViewConfig.MainWindow.Position.y());
        ImGui.begin("Изменение даты рождения",  ViewConfig.MainWindow.WindowFlag);
        ImGui.setWindowSize(300, 300);

        ImGui.setNextItemWidth(100);
        ImGui.inputText("Дата рождения", container.DateOfBirth);

        ImGui.newLine();
        if (ImGui.button("Готово", 200, 70))
            accountSettingsView.onEditDateOfBirthButtonClick(container, this);

        ImGui.end();
    }

    public void nameEditLayer()
    {
        ImGui.setNextWindowPos(ViewConfig.MainWindow.Position.x(), ViewConfig.MainWindow.Position.y());
        ImGui.begin("Изменение имени",  ViewConfig.MainWindow.WindowFlag);
        ImGui.setWindowSize(300, 300);

        ImGui.setNextItemWidth(100);
        ImGui.inputText("Имя", container.FirstName, ImGuiInputTextFlags.CharsNoBlank);

        ImGui.setNextItemWidth(100);
        ImGui.inputText("Фамилия", container.LastName, ImGuiInputTextFlags.CharsNoBlank);

        ImGui.newLine();
        if (ImGui.button("Готово", 200, 70))
            accountSettingsView.onEditNameButtonClick(container, this);

        ImGui.end();
    }

    @Override
    public void renderReturnButton()
    {
        ImGui.setNextWindowPos(ViewConfig.ReturnButton.Position.x(), ViewConfig.ReturnButton.Position.y());
        ImGui.begin("Выйти",ViewConfig.ReturnButton.WindowFlag);
        if (ImGui.button("Выйти", ViewConfig.ReturnButton.Size.x(), ViewConfig.ReturnButton.Size.y()))
            accountSettingsView.onReturnButtonClick();
        ImGui.end();
    }

    public static class Container
    {
        public boolean loginFlag = false;
        public boolean passFlag = false;
        public boolean nameFlag = false;
        public boolean ageFlag = false;

        public ImString Login = new ImString(20);
        public ImString Password = new ImString(20);

        public ImString FirstName = new ImString(20);
        public ImString LastName = new ImString(20);
        public ImString DateOfBirth = new ImString();
    }
}

