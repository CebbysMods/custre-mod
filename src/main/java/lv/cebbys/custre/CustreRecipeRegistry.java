package lv.cebbys.custre;

import java.util.HashMap;
import java.util.Map;

import lv.cebbys.celib.loggers.CelibLogger;
import lv.cebbys.custre.mixins.StrippingRecipeAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.PillarBlock;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CustreRecipeRegistry {

	private static Map<Block, Block> recipes;
	
	public static void addRecipe(Identifier id, PillarBlock ingredient, PillarBlock result) throws Exception {
		if (recipes.keySet().contains(ingredient)) {
			throw new Exception("Stripping recipes already has " + Registry.BLOCK.getId(ingredient).toString() + " as ingredient!");
		}
		recipes.put(ingredient, result);
	}
	
	public static void clearRecipeList() {
		recipes.clear();
	}
	
	public static void updateAxeRecipes() {
		try {
			StrippingRecipeAccessor.setStrippingRecipes(recipes);
		} catch (Exception e) {
			CelibLogger.ferror("An exception has occurred while registering stripping recipes. %s", e);
		}
	}
	
	static {
		recipes = new HashMap<>();
	}
}
