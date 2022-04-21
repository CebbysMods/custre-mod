package lv.cebbys.mcmods.custre;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lv.cebbys.mcmods.custre.event.PlayerJoinServerCallback;
import lv.cebbys.mcmods.custre.mixins.StrippingRecipeAccessor;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.block.Block;
import net.minecraft.block.PillarBlock;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.resource.LifecycledResourceManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static lv.cebbys.mcmods.custre.Custre.CUSTRE_RELOAD_ID;
import static lv.cebbys.mcmods.custre.Custre.MODID;

class CustreServerListener implements SimpleSynchronousResourceReloadListener,
        ServerLifecycleEvents.EndDataPackReload, PlayerJoinServerCallback {
    private static final Logger LOGGER = LoggerFactory.getLogger("CustreResourceReloadListener");
    private static final Map<Block, Block> RECIPES = new HashMap<>();

    @Override
    public Identifier getFabricId() {
        return new Identifier(MODID, MODID + "_recipe");
    }

    @Override
    public void reload(ResourceManager manager) {
        RECIPES.clear();
        for (Identifier id : manager.findResources(MODID, path -> path.endsWith(".json"))) {
            try (InputStream stream = manager.getResource(id).getInputStream()) {
                JsonObject data = JsonParser.parseReader(new InputStreamReader(stream)).getAsJsonObject();
                Pair<PillarBlock, PillarBlock> recipe = parseStrippingRecipe(data);
                addRecipe(recipe.getLeft(), recipe.getRight());
            } catch (Exception e) {
                LOGGER.error("Error occurred while loading resource {}", id, e);
            }
        }
        StrippingRecipeAccessor.setStrippingRecipes(RECIPES);
    }

    @Override
    public void endDataPackReload(MinecraftServer server, LifecycledResourceManager resourceManager, boolean success) {
        Collection<ServerPlayerEntity> players = PlayerLookup.all(server);
        PacketByteBuf packet = createRecipePacket();
        players.forEach(player -> {
            ServerPlayNetworking.send(player, CUSTRE_RELOAD_ID, packet);
        });
    }

    public Pair<PillarBlock, PillarBlock> parseStrippingRecipe(JsonObject recipe) throws Exception {
        List<PillarBlock> result = new ArrayList<>();
        for (String key : new String[]{"ingredient", "result"}) {
            if (recipe.has(key)) {
                String value = recipe.get(key).getAsString();
                Block block = Registry.BLOCK.get(new Identifier(value));
                if (block instanceof PillarBlock) {
                    result.add((PillarBlock) block);
                    continue;
                }
                throw new Exception("Stripping recipe has invalid " + key + " value " + value + ". Block does not extend PillarBlock class");
            }
            throw new Exception("Stripping recipe missing key " + key);
        }
        return new Pair<>(result.get(0), result.get(1));
    }

    public void addRecipe(PillarBlock ingredient, PillarBlock result) throws Exception {
        if (RECIPES.containsKey(ingredient)) {
            throw new Exception("Stripping recipes already has " + Registry.BLOCK.getId(ingredient) + " as ingredient!");
        }
        RECIPES.put(ingredient, result);
    }

    private PacketByteBuf createRecipePacket() {
        PacketByteBuf buffer = PacketByteBufs.create();
        buffer.writeInt(RECIPES.size());
        RECIPES.forEach((in, out) -> {
            Identifier i = Registry.BLOCK.getId(in);
            Identifier o = Registry.BLOCK.getId(out);
            buffer.writeString(String.format("{\"i\":\"%s\",\"o\":\"%s\"}", i, o));
        });
        return buffer;
    }

    @Override
    public void playerJoin(MinecraftServer server, ServerPlayerEntity player) {
        PacketByteBuf packet = createRecipePacket();
        ServerPlayNetworking.send(player, CUSTRE_RELOAD_ID, packet);
    }
}
