package lv.cebbys.mcmods.custre.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

@FunctionalInterface
public interface PlayerJoinServerCallback {
    Event<PlayerJoinServerCallback> EVENT = EventFactory.createArrayBacked(
            PlayerJoinServerCallback.class,
            (listeners) -> (server, player) -> {
                for (PlayerJoinServerCallback callback : listeners) {
                    callback.playerJoin(server, player);
                }
            }
    );

    void playerJoin(MinecraftServer server, ServerPlayerEntity player);
}
