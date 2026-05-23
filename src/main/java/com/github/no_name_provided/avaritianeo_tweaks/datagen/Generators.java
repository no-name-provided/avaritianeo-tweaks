package com.github.no_name_provided.avaritianeo_tweaks.datagen;

import com.github.no_name_provided.avaritianeo_tweaks.datagen.providers.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Locale;
import java.util.concurrent.CompletableFuture;

import static com.github.no_name_provided.avaritianeo_tweaks.AvaritiaNeoTweaks.MODID;

@EventBusSubscriber(modid = MODID)
public class Generators {
    @SubscribeEvent
    private static void onGatherData(GatherDataEvent event) {
        net.minecraft.data.DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> provider = event.getLookupProvider();
        ExistingFileHelper notHelping = event.getExistingFileHelper();

//        notHelping.trackGenerated(ResourceLocation.fromNamespaceAndPath(Avaritia.MODID, AvaritiaBlocks.COMPRESSOR.getRegisteredName()));
        
        generator.addProvider(event.includeClient(), new ANTBlockModelProvider(
                        output,
                        MODID,
                        notHelping
                )
        );
        generator.addProvider(event.includeClient(), new ANTItemModelProvider(
                        output,
                        MODID,
                        notHelping
                )
        );
        generator.addProvider(event.includeClient(), new ANTBlockStateProvider(
                        output,
                        notHelping
                )
        );
        generator.addProvider(event.includeClient(), new ANTLanguageProvider(
                        output,
                        MODID,
                        Locale.US.toString().toLowerCase()
                )
        );
        
        
        generator.addProvider(event.includeServer(), new ANTRecipeProvider(
                        output,
                        provider
                )
        );
    }
}
