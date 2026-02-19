package com.reaqwq.nodelaypackets.mixin;

import net.minecraft.network.ClientConnection;
import net.minecraft.network.NetworkSide;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;

@Mixin(ClientConnection.class)
public class ClientConnectionMixin {
    /**
     * Using Object for the 4th parameter to avoid "symbol not found" if the class
     * name is unstable or missing in current mappings.
     * The Mixin transformer will still be able to match the descriptor if the
     * method exists.
     */
    @Inject(method = "addHandlers(Lio/netty/channel/ChannelPipeline;Lnet/minecraft/network/NetworkSide;ZLnet/minecraft/network/PacketSizeLog;)V", at = @At("TAIL"))
    private void onAddHandlers(ChannelPipeline pipeline, NetworkSide side, boolean transitions, Object packetSizeLog,
            CallbackInfo ci) {
        if (side == NetworkSide.CLIENTBOUND) {
            pipeline.channel().config().setOption(ChannelOption.TCP_NODELAY, true);
        }
    }
}
