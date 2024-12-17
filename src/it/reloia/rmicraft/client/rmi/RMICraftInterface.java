package it.reloia.rmicraft.client.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMICraftInterface extends Remote {
    void userConnected() throws RemoteException;
    boolean checkConnection();
    void sendChatMessage(String message) throws RemoteException;
}
