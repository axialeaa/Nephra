package com.axialeaa.nephra.data.registry;

import com.axialeaa.nephra.Nephra;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public final class ModTags {

	public static final class Blocks {

		public static final TagKey<Block> MAGMA_COOLANTS = ModBlocks.REGISTRANT.tag("magma_coolants");

		private Blocks() {}

	}

	public static final class Biomes {

		public static final TagKey<Biome> MAGMA_PASSIVE_COOLING_OVERRIDE = create("magma_passive_cooling_override");
		public static final TagKey<Biome> CANNOT_PASSIVELY_COOL_MAGMA = create("cannot_passively_cool_magma");

		private Biomes() {}

		private static TagKey<Biome> create(String path) {
			return TagKey.create(Registries.BIOME, Nephra.id(path));
		}

	}

	private ModTags() {}

}
