package io.github.killerjdog51.biome_enhancements.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FacingBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.Direction;


public class RotatedBlock extends Block
{
	   public static final EnumProperty<Direction> FACING;


	public RotatedBlock(Settings settings)
	{
		super(settings);
	    this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(FACING, Direction.UP));
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext context)
	{
	      return (BlockState)this.getDefaultState().with(FACING, context.getSide());
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
	{
		builder.add(FACING);
	}
	
	@Override
	public BlockState rotate(BlockState state, BlockRotation rotation)
	{
	      return (BlockState)state.with(FACING, rotation.rotate((Direction)state.get(FACING)));
	}
	
	@Override
	public BlockState mirror(BlockState state, BlockMirror mirror)
	{
	      return state.rotate(mirror.getRotation((Direction)state.get(FACING)));
	}
	
	static {
		FACING = FacingBlock.FACING;
	}
}
