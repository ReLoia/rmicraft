package it.reloia.rmicraft.client.rmi;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import java.rmi.RemoteException;

public class RMICraftImplementation implements RMICraftInterface {
    MinecraftClient client;

    public RMICraftImplementation(MinecraftClient client) {
        this.client = client;
    }

    @Override
    public void userConnected() throws RemoteException {
        if (client == null || client.player == null)
            throw new RemoteException("Waiting for player to join a world");

        client.player.sendMessage(Text.of("User connected"), false);
    }

    @Override
    public boolean checkConnection() throws RemoteException {
        return true;
    }

    @Override
    public void sendChatMessage(String message) {
        if (client == null || client.getNetworkHandler() == null)
            return;

        client.getNetworkHandler().sendChatMessage(message);
    }
}
