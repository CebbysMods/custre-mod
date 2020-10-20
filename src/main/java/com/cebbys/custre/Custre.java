package com.cebbys.custre;

import com.cebbys.celib.directories.CelibDirectories;
import com.cebbys.celib.directories.DirectoryHandler;
import com.cebbys.custre.generators.StrippingTemplateGenerator;
import com.cebbys.custre.handlers.StrippingRecipeHandler;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.nio.file.Path;
import java.util.HashMap;

public class Custre implements ModInitializer {

    public static final String MOD_ID = "custre";

    public static final Path STRIPPING_DIRECTORY;
    public static final Path STRIPPING_TEMPLATE_DIRECTORY;

    private static final HashMap<Block, Block> vanillaTemplate;

    static {
        STRIPPING_DIRECTORY = DirectoryHandler.appendToPath(CelibDirectories.LIB_DIRECTORY, "recipes/stripping");
        STRIPPING_TEMPLATE_DIRECTORY = DirectoryHandler.appendToPath(CelibDirectories.LIB_DIRECTORY, "templates/stripping");

        vanillaTemplate = new HashMap<Block, Block>() {
            {
                put(Blocks.OAK_WOOD, Blocks.STRIPPED_OAK_WOOD);
                put(Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG);
                put(Blocks.DARK_OAK_WOOD, Blocks.STRIPPED_DARK_OAK_WOOD);
                put(Blocks.DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG);
                put(Blocks.ACACIA_WOOD, Blocks.STRIPPED_ACACIA_WOOD);
                put(Blocks.ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG);
                put(Blocks.BIRCH_WOOD, Blocks.STRIPPED_BIRCH_WOOD);
                put(Blocks.BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG);
                put(Blocks.JUNGLE_WOOD, Blocks.STRIPPED_JUNGLE_WOOD);
                put(Blocks.JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG);
                put(Blocks.SPRUCE_WOOD, Blocks.STRIPPED_SPRUCE_WOOD);
                put(Blocks.SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG);
                put(Blocks.WARPED_STEM, Blocks.STRIPPED_WARPED_STEM);
                put(Blocks.WARPED_HYPHAE, Blocks.STRIPPED_WARPED_HYPHAE);
                put(Blocks.CRIMSON_STEM, Blocks.STRIPPED_CRIMSON_STEM);
                put(Blocks.CRIMSON_HYPHAE, Blocks.STRIPPED_CRIMSON_HYPHAE);
            }
        };
    }

    @Override
    public void onInitialize() {
        StrippingRecipeHandler.initializeDirectories();

        StrippingTemplateGenerator.saveTemplate(StrippingTemplateGenerator.generateTemplate(vanillaTemplate), "vanilla");

        StrippingRecipeHandler.loadStrippingRecipes();
        StrippingRecipeHandler.registerRecipes();
    }
}
