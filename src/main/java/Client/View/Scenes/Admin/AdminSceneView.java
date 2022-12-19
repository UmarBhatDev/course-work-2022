package Client.View.Scenes.Admin;

import Client.View.Scenes.Behaviors.Notification.NotificationLayer;
import Client.View.Scenes.Behaviors.UnDo.ReturnView;

public interface AdminSceneView extends ReturnView
{
    void onJoinRequestButtonClick(NotificationLayer notificationLayer);
    void onMyAccountButtonClick(NotificationLayer notificationLayer);
    void onAccountInfoButtonClick();
    void onGetAllEmployeesInfoButtonClick(NotificationLayer notificationLayer);
    void onSetPosition(NotificationLayer notificationLayer);
    void onBoardsButtonClicked();
    void onViewReportsButtonClicked(NotificationLayer notificationLayer);
    void onBoardsEditButtonClicked();
    void onAccountsEditButtonClicked();
}
