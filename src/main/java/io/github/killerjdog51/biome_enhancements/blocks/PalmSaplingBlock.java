package io.github.killerjdog51.biome_enhancements.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.CollisionView;

public class PalmSaplingBlock extends ModSaplingBlock {

	public PalmSaplingBlock(SaplingGenerator tree, Settings settings) {
		super(tree, settings);
	}

	// We want our sapling to be placed on sand (since it's a desert/beach tree)
	@Override
	public boolean canPlaceAt(BlockState state, CollisionView worldIn, BlockPos pos)
	{
		Block block = worldIn.getBlockState(pos.down()).getBlock();
		return block == Blocks.GRASS_BLOCK || block == Blocks.DIRT || block == Blocks.COARSE_DIRT || block == Blocks.PODZOL || block == Blocks.FARMLAND || block.getDefaultState().matches(BlockTags.SAND);
	}
}
