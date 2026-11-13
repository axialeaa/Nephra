package com.axialeaa.nephra.mixin.impl;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Block.class)
public abstract class BlockImplMixin extends BlockBehaviourImplMixin {

	@WrapMethod(method = "animateTick")
	public void animateTickImpl(BlockState state, Level level, BlockPos pos, RandomSource random, Operation<Void> original) {
		original.call(state, level, pos, random);
	}

}
