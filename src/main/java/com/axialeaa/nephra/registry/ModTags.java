package com.axialeaa.nephra.registry;

import com.axialeaa.nephra.Nephra;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BlockItemTagId;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.material.Fluid;

public final class ModTags {

	public static final class BlockItems {

		public static final BlockItemTagId MAGMA_COOLANTS = create("magma_coolants");

		private BlockItems() {}

		private static BlockItemTagId create(String path) {
			return BlockItemTagId.create(Nephra.id(path), Nephra.id(path));
		}

	}

	public static final class Fluids {

		public static final TagKey<Fluid> NEPHRA_ABSORBABLE = create("nephra_absorbable");

		private Fluids() {}

		private static TagKey<Fluid> create(String path) {
			return TagKey.create(Registries.FLUID, Nephra.id(path));
		}

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
