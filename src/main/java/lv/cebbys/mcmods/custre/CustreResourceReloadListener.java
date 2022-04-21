package lv.cebbys.mcmods.custre;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lv.cebbys.mcmods.custre.mixins.StrippingRecipeAccessor;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.block.Block;
import net.minecraft.block.PillarBlock;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class CustreResourceReloadListener implements SimpleSynchronousResourceReloadListener {

    private static final Logger logger = LoggerFactory.getLogger(CustreResourceReloadListener.class);
    private static final Map<Block, Block> recipes;

    @Override
    public Identifier getFabricId() {
        return new Identifier(Custre.MODID, "recipes");
    }

    @Override
    public void reload(ResourceManager manager) {
        clearRecipeList();
        for (Identifier id : manager.findResources("custre", path -> path.endsWith(".json"))) {
            try (InputStream stream = manager.getResource(id).getInputStream()) {
                JsonObject data = JsonParser.parseReader(new InputStreamReader(stream)).getAsJsonObject();
                Pair<PillarBlock, PillarBlock> recipe = parseStrippingRecipe(data);
                addRecipe(recipe.getLeft(), recipe.getRight());
            } catch (Exception e) {
                logger.error("Error occurred while loading resource {}", id, e);
            }
        }
        updateAxeRecipes();
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

    public void clearRecipeList() {
        recipes.clear();
    }

    public void addRecipe(PillarBlock ingredient, PillarBlock result) throws Exception {
        if (recipes.containsKey(ingredient)) {
            throw new Exception("Stripping recipes already has " + Registry.BLOCK.getId(ingredient) + " as ingredient!");
        }
        recipes.put(ingredient, result);
    }

    public void updateAxeRecipes() {
        try {
            StrippingRecipeAccessor.setStrippingRecipes(recipes);
        } catch (Exception e) {
            logger.error("An exception has occurred while registering stripping recipes.", e);
        }
    }

    static {
        recipes = new HashMap<>();
    }
}
