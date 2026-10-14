package com.axialeaa.nephra.data.provider;

import com.axialeaa.nephra.block.ConnectedPillarBlock;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.*;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import static com.axialeaa.nephra.data.registry.ModBlocks.*;
import static net.minecraft.client.data.models.BlockModelGenerators.*;

public class ModModelProvider extends FabricModelProvider {

	public ModModelProvider(FabricPackOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockModelGenerators generators) {
		generators.family(NEPHRA.get()).generateFor(Families.NEPHRA);

		BlockModelGenerators.BlockFamilyProvider polishedNephraFamily = generators.family(POLISHED_NEPHRA.get());
		ConnectedPillarBlock pillar = POLISHED_NEPHRA_PILLAR.get();

		polishedNephraFamily.skipGeneratingModelsFor.add(pillar);
		polishedNephraFamily.generateFor(Families.POLISHED_NEPHRA);

		generateConnectedPillar(generators, pillar);

		generators.family(NEPHRA_BRICKS.get()).generateFor(Families.NEPHRA_BRICKS);
		generators.family(NEPHRA_TILES.get()).generateFor(Families.NEPHRA_TILES);
	}

	private static void generateConnectedPillar(BlockModelGenerators generators, ConnectedPillarBlock pillar) {
		TextureMapping textures = TextureMapping.column(pillar);
		TextureMapping texturesP = textures.copyAndUpdate(TextureSlot.SIDE, TextureMapping.getBlockTexture(pillar, "_side_positive"));
		TextureMapping texturesN = textures.copyAndUpdate(TextureSlot.SIDE, TextureMapping.getBlockTexture(pillar, "_side_negative"));
		TextureMapping texturesPN = textures.copyAndUpdate(TextureSlot.SIDE, TextureMapping.getBlockTexture(pillar, "_side_positive_negative"));

		MultiVariant verticalModel = plainVariant(ModelTemplates.CUBE_COLUMN.create(pillar, textures, generators.modelOutput));
		MultiVariant verticalModelP = plainVariant(ModelTemplates.CUBE_COLUMN.createWithSuffix(pillar, "_positive", texturesP, generators.modelOutput));
		MultiVariant verticalModelN = plainVariant(ModelTemplates.CUBE_COLUMN.createWithSuffix(pillar, "_negative", texturesN, generators.modelOutput));
		MultiVariant verticalModelPN = plainVariant(ModelTemplates.CUBE_COLUMN.createWithSuffix(pillar, "_positive_negative", texturesPN, generators.modelOutput));

		MultiVariant horizontalModel = plainVariant(ModelTemplates.CUBE_COLUMN_HORIZONTAL.create(pillar, textures, generators.modelOutput));
		MultiVariant horizontalModelP = plainVariant(ModelTemplates.CUBE_COLUMN_HORIZONTAL.createWithSuffix(pillar, "_positive", texturesP, generators.modelOutput));
		MultiVariant horizontalModelN = plainVariant(ModelTemplates.CUBE_COLUMN_HORIZONTAL.createWithSuffix(pillar, "_negative", texturesN, generators.modelOutput));
		MultiVariant horizontalModelPN = plainVariant(ModelTemplates.CUBE_COLUMN_HORIZONTAL.createWithSuffix(pillar, "_positive_negative", texturesPN, generators.modelOutput));

		generators.blockStateOutput.accept(MultiVariantGenerator.dispatch(pillar)
			.with(PropertyDispatch.initial(BlockStateProperties.AXIS, ConnectedPillarBlock.CONNECTED_POSITIVE, ConnectedPillarBlock.CONNECTED_NEGATIVE)
				.select(Direction.Axis.Y, false, false, verticalModel)
				.select(Direction.Axis.Y, true, false, verticalModelP)
				.select(Direction.Axis.Y, false, true, verticalModelN)
				.select(Direction.Axis.Y, true, true, verticalModelPN)

				.select(Direction.Axis.Z, false, false, horizontalModel.with(X_ROT_270))
				.select(Direction.Axis.Z, true, false, horizontalModelP.with(X_ROT_270))
				.select(Direction.Axis.Z, false, true, horizontalModelN.with(X_ROT_270))
				.select(Direction.Axis.Z, true, true, horizontalModelPN.with(X_ROT_270))

				.select(Direction.Axis.X, false, false, horizontalModel.with(X_ROT_90).with(Y_ROT_90))
				.select(Direction.Axis.X, true, false, horizontalModelP.with(X_ROT_90).with(Y_ROT_90))
				.select(Direction.Axis.X, false, true, horizontalModelN.with(X_ROT_90).with(Y_ROT_90))
				.select(Direction.Axis.X, true, true, horizontalModelPN.with(X_ROT_90).with(Y_ROT_90))
			)
		);
	}

	@Override
	public void generateItemModels(ItemModelGenerators generators) {}

}
