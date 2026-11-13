package com.axialeaa.nephra.block;

import com.axialeaa.nephra.registry.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

import java.util.function.Consumer;
import java.util.function.Function;

public enum LavaSpongeBehaviour {
		
	VISCOUS(3, 10),
	FAST(4, 24);

	private final int maxDepth;
	private final int maxCount;

	LavaSpongeBehaviour(int maxDepth, int maxCount) {
		this.maxDepth = maxDepth;
		this.maxCount = maxCount;
	}

	public boolean absorbLava(ServerLevel level, BlockPos pos) {
		Function<BlockPos, BlockPos.TraversalNodeStatus> callback = p -> getNodeStatus(level, pos, p);
		return BlockPos.breadthFirstTraversal(pos, this.maxDepth, this.maxCount, LavaSpongeBehaviour::enqueueNextPos, callback) > 1;
	}

	private static void enqueueNextPos(BlockPos pos, Consumer<BlockPos> queuer) {
		for (Direction direction : Direction.values())
			queuer.accept(pos.relative(direction));
	}

	private static BlockPos.TraversalNodeStatus getNodeStatus(ServerLevel level, BlockPos startPos, BlockPos nodePos) {
		return isAcceptableTraversalNode(level, startPos, nodePos) ? BlockPos.TraversalNodeStatus.ACCEPT : BlockPos.TraversalNodeStatus.SKIP;
	}

	private static boolean isAcceptableTraversalNode(ServerLevel level, BlockPos nephraPos, BlockPos visitingPos) {
		if (visitingPos.equals(nephraPos))
			return true;

		BlockState visitingState = level.getBlockState(visitingPos);
		FluidState visitingFluidState = level.getFluidState(visitingPos);
		Block visitingBlock = visitingState.getBlock();

		if (visitingFluidState.is(ModTags.Fluids.NEPHRA_ABSORBABLE) && visitingBlock instanceof BucketPickup bucketPickup) {
			if (visitingBlock instanceof LiquidBlock) {
				level.setBlockAndUpdate(visitingPos, Blocks.AIR.defaultBlockState());
				return true;
			}

			return !bucketPickup.pickupBlock(null, level, visitingPos, visitingState).isEmpty();
		}

		return false;
	}

}