package it.reloia.rmicraft.client;

import it.reloia.rmicraft.client.rmi.RMICraftImplementation;
import it.reloia.rmicraft.client.rmi.RMICraftInterface;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMICraftClient implements ClientModInitializer {
    private static final String SERVER_IP = "0.0.0.0";
    // To avoid these two from being garbage collected (Fuck you Java)
    private RMICraftInterface stub;
    private final RMICraftInterface rmiCraftImpl = new RMICraftImplementation(MinecraftClient.getInstance());

    @Override
    public void onInitializeClient() {
        try {
            stub = (RMICraftInterface) UnicastRemoteObject.exportObject(rmiCraftImpl, 0);
            Registry registry = LocateRegistry.createRegistry(9001);
            registry.rebind("RMICraft", stub);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
