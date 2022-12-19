package Server.Session;

import Infrastructure.Disposable;
import Server.TransferMessageContainer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class SessionSteam implements Disposable
{
    private final Socket forwardSocket;
    public final ObjectInputStream in;
    public final ObjectOutputStream out;

    public SessionSteam(Socket forwardSocket) throws Exception
    {
        this.forwardSocket = forwardSocket;

        out = new ObjectOutputStream(forwardSocket.getOutputStream());
        in = new ObjectInputStream(forwardSocket.getInputStream());
    }

    public void send(Serializable object)
    {
        try
        {
            out.writeObject(object);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Serializable accept() 
    {
        try
        {
            return (Serializable) in.readObject();
        }
        catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void startAction(String actionKey) 
    {
        try
        {
            out.writeObject(new TransferMessageContainer(actionKey, ""));
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void startAction(TransferMessageContainer transferMessageContainer)
    {
        try
        {
            out.writeObject(transferMessageContainer);
        } 
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String waitAction()
    {
        try
        {
            return ((TransferMessageContainer) in.readObject()).Key();
        }
        catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public TransferMessageContainer waitMessage()
    {
        try
        {
            return ((TransferMessageContainer) in.readObject());
        }
        catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void dispose() 
    {
        try
        {
            in.close();
            out.close();
            forwardSocket.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        
    }
}
