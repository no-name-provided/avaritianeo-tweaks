package com.github.no_name_provided.avaritianeo_tweaks.common.menus;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.github.no_name_provided.avaritianeo_tweaks.AvaritiaNeoTweaks.MODID;

public class ANTMenus {
    public static DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Registries.MENU, MODID);
    
    public static DeferredHolder<MenuType<?>, MenuType<FastNeutroniumCompressorMenu>> FAST_NEUTRONIUM_COMPRESSOR = MENU_TYPES.register(
            MODID,
            () -> IMenuTypeExtension.create((windowId, inv, data) -> new FastNeutroniumCompressorMenu(
                            windowId,
                            inv.player.getInventory(),
                            data.readBlockPos()
                    )
            )
    );
}
