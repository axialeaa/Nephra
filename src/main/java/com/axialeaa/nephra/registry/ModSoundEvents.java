package com.axialeaa.nephra.registry;

import com.axialeaa.nephra.Nephra;
import net.minecraft.sounds.SoundEvent;
import survivalblock.atmosphere.registrar.SoundEventRegistrant;

public final class ModSoundEvents {

	private static final SoundEventRegistrant REGISTRANT = Nephra.createRegistrant(SoundEventRegistrant::new);

	public static final SoundEvent NEPHRA_ABSORB = REGISTRANT.register("block.nephra.absorb");

	private ModSoundEvents() {}

	public static void init() {}

}
