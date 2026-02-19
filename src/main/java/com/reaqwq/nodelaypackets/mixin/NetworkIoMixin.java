package com.reaqwq.nodelaypackets.mixin;

import net.minecraft.network.ClientConnection;
import net.minecraft.network.NetworkSide;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.PacketCallbacks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import io.netty.channel.Channel;
import org.jetbrains.annotations.Nullable;

@Mixin(ClientConnection.class)
public abstract class NetworkIoMixin {
    @Shadow
    private Channel channel;

    @Shadow
    private NetworkSide side;

    @Inject(method = "send(Lnet/minecraft/network/packet/Packet;Lnet/minecraft/network/PacketCallbacks;Z)V", at = @At("TAIL"))
    private void onSend(Packet<?> packet, @Nullable PacketCallbacks callbacks, boolean flush, CallbackInfo ci) {
        // Only flush on client-side connections (sending to server) to avoid
        // unnecessary server load.
        if (this.side == NetworkSide.CLIENTBOUND && this.channel != null && this.channel.isOpen()) {
            this.channel.flush();
        }
    }
}
