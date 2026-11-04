package com.axialeaa.nephra.data;

import com.axialeaa.nephra.data.provider.*;
import com.axialeaa.nephra.data.provider.tag.ModBlockTagsProvider;
import com.axialeaa.nephra.data.provider.tag.ModFluidTagsProvider;
import com.axialeaa.nephra.data.provider.tag.ModItemTagsProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class NephraDataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();

		ModBlockTagsProvider blockTagsProvider = pack.addProvider(ModBlockTagsProvider::new);
		pack.addProvider(ModFluidTagsProvider::new);
		pack.addProvider((output, registryLookupFuture) -> new ModItemTagsProvider(output, registryLookupFuture, blockTagsProvider));

		pack.addProvider(ModBlockLootSubProvider::new);
		pack.addProvider(ModModelProvider::new);
		pack.addProvider(ModRecipeProvider::new);
		pack.addProvider(ModSoundsProvider::new);
    }

}
