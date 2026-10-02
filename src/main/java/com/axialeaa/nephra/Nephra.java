package com.axialeaa.nephra;

import com.axialeaa.nephra.data.registry.ModBlocks;
import com.axialeaa.nephra.data.registry.ModGameRules;
import com.axialeaa.nephra.data.registry.ModSoundEvents;
import com.axialeaa.nephra.data.registry.References;
import com.axialeaa.nephra.network.ClientboundCoolingMagmaPayload;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTabOutput;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import survivalblock.atmosphere.registrar.BlockRegistrant;
import survivalblock.atmosphere.registrar.GameRuleRegistrant;
import survivalblock.atmosphere.registrar.ItemRegistrant;
import survivalblock.atmosphere.registrar.SoundEventRegistrant;

public class Nephra implements ModInitializer {

	public static final String MOD_ID = "nephra";
	public static final String MOD_NAME = "Nephra";

	public static final BlockRegistrant BLOCK_REGISTRANT = new BlockRegistrant(MOD_ID);
	public static final ItemRegistrant ITEM_REGISTRANT = new ItemRegistrant(MOD_ID);
	public static final SoundEventRegistrant SOUND_EVENT_REGISTRANT = new SoundEventRegistrant(MOD_ID);
	public static final GameRuleRegistrant GAME_RULE_REGISTRANT = new GameRuleRegistrant(MOD_ID);

	@Override
	public void onInitialize() {
		ModBlocks.init();
		ModSoundEvents.init();
		ModGameRules.init();

		ITEM_REGISTRANT.registerFromAnnotations(References.class, false);

		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.NATURAL_BLOCKS).register(Nephra::tabModification);
		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(Nephra::tabModification);

		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.BUILDING_BLOCKS).register(output -> output.insertAfter(
			Items.NETHERRACK,
			ModBlocks.NEPHRA,
			ModBlocks.NEPHRA_STAIRS,
			ModBlocks.NEPHRA_SLAB,
			ModBlocks.NEPHRA_WALL
		));

		PayloadTypeRegistry.clientboundPlay().register(ClientboundCoolingMagmaPayload.TYPE, ClientboundCoolingMagmaPayload.STREAM_CODEC);
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}

	public static <T extends CustomPacketPayload> CustomPacketPayload.Type<T> payloadType(String path) {
		return new CustomPacketPayload.Type<>(id(path));
	}

	private static void tabModification(FabricCreativeModeTabOutput output) {
		output.insertBefore(Items.MAGMA_BLOCK, ModBlocks.NEPHRA);
	}

}