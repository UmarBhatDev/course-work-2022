package Client.View.Scenes.login;

import Client.View.Scenes.Behaviors.Notification.NotificationLayer;
import Client.View.Scenes.Behaviors.UnDo.ReturnView;

public interface LogInSceneView extends ReturnView
{
    void onLoginEndedButtonClick(LogInScene.Container container, NotificationLayer notificationLayer);
}
