package com.axialeaa.nephra.data.provider.tag;

import com.axialeaa.nephra.registry.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockItemTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.Nullable;
import survivalblock.atmosphere.registrar.wrapper.BlockPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.axialeaa.nephra.registry.ModBlocks.*;

public class ModItemTagsProvider extends FabricTagsProvider.ItemTagsProvider {

	public ModItemTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture, @Nullable BlockTagsProvider blockTagsProvider) {
		super(output, registryLookupFuture, blockTagsProvider);
	}

	@Override
	protected void addTags(HolderLookup.Provider registries) {
		this.copy(BlockItemTags.STAIRS);
		this.copy(BlockItemTags.SLABS);
		this.copy(BlockItemTags.WALLS);
		this.copy(ModTags.BlockItems.MAGMA_COOLANTS);
		this.builder(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY).addAll(toIds(
			NEPHRA,
			POLISHED_NEPHRA,
			NEPHRA_BRICKS,
			NEPHRA_PILLAR,
			CHISELED_NEPHRA_BRICKS
		));
	}

	@SafeVarargs
	private static List<ResourceKey<Item>> toIds(BlockPresenter<? extends Block>... blocks) {
		List<ResourceKey<Item>> newBlocks = new ArrayList<>(blocks.length);

		for (BlockPresenter<? extends Block> block : blocks)
			newBlocks.add(block.getBlockItemIdOrThrow().item());

		return newBlocks;
	}

}
