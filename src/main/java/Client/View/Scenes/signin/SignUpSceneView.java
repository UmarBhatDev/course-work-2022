package Client.View.Scenes.signin;

import Client.View.Scenes.Behaviors.Notification.NotificationLayer;
import Client.View.Scenes.Behaviors.UnDo.ReturnView;

public interface SignUpSceneView extends ReturnView
{
    void onRegistrationCompletedButtonClick(SignUpScene.Container container, NotificationLayer notificationLayer);
}
