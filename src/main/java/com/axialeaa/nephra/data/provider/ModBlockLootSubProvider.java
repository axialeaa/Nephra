package com.axialeaa.nephra.data.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

import static com.axialeaa.nephra.registry.ModBlocks.*;

public class ModBlockLootSubProvider extends FabricBlockLootSubProvider {

	public ModBlockLootSubProvider(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(packOutput, registriesFuture);
	}

	@Override
    public void generate() {
        this.dropSelf(NEPHRA.get());
		this.dropSelf(NEPHRA_STAIRS.get());
		this.add(NEPHRA_SLAB.get(), this::createSlabItemTable);
		this.dropSelf(NEPHRA_WALL.get());

		this.dropSelf(POLISHED_NEPHRA.get());
		this.dropSelf(POLISHED_NEPHRA_STAIRS.get());
		this.add(POLISHED_NEPHRA_SLAB.get(), this::createSlabItemTable);
		this.dropSelf(NEPHRA_PILLAR.get());

		this.dropSelf(NEPHRA_BRICKS.get());
		this.dropSelf(NEPHRA_BRICK_STAIRS.get());
		this.add(NEPHRA_BRICK_SLAB.get(), this::createSlabItemTable);
		this.dropSelf(NEPHRA_BRICK_WALL.get());

		this.dropSelf(CHISELED_NEPHRA_BRICKS.get());
    }

}
