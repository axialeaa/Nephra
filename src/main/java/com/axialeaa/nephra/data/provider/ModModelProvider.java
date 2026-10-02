package com.axialeaa.nephra.data.provider;

import com.axialeaa.nephra.data.registry.ModBlockFamilies;
import com.axialeaa.nephra.data.registry.ModBlocks;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;

public class ModModelProvider extends FabricModelProvider {

	public ModModelProvider(FabricPackOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockModelGenerators generators) {
		generators.family(ModBlocks.NEPHRA).generateFor(ModBlockFamilies.NEPHRA);
	}

	@Override
	public void generateItemModels(ItemModelGenerators generators) {}

}
