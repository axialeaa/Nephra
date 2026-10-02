package com.axialeaa.nephra;

import com.axialeaa.nephra.network.ClientboundCoolingMagmaPayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class NephraClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(ClientboundCoolingMagmaPayload.TYPE, new ClientboundCoolingMagmaPayload.Receiver());
    }

}
