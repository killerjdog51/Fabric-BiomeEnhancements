package io.github.killerjdog51.biome_enhancements.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.Waterloggable;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class MangroveSaplingBlock extends SaplingBlock  implements Waterloggable {

	public static final BooleanProperty WATERLOGGED;

	public MangroveSaplingBlock(SaplingGenerator tree, Settings settings) {
		super(tree, settings);
		this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState().with(WATERLOGGED, Boolean.valueOf(false))));
	}

	@Override
	public FluidState getFluidState(BlockState state)
	{
	      return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
	}
	
	@Override
	public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving)
	{
		worldIn.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
	}
	
	@Override
	public BlockState getPlacementState(ItemPlacementContext context) {
	      BlockPos blockpos = context.getBlockPos();
	      FluidState ifluidstate = context.getWorld().getFluidState(blockpos);
	      BlockState blockstate = this.getDefaultState().with(WATERLOGGED, Boolean.valueOf(ifluidstate.getFluid() == Fluids.WATER));
	      return blockstate;
	   }
	
	@Override
	public BlockState getStateForNeighborUpdate(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
	{
		if (stateIn.get(WATERLOGGED)) {
			worldIn.getFluidTickScheduler().schedule(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
		}
		return super.getStateForNeighborUpdate(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}
	
	
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
	{
	      builder.add(WATERLOGGED);
	      super.appendProperties(builder);
	}
	
	static
	{
		WATERLOGGED = Properties.WATERLOGGED;
	}
}
