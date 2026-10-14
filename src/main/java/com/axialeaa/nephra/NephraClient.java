package com.axialeaa.nephra;

import com.axialeaa.nephra.network.ClientboundMagmaCoolingPayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class NephraClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(ClientboundMagmaCoolingPayload.TYPE, new ClientboundMagmaCoolingPayload.Receiver());
    }

}
