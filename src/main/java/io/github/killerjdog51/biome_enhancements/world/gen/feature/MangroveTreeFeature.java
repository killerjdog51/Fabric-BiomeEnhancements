package io.github.killerjdog51.biome_enhancements.world.gen.feature;

import com.mojang.datafixers.Dynamic;

import io.github.killerjdog51.biome_enhancements.registries.ModBlocks;

import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.VineBlock;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.Heightmap;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;

/**
*
* Description of tree generation mechanics and how I personally handle tree generation
*
* I have decided to implement comments within my tree generation code for my Minecraft mod in case I ever wanted to reference it for another project,
* required to return to it in the future due to an issue, or if other people wished to understand how tree generation works in the game of Minecraft.
* Because when you first open up a tree generation file it can look confusing and complicated.
* This is mostly because Mojang uses a waterfall technique for generating trees, which is generally frowned upon in the programming/computer science community.
* It is much more respected to utilize functions/methods to make your code more readable and understandable for other people.
* Another benefit to using functions is that they are re-callable.
* So when creating more complex trees like I have, it is usually a good idea to use functions and split complex code into more manageable pieces.
* 
* I will try my best to walk through my code and explain what is happening.
* But first I wanted to point out that Minecraft tree generation is split into two parts, checking/testing and then generating.
* Minecraft uses mutable blocks to pre-generate the tree to make sure it can fully be completed or will fit within a designated bounding box.
* Essentially, this is the code that is used to prevent a tree from being generated from too small of a space.
* This code is usually the same for all trees, so there isn’t too much that needs to be altered here or should be fretted about (I will explain it as best I can none the less though). 
* 
* The important part is the generation portion. Once the game confirms that the tree is capable of generating within a certain spot then it will create the tree within the game.
* You can tackle this however you wish, but I generally prefer to start big (or from a central point) and then go small (or move away from that central point.
* Another thing that I prefer to do is split my tree generation into different parts/features or more manageable layers.
* I will first build a mock-up of my tree in the game and separate the major differences/unique parts of the tree.
* I will then write down on paper the technicalities of how I want the tree to generate.
* This includes a min and max height for my tree, if I want any special properties in how the tree generates (does the tree generate branches or roots? And how do they generate?),
* and anything else I think is necessary to understand before writing the code for generating my tree. 
* 
* Now that I have briefly explained why I generate my trees using functions,
* how Minecraft tree generation generally works and how it can be split into two defining portions,
* and how I handle defining/generating my trees it is time to look at the actual code.
*/

/**
*
* Description of Mangrove tree generation/mechanics
*
* Mangroves are a new Swamp specific tree. Like most other Minecraft trees they generate with a single trunk and have a boxy leaf top.
* Unlike other trees, Mangroves generate with vines (similar to mega jungle trees).
* The most unique feature though is that when generated under water,
* mangroves will naturally sprawl their knarly roots out from the base of the tree to create an cool-looking root mound.
* This is a possible way to get more wood from the sapling.
* Another thing to note is that the Mangrove saplings are water-logable, but won’t generate under 3 blocks of water
*/

public class MangroveTreeFeature extends AbstractTreeFeature<DefaultFeatureConfig>
{
	
	// Some global variables
	//(generally expected to use the "final" to make the variable unchangable. But since I change the log block too wood I prefer to not finalize my log variable)
   private static BlockState LOG = ModBlocks.MANGROVE_LOG.getDefaultState();
   private static final BlockState LEAF = ModBlocks.MANGROVE_LEAVES.getDefaultState();
   private final int minHeight = 5;
   private boolean roots;
   private int water;

