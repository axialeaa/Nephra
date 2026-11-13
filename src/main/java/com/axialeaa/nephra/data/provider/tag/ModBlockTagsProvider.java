package com.axialeaa.nephra.data.provider.tag;

import com.axialeaa.nephra.registry.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.references.BlockItemIds;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import survivalblock.atmosphere.registrar.wrapper.BlockPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.axialeaa.nephra.registry.ModBlocks.*;

public class ModBlockTagsProvider extends FabricTagsProvider.BlockTagsProvider {

	public ModBlockTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
		super(output, registryLookupFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider registries) {
		this.builder(BlockTags.MINEABLE_WITH_PICKAXE).addAll(toIds(
			NEPHRA,
			NEPHRA_STAIRS,
			NEPHRA_SLAB,
			NEPHRA_WALL,

			POLISHED_NEPHRA,
			POLISHED_NEPHRA_STAIRS,
			POLISHED_NEPHRA_SLAB,
			NEPHRA_PILLAR,

			NEPHRA_BRICKS,
			NEPHRA_BRICK_STAIRS,
			NEPHRA_BRICK_SLAB,
			NEPHRA_BRICK_WALL,

			CHISELED_NEPHRA_BRICKS
		));

		this.builder(BlockTags.INFINIBURN_OVERWORLD).addAll(toIds(
			NEPHRA,
			NEPHRA_STAIRS,
			NEPHRA_SLAB,
			NEPHRA_WALL,

			POLISHED_NEPHRA,
			POLISHED_NEPHRA_STAIRS,
			POLISHED_NEPHRA_SLAB,
			NEPHRA_PILLAR,

			NEPHRA_BRICKS,
			NEPHRA_BRICK_STAIRS,
			NEPHRA_BRICK_SLAB,
			NEPHRA_BRICK_WALL,

			CHISELED_NEPHRA_BRICKS
		));

		this.builder(BlockTags.STAIRS).addAll(toIds(
			NEPHRA_STAIRS,
			POLISHED_NEPHRA_STAIRS,
			NEPHRA_BRICK_STAIRS
		));

		this.builder(BlockTags.SLABS).addAll(toIds(
			NEPHRA_SLAB,
			POLISHED_NEPHRA_SLAB,
			NEPHRA_BRICK_SLAB
		));

		this.builder(BlockTags.WALLS).addAll(toIds(
			NEPHRA_WALL,
			NEPHRA_BRICK_WALL
		));

		this.builder(BlockTags.BLOCKS_MOTION_NO_LEAVES).addAll(toIds(
			NEPHRA,
			POLISHED_NEPHRA,
			NEPHRA_PILLAR,
			NEPHRA_BRICKS,
			CHISELED_NEPHRA_BRICKS
		));

		this.builder(ModTags.BlockItems.INSTANT_MAGMA_COOLANTS.block())
			.add(BlockItemIds.BLUE_ICE);

		this.builder(ModTags.BlockItems.PASSIVE_MAGMA_COOLANTS.block())
			.forceAddTag(ConventionalBlockTags.ICE);
	}
	
	@SafeVarargs
	private static List<ResourceKey<Block>> toIds(BlockPresenter<? extends Block>... blocks) {
		List<ResourceKey<Block>> newBlocks = new ArrayList<>(blocks.length);

		for (BlockPresenter<? extends Block> block : blocks)
			newBlocks.add(block.getKey());
		
		return newBlocks;
	}

}
