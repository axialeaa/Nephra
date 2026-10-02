package com.axialeaa.nephra.data.provider;

import com.axialeaa.nephra.data.registry.ModBlockFamilies;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.flag.FeatureFlags;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {

	public ModRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
		return new Runner(registries, output);
	}

	@Override
	public String getName() {
		return "Recipes";
	}

	private static class Runner extends RecipeProvider {

		private Runner(HolderLookup.Provider registries, RecipeOutput output) {
			super(registries, output);
		}

		@Override
		public void buildRecipes() {
			this.generateRecipes(ModBlockFamilies.NEPHRA, FeatureFlags.VANILLA_SET);
		}

	}

}
