package Client.View.Scenes.boardCreation;

import Client.View.Config.ViewConfig;
import Client.View.Scenes.Behaviors.Notification.NotificationLayer;
import Client.View.Scenes.Behaviors.UnDo.ReturnableLayer;
import Client.View.Scenes.Layer;
import imgui.ImGui;
import imgui.flag.ImGuiInputTextFlags;
import imgui.type.ImString;

public class BoardCreateScene extends NotificationLayer implements Layer, ReturnableLayer
{
    private static final int ITEM_WIDTH = 200;

    private final BoardCreateView boardCreateView;
    private final Container container;

    public BoardCreateScene(BoardCreateView boardCreateView)
    {
        this.boardCreateView = boardCreateView;
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
        ImGui.begin("Создание доски", 10);
        ImGui.setWindowSize(350, 500);

        ImGui.setNextItemWidth(ITEM_WIDTH);
        ImGui.inputText("Название", container.Name, ImGuiInputTextFlags.CharsNoBlank);

        ImGui.newLine();
        ImGui.setNextItemWidth(ITEM_WIDTH);
        ImGui.inputText("Дата начала спринта:", container.StartDate, ImGuiInputTextFlags.CharsNoBlank);

        ImGui.newLine();
        ImGui.newLine();

        ImGui.setNextItemWidth(ITEM_WIDTH);
        ImGui.inputText("Дата окончания спринта:", container.EndDate, ImGuiInputTextFlags.CharsNoBlank);

        ImGui.newLine();
        if (ImGui.button("Готово", 200, 70))
            boardCreateView.onBoardCreateCompletedButtonClick(container, this);

        ImGui.end();
    }
    
    public void renderReturnButton()
    {
        ImGui.setNextWindowPos(ViewConfig.ReturnButton.Position.x(), ViewConfig.ReturnButton.Position.y());
        ImGui.begin("Выйти",ViewConfig.ReturnButton.WindowFlag);
        if (ImGui.button("Выйти", ViewConfig.ReturnButton.Size.x(), ViewConfig.ReturnButton.Size.y()))
            boardCreateView.onReturnButtonClick();
        ImGui.end();
    }


    public static class Container
    {
        public ImString Name = new ImString(20);
        public ImString StartDate = new ImString(20);
        public ImString EndDate = new ImString(20);
    }
}
