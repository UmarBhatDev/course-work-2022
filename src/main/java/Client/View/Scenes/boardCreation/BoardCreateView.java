package Client.View.Scenes.boardCreation;

import Client.View.Scenes.Behaviors.Notification.NotificationLayer;
import Client.View.Scenes.Behaviors.UnDo.ReturnView;

public interface BoardCreateView extends ReturnView
{
    void onBoardCreateCompletedButtonClick(BoardCreateScene.Container container, NotificationLayer notificationLayer);
}
