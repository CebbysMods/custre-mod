package lv.cebbys.mcmods.custre;

import lv.cebbys.mcmods.custre.datapack.CustreDatapacks;
import lv.cebbys.mcmods.custre.event.PlayerJoinServerCallback;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

public class Custre implements ModInitializer, ClientModInitializer {
    public static final String MODID = "custre";
    public static final Identifier CUSTRE_RELOAD_ID = new Identifier(MODID, "custre_recipe_reload");

    @Override
    public void onInitialize() {
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new CustreServerListener());
        PlayerJoinServerCallback.EVENT.register(new CustreServerListener());
        ServerLifecycleEvents.END_DATA_PACK_RELOAD.register(new CustreServerListener());
        CustreDatapacks.registerCustreDatapacks();
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(CUSTRE_RELOAD_ID, new CustreClientListener());
    }
}
