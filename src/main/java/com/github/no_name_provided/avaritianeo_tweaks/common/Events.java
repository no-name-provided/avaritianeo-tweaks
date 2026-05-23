package com.github.no_name_provided.avaritianeo_tweaks;

import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

import static com.github.no_name_provided.avaritianeo_tweaks.Avaritianeo_tweaks.EXAMPLE_BLOCK_ITEM;

@EventBusSubscriber
public class Events {
    // Add the example block item to the building blocks tab
    @SubscribeEvent
    private static void onBuildCreativeModeTabContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) event.accept(EXAMPLE_BLOCK_ITEM);
    }
}
