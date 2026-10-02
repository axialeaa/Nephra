package com.axialeaa.nephra.data.provider;

import com.axialeaa.nephra.data.registry.ModTags;
import com.axialeaa.nephra.data.registry.References;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.references.BlockItemIds;
import net.minecraft.tags.BlockTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends FabricTagsProvider.BlockTagsProvider {

	public ModBlockTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
		super(output, registryLookupFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider registries) {
		this.builder(BlockTags.MINEABLE_WITH_PICKAXE).add(
			References.NEPHRA,
			References.NEPHRA_SLAB,
			References.NEPHRA_STAIRS,
			References.NEPHRA_WALL
		);

		this.builder(BlockTags.INFINIBURN_OVERWORLD).add(
			References.NEPHRA,
			References.NEPHRA_SLAB,
			References.NEPHRA_STAIRS,
			References.NEPHRA_WALL
		);

		this.builder(BlockTags.SLABS).add(References.NEPHRA_SLAB);
		this.builder(BlockTags.STAIRS).add(References.NEPHRA_STAIRS);
		this.builder(BlockTags.WALLS).add(References.NEPHRA_WALL);

		this.builder(ModTags.Blocks.MAGMA_COOLANTS).add(BlockItemIds.BLUE_ICE);
	}

}
