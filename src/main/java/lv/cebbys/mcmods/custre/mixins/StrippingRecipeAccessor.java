package lv.cebbys.mcmods.custre.mixins;

import net.minecraft.block.Block;
import net.minecraft.item.AxeItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(AxeItem.class)
public interface StrippingRecipeAccessor {
    @Accessor("STRIPPED_BLOCKS")
    @Mutable
    static void setStrippingRecipes(Map<Block, Block> map) {
        throw new AssertionError();
    }

    @Accessor("STRIPPED_BLOCKS")
    static Map<Block, Block> getStrippingRecipes() {
        throw new AssertionError();
    }
}
