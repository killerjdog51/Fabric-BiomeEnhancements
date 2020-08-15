package io.github.killerjdog51.biome_enhancements.blocks.sapling;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.FeatureConfig;

public abstract class GiantTreeSaplingGenerator extends SaplingGenerator
{
   public boolean generate(IWorld worldIn, BlockPos pos, BlockState blockUnder, Random random)
   {
	   // Find the start position for tree generation
	   BlockPos blockpos = findEdge(worldIn, pos);
	   
	   // Double check that there are enough saplings to generate the tree (4x4)
      for(int x = 0; x >= -3; --x)
      {
         for(int z = 0; z >= -3; --z)
         {
        	// If there are the correct amount of saplings and the correct block underneath try to grow the tree
            if (canGenerateLargeTree(blockUnder, worldIn, blockpos, x, z))
            {
               return this.generateLargeTree(worldIn, blockpos, blockUnder, random, x, z);
            }
         }
      }

      // If the mutable tree was successful then we actually generate the tree
      return super.generate(worldIn, blockpos, blockUnder, random);
   }

   // This holds the tree feature that will be generated
   protected abstract AbstractTreeFeature<DefaultFeatureConfig> createLargeTreeFeature(Random random);

   // This attempts to generate a mutable version of the tree
   public boolean generateLargeTree(IWorld worldIn, BlockPos pos, BlockState blockUnder, Random random, int xOffset, int zOffset)
   {
	  // Retrieves the tree feature being used
      AbstractTreeFeature<DefaultFeatureConfig> abstracttreefeature = this.createLargeTreeFeature(random);
      
      // If there isn't a tree feature then exit
      if (abstracttreefeature == null)
      {
         return false;
      }
      
      // Otherwise try to generate the tree
      else
      {
    	 // This sets all the saplings to air
         BlockState blockstate = Blocks.AIR.getDefaultState();
         worldIn.setBlockState(pos.add(xOffset, 0, zOffset), blockstate, 4);
         worldIn.setBlockState(pos.add(xOffset, 0, zOffset + 1), blockstate, 4);
         worldIn.setBlockState(pos.add(xOffset, 0, zOffset + 2), blockstate, 4);
         worldIn.setBlockState(pos.add(xOffset, 0, zOffset + 3), blockstate, 4);

         worldIn.setBlockState(pos.add(xOffset + 1, 0, zOffset), blockstate, 4);
         worldIn.setBlockState(pos.add(xOffset + 1, 0, zOffset + 1), blockstate, 4);
         worldIn.setBlockState(pos.add(xOffset + 1, 0, zOffset + 2), blockstate, 4);
         worldIn.setBlockState(pos.add(xOffset + 1, 0, zOffset + 3), blockstate, 4);

         worldIn.setBlockState(pos.add(xOffset + 2, 0, zOffset), blockstate, 4);
         worldIn.setBlockState(pos.add(xOffset + 2, 0, zOffset + 1), blockstate, 4);
         worldIn.setBlockState(pos.add(xOffset + 2, 0, zOffset + 2), blockstate, 4);
         worldIn.setBlockState(pos.add(xOffset + 2, 0, zOffset + 3), blockstate, 4);

         worldIn.setBlockState(pos.add(xOffset + 3, 0, zOffset), blockstate, 4);
         worldIn.setBlockState(pos.add(xOffset + 3, 0, zOffset + 1), blockstate, 4);
         worldIn.setBlockState(pos.add(xOffset + 3, 0, zOffset + 2), blockstate, 4);
         worldIn.setBlockState(pos.add(xOffset + 3, 0, zOffset + 3), blockstate, 4);

         // This tests if the tree can generate using the mutable blocks
         if (abstracttreefeature.generate(worldIn, worldIn.getChunkManager().getChunkGenerator(), random, pos.add(xOffset, 0, zOffset), FeatureConfig.DEFAULT)) {
            return true;
         }
         else
         {
        	 // This sets all the blocks back to saplings if the tree generation failed
        	 worldIn.setBlockState(pos.add(xOffset, 0, zOffset), blockUnder, 4);
             worldIn.setBlockState(pos.add(xOffset, 0, zOffset + 1), blockUnder, 4);
             worldIn.setBlockState(pos.add(xOffset, 0, zOffset + 2), blockUnder, 4);
             worldIn.setBlockState(pos.add(xOffset, 0, zOffset + 3), blockUnder, 4);

             worldIn.setBlockState(pos.add(xOffset + 1, 0, zOffset), blockUnder, 4);
             worldIn.setBlockState(pos.add(xOffset + 1, 0, zOffset + 1), blockUnder, 4);
             worldIn.setBlockState(pos.add(xOffset + 1, 0, zOffset + 2), blockUnder, 4);
             worldIn.setBlockState(pos.add(xOffset + 1, 0, zOffset + 3), blockUnder, 4);

             worldIn.setBlockState(pos.add(xOffset + 2, 0, zOffset), blockUnder, 4);
             worldIn.setBlockState(pos.add(xOffset + 2, 0, zOffset + 1), blockUnder, 4);
             worldIn.setBlockState(pos.add(xOffset + 2, 0, zOffset + 2), blockUnder, 4);
             worldIn.setBlockState(pos.add(xOffset + 2, 0, zOffset + 3), blockUnder, 4);

             worldIn.setBlockState(pos.add(xOffset + 3, 0, zOffset), blockUnder, 4);
             worldIn.setBlockState(pos.add(xOffset + 3, 0, zOffset + 1), blockUnder, 4);
             worldIn.setBlockState(pos.add(xOffset + 3, 0, zOffset + 2), blockUnder, 4);
             worldIn.setBlockState(pos.add(xOffset + 3, 0, zOffset + 3), blockUnder, 4);
            return false;
         }
      }
   }

