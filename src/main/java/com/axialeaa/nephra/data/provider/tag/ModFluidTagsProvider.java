package com.axialeaa.nephra.data.provider.tag;

import com.axialeaa.nephra.registry.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.FluidTags;

import java.util.concurrent.CompletableFuture;

public class ModFluidTagsProvider extends FabricTagsProvider.FluidTagsProvider {

	public ModFluidTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
		super(output, registryLookupFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider registries) {
		this.builder(ModTags.Fluids.NEPHRA_ABSORBABLE).forceAddTag(FluidTags.LAVA);
	}

}
