package com.cebbys.custre.registrators;

import com.cebbys.celib.loggers.CelibLogger;
import com.cebbys.custre.Custre;
import com.cebbys.custre.mixins.StrippingRecipeAccessor;
import net.minecraft.block.Block;

import java.util.HashMap;

public class StrippingRecipeRegistrator {

    private static HashMap<Block, Block> strippingRecipes;

    static {
        strippingRecipes = new HashMap<>();
    }

    public static void registerStrippingRecipe(Block base, Block result) {
        if (!strippingRecipes.keySet().contains(base)) {
            strippingRecipes.put(base, result);
            CelibLogger.log(Custre.MOD_ID, getRecipeResultString(base, result));
        } else {
            CelibLogger.error(Custre.MOD_ID, getRecipeFailedString(base));
        }
    }

    public static HashMap<Block, Block> getStrippingRecipes() {
        return strippingRecipes;
    }

    private static String getRecipeResultString(Block base, Block result) {
        String b = base.toString();
        String r = result.toString();
        return "Successfully registered stripping recipe " + b + " -> " + r + " !";
    }

    private static String getRecipeFailedString(Block base) {
        String b = base.toString();
        return "Failed to register recipe base " + b + " exists in the list !";
    }

    public static void registerRecipesToAxe() {
        StrippingRecipeAccessor.setStrippingBlocks(strippingRecipes);
    }
}
