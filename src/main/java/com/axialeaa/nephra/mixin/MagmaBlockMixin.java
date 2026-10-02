package com.axialeaa.nephra.mixin;

import com.axialeaa.nephra.block.NephraBlock;
import com.axialeaa.nephra.data.registry.ModGameRules;
import com.axialeaa.nephra.mixin.impl.BlockBehaviourImplMixin;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MagmaBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.redstone.Orientation;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(MagmaBlock.class)
public class MagmaBlockMixin extends BlockBehaviourImplMixin {

	@Override
	public void onPlaceImpl(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston, Operation<Void> original) {
		if (level instanceof ServerLevel serverLevel)
			NephraBlock.coolMagma(serverLevel, pos, false);

		super.onPlaceImpl(state, level, pos, oldState, movedByPiston, original);
	}

	@Override
	public void neighborChangedImpl(BlockState state, Level level, BlockPos pos, Block block, Orientation orientation, boolean movedByPiston, Operation<Void> original) {
		if (level instanceof ServerLevel serverLevel)
			NephraBlock.coolMagma(serverLevel, pos, false);

		super.neighborChangedImpl(state, level, pos, block, orientation, movedByPiston, original);
	}

	@Override
	public boolean isRandomlyTickingImpl(BlockState state, Operation<Boolean> original) {
		return true;
	}

	@Override
	public void randomTickImpl(BlockState state, ServerLevel level, BlockPos pos, RandomSource random, Operation<Void> original) {
		super.randomTickImpl(state, level, pos, random, original);

		if (NephraBlock.canCoolPassively(level, pos) && !level.getBlockTicks().hasScheduledTick(pos, Blocks.MAGMA_BLOCK)) {
			int coolingTicks = level.getGameRules().get(ModGameRules.PASSIVE_BIOME_COOLING_TICKS);

			if (coolingTicks == 0)
				state.tick(level, pos, random);
			else level.scheduleTick(pos, Blocks.MAGMA_BLOCK, coolingTicks);
		}
	}

	@Override
	public void tickImpl(BlockState state, ServerLevel level, BlockPos pos, RandomSource random, Operation<Void> original) {
		super.tickImpl(state, level, pos, random, original);

		if (NephraBlock.canCoolPassively(level, pos))
			NephraBlock.coolMagma(level, pos, true);
	}

}
