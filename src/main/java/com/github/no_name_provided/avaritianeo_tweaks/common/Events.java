package com.github.no_name_provided.avaritianeo_tweaks.common;

import com.github.no_name_provided.avaritianeo_tweaks.common.capabilities.ExtremeCraftingTableCapability;
import com.github.no_name_provided.avaritianeo_tweaks.common.items.ANTItems;
import net.byAqua3.avaritia.Avaritia;
import net.byAqua3.avaritia.loader.AvaritiaBlocks;
import net.byAqua3.avaritia.tile.TileExtremeCraftingTable;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@EventBusSubscriber
public class Events {
    @SubscribeEvent
    private static void onBuildCreativeModeTabContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().location().getNamespace().equals(Avaritia.MODID))  {
            event.accept(ANTItems.FAST_NEUTRONIUM_COMPRESSOR.get());
        }
    }
    @SubscribeEvent
    private static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlock(
                Capabilities.ItemHandler.BLOCK,
                (level, pos,state, be,side) -> new ExtremeCraftingTableCapability((TileExtremeCraftingTable) be, side),
                AvaritiaBlocks.EXTREME_CRAFTING_TABLE.get()
        );
        
        // Doesn't support sided behavior?
//        event.registerBlockEntity(
//                Capabilities.ItemHandler.BLOCK,
//                AvaritiaBlocks.EXTREME_CRAFTING_TABLE_TILE.get(),
//                (be, pos) -> new ExtremeCraftingTableCapability(be, Direction.DOWN)
//        );
    }
}
