package com.axialeaa.nephra.data.provider;

import com.axialeaa.nephra.data.registry.ModSoundEvents;
import net.fabricmc.fabric.api.client.datagen.v1.builder.SoundTypeBuilder;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricSoundsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvents;

import java.util.concurrent.CompletableFuture;

public class ModSoundsProvider extends FabricSoundsProvider {

	public ModSoundsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void configure(HolderLookup.Provider registryLookup, SoundExporter exporter) {
		exporter.add(ModSoundEvents.NEPHRA_ABSORB, SoundTypeBuilder.of(ModSoundEvents.NEPHRA_ABSORB)
			.sound(SoundTypeBuilder.RegistrationBuilder.ofEvent(SoundEvents.SPONGE_ABSORB).pitch(0.6F))
		);
	}

	@Override
	public String getName() {
		return "Sounds";
	}

}
