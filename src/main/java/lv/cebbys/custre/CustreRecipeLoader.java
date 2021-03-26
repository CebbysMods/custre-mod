package lv.cebbys.custre;

import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lv.cebbys.celib.loggers.CelibLogger;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.block.PillarBlock;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

public class CustreRecipeLoader implements SimpleSynchronousResourceReloadListener {

	@Override
	public Identifier getFabricId() {
		return new Identifier(Custre.MODID, "recipes");
	}

	@Override
	public void apply(ResourceManager manager) {
		CustreRecipeRegistry.clearRecipeList();
        for(Identifier id : manager.findResources("custre", path -> path.endsWith(".json"))) {
            try {
            	InputStream stream = manager.getResource(id).getInputStream();
            	JsonObject data = new JsonParser().parse(new InputStreamReader(stream)).getAsJsonObject();
                Pair<PillarBlock, PillarBlock> recipe = CustreRecipeParser.parseStrippingRecipe(data);
                CelibLogger.log(id.toString());
                CustreRecipeRegistry.addRecipe(id, recipe.getLeft(), recipe.getRight());
            } catch(Exception e) {
            	CelibLogger.ferror("Error occurred while loading resource %s. %s", id.toString(), e.toString());
            }
        }
		CustreRecipeRegistry.updateAxeRecipes();
	}

}
