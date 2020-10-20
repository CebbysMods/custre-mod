package com.cebbys.custre.mixins;

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
    public static void setStrippingBlocks(Map<Block, Block> map) {
        throw new AssertionError();
    }

}
