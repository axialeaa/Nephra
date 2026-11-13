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
import net.minecraft.tags.BlockItemTagId;
import net.minecraft.util.ParticleUtils;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
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

	public static boolean canCoolInstantly(ServerLevel level, BlockPos pos) {
		return level.getGameRules().get(ModGameRules.INSTANT_BLOCK_COOLING_MODE).booleanValue(level, pos) &&
			   isTouchingCoolant(level, pos, false);
	}

	private static boolean isTouchingLava(BlockGetter level, BlockPos pos) {
		for (Direction direction : Direction.values()) {
			if (level.getFluidState(pos.relative(direction)).is(ModTags.Fluids.NEPHRA_ABSORBABLE))
				return true;
		}

		return false;
	}

	private static boolean isTouchingCoolant(BlockGetter level, BlockPos pos, boolean instant) {
		BlockItemTagId tag = instant ? ModTags.BlockItems.INSTANT_MAGMA_COOLANTS : ModTags.BlockItems.PASSIVE_MAGMA_COOLANTS;

		for (Direction direction : Direction.values()) {
			if (level.getBlockState(pos.relative(direction)).is(tag.block()))
				return true;
		}

		return false;
	}

	private void update(ServerLevel level, BlockPos pos) {
		if (!canCoolInstantly(level, pos) && getSpongeBehaviour(level).absorbLava(level, pos)) {
			level.setBlockAndUpdate(pos, Blocks.MAGMA_BLOCK.defaultBlockState());
			level.gameEvent(null, GameEvent.BLOCK_CHANGE, pos);
			level.playSound(null, pos, ModSoundEvents.NEPHRA_ABSORB, SoundSource.BLOCKS);
		}
	}

	public static void coolMagmaToNephra(ServerLevel level, BlockPos pos) {
		ClientboundMagmaCoolingPayload.sendToNearbyPlayers(level, pos);

		level.setBlockAndUpdate(pos, ModBlocks.NEPHRA.get().defaultBlockState());
		level.gameEvent(null, GameEvent.BLOCK_CHANGE, pos);
	}

	public static boolean canCoolPassively(ServerLevel level, BlockPos pos) {
		if (level.getGameRules().get(ModGameRules.PASSIVE_BIOME_COOLING_REQUIRES_COOLANT) && !isTouchingCoolant(level, pos, false))
			return false;

		return !isTouchingLava(level, pos);
	}

	public static boolean isInColdBiome(Level level, BlockPos pos) {
		Holder<Biome> biome = level.getBiome(pos);

		if (biome.is(ModTags.Biomes.CANNOT_PASSIVELY_COOL_MAGMA))
			return false;

		if (biome.is(ModTags.Biomes.MAGMA_PASSIVE_COOLING_OVERRIDE))
			return true;

		return biome.value().coldEnoughToSnow(pos, level.getSeaLevel());
	}

	public static void animate(Level level, BlockPos pos, Direction face) {
		if (isInColdBiome(level, pos) || isTouchingCoolant(level, pos, false)) {
			Vec3 motion = new Vec3(0.05, 0.05, 0.05).multiply(face.getUnitVec3());
			ParticleUtils.spawnParticleOnFace(level, pos, face, ParticleTypes.SMOKE, motion, 0.55F);
		}
	}

}
