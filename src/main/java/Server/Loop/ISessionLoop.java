package Server.Loop;

import Infrastructure.Disposable;
import Server.Bootstrap.Token.CancellationSessionToken;

public interface ISessionLoop extends Disposable
{
    void startSession(CancellationSessionToken token);

}
