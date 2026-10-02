package com.axialeaa.nephra.block;

import com.axialeaa.nephra.data.registry.ModGameRules;
import com.axialeaa.nephra.data.registry.ModSoundEvents;
import com.axialeaa.nephra.data.registry.ModTags;
import com.axialeaa.nephra.data.registry.ModBlocks;
import com.axialeaa.nephra.network.ClientboundCoolingMagmaPayload;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.redstone.Orientation;
import org.jspecify.annotations.Nullable;

public class NephraBlock extends Block {

	public NephraBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
		if (!oldState.is(state.getBlock()) && level instanceof ServerLevel serverLevel)
			this.update(serverLevel, pos);
	}

	@Override
	protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, @Nullable Orientation orientation, boolean movedByPiston) {
		if (level instanceof ServerLevel serverLevel)
			this.update(serverLevel, pos);
	}

	private static LavaSpongeBehaviour getSpongeBehaviour(ServerLevel level) {
		return level.environmentAttributes().getDimensionValue(EnvironmentAttributes.FAST_LAVA) ? LavaSpongeBehaviour.FAST : LavaSpongeBehaviour.VISCOUS;
	}

	private static boolean canCoolInstantly(ServerLevel level, BlockPos pos) {
		if (!level.getGameRules().get(ModGameRules.INSTANT_BLOCK_COOLING))
			return false;

		for (Direction direction : Direction.values()) {
			if (level.getBlockState(pos.relative(direction)).is(ModTags.Blocks.MAGMA_COOLANTS))
				return true;
		}

		return false;
	}

	public static boolean isTouchingLava(ServerLevel level, BlockPos pos) {
		for (Direction direction : Direction.values()) {
			if (level.getFluidState(pos.relative(direction)).is(FluidTags.LAVA))
				return true;
		}

		return false;
	}

	private void update(ServerLevel level, BlockPos pos) {
		if (level.getGameRules().get(ModGameRules.LAVA_SPONGING) && !canCoolInstantly(level, pos) && getSpongeBehaviour(level).absorbLava(level, pos)) {
			level.setBlockAndUpdate(pos, Blocks.MAGMA_BLOCK.defaultBlockState());
			level.gameEvent(null, GameEvent.BLOCK_CHANGE, pos);
			level.playSound(null, pos, ModSoundEvents.NEPHRA_ABSORB, SoundSource.BLOCKS);
		}
	}

	public static void coolMagma(ServerLevel level, BlockPos pos, boolean force) {
		if (force || canCoolInstantly(level, pos)) {
			ClientboundCoolingMagmaPayload.sendToNearbyPlayers(level, pos);

			level.setBlockAndUpdate(pos, ModBlocks.NEPHRA.defaultBlockState());
			level.gameEvent(null, GameEvent.BLOCK_CHANGE, pos);
		}
	}

	public static boolean canCoolPassively(ServerLevel level, BlockPos pos) {
		GameRules gameRules = level.getGameRules();
		Holder<Biome> biome = level.getBiome(pos);

		if (!gameRules.get(ModGameRules.ALLOW_PASSIVE_COOLING_IF_TOUCHING_LAVA) && isTouchingLava(level, pos))
			return false;

		if (gameRules.get(ModGameRules.PASSIVE_BIOME_COOLING_TICKS) == -1 || biome.is(ModTags.Biomes.CANNOT_PASSIVELY_COOL_MAGMA))
			return false;

		return biome.is(ModTags.Biomes.MAGMA_PASSIVE_COOLING_OVERRIDE) || biome.value().coldEnoughToSnow(pos, level.getSeaLevel());
	}

}
