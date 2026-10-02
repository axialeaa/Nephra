package com.axialeaa.nephra.data.registry;

import net.minecraft.sounds.SoundEvent;

import static com.axialeaa.nephra.Nephra.SOUND_EVENT_REGISTRANT;

public final class ModSoundEvents {

	public static final SoundEvent NEPHRA_ABSORB = SOUND_EVENT_REGISTRANT.register("block.nephra.absorb");

	private ModSoundEvents() {}

	public static void init() {}

}
