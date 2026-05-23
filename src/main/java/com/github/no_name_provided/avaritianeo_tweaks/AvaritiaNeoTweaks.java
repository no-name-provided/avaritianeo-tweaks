package com.github.no_name_provided.avaritianeo_tweaks;

import com.github.no_name_provided.avaritianeo_tweaks.common.block_entities.ANTBlockEntities;
import com.github.no_name_provided.avaritianeo_tweaks.common.blocks.ANTBlocks;
import com.github.no_name_provided.avaritianeo_tweaks.common.items.ANTItems;
import com.github.no_name_provided.avaritianeo_tweaks.common.menus.ANTMenus;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredRegister;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(AvaritiaNeoTweaks.MODID)
public class AvaritiaNeoTweaks {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "avaritianeo_tweaks";
    // Create a Deferred Register to hold Items which will all be registered under the "avaritianeo_tweaks" namespace
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    
    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public AvaritiaNeoTweaks(IEventBus modEventBus, ModContainer modContainer) {
        // Register the DeferredRegisters with the mod event bus
        ANTBlocks.BLOCKS.register(modEventBus);
        ANTItems.ITEMS.register(modEventBus);
        ANTMenus.MENU_TYPES.register(modEventBus);
        ANTBlockEntities.BLOCK_ENTITY_TYPES.register(modEventBus);
    }
}
