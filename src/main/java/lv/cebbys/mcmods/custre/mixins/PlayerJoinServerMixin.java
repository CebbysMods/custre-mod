package lv.cebbys.mcmods.custre.mixins;

import lv.cebbys.mcmods.custre.event.PlayerJoinServerCallback;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerManager.class)
public class PlayerJoinServerMixin {
    @Final
    @Shadow
    private MinecraftServer server;

    @Inject(at = @At(value = "TAIL"), method = "onPlayerConnect")
    private void onJoin(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci) {
        PlayerJoinServerCallback.EVENT.invoker().playerJoin(server, player);
    }
}
