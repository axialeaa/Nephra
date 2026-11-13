package com.axialeaa.nephra.mixin;

import com.axialeaa.nephra.registry.ModGameRules;
import com.axialeaa.nephra.mixin.impl.BlockImplMixin;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MagmaBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.redstone.Orientation;
import org.spongepowered.asm.mixin.Mixin;

import static com.axialeaa.nephra.block.NephraBlock.*;

@Mixin(MagmaBlock.class)
public class MagmaBlockMixin extends BlockImplMixin {

	@Override
	public void onPlaceImpl(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston, Operation<Void> original) {
		if (level instanceof ServerLevel serverLevel && canCoolInstantly(serverLevel, pos))
			coolMagmaToNephra(serverLevel, pos);

		super.onPlaceImpl(state, level, pos, oldState, movedByPiston, original);
	}

	@Override
	public void neighborChangedImpl(BlockState state, Level level, BlockPos pos, Block block, Orientation orientation, boolean movedByPiston, Operation<Void> original) {
		if (level instanceof ServerLevel serverLevel && canCoolInstantly(serverLevel, pos))
			coolMagmaToNephra(serverLevel, pos);

		super.neighborChangedImpl(state, level, pos, block, orientation, movedByPiston, original);
	}

	@Override
	protected boolean isRandomlyTicking(BlockState state) {
		return true;
	}

	@Override
	protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (canCoolPassively(level, pos))
			return;

		Block thisBlock = (Block) (Object) this;

		if (!level.getBlockTicks().hasScheduledTick(pos, thisBlock)) {
			int coolingTicks = level.getGameRules().get(ModGameRules.PASSIVE_BIOME_COOLING_TICKS);

			if (coolingTicks == 0)
				state.tick(level, pos, random);
			else level.scheduleTick(pos, thisBlock, coolingTicks);
		}
	}

	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (canCoolPassively(level, pos))
			coolMagmaToNephra(level, pos);
	}

	@Override
	public void animateTickImpl(BlockState state, Level level, BlockPos pos, RandomSource random, Operation<Void> original) {
		super.animateTickImpl(state, level, pos, random, original);

		Direction face = Direction.getRandom(random);
		BlockPos relativePos = pos.relative(face);
		BlockState blockState = level.getBlockState(relativePos);

		if (!state.canOcclude() || !blockState.isFaceSturdy(level, relativePos, face.getOpposite()))
			animate(level, pos, face);
	}

}
