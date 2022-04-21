package lv.cebbys.mcmods.custre;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lv.cebbys.mcmods.custre.event.PlayerJoinServerCallback;
import lv.cebbys.mcmods.custre.mixins.StrippingRecipeAccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class CustreClientListener implements ClientPlayNetworking.PlayChannelHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger("CustreResourceReloadListener");
    private static final Map<Block, Block> RECIPES = new HashMap<>();

    @Override
    public void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        readRecipePacket(buf);
        StrippingRecipeAccessor.setStrippingRecipes(RECIPES);
    }

    private void readRecipePacket(PacketByteBuf packet) {
        RECIPES.clear();
        int size = packet.readInt();
        for (int c = 0; c < size; c++) {
            JsonObject json = JsonParser.parseString(packet.readString()).getAsJsonObject();
            Block i = Registry.BLOCK.get(new Identifier(json.get("i").getAsString()));
            Block o = Registry.BLOCK.get(new Identifier(json.get("o").getAsString()));
            RECIPES.put(i, o);
        }
    }
}
