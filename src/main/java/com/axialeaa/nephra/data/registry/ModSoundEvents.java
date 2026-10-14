package com.axialeaa.nephra.data.registry;

import com.axialeaa.nephra.Nephra;
import net.minecraft.sounds.SoundEvent;
import survivalblock.atmosphere.registrar.SoundEventRegistrant;

public final class ModSoundEvents {

	public static final SoundEventRegistrant REGISTRANT = new SoundEventRegistrant(Nephra.MOD_ID);

	public static final SoundEvent NEPHRA_ABSORB = REGISTRANT.register("block.nephra.absorb");

	private ModSoundEvents() {}

	public static void init() {}

}
