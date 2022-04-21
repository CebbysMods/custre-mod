package lv.cebbys.mcmods.custre;

import lv.cebbys.mcmods.custre.datapack.CustreDatapacks;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;

public class Custre implements ModInitializer {

    public static final String MODID = "custre";

    @Override
    public void onInitialize() {
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new CustreResourceReloadListener());
        CustreDatapacks.registerCustreDatapacks();
    }
}
