package com.axialeaa.nephra;

import com.axialeaa.nephra.data.registry.ModBlocks;
import com.axialeaa.nephra.data.registry.ModGameRules;
import com.axialeaa.nephra.data.registry.ModSoundEvents;
import com.axialeaa.nephra.network.ClientboundMagmaCoolingPayload;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTabOutput;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;

import static com.axialeaa.nephra.data.registry.ModBlocks.*;

public class Nephra implements ModInitializer {

	public static final String MOD_ID = "nephra";
	public static final String MOD_NAME = "Nephra";

	@Override
	public void onInitialize() {
		ModBlocks.init();
		ModSoundEvents.init();
		ModGameRules.init();

		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.NATURAL_BLOCKS).register(Nephra::addAfterMagma);
		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(Nephra::addAfterMagma);

		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.BUILDING_BLOCKS).register(output -> output.insertAfter(
			Items.NETHERRACK,
			NEPHRA, NEPHRA_STAIRS, NEPHRA_SLAB, NEPHRA_WALL,
			POLISHED_NEPHRA, POLISHED_NEPHRA_STAIRS, POLISHED_NEPHRA_SLAB,
			POLISHED_NEPHRA_PILLAR,
			NEPHRA_BRICKS, NEPHRA_BRICK_STAIRS, NEPHRA_BRICK_SLAB, NEPHRA_BRICK_WALL,
			CHISELED_NEPHRA_BRICKS,
			NEPHRA_TILES, NEPHRA_TILE_STAIRS, NEPHRA_TILE_SLAB, NEPHRA_TILE_WALL
		));

		PayloadTypeRegistry.clientboundPlay().register(ClientboundMagmaCoolingPayload.TYPE, ClientboundMagmaCoolingPayload.STREAM_CODEC);
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}

	public static <T extends CustomPacketPayload> CustomPacketPayload.Type<T> payloadType(String path) {
		return new CustomPacketPayload.Type<>(id(path));
	}

	private static void addAfterMagma(FabricCreativeModeTabOutput output) {
		output.insertAfter(Items.MAGMA_BLOCK, NEPHRA);
	}

}