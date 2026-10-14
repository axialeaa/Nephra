package com.axialeaa.nephra.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class ConnectedPillarBlock extends RotatedPillarBlock {

	public static final BooleanProperty CONNECTED_POSITIVE = BooleanProperty.create("connected_positive");
	public static final BooleanProperty CONNECTED_NEGATIVE = BooleanProperty.create("connected_negative");

	public ConnectedPillarBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState()
			.setValue(CONNECTED_POSITIVE, false)
			.setValue(CONNECTED_NEGATIVE, false)
		);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockState stateForPlacement = super.getStateForPlacement(context);

		Level level = context.getLevel();
		BlockPos pos = context.getClickedPos();
		Direction.Axis axis = stateForPlacement.getValue(AXIS);

		BlockPos positivePos = pos.relative(axis.getPositive());
		BlockPos negativePos = pos.relative(axis.getNegative());

		if (this.isAlignedWithNeighbour(stateForPlacement, level.getBlockState(positivePos)))
			stateForPlacement = stateForPlacement.setValue(CONNECTED_POSITIVE, true);

		if (this.isAlignedWithNeighbour(stateForPlacement, level.getBlockState(negativePos)))
			stateForPlacement = stateForPlacement.setValue(CONNECTED_NEGATIVE, true);

		return stateForPlacement;
	}

	@Override
	protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction directionToNeighbour, BlockPos neighbourPos, BlockState neighbourState, RandomSource random) {
		Direction.Axis axis = state.getValue(AXIS);
		boolean aligned = this.isAlignedWithNeighbour(state, neighbourState);

		if (directionToNeighbour == axis.getPositive() && state.getValue(CONNECTED_POSITIVE) != aligned)
			return state.setValue(CONNECTED_POSITIVE, aligned);

		if (directionToNeighbour == axis.getNegative() && state.getValue(CONNECTED_NEGATIVE) != aligned)
			return state.setValue(CONNECTED_NEGATIVE, aligned);

		return state;
	}

	private boolean isAlignedWithNeighbour(BlockState state, BlockState neighbourState) {
		return neighbourState.is(this) && neighbourState.getValue(AXIS) == state.getValue(AXIS);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder.add(CONNECTED_POSITIVE, CONNECTED_NEGATIVE));
	}

}
