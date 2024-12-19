package it.reloia.rmicraft.client.rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMICraft {
    // in Java 21 - Security Manager is not needed

    public static RMICraft INSTANCE = new RMICraft();
    private RMICraftInterface rmiCraft;

    private static final String SERVER_IP = "localhost";

    private RMICraft() { }

    public void connect() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(SERVER_IP, 9001);

        rmiCraft = (RMICraftInterface) registry.lookup("RMICraft");
        rmiCraft.userConnected();
    }

    public boolean isConnected() {
        try {
            return rmiCraft.checkConnection();
        } catch (RemoteException e) {
            return false;
        }
    }

    public void sendChatMessage(String message) throws RemoteException {
        if (rmiCraft == null)
            throw new RemoteException("Not connected to server");
        if (!isConnected())
            throw new RemoteException("Connection lost");
        if (message.isEmpty())
            return;

        rmiCraft.sendChatMessage(message);
    }

    public void disconnect() {
        rmiCraft = null;
    }
}