package com.axialeaa.nephra.registry;

import com.axialeaa.nephra.Nephra;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleBuilder;
import net.minecraft.SharedConstants;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;
import survivalblock.atmosphere.registrar.GameRuleRegistrant;

public final class ModGameRules {

	private static final GameRuleCategory CATEGORY = new GameRuleCategory(Nephra.id("gamerule_category"));
	private static final GameRuleRegistrant REGISTRANT = Nephra.createRegistrant(GameRuleRegistrant::new)
		.specifyDefaultCategory(CATEGORY);

	public static final int DISABLE_PASSIVE_BIOME_COOLING = -1;

	public static final GameRule<Boolean> ALLOW_PASSIVE_COOLING_IF_TOUCHING_LAVA = REGISTRANT.registerBoolean("allow_passive_cooling_if_touching_lava", false);
	public static final GameRule<Boolean> LAVA_SPONGING = REGISTRANT.registerBoolean("lava_sponging", true);
	public static final GameRule<Boolean> INSTANT_BLOCK_COOLING = REGISTRANT.registerBoolean("instant_block_cooling", false);
	public static final GameRule<Integer> PASSIVE_BIOME_COOLING_TICKS = REGISTRANT.register(
		"passive_biome_cooling_ticks",
		GameRuleBuilder.forInteger(SharedConstants.TICKS_PER_MINUTE * 10)
			.minValue(DISABLE_PASSIVE_BIOME_COOLING)
			.category(CATEGORY)
	);

	private ModGameRules() {}

	public static void init() {}

}
