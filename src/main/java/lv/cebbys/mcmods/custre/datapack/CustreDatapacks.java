package lv.cebbys.mcmods.custre.datapack;

import com.mojang.bridge.game.PackType;
import lv.cebbys.mcmods.custre.Custre;
import lv.cebbys.mcmods.respro.api.ResproRegistry;
import lv.cebbys.mcmods.respro.imp.builders.datapack.DataPackBuilder;
import lv.cebbys.mcmods.respro.imp.resource.recipe.CustreRecipeResource;
import net.minecraft.SharedConstants;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.minecraft.block.Blocks.ACACIA_LOG;
import static net.minecraft.block.Blocks.ACACIA_WOOD;
import static net.minecraft.block.Blocks.BIRCH_LOG;
import static net.minecraft.block.Blocks.BIRCH_WOOD;
import static net.minecraft.block.Blocks.CRIMSON_HYPHAE;
import static net.minecraft.block.Blocks.CRIMSON_STEM;
import static net.minecraft.block.Blocks.DARK_OAK_LOG;
import static net.minecraft.block.Blocks.DARK_OAK_WOOD;
import static net.minecraft.block.Blocks.JUNGLE_LOG;
import static net.minecraft.block.Blocks.JUNGLE_WOOD;
import static net.minecraft.block.Blocks.OAK_LOG;
import static net.minecraft.block.Blocks.OAK_WOOD;
import static net.minecraft.block.Blocks.SPRUCE_LOG;
import static net.minecraft.block.Blocks.SPRUCE_WOOD;
import static net.minecraft.block.Blocks.STRIPPED_ACACIA_LOG;
import static net.minecraft.block.Blocks.STRIPPED_ACACIA_WOOD;
import static net.minecraft.block.Blocks.STRIPPED_BIRCH_LOG;
import static net.minecraft.block.Blocks.STRIPPED_BIRCH_WOOD;
import static net.minecraft.block.Blocks.STRIPPED_CRIMSON_HYPHAE;
import static net.minecraft.block.Blocks.STRIPPED_CRIMSON_STEM;
import static net.minecraft.block.Blocks.STRIPPED_DARK_OAK_LOG;
import static net.minecraft.block.Blocks.STRIPPED_DARK_OAK_WOOD;
import static net.minecraft.block.Blocks.STRIPPED_JUNGLE_LOG;
import static net.minecraft.block.Blocks.STRIPPED_JUNGLE_WOOD;
import static net.minecraft.block.Blocks.STRIPPED_OAK_LOG;
import static net.minecraft.block.Blocks.STRIPPED_OAK_WOOD;
import static net.minecraft.block.Blocks.STRIPPED_SPRUCE_LOG;
import static net.minecraft.block.Blocks.STRIPPED_SPRUCE_WOOD;
import static net.minecraft.block.Blocks.STRIPPED_WARPED_HYPHAE;
import static net.minecraft.block.Blocks.STRIPPED_WARPED_STEM;
import static net.minecraft.block.Blocks.WARPED_HYPHAE;
import static net.minecraft.block.Blocks.WARPED_STEM;

public class CustreDatapacks {
    private static final Logger logger = LoggerFactory.getLogger(CustreDatapacks.class);

    public static void registerCustreDatapacks() {
        registerMinecraftCustrePack();
    }

    private static void registerMinecraftCustrePack() {
        logger.info("Registering Minecraft stripping recipes");
        Identifier id = new Identifier("custre", "minecraft");
        ResproRegistry.registerDataPack(id, data -> {
            data.addPackName("Custre: Minecraft");

            data.addPackIcon(new Identifier(Custre.MODID, "textures/icons/minecraft.png"));

            data.addPackMeta(meta -> {
                meta.packFormat(SharedConstants.getGameVersion().getPackVersion(PackType.DATA));
                meta.description("Minecraft stripping recipes");
            });

            addRecipe(data, id, OAK_LOG, STRIPPED_OAK_LOG);
            addRecipe(data, id, OAK_WOOD, STRIPPED_OAK_WOOD);
            addRecipe(data, id, SPRUCE_LOG, STRIPPED_SPRUCE_LOG);
            addRecipe(data, id, SPRUCE_WOOD, STRIPPED_SPRUCE_WOOD);
            addRecipe(data, id, DARK_OAK_LOG, STRIPPED_DARK_OAK_LOG);
            addRecipe(data, id, DARK_OAK_WOOD, STRIPPED_DARK_OAK_WOOD);
            addRecipe(data, id, JUNGLE_LOG, STRIPPED_JUNGLE_LOG);
            addRecipe(data, id, JUNGLE_WOOD, STRIPPED_JUNGLE_WOOD);
            addRecipe(data, id, BIRCH_LOG, STRIPPED_BIRCH_LOG);
            addRecipe(data, id, BIRCH_WOOD, STRIPPED_BIRCH_WOOD);
            addRecipe(data, id, ACACIA_LOG, STRIPPED_ACACIA_LOG);
            addRecipe(data, id, ACACIA_WOOD, STRIPPED_ACACIA_WOOD);
            addRecipe(data, id, CRIMSON_STEM, STRIPPED_CRIMSON_STEM);
            addRecipe(data, id, CRIMSON_HYPHAE, STRIPPED_CRIMSON_HYPHAE);
            addRecipe(data, id, WARPED_STEM, STRIPPED_WARPED_STEM);
            addRecipe(data, id, WARPED_HYPHAE, STRIPPED_WARPED_HYPHAE);
        });
    }

    private static void addRecipe(DataPackBuilder builder, Identifier id, Block in, Block out) {
        String inId = Registry.BLOCK.getId(in).toString();
        String outId = Registry.BLOCK.getId(out).toString();
        String fileName = inId.replace(":", "_") + "_" + outId.replace(":", "_");
        builder.addCustreRecipe(
                new Identifier(id.getNamespace(), fileName),
                new CustreRecipeResource(in, out)
        );
    }
}
