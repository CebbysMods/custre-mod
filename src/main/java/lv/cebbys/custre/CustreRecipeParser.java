package lv.cebbys.custre;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

import net.minecraft.block.Block;
import net.minecraft.block.PillarBlock;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.Registry;

public class CustreRecipeParser {

	public static Pair<PillarBlock, PillarBlock> parseStrippingRecipe(JsonObject recipe) throws Exception {
		List<PillarBlock> result = new ArrayList<>();
		for(String key : new String[]{"ingredient", "result"}) {
			if(recipe.has(key)) {
				String value = recipe.get(key).getAsString();
				Block block = Registry.BLOCK.get(new Identifier(value));
				if(block == null) {
					throw new Exception("Stripping recipe has invalid " + key + " value " + value + ". Block does not exist");
				} else if(block instanceof PillarBlock) {
					result.add((PillarBlock) block);
					continue;
				}
				throw new Exception("Stripping recipe has invalid " + key + " value " + value + ". Block does not extend PillarBlock class");
			}
			throw new Exception("Stripping recipe missing key " + key);
		}
		return new Pair<>(result.get(0), result.get(1));
	}
}
