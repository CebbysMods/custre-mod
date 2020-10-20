package com.cebbys.custre.handlers;

import com.cebbys.celib.directories.DirectoryHandler;
import com.cebbys.celib.loaders.JsonLoader;
import com.cebbys.celib.loggers.CelibLogger;
import com.cebbys.custre.Custre;
import com.cebbys.custre.registrators.StrippingRecipeRegistrator;
import com.cebbys.custre.validators.StrippingRecipeValidator;
import com.google.gson.JsonElement;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Map.Entry;
import java.util.Set;

public class StrippingRecipeHandler {

    public static void loadStrippingRecipes() {
        FilenameFilter jsonFilter = (file, s) -> s.endsWith(".json");
        File[] files = Custre.STRIPPING_DIRECTORY.toFile().listFiles( jsonFilter );
        for( File f : files ) {
            if( StrippingRecipeValidator.isValidJson( f ) ) {
                Set<Entry<String, JsonElement>> set = JsonLoader.loadJson(f).getAsJsonObject().entrySet();
                for (Entry<String, JsonElement> e : set) {
                    if (StrippingRecipeValidator.isValidEntry(e)) {
                        Block base = Registry.BLOCK.get( getBlockIdentifier( e.getKey() ) );
                        Block result = Registry.BLOCK.get( getBlockIdentifier( e.getValue().getAsString() ) );
                        StrippingRecipeRegistrator.registerStrippingRecipe( base, result );
                    } else {
                        CelibLogger.error( Custre.MOD_ID, "Failed to register stripping recipe " + e.toString() );
                    }
                }
            } else {
                CelibLogger.error( Custre.MOD_ID, "Failed to validate json file " + f.toPath().toString() );
            }
        }
    }



    public static void registerRecipes() {
        if( StrippingRecipeRegistrator.getStrippingRecipes().size() > 0 ) {
            StrippingRecipeRegistrator.registerRecipesToAxe();
        }
    }



    public static void initializeDirectories() {
        if( ! Custre.STRIPPING_DIRECTORY.toFile().exists() ) {
            DirectoryHandler.initDirectory( Custre.STRIPPING_DIRECTORY );
        }
        if( ! Custre.STRIPPING_TEMPLATE_DIRECTORY.toFile().exists() ) {
            DirectoryHandler.initDirectory( Custre.STRIPPING_TEMPLATE_DIRECTORY );
        }
    }



    private static Identifier getBlockIdentifier(String id ) {
        int index = id.indexOf( ":" );
        String mod_id = id.substring( 0, index );
        String block_id = id.substring( index + 1 );
        return new Identifier( mod_id, block_id );
    }
}
