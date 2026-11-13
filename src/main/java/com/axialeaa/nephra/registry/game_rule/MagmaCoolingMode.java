package com.axialeaa.nephra.registry.game_rule;

import com.axialeaa.nephra.block.NephraBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public enum MagmaCoolingMode {

	TRUE,
	IN_COLD_BIOME,
	FALSE;

	public boolean booleanValue(Level level, BlockPos pos) {
		return this == IN_COLD_BIOME ? NephraBlock.isInColdBiome(level, pos) : this == TRUE;
	}

}
