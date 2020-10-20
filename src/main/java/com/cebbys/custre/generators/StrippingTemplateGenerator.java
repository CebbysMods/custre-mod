package com.cebbys.custre.generators;

import com.cebbys.celib.directories.DirectoryHandler;
import com.cebbys.custre.Custre;
import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class StrippingTemplateGenerator {

    public static JsonObject generateTemplate(HashMap<Block, Block> map) {
        JsonObject object = new JsonObject();
        for (Block base : map.keySet()) {
            Block result = map.get(base);
            Identifier b = Registry.BLOCK.getId(base);
            Identifier r = Registry.BLOCK.getId(result);
            object.addProperty(b.toString(), r.toString());
        }
        return object;
    }

    public static void saveTemplate(JsonObject template, String templateName) {
        try {
            FileWriter writer = new FileWriter(DirectoryHandler.appendToPath(Custre.STRIPPING_TEMPLATE_DIRECTORY, templateName + ".json").toString());
            writer.write(template.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {

        }
    }
}
