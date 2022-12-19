package Client.View.Scenes.Behaviors.Notification;

import imgui.ImGui;

import static Client.View.Config.ViewConfig.NotificationWindow;

public abstract class NotificationLayer
{
    public boolean NotificationFlag = false;

    public String Message;
    
    public void renderNotificationPopup()
    {
        if (NotificationFlag)
        {
            ImGui.setNextWindowPos(NotificationWindow.Position.x(), NotificationWindow.Position.y());
            ImGui.begin(Message, NotificationWindow.WindowFlag);
            ImGui.setWindowSize(NotificationWindow.Size.x(), NotificationWindow.Size.y());
            if (ImGui.button("okay", 200, 70))
                NotificationFlag = false;
            ImGui.end();
        }
    }
}
