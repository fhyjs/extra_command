package org.eu.hanana.reimu.mc.extra_cmd.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.eu.hanana.reimu.mc.lcr.LCRMod;


public record S2CWindowOperationPayload(String operation,String data) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<S2CWindowOperationPayload> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(LCRMod.MOD_ID, "s2c_window_operation"));

    // Each pair of elements defines the stream codec of the element to encode/decode and the getter for the element to encode
    // 'name' will be encoded and decoded as a string
    // 'age' will be encoded and decoded as an integer
    // The final parameter takes in the previous parameters in the order they are provided to construct the payload object
    public static final StreamCodec<ByteBuf, S2CWindowOperationPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            S2CWindowOperationPayload::operation,
            ByteBufCodecs.STRING_UTF8,
            S2CWindowOperationPayload::data,
            S2CWindowOperationPayload::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
