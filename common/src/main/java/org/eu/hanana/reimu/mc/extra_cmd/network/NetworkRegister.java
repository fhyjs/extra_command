package org.eu.hanana.reimu.mc.extra_cmd.network;

import dev.architectury.networking.NetworkManager;
import dev.architectury.platform.Platform;
import dev.architectury.utils.Env;
import org.eu.hanana.reimu.mc.extra_cmd.network.s2c.*;

public class NetworkRegister {
    public static void register(){
        //NetworkManager.registerReceiver(NetworkManager.c2s(), C2SGetSuggestionPayload.TYPE,C2SGetSuggestionPayload.STREAM_CODEC, C2SGetSuggestionHandle::receive);
        if (Platform.getEnvironment() == Env.CLIENT) {
            NetworkManager.registerReceiver(NetworkManager.s2c(), S2CWindowOperationPayload.TYPE, S2CWindowOperationPayload.STREAM_CODEC, S2CWindowOperationHandler::receive);
            NetworkManager.registerReceiver(NetworkManager.s2c(), S2CActionOperationPayload.TYPE, S2CActionOperationPayload.STREAM_CODEC, S2CActionOperationHandler::receive);
            NetworkManager.registerReceiver(NetworkManager.s2c(), S2CToastOperationPayload.TYPE, S2CToastOperationPayload.STREAM_CODEC, S2CToastOperationHandler::receive);
            //NetworkManager.registerReceiver(NetworkManager.s2c(), S2CPayloadSendSetCommandField.TYPE, S2CPayloadSendSetCommandField.STREAM_CODEC, S2CHandlerSetCommandField::receive);
        }else {
            NetworkManager.registerS2CPayloadType(S2CWindowOperationPayload.TYPE, S2CWindowOperationPayload.STREAM_CODEC);
            NetworkManager.registerS2CPayloadType(S2CActionOperationPayload.TYPE, S2CActionOperationPayload.STREAM_CODEC);
            NetworkManager.registerS2CPayloadType(S2CToastOperationPayload.TYPE, S2CToastOperationPayload.STREAM_CODEC);
            //NetworkManager.registerS2CPayloadType(S2CPayloadSendSetCommandField.TYPE, S2CPayloadSendSetCommandField.STREAM_CODEC);
        }
    }
}
