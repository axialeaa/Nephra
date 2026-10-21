package com.axialeaa.nephra.data.provider;

import com.axialeaa.nephra.data.registry.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.crafting.Recipe;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {

	public ModRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, BootstrapContext<Recipe<?>> recipes, BootstrapContext<Advancement> advancements) {
		return new Runner(recipes, advancements);
	}

	private static class Runner extends RecipeProvider {

		private Runner(BootstrapContext<Recipe<?>> recipeOutput, BootstrapContext<Advancement> advancementOutput) {
			super(recipeOutput, advancementOutput);
		}

		@Override
		public void buildRecipes() {
			this.generateRecipes(ModBlocks.Families.NEPHRA, FeatureFlags.VANILLA_SET);
			this.generateRecipes(ModBlocks.Families.POLISHED_NEPHRA, FeatureFlags.VANILLA_SET);
			this.generateRecipes(ModBlocks.Families.NEPHRA_BRICKS, FeatureFlags.VANILLA_SET);
		}

	}

}
