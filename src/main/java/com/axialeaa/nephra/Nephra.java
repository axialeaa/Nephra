package com.axialeaa.nephra;

import com.axialeaa.nephra.registry.ModBlocks;
import com.axialeaa.nephra.registry.ModGameRules;
import com.axialeaa.nephra.registry.ModSoundEvents;
import com.axialeaa.nephra.network.ClientboundMagmaCoolingPayload;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTabOutput;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import survivalblock.atmosphere.registrar.Registrant;

import java.util.function.Function;

import static com.axialeaa.nephra.registry.ModBlocks.*;

public class Nephra implements ModInitializer {

	private static final String MOD_ID = "nephra";

	@Override
	public void onInitialize() {
		ModBlocks.init();
		ModSoundEvents.init();
		ModGameRules.init();

		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.NATURAL_BLOCKS).register(Nephra::addAfterMagma);
		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(Nephra::addAfterMagma);

		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.BUILDING_BLOCKS).register(output -> output.insertAfter(
			Items.NETHERRACK,
			NEPHRA, NEPHRA_STAIRS, NEPHRA_SLAB, NEPHRA_WALL, // raw
			POLISHED_NEPHRA, POLISHED_NEPHRA_STAIRS, POLISHED_NEPHRA_SLAB, // polished
			NEPHRA_PILLAR,
			NEPHRA_BRICKS, NEPHRA_BRICK_STAIRS, NEPHRA_BRICK_SLAB, NEPHRA_BRICK_WALL, // bricks
			CHISELED_NEPHRA_BRICKS
		));

		PayloadTypeRegistry.clientboundPlay().register(ClientboundMagmaCoolingPayload.TYPE, ClientboundMagmaCoolingPayload.STREAM_CODEC);
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}

	public static <T extends CustomPacketPayload> CustomPacketPayload.Type<T> payloadType(String path) {
		return new CustomPacketPayload.Type<>(id(path));
	}

	public static <T extends Registrant<?>> T createRegistrant(Function<String, T> registrant) {
		return registrant.apply(MOD_ID);
	}

	private static void addAfterMagma(FabricCreativeModeTabOutput output) {
		output.insertAfter(Items.MAGMA_BLOCK, NEPHRA);
	}

}