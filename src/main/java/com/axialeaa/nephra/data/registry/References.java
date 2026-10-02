package com.axialeaa.nephra.data.registry;

import com.axialeaa.nephra.Nephra;
import net.minecraft.references.BlockItemId;
import survivalblock.atmosphere.registrar.annotation.ConstructItem;

public final class References {

	@ConstructItem
	public static final BlockItemId NEPHRA = Nephra.BLOCK_REGISTRANT.createId("nephra");

	@ConstructItem
	public static final BlockItemId NEPHRA_SLAB = Nephra.BLOCK_REGISTRANT.createId("nephra_slab");

	@ConstructItem
	public static final BlockItemId NEPHRA_STAIRS = Nephra.BLOCK_REGISTRANT.createId("nephra_stairs");

	@ConstructItem
	public static final BlockItemId NEPHRA_WALL = Nephra.BLOCK_REGISTRANT.createId("nephra_wall");

	private References() {}

}