   // Makes sure the correct block is underneath the sapling to allow for the generation
   public static boolean canGenerateLargeTree(BlockState blockUnder, BlockView worldIn, BlockPos pos, int xOffset, int zOffset) {
      Block block = blockUnder.getBlock();
      return isTypeAt(worldIn, pos.add(xOffset, 0, zOffset), block) && isTypeAt(worldIn, pos.add(xOffset + 1, 0, zOffset), block) && isTypeAt(worldIn, pos.add(xOffset + 2, 0, zOffset), block) && isTypeAt(worldIn, pos.add(xOffset + 3, 0, zOffset), block)
      		&& isTypeAt(worldIn, pos.add(xOffset, 0, zOffset + 1), block) && isTypeAt(worldIn, pos.add(xOffset + 1, 0, zOffset + 1), block) && isTypeAt(worldIn, pos.add(xOffset + 2, 0, zOffset + 1), block) && isTypeAt(worldIn, pos.add(xOffset + 3, 0, zOffset + 1), block)
      		&& isTypeAt(worldIn, pos.add(xOffset, 0, zOffset + 2), block) && isTypeAt(worldIn, pos.add(xOffset + 1, 0, zOffset + 2), block) && isTypeAt(worldIn, pos.add(xOffset + 2, 0, zOffset + 2), block) && isTypeAt(worldIn, pos.add(xOffset + 3, 0, zOffset + 2), block)
      		&& isTypeAt(worldIn, pos.add(xOffset, 0, zOffset + 3), block) && isTypeAt(worldIn, pos.add(xOffset + 1, 0, zOffset + 3), block) && isTypeAt(worldIn, pos.add(xOffset + 2, 0, zOffset + 3), block) && isTypeAt(worldIn, pos.add(xOffset + 3, 0, zOffset + 3), block);
      
      }
   
   public static boolean isTypeAt(BlockView worldIn, BlockPos pos, Block block)
   {
       return block == worldIn.getBlockState(pos).getBlock();
   }
   
   // Find the most North West sapling from the bone mealed sapling
   private BlockPos findEdge(IWorld worldIn, BlockPos position)
	{
		BlockPos pos = position;
		int X = 0;
		int Z = 0;
		
		// If there is a sapling to the north we move the position that way
		if (worldIn.getBlockState(pos.north()).matches(BlockTags.SAPLINGS))
		{
			// If there aren't any more saplings then we break the loop
			while (worldIn.getBlockState(pos.north()).matches(BlockTags.SAPLINGS))
			{
				// If there are more than 4 saplings in a row we also want to break from the loop and not go too far
				if(Z >= 3)
				{
					break;
				}
				
				pos = pos.north();
				Z++;
			} 
		}
		
		// If there is a sapling to the west we move the position that way
		if (worldIn.getBlockState(pos.west()).matches(BlockTags.SAPLINGS))
		{
			// Re-write of above code but for the west
			while (worldIn.getBlockState(pos.west()).matches(BlockTags.SAPLINGS)) 
			{
				if(X >= 3)
				{
					break;
				}
				
				pos = pos.west();
				X++;
			} 
		}
		
		// Return the most North West sapling's position for where to start the tree generation
		return pos;
	}
}