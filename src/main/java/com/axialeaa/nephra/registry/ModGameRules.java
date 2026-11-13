package com.axialeaa.nephra.registry;

import com.axialeaa.nephra.Nephra;
import com.axialeaa.nephra.registry.game_rule.MagmaCoolingMode;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleBuilder;
import net.minecraft.SharedConstants;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;
import survivalblock.atmosphere.registrar.GameRuleRegistrant;

public final class ModGameRules {

	private static final GameRuleCategory CATEGORY = new GameRuleCategory(Nephra.id("gamerules"));
	private static final GameRuleRegistrant REGISTRANT = Nephra.createRegistrant(GameRuleRegistrant::new)
		.specifyDefaultCategory(CATEGORY);

	public static final GameRule<MagmaCoolingMode> INSTANT_BLOCK_COOLING_MODE = REGISTRANT.registerEnum(
		"instant_block_cooling",
		MagmaCoolingMode.FALSE
	);

	public static final GameRule<Boolean> PASSIVE_BIOME_COOLING_REQUIRES_COOLANT = REGISTRANT.registerBoolean(
		"passive_biome_cooling_requires_coolant",
		true
	);

	public static final GameRule<Integer> PASSIVE_BIOME_COOLING_TICKS = REGISTRANT.register(
		"passive_biome_cooling_ticks",
		GameRuleBuilder.forInteger(SharedConstants.TICKS_PER_MINUTE * 10)
			.minValue(0)
			.category(CATEGORY)
	);

	private ModGameRules() {}

	public static void init() {}

}
