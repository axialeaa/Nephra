package com.axialeaa.nephra.data.registry;

import com.axialeaa.nephra.Nephra;
import com.axialeaa.nephra.block.ConnectedPillarBlock;
import com.axialeaa.nephra.block.NephraBlock;
import net.minecraft.data.BlockFamilies;
import net.minecraft.data.BlockFamily;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import survivalblock.atmosphere.registrar.BlockRegistrant;
import survivalblock.atmosphere.registrar.annotation.ConstructBlock;
import survivalblock.atmosphere.registrar.annotation.ConstructItem;
import survivalblock.atmosphere.registrar.wrapper.BlockPresenter;

import java.util.function.Function;

@ConstructBlock
public final class ModBlocks {

	public static final BlockRegistrant REGISTRANT = new BlockRegistrant(Nephra.MOD_ID);

	@ConstructItem
	public static final BlockPresenter<NephraBlock> NEPHRA = REGISTRANT.registerAndPresent(
		NephraBlock::new,
		BlockBehaviour.Properties.of()
			.mapColor(MapColor.NETHER)
			.instrument(NoteBlockInstrument.BASEDRUM)
			.requiresCorrectToolForDrops()
			.strength(0.5F)
	);

	@ConstructItem public static final BlockPresenter<StairBlock> NEPHRA_STAIRS = registerStairs(NEPHRA);
	@ConstructItem public static final BlockPresenter<SlabBlock> NEPHRA_SLAB = registerSlab();
	@ConstructItem public static final BlockPresenter<WallBlock> NEPHRA_WALL = registerWall();

	@ConstructItem public static final BlockPresenter<Block> POLISHED_NEPHRA = registerBasic();
	@ConstructItem public static final BlockPresenter<StairBlock> POLISHED_NEPHRA_STAIRS = registerStairs(POLISHED_NEPHRA);
	@ConstructItem public static final BlockPresenter<SlabBlock> POLISHED_NEPHRA_SLAB = registerSlab();
	@ConstructItem public static final BlockPresenter<ConnectedPillarBlock> POLISHED_NEPHRA_PILLAR = registerNephraBlock(ConnectedPillarBlock::new);

	@ConstructItem public static final BlockPresenter<Block> NEPHRA_BRICKS = registerBasic();
	@ConstructItem public static final BlockPresenter<StairBlock> NEPHRA_BRICK_STAIRS = registerStairs(NEPHRA_BRICKS);
	@ConstructItem public static final BlockPresenter<SlabBlock> NEPHRA_BRICK_SLAB = registerSlab();
	@ConstructItem public static final BlockPresenter<WallBlock> NEPHRA_BRICK_WALL = registerWall();

	@ConstructItem public static final BlockPresenter<Block> CHISELED_NEPHRA_BRICKS = registerBasic();

	@ConstructItem public static final BlockPresenter<Block> NEPHRA_TILES = registerBasic();
	@ConstructItem public static final BlockPresenter<StairBlock> NEPHRA_TILE_STAIRS = registerStairs(NEPHRA_TILES);
	@ConstructItem public static final BlockPresenter<SlabBlock> NEPHRA_TILE_SLAB = registerSlab();
	@ConstructItem public static final BlockPresenter<WallBlock> NEPHRA_TILE_WALL = registerWall();

	private ModBlocks() {}

	private static <T extends Block> BlockPresenter<T> registerNephraBlock(Function<BlockBehaviour.Properties, T> blockFunction) {
		return REGISTRANT.registerAndPresent(blockFunction, BlockBehaviour.Properties.ofFullCopy(NEPHRA.get()));
	}

	private static BlockPresenter<Block> registerBasic() {
		return registerNephraBlock(Block::new);
	}

	private static BlockPresenter<SlabBlock> registerSlab() {
		return registerNephraBlock(SlabBlock::new);
	}

	private static BlockPresenter<StairBlock> registerStairs(BlockPresenter<?> baseBlock) {
		return registerNephraBlock(p -> new StairBlock(baseBlock.get().defaultBlockState(), p));
	}

	private static BlockPresenter<WallBlock> registerWall() {
		return REGISTRANT.registerAndPresent(WallBlock::new, BlockBehaviour.Properties.ofFullCopy(NEPHRA.get()));
	}

	public static void init() {}

	public static final class Families {

		public static final BlockFamily NEPHRA = BlockFamilies.familyBuilder(ModBlocks.NEPHRA.get())
			.slab(NEPHRA_SLAB.get())
			.stairs(NEPHRA_STAIRS.get())
			.wall(NEPHRA_WALL.get())
			.bricks(ModBlocks.NEPHRA_BRICKS.get())
			.polished(ModBlocks.POLISHED_NEPHRA.get())
			.generateStonecutterRecipe()
			.getFamily();

		public static final BlockFamily POLISHED_NEPHRA = BlockFamilies.familyBuilder(ModBlocks.POLISHED_NEPHRA.get())
			.slab(POLISHED_NEPHRA_SLAB.get())
			.stairs(POLISHED_NEPHRA_STAIRS.get())
			.pillar(POLISHED_NEPHRA_PILLAR.get())
			.generateStonecutterRecipe()
			.getFamily();

		public static final BlockFamily NEPHRA_BRICKS = BlockFamilies.familyBuilder(ModBlocks.NEPHRA_BRICKS.get())
			.slab(NEPHRA_BRICK_SLAB.get())
			.stairs(NEPHRA_BRICK_STAIRS.get())
			.wall(NEPHRA_BRICK_WALL.get())
			.chiseled(ModBlocks.CHISELED_NEPHRA_BRICKS.get())
			.tiles(ModBlocks.NEPHRA_TILES.get())
			.generateStonecutterRecipe()
			.getFamily();

		public static final BlockFamily NEPHRA_TILES = BlockFamilies.familyBuilder(ModBlocks.NEPHRA_TILES.get())
			.slab(NEPHRA_TILE_SLAB.get())
			.stairs(NEPHRA_TILE_STAIRS.get())
			.wall(NEPHRA_TILE_WALL.get())
			.generateStonecutterRecipe()
			.getFamily();

		private Families() {}

	}

}