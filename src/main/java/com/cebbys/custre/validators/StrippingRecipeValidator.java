package com.cebbys.custre.validators;

import com.cebbys.celib.loaders.JsonLoader;
import com.google.gson.JsonElement;
import net.minecraft.block.Block;
import net.minecraft.block.PillarBlock;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.io.File;
import java.util.Map.Entry;

public class StrippingRecipeValidator {



    public static final Block AIR = Registry.BLOCK.get( new Identifier( "minecraft", "air" ) );



    public static boolean isValidEntry( Entry< String,JsonElement > e ) {
        Block o0 = Registry.BLOCK.get( getBlockIdentifier( e.getKey() ) );
        if( o0 == AIR ) return false;
        else if( ! ( o0 instanceof PillarBlock ) ) return false;
        JsonElement e1 = e.getValue();
        if( e1.isJsonPrimitive() ) {
            if( e1.getAsJsonPrimitive().isString() ) {
                Block o1 = Registry.BLOCK.get( getBlockIdentifier( e1.getAsString() ) );
                if( o1 != AIR ) return true;
            }
        }
        return false;
    }



    public static boolean isValidJson( File f ) {
        JsonElement j = JsonLoader.loadJson( f );
        if( j != null ) {
            if( j.isJsonObject() ) {
                return true;
            }
        }
        return false;
    }



    private static Identifier getBlockIdentifier(String id ) {
        int index = id.indexOf( ":" );
        String mod_id = id.substring( 0, index );
        String block_id = id.substring( index + 1 );
        return new Identifier( mod_id, block_id );
    }
}
