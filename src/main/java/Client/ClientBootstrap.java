package Client;

import Client.Loop.ClientLoop;
import Client.View.Initializer.UIInitializer;
import Client.View.Scenes.bootstrapScene.BootstrapScene;
import Client.View.Window.Window;

public class ClientBootstrap
{
    public static void main(String[] args)
    {
        try
        {
            ServerProvider connection = ServerProvider.connect();

            Window window = new Window();
            ClientLoop clientLoop = new ClientLoop(connection, window);
            UIInitializer.initialize(window, new BootstrapScene(clientLoop));

        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
}
