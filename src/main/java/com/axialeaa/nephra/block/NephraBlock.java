package com.axialeaa.nephra.block;

import com.axialeaa.nephra.registry.ModGameRules;
import com.axialeaa.nephra.registry.ModSoundEvents;
import com.axialeaa.nephra.registry.ModTags;
import com.axialeaa.nephra.registry.ModBlocks;
import com.axialeaa.nephra.network.ClientboundMagmaCoolingPayload;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.PotentSulfurState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.Vec3;
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
		return level.getGameRules().get(ModGameRules.INSTANT_BLOCK_COOLING) && isTouchingCoolant(level, pos);
	}

	public static boolean isTouchingLava(BlockGetter level, BlockPos pos) {
		for (Direction direction : Direction.values()) {
			if (level.getFluidState(pos.relative(direction)).is(ModTags.Fluids.NEPHRA_ABSORBABLE))
				return true;
		}

		return false;
	}

	private static boolean isTouchingCoolant(BlockGetter level, BlockPos pos) {
		for (Direction direction : Direction.values()) {
			if (level.getBlockState(pos.relative(direction)).is(ModTags.BlockItems.MAGMA_COOLANTS.block()))
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
			ClientboundMagmaCoolingPayload.sendToNearbyPlayers(level, pos);

			level.setBlockAndUpdate(pos, ModBlocks.NEPHRA.get().defaultBlockState());
			level.gameEvent(null, GameEvent.BLOCK_CHANGE, pos);
		}
	}

	public static boolean canCoolPassively(ServerLevel level, BlockPos pos, BlockState state) {
		GameRules gameRules = level.getGameRules();

		if (gameRules.get(ModGameRules.PASSIVE_BIOME_COOLING_TICKS) == ModGameRules.DISABLE_PASSIVE_BIOME_COOLING)
			return false;

		if (!gameRules.get(ModGameRules.ALLOW_PASSIVE_COOLING_IF_TOUCHING_LAVA) && isTouchingLava(level, pos))
			return false;

		BlockState aboveState = level.getBlockState(pos.above());

		if (aboveState.getBlock() instanceof BubbleColumnBlock || isProvidingGeyserPlume(state, aboveState))
			return false;

		return isInColdBiome(level, pos);
	}

	private static boolean isProvidingGeyserPlume(BlockState state, BlockState aboveState) {
		if (!state.is(BlockTags.CAUSES_PERIODIC_GEYSER_ERUPTIONS) && !state.is(BlockTags.CAUSES_CONTINUOUS_GEYSER_ERUPTIONS))
			return false;

		return aboveState.getBlock() instanceof PotentSulfurBlock && aboveState.getValue(PotentSulfurBlock.STATE) != PotentSulfurState.DRY;
	}

	private static boolean isInColdBiome(Level level, BlockPos pos) {
		Holder<Biome> biome = level.getBiome(pos);

		if (biome.is(ModTags.Biomes.CANNOT_PASSIVELY_COOL_MAGMA))
			return false;

		return biome.is(ModTags.Biomes.MAGMA_PASSIVE_COOLING_OVERRIDE) || biome.value().coldEnoughToSnow(pos, level.getSeaLevel());
	}

	public static void animate(Level level, BlockPos pos, Direction face, RandomSource random) {
		if (isInColdBiome(level, pos)) {
			Vec3 motion = new Vec3(0.05, 0.05, 0.05).multiply(face.getUnitVec3());
			ParticleUtils.spawnParticleOnFace(level, pos, face, ParticleTypes.SMOKE, motion, 0.55F);
		}
		else if (face != Direction.UP && random.nextFloat() < 0.25F)
			ParticleUtils.spawnParticleOnFace(level, pos, face, ParticleTypes.DRIPPING_LAVA, Vec3.ZERO, 0.55F);
	}

}
