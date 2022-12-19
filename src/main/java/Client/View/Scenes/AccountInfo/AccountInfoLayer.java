package Client.View.Scenes.AccountInfo;

import Client.View.Config.ViewConfig;
import Client.View.Scenes.Behaviors.UnDo.ReturnView;
import Client.View.Scenes.Behaviors.UnDo.ReturnableLayer;
import Client.View.Scenes.Layer;
import Server.Entities.Employee;
import imgui.ImGui;

public class AccountInfoLayer implements Layer, ReturnableLayer
{
    private static final int ITEM_WIDTH = 200;
    private final ReturnView returnView;
    private final Employee employee;
    
    public AccountInfoLayer(ReturnView returnView, Employee employee)
    {
        this.returnView = returnView;
        this.employee = employee;
    }

    public void layer()
    {
        mainLayer();
        renderReturnButton();
    }

    public void mainLayer()
    {
        ViewConfig.setColorSchema();
        ImGui.setNextWindowPos(ViewConfig.MainWindow.Position.x(), ViewConfig.MainWindow.Position.y());
        ImGui.begin("Инфо", 1);
        ImGui.setWindowSize(500, 500);

        ImGui.setNextItemWidth(ITEM_WIDTH);
        ImGui.text("Login - " + employee.getUser().getLogin());

        ImGui.newLine();
        ImGui.setNextItemWidth(ITEM_WIDTH);
        ImGui.text("Registration date - " + employee.getUser().getRegistrationDate().toString());

        ImGui.newLine();
        ImGui.newLine();

        ImGui.setNextItemWidth(ITEM_WIDTH);
        ImGui.text("First Name - " + employee.getPerson().getFirstName());

        ImGui.newLine();
        ImGui.setNextItemWidth(ITEM_WIDTH);
        ImGui.text("Last Name - " + employee.getPerson().getLastName());
        
        ImGui.newLine();
        ImGui.setNextItemWidth(ITEM_WIDTH);
        ImGui.text("Date of Birth - " + employee.getPerson().getDateOfBirth().toString());

        ImGui.newLine();
        ImGui.setNextItemWidth(ITEM_WIDTH);
        ImGui.text("Email - " + employee.getPerson().getEmail());

        ImGui.newLine();
        ImGui.setNextItemWidth(ITEM_WIDTH);
        ImGui.text("Position - " + employee.getPosition());

        ImGui.newLine();
        
        ImGui.end();
    }

    public void renderReturnButton()
    {
        ImGui.setNextWindowPos(ViewConfig.ReturnButton.Position.x(), ViewConfig.ReturnButton.Position.y());
        ImGui.begin("Return",ViewConfig.ReturnButton.WindowFlag);
        if (ImGui.button("Return", ViewConfig.ReturnButton.Size.x(), ViewConfig.ReturnButton.Size.y()))
            returnView.onReturnButtonClick();
        ImGui.end();
    }
}
