package com.axialeaa.nephra.data.registry;

import com.axialeaa.nephra.Nephra;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleBuilder;
import net.minecraft.SharedConstants;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;

import static com.axialeaa.nephra.Nephra.GAME_RULE_REGISTRANT;

public final class ModGameRules {

	private static final GameRuleCategory CATEGORY = new GameRuleCategory(Nephra.id("gamerule_category"));

	public static final GameRule<Boolean> INSTANT_BLOCK_COOLING = register(
		"instant_block_cooling",
		GameRuleBuilder.forBoolean(false)
	);

	public static final GameRule<Integer> PASSIVE_BIOME_COOLING_TICKS = register(
		"passive_biome_cooling_ticks",
		GameRuleBuilder.forInteger(SharedConstants.TICKS_PER_MINUTE * 10).minValue(-1)
	);

	public static final GameRule<Boolean> ALLOW_PASSIVE_COOLING_IF_TOUCHING_LAVA = register(
		"allow_passive_cooling_if_touching_lava",
		GameRuleBuilder.forBoolean(false)
	);

	public static final GameRule<Boolean> LAVA_SPONGING = register(
		"lava_sponging",
		GameRuleBuilder.forBoolean(true)
	);

	private ModGameRules() {}

	private static <T> GameRule<T> register(String path, GameRuleBuilder<T> builder) {
		return GAME_RULE_REGISTRANT.register(path, builder.category(CATEGORY));
	}

	public static void init() {}

}
