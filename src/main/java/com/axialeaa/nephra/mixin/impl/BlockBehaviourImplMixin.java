package com.axialeaa.nephra.mixin.impl;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.redstone.Orientation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BlockBehaviour.class)
public abstract class BlockBehaviourImplMixin {

	@SuppressWarnings("unused")
	@Shadow
	protected abstract boolean isRandomlyTicking(BlockState state);

	@SuppressWarnings("unused")
	@Shadow protected abstract void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random);

	@SuppressWarnings("unused")
	@Shadow protected abstract void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random);

	@WrapMethod(method = "neighborChanged")
    public void neighborChangedImpl(BlockState state, Level level, BlockPos pos, Block block, Orientation orientation, boolean movedByPiston, Operation<Void> original) {
        original.call(state, level, pos, block, orientation, movedByPiston);
    }

	@WrapMethod(method = "onPlace")
	public void onPlaceImpl(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston, Operation<Void> original) {
		original.call(state, level, pos, oldState, movedByPiston);
	}

}
