package com.github.no_name_provided.avaritianeo_tweaks.client;

import com.github.no_name_provided.avaritianeo_tweaks.client.screens.FastNeutroniumCompressorScreen;
import com.github.no_name_provided.avaritianeo_tweaks.common.blocks.ANTBlocks;
import com.github.no_name_provided.avaritianeo_tweaks.common.items.ANTItems;
import com.github.no_name_provided.avaritianeo_tweaks.common.menus.ANTMenus;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

import static com.github.no_name_provided.avaritianeo_tweaks.AvaritiaNeoTweaks.MODID;

@EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
public class Events {
    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ANTMenus.FAST_NEUTRONIUM_COMPRESSOR.get(), FastNeutroniumCompressorScreen::new);
    }
    
    @SubscribeEvent
    private static void onRegisterItemColorHandlers(RegisterColorHandlersEvent.Item event) {
        event.register(
                (state, getter) -> MapColor.COLOR_RED.col,
                ANTItems.FAST_NEUTRONIUM_COMPRESSOR.get()
        );
    }
    @SubscribeEvent
    private static void onRegisterBlockColorHandlers(RegisterColorHandlersEvent.Block event) {
        event.register(
                (state, getter, pos, random) -> MapColor.COLOR_RED.col,
                ANTBlocks.FAST_NEUTRONIUM_COMPRESSOR.get()
        );
    }
}
