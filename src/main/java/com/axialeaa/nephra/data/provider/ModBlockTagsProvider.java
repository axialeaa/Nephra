package com.axialeaa.nephra.data.provider;

import com.axialeaa.nephra.data.registry.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.references.BlockItemIds;
import net.minecraft.tags.BlockTags;

import java.util.concurrent.CompletableFuture;

import static com.axialeaa.nephra.data.registry.ModBlocks.*;

public class ModBlockTagsProvider extends FabricTagsProvider.BlockTagsProvider {

	public ModBlockTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
		super(output, registryLookupFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider registries) {
		this.builder(BlockTags.MINEABLE_WITH_PICKAXE).add(
			NEPHRA.getKey(),
			NEPHRA_STAIRS.getKey(),
			NEPHRA_SLAB.getKey(),
			NEPHRA_WALL.getKey(),

			POLISHED_NEPHRA.getKey(),
			POLISHED_NEPHRA_STAIRS.getKey(),
			POLISHED_NEPHRA_SLAB.getKey(),
			NEPHRA_PILLAR.getKey(),

			NEPHRA_BRICKS.getKey(),
			NEPHRA_BRICK_STAIRS.getKey(),
			NEPHRA_BRICK_SLAB.getKey(),
			NEPHRA_BRICK_WALL.getKey(),

			CHISELED_NEPHRA_BRICKS.getKey()
		);

		this.builder(BlockTags.INFINIBURN_OVERWORLD).add(
			NEPHRA.getKey(),
			NEPHRA_STAIRS.getKey(),
			NEPHRA_SLAB.getKey(),
			NEPHRA_WALL.getKey(),

			POLISHED_NEPHRA.getKey(),
			POLISHED_NEPHRA_STAIRS.getKey(),
			POLISHED_NEPHRA_SLAB.getKey(),
			NEPHRA_PILLAR.getKey(),

			NEPHRA_BRICKS.getKey(),
			NEPHRA_BRICK_STAIRS.getKey(),
			NEPHRA_BRICK_SLAB.getKey(),
			NEPHRA_BRICK_WALL.getKey(),

			CHISELED_NEPHRA_BRICKS.getKey()
		);

		this.builder(BlockTags.STAIRS).add(
			NEPHRA_STAIRS.getKey(),
			POLISHED_NEPHRA_STAIRS.getKey(),
			NEPHRA_BRICK_STAIRS.getKey()
		);

		this.builder(BlockTags.SLABS).add(
			NEPHRA_SLAB.getKey(),
			POLISHED_NEPHRA_SLAB.getKey(),
			NEPHRA_BRICK_SLAB.getKey()
		);

		this.builder(BlockTags.WALLS).add(
			NEPHRA_WALL.getKey(),
			NEPHRA_BRICK_WALL.getKey()
		);

		this.builder(ModTags.Blocks.MAGMA_COOLANTS).add(BlockItemIds.BLUE_ICE);
	}

}
