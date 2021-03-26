package lv.cebbys.custre.mixins;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.block.Block;
import net.minecraft.item.AxeItem;

@Mixin(AxeItem.class)
public interface StrippingRecipeAccessor {
    @Accessor("STRIPPED_BLOCKS")
    @Mutable
    public static void setStrippingRecipes(Map<Block, Block> map) {
        throw new AssertionError();
    }

    @Accessor("STRIPPED_BLOCKS")
    public static Map<Block, Block> getStrippingRecipes() {
        throw new AssertionError();
    }
}