   public MangroveTreeFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> config) {
      super(config, false);
   }

   public boolean generate(Set<BlockPos> changedBlocks, ModifiableTestableWorld worldIn, Random rand, BlockPos position, BlockBox box)
   {
	
	   // Setting the height of the tree (we also set the position to be the first solid block above the ocean floor(Y = 64))
      int height = this.minHeight + rand.nextInt(3);
      position = worldIn.getTopPosition(Heightmap.Type.OCEAN_FLOOR, position);

      	// This tests/checks if the tree is able to grow, if not then we exit
   		if (!this.ensureGrowable(worldIn, position, height))
       {
           return false;
       }	
   		else
   		{
   			int x = position.getX();
   			int z = position.getZ();
   			int y = position.getY();
   			
            //Trunk generation
            for (int block = 0; block < height; ++block)
            {
            	LOG = ModBlocks.MANGROVE_LOG.getDefaultState();
            	
            	// If our tree generates in water, we want the trunk to start one block above the water
                BlockPos pos = position.up(block + water);
                placeLogAt(changedBlocks, worldIn, pos, box);
             }
            
            //root generation
            if(roots) {
            	
            	// We start growing the root mound from each cardinal direction
	            for (Direction direction : Direction.Type.HORIZONTAL)
	            {
	            	// We generate our tree's trunk down to where the generation started (ie: where the sapling is grown)
	            	for (int i = 0; i <= water; i++)
	        	   	{
	        	   		placeLogAt(changedBlocks, worldIn, position.down(i), box);
	        	   	}
	            	
	            	// We set the log block to be wood to make orientation simpler (I don't want to deal with knowing if the root is growing out north or east)
	            	LOG = ModBlocks.MANGROVE_WOOD.getDefaultState();
	            	
	            	// We set our position at the base of the trunk (where the top water level is
	            	BlockPos pos = position.offset(direction).up(water);
	            	
	            	// We begin generating our roots from the base of the trunk in the specified cardinal direction
	            	createRoots(changedBlocks, worldIn, pos, box, rand, water);
	            	
	            	// If the tree is extra tall, we want the chance to bring the root mound up by a block, similar to what was shown in the Biome vote video
	            	if (rand.nextInt(10) == 1 && height < 7)
	            	{
		            	pos = position.offset(direction).up(water);
		            	pos = position.offset(direction).up(water + 1);
		    	   		placeLogAt(changedBlocks, worldIn, pos, box);
	            	}
	            }
            }
            
            //Leaves generation 
            for (int yPos = y - 3 + (height + water); yPos <= y + (height + water); ++yPos)
            {
            	// We want our leaves to start at the top of the trunk while also accounting for the change due to water
               int leafHeight = yPos - (y + (height + water));
               int leafWidth = 2 - leafHeight / 2;

               // Stereotypical boxy Minecraft tree top
               for(int xPos = x - leafWidth; xPos <= x + leafWidth; ++xPos)
               {
                  int xWidth = xPos - x;

                  for(int zPos = z - leafWidth; zPos <= z + leafWidth; ++zPos)
                  {
                     int zWidth = zPos - z;
                     if (Math.abs(xWidth) != leafWidth || Math.abs(zWidth) != leafWidth || rand.nextInt(2) != 0 && leafHeight != 0)
                     {
                        BlockPos pos = new BlockPos(xPos, yPos, zPos);
                        placeLeafAt(changedBlocks, worldIn, pos, box);
                     }
                  }
               }
            }

            //Vine Generation
            for(int yPos = y - 3 + (height + water); yPos <= y + (height + water); ++yPos)
            {
            	// We want to account for the change in height due to water
               int leafHeight = yPos - (y + (height + water));
               int leafWidth = 2 - leafHeight / 2;
               
               // We still want to use mutable blocks since they haven't been placed in the world yet
               BlockPos.Mutable mutable = new BlockPos.Mutable();

               for(int xPos = x - leafWidth; xPos <= x + leafWidth; ++xPos)
               {
                  for(int zPos = z - leafWidth; zPos <= z + leafWidth; ++zPos)
                  {
                     mutable.set(xPos, yPos, zPos);
                     
                     // If it is a leaf block then we want to place vines on it
                     if (isLeaves(worldIn, mutable))
                     {
                        BlockPos westBlock = mutable.west();
                        BlockPos eastBlock = mutable.east();
                        BlockPos northBlock = mutable.north();
                        BlockPos southBlock = mutable.south();
                        
                        // We want to place the vines in the correct orientation 
                        if (rand.nextInt(4) == 0 && isAir(worldIn, westBlock))
                        {
                           this.addVine(worldIn, westBlock, VineBlock.EAST);
                        }

                        if (rand.nextInt(4) == 0 && isAir(worldIn, eastBlock)) {
                           this.addVine(worldIn, eastBlock, VineBlock.WEST);
                        }

                        if (rand.nextInt(4) == 0 && isAir(worldIn, northBlock)) {
                           this.addVine(worldIn, northBlock, VineBlock.SOUTH);
                        }

                        if (rand.nextInt(4) == 0 && isAir(worldIn, southBlock)) {
                           this.addVine(worldIn, southBlock, VineBlock.NORTH);
                        }
                     }
                  }
               }
            }

            return true;
   		}
   }

	// We want our swamp trees to generate vines
   private void addVine(ModifiableTestableWorld worldIn, BlockPos pos, BooleanProperty prop) {
      BlockState blockstate = Blocks.VINE.getDefaultState().with(prop, Boolean.valueOf(true));
      this.setBlockState(worldIn, pos, blockstate);
      int i = 4;

      for(BlockPos blockpos = pos.down(); isAir(worldIn, blockpos) && i > 0; --i) {
         this.setBlockState(worldIn, blockpos, blockstate);
         blockpos = blockpos.down();
      }

   }
   
   // This is how I handle root generation
   private void createRoots(Set<BlockPos> changedBlocks, ModifiableTestableWorld worldIn, BlockPos pos, BlockBox box, Random rand, int water)
   {
	   // Even though we start in each cardinal direction, I still want to randomly go in a direction to get a randomized root mound
	   // ( perhaps the roots spread out and become really long instead of compact)
	   Direction direction = Direction.Type.HORIZONTAL.random(rand);
	   
	   // Until we hit the dirt level, we want to generate roots
		for (int i = 0; i <= water; i++)
	   	{
			// This is what allows our roots to sprawl out from the base
			// I use recursion to recall the same function to make the roots travel down and out
	   		if (rand.nextInt(2) == 1) {createRoots(changedBlocks, worldIn, pos.offset(direction).down(water - i), box, rand, i);}
	   		
	   		// This will just randomly stop the recursion / prevent the root from generating prior to the for loop stopping
	   		if (rand.nextInt(10) == 1) {continue;}
	   		
	   		// This will place our top most root
	   		placeLogAt(changedBlocks, worldIn, pos.down(i), box);
	   		
	   		// If there is water, air, or a grass/seagrass block under our root then we replace all of it with roots until we reach a solid block (like dirt or stone)
	   		int waterBlock = 1;
	   		while (isWater(worldIn, pos.down(i + waterBlock)) || isAir(worldIn, pos.down(i + waterBlock)) || isGrass(worldIn, pos.down(i + waterBlock)))
	   		{
		   		placeLogAt(changedBlocks, worldIn, pos.down(i + waterBlock), box);
		   		waterBlock++;
	   		}
	   	}
   }
   
   // I want to replace short grass and short seagrass blocks
   protected static boolean isGrass(ModifiableTestableWorld worldIn, BlockPos pos) {
	      return worldIn.testBlockState(pos, (state) -> {
	         return state.getBlock() == Blocks.GRASS || state.getBlock() == Blocks.SEAGRASS;
	      });
	   }
   
	// Just as the title says, this sets a log block in the world
   private void placeLogAt(Set<BlockPos> changedBlocks, ModifiableTestableWorld worldIn, BlockPos pos, BlockBox box)
   {
	   if (isAirOrLeaves(worldIn, pos) || isWater(worldIn, pos))
       {
          this.setBlockState(changedBlocks, worldIn, pos, LOG, box);
       }
   }
   
	// Just as the title says, this sets a leaf block in the world
   private void placeLeafAt(Set<BlockPos> changedBlocks, ModifiableTestableWorld worldIn, BlockPos pos, BlockBox box)
   {
       if (isAirOrLeaves(worldIn, pos) || isReplaceablePlant(worldIn, pos))
       {
   		this.setBlockState(changedBlocks, worldIn, pos, LEAF, box);
       }
   }
   
   // To test if the tree can grow we first check if there is available space and then if the blocks underneath can sustain our tree
   protected boolean ensureGrowable(ModifiableTestableWorld worldIn, BlockPos treePos, int height)
   {
       return this.isSpaceAt(worldIn, treePos, height) && this.ensureViableBlockUnderneath(treePos, worldIn);
   }
   
   // Check if there is space for the tree to grow
 	private boolean isSpaceAt(ModifiableTestableWorld worldIn, BlockPos position, int height)
    {
      boolean flag = true;
      roots = false;
  	  water = 0;
      int x = position.getX();
      int y = position.getY();
      int z = position.getZ();
      
	  // Obviously we don't want the tree to grow in the void or above the build limit
      if (y >= 1 && y + height + 1 <= 256)
      {
    	  
    	 // Extra math in case our tree is taller than expected
         for(int yPos = y; yPos <= y + 1 + height; ++yPos)
         {
            int b0 = 1;
            if (yPos == y) {
               b0 = 0;
            }

            if (yPos >= y + 1 + height - 2) {
               b0 = 3;
            }

	        // Use mutable blocks to test if the location is available
            BlockPos.Mutable mutable = new BlockPos.Mutable();

            
            // We check each position for future blocks
            for(int xPos = x - b0; xPos <= x + b0 && flag; ++xPos)
            {
               for(int zPos = z - b0; zPos <= z + b0 && flag; ++zPos)
               {
            	   
            	  // Double check to make sure our tree doesn't generate in the void or above the build limit
                  if (yPos >= 0 && yPos < 256)
                  {
                     mutable.set(xPos, yPos, zPos);
                     
                     // We check if our future blocks can be placed in their respective location
                     if (!isAirOrLeaves(worldIn, mutable))
                     {
                    	
                    	// We check if our future blocks are being placed within water
                        if (isWater(worldIn, mutable))
                        {
                        	BlockPos pos = mutable;
                        	
                        	// If our tree generates in water we want it to generate a root mound
                        	roots = true;
                        	int i;
                        	
                        	// We check how many blocks under water our tree starts generating from
                        	 for (i = 1; isWater(worldIn, pos); pos = pos.up())
                             {
                                 i++;
                             }
                        	 
                        	 // We want the total amount of water above our start position
                        	 // If we don't do this then the mutable block will move up and the total amount of water above will decrease
                        	 if (water < i)
                        	 {
                             	water = i;
                        	 }
                        	
                        	 // We don't want our tree to generate too deep from under water
                        	 // (It'd be weird to have a mangrove grow from the ocean floor and grow a giant root mound)
                           if (water > 3) {
                              flag = false;
                           }
                           
                        // The block in the way was not water, leaves, or air
                        } else {
                           flag = false;
                        }
                     }
                  
                  // Guess the tree was somehow either in the void or above the build limit
                  } else {
                     flag = false;
                  }
               }
            }
         }
         return flag;
         
 	  //Tree was either in void or above build limit
      } else {
    	  return false;
      }

    }
 	
 	// Check if the tree can generate on the block underneath
 	private boolean ensureViableBlockUnderneath(BlockPos pos, ModifiableTestableWorld worldIn)
    {
 		 // If the block underneath is considered to be a dirt or sand variant then we allow the tree to generate
 		if ((isNaturalDirtOrGrass(worldIn, pos.down())))
		{
 			this.setToDirt(worldIn, pos.down());
            return true;
		}
 		else
 		{
 			return false;
 		}
    }
}