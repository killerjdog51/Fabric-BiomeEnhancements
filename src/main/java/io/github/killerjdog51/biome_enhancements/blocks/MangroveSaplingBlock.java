package io.github.killerjdog51.biome_enhancements.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
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

// We want this tree to be grown in/under water
public class MangroveSaplingBlock extends ModSaplingBlock  implements Waterloggable {

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
	
	// This checks if the block is waterlogged when initially placed in the world
	@Override
	public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving)
	{
		worldIn.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
	}
	
	// This will allow the block to be waterlogged after placement
	@Override
	public BlockState getPlacementState(ItemPlacementContext context) {
	      BlockPos blockpos = context.getBlockPos();
	      FluidState ifluidstate = context.getWorld().getFluidState(blockpos);
	      BlockState blockstate = this.getDefaultState().with(WATERLOGGED, Boolean.valueOf(ifluidstate.getFluid() == Fluids.WATER));
	      return blockstate;
	   }
	
	// This helps update the block property
	@Override
	public BlockState getStateForNeighborUpdate(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
	{
		if (stateIn.get(WATERLOGGED)) {
			worldIn.getFluidTickScheduler().schedule(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
		}
		return super.getStateForNeighborUpdate(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}
	
	// This is how the property is implemented into the block
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
	{
	      builder.add(WATERLOGGED);
	      super.appendProperties(builder);
	}
	
	// This is where we set the property value
	static
	{
		WATERLOGGED = Properties.WATERLOGGED;
	}
}
