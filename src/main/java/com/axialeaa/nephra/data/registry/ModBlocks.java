package com.axialeaa.nephra.data.registry;

import com.axialeaa.nephra.block.NephraBlock;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

import static com.axialeaa.nephra.Nephra.BLOCK_REGISTRANT;

public final class ModBlocks {

	public static final NephraBlock NEPHRA = BLOCK_REGISTRANT.register(
		References.NEPHRA,
		NephraBlock::new,
		BlockBehaviour.Properties.of()
			.mapColor(MapColor.NETHER)
			.instrument(NoteBlockInstrument.BASEDRUM)
			.requiresCorrectToolForDrops()
			.strength(0.5F)
	);

	public static final SlabBlock NEPHRA_SLAB = BLOCK_REGISTRANT.register(
		References.NEPHRA_SLAB,
		SlabBlock::new,
		BlockBehaviour.Properties.ofFullCopy(NEPHRA)
	);

	public static final StairBlock NEPHRA_STAIRS = BLOCK_REGISTRANT.register(
		References.NEPHRA_STAIRS,
		p -> new StairBlock(NEPHRA.defaultBlockState(), p),
		BlockBehaviour.Properties.ofFullCopy(NEPHRA)
	);

	public static final WallBlock NEPHRA_WALL = BLOCK_REGISTRANT.register(
		References.NEPHRA_WALL,
		WallBlock::new,
		BlockBehaviour.Properties.ofFullCopy(NEPHRA)
	);

	private ModBlocks() {}

	public static void init() {}

}