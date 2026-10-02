package com.axialeaa.nephra.data.registry;

import net.minecraft.data.BlockFamilies;
import net.minecraft.data.BlockFamily;

public final class ModBlockFamilies {

	public static final BlockFamily NEPHRA = BlockFamilies.familyBuilder(ModBlocks.NEPHRA)
		.slab(ModBlocks.NEPHRA_SLAB)
		.stairs(ModBlocks.NEPHRA_STAIRS)
		.wall(ModBlocks.NEPHRA_WALL)
		.generateStonecutterRecipe()
		.getFamily();

	private ModBlockFamilies() {}

}
