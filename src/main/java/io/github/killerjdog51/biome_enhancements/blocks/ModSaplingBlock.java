package io.github.killerjdog51.biome_enhancements.blocks;

import io.github.killerjdog51.biome_enhancements.registries.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.CollisionView;

public class ModSaplingBlock extends SaplingBlock {

	public ModSaplingBlock(SaplingGenerator tree, Settings settings) {
		super(tree, settings);
	}

	@Override
	public boolean canPlaceAt(BlockState state, CollisionView worldIn, BlockPos pos) {
	      Block block = state.getBlock();
	      if (this == ModBlocks.PALM_SAPLING)
	      {
		      return block == Blocks.GRASS_BLOCK || block == Blocks.DIRT || block == Blocks.COARSE_DIRT || block == Blocks.PODZOL || block == Blocks.FARMLAND || state.matches(BlockTags.SAND);
	      }
	      return block == Blocks.GRASS_BLOCK || block == Blocks.DIRT || block == Blocks.COARSE_DIRT || block == Blocks.PODZOL || block == Blocks.FARMLAND;
	   }
}
