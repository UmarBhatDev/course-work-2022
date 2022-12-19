package Client.View.Scenes.bootstrapScene;

import Client.View.Scenes.Behaviors.Exit.ExitView;

public interface BootstrapSceneView extends ExitView
{
    void onLogInButtonClick();
    void onSignUpButtonClick();
    void onSetColorButtonDown(float[] color);
}
