package com.github.no_name_provided.avaritianeo_tweaks.common.items;

import com.github.no_name_provided.avaritianeo_tweaks.common.blocks.ANTBlocks;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.github.no_name_provided.avaritianeo_tweaks.AvaritiaNeoTweaks.MODID;

public class ANTItems {
    public static DeferredRegister<Item> ITEMS = DeferredRegister.createItems(MODID);//.createItems(MODID);
    
    public static DeferredHolder<Item, Item> FAST_NEUTRONIUM_COMPRESSOR = ITEMS.register(
            "fast_neutronium_compressor",
            () -> new BlockItem(
                    ANTBlocks.FAST_NEUTRONIUM_COMPRESSOR.get(),
                    new Item.Properties().rarity(Rarity.RARE)
            )
    );
}
