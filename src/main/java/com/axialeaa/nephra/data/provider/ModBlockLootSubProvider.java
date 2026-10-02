package com.axialeaa.nephra.data.provider;

import com.axialeaa.nephra.data.registry.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class ModBlockLootSubProvider extends FabricBlockLootSubProvider {

	public ModBlockLootSubProvider(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(packOutput, registriesFuture);
	}

	@Override
    public void generate() {
        this.dropSelf(ModBlocks.NEPHRA);
		this.dropSelf(ModBlocks.NEPHRA_STAIRS);
		this.dropSelf(ModBlocks.NEPHRA_WALL);
		this.add(ModBlocks.NEPHRA_SLAB, this::createSlabItemTable);
    }

}
