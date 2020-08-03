package io.github.killerjdog51.biome_enhancements.world.gen.feature;

import java.util.Random;
import java.util.Set;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import io.github.killerjdog51.biome_enhancements.registries.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LogBlock;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
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
* Description of Baobab tree generation/mechanics
*
* Baobabs are giant trees, while shorter than the other mega trees, they are much wider.
* Baobabs generate in a 4x4 (16 blocks per layer) and are capable of generating anywhere between 8 to 18 blocks in height.
* Their primary feature is their top branches. Baobabs will always generate one set (of four branches in each cardinal direction),and are capable of generating a second set.
* It is also possible that a branch may be omitted from generating or that a branch will generate in the same location as another
* (mutable blocks only check if a block currently exists in a location, it does not account for future blocks).
* Baobab’s secondary feature is their side branches. These side branches will generate anywhere from zero to two sets and may also be omitted or generated over.
* The last two features of the Baobab worth mentioning are that it does generate leaves as well as roots.
*/

public class BaobabTreeFeature extends AbstractTreeFeature<DefaultFeatureConfig>
{
	// Some global variables
	//(generally expected to use the "final" to make the variable unchangable. But since I often change the orientation of the Logs or use wood blocks instead I prefer to not finalize my log variable)
   private static BlockState LOG = ModBlocks.BAOBAB_LOG.getDefaultState();
   private static final BlockState LEAVES = ModBlocks.BAOBAB_LEAVES.getDefaultState();
   private final int minHeight = 8;

   public BaobabTreeFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> config)
   {
      super(config, false);
   }

   public boolean generate(Set<BlockPos> changedBlocks, ModifiableTestableWorld worldIn, Random rand, BlockPos pos, BlockBox box)
   {
	   // Creating a brand new random, this is unnecessary but I really wanted my baobabs to always be unique and random
	   Random newRand = new Random();
		
	   // Setting the height of the tree
		int height = this.minHeight + newRand.nextInt(4);
		 if(newRand.nextBoolean()) {height += newRand.nextInt(6);}
		
		// This tests/checks if the tree is able to grow, if not then we exit
		if (!this.ensureGrowable(worldIn, pos, height))
       {
           return false;
       }
		else
		{			
			// Generate the 4x4 trunk (I do this in the main function because there is only one trunk and I think it's easier to read)
			for (int currentX = 0; currentX < 4; ++currentX)
			{
				for (int currentZ = 0; currentZ < 4; ++currentZ)
				{
					for (int currentY = 0; currentY < height; currentY++)
		            {
		                if (isAirOrLeaves(worldIn, pos.add(currentX, currentY, currentZ)))
		                {
		                	// Set the top of the tree to wood for that smooth finish
		                	if (currentY == (height - 1))
							{
								LOG = ModBlocks.BAOBAB_WOOD.getDefaultState();
							}
		                	else
		                	{
								LOG = ModBlocks.BAOBAB_LOG.getDefaultState();	
		                	}
		                	
							 BlockPos blockpos = pos.add(currentX, currentY, currentZ);
							 this.placeLogAt(changedBlocks, worldIn, blockpos, box);
		                }
		            }
				}
			}
			
			// Generate branches/leaves
           this.createCrown(changedBlocks, worldIn, box, pos.getX(), pos.getZ(), pos.getY() + (height - 1), newRand);
           this.createSideBranch(changedBlocks, worldIn, box, pos.getX(), pos.getZ(), pos.getY() + (height - 5), newRand);
           
           // Do we need additional side branches?
           if (height >= 15)
           {
               this.createSideBranch(changedBlocks, worldIn, box, pos.getX(), pos.getZ(), pos.getY() + (height - 9), newRand);
           }
           
           // Generate roots
           if (newRand.nextBoolean())
           {
           	for (Direction direction : Direction.Type.HORIZONTAL)
           	{
           		// Omit and continue onto the next side
           		if (newRand.nextInt(5) == 1) { continue; }
           		
   	    		int block = rand.nextInt(3);
   	    		int xPos = pos.getX();
   	    		int zPos = pos.getZ();
   	    		
   	    		// We want our roots to be wood for simplicity
				LOG = ModBlocks.BAOBAB_WOOD.getDefaultState();

				// Set where the block will generate
   	    		if (direction == Direction.NORTH || direction == Direction.SOUTH)
   				{
   					xPos += block;
   					if (direction == Direction.SOUTH) { zPos += 3;}
   				}
   				else if (direction == Direction.EAST || direction == Direction.WEST)
   				{
   					zPos += block;
   					if (direction == Direction.EAST) { xPos += 3;} 
   				}
   	    		
   	    		xPos += direction.getOffsetX();
   	    		zPos += direction.getOffsetZ();
   	    		BlockPos blockpos = new BlockPos(xPos, pos.getY(), zPos);

   		    	this.placeLogAt(changedBlocks, worldIn, blockpos, box);
   		    	
   		    	// Randomly decide to make a root two blocks tall instead of one
   		    	if (newRand.nextInt(5) == 1)
   		    	{
       		    	this.placeLogAt(changedBlocks, worldIn, new BlockPos(xPos, pos.getY()+1, zPos), box);

   		    	}

           	}
           }

			return true;
		}
      } 
   
   /**
   *
   * Lots of weird Branch functions
   *
   * I needed to create each set of branches the same way (using the four cardinal directions).
   * But I have two different kind of branches that generate in their own unique way (top and side branches)
   * and also using a different amount of branches (top layer will always have one set while side layer will have zero to two sets)
   * 
   * So I set the amount of branches in the first function and the type of branches being generated (the true/false since there are only two different kinds of branches).
   * 
   * I then go into the same "createBranches" function which will generate the sets of branches. 
   * This function also randomly chooses where the branches protrude from and the respected log orientation. 
   * Basically, I go to each cardinal direction and set the log direction to match with it.
   * Then I choose one of the four blocks on that side of the tree to generate the branch from.
   * Finally, depending on the type of branch that we set in the first function (true/false), we go into the function for that specific branch generation.
   * 
   * The final (specified) branch function takes care of generating the branch in a random way.
   * This ensures that each individual branch is unique. 
   */

   
   // Initiate the generation for the top branches
   private void createCrown(Set<BlockPos> changedBlocks, ModifiableTestableWorld worldIn, BlockBox box, int x, int z, int y, Random rand)
	{
	   // Set the maximum amount of branches to generate on each side of the tree (one or two sets)
   		int maxBranches = 1 + rand.nextInt(2);
   		this.createBranches(changedBlocks, worldIn, box, x, z, y, rand, maxBranches, true);

	}
	
   // Initiate the generation for the side branches
	private void createSideBranch(Set<BlockPos> changedBlocks, ModifiableTestableWorld worldIn, BlockBox box, int x, int z, int y, Random rand)
   {
		// Set the maximum amount of branches to generate on each side of the tree (zero to two sets)
		int maxBranches = rand.nextInt(3);
		this.createBranches(changedBlocks, worldIn, box, x, z, y, rand, maxBranches, false);
   }

	// Decides the starting point for each branch
   private void createBranches(Set<BlockPos> changedBlocks, ModifiableTestableWorld worldIn, BlockBox box, int x, int z, int y, Random rand, int maxBranches, boolean top)
   {
   	int branches = 0;
   	
   	// This is for the amount of sets, or for the amount of branches on each side
   	while (branches < maxBranches)
   	{    		
   			// This is for which side the branch is generated from (each of the cardinal directions)
	    	for (Direction direction : Direction.Type.HORIZONTAL)
	    	{
	    		// Breaks out of the for loop and continues to the next side if we don't want to generate a branch (1/5 chance)
	    		if(rand.nextInt(5) == 1) { continue;}

	    		// Randomly decides which block to protrude the branch from
	    		int block = rand.nextInt(3);
	    		int xPos = x;
	    		int yPos = y;
	    		int zPos = z;
	    		
	    		// Sets our log block to the right orientation (as well as side of the trunk)
	    		if (direction == Direction.NORTH || direction == Direction.SOUTH)
				{
					LOG = ModBlocks.BAOBAB_LOG.getDefaultState().with(LogBlock.AXIS, Direction.Axis.Z);
					xPos += block;
					if (direction == Direction.SOUTH) { zPos += 3;}
				}
				else if (direction == Direction.EAST || direction == Direction.WEST)
				{
					LOG = ModBlocks.BAOBAB_LOG.getDefaultState().with(LogBlock.AXIS, Direction.Axis.X);
					zPos += block;
					if (direction == Direction.EAST) { xPos += 3;}
				}
				else 
				{
					// Not necessary but figured I'd include it just in case
					LOG = ModBlocks.BAOBAB_WOOD.getDefaultState();
				}
	    		
	    		// Decides whether to generate the top type or side type of branch
	    		BlockPos blockpos = new BlockPos(xPos, yPos, zPos);
				if(top)
				{
					this.generateTopBranch(changedBlocks, worldIn, blockpos, box, direction, rand);
				}
				else
				{
					this.generateSideBranch(changedBlocks, worldIn, blockpos, box, direction, rand);
				}
	    	}
	    	// Increase the set of branches once finished with all sides
	    	branches++;
   		}
   }
   
   // This is how each top branch is generated in the same general yet unique fashion
   private void generateTopBranch(Set<BlockPos> changedBlocks, ModifiableTestableWorld worldIn, BlockPos pos, BlockBox box, Direction direction, Random rand)
   {
	   	int xPos = direction.getOffsetX();
		int zPos = direction.getOffsetZ();
		int yPos = direction.getOffsetY();
	
		// We want our top branches to always be the same length from the tree
	   	for(int block = 0; block < 4; block++)
	   	{
	   		// If we reach the end of the branch we want a wood block
	   		if(block == 3)
	   		{
					LOG = ModBlocks.BAOBAB_WOOD.getDefaultState();
	   		}
	   		
	   		this.placeLogAt(changedBlocks, worldIn, pos.add(xPos, yPos, zPos), box);
	   		
	   		// If we reached the end of the branch we don't want to progress any further
	   		if(block == 3) { break;}
	   		
	   		xPos += direction.getOffsetX();
			zPos += direction.getOffsetZ();
			yPos += direction.getOffsetY();
	   	}
	   	
	   	// Randomly decides the amount of vertical logs for the branch (and sets our log variable to the right orientation)
		LOG = ModBlocks.BAOBAB_LOG.getDefaultState().with(LogBlock.AXIS, Direction.Axis.Y);
		int top = 1 + rand.nextInt(3);
			
		for(int i = 0; i < top; i++)
		{
	    	this.placeLogAt(changedBlocks, worldIn, pos.add(xPos, yPos + (i + 1), zPos), box);
		}
   	
   	
		// Generates a leaf layer at our top block and another (smaller) layer above that
		xPos += direction.getOffsetX();
		zPos += direction.getOffsetZ();
		BlockPos blockpos = pos.add(xPos, yPos + top, zPos);
		this.growLeavesLayer(changedBlocks, worldIn, blockpos, box, 3);
		this.growLeavesLayer(changedBlocks, worldIn, blockpos.add(0, 1, 0), box, 2);

   }
   

   // this is how each side branch is generated 
   private void generateSideBranch(Set<BlockPos> changedBlocks, ModifiableTestableWorld worldIn, BlockPos pos, BlockBox box, Direction direction, Random rand)
   {
   		int xPos = direction.getOffsetX();
		int zPos = direction.getOffsetZ();
		
		// We want our side branches to randomly be at different heights
		int yPos = direction.getOffsetY() + rand.nextInt(3);
		
		// We also want our side branches to randomly be at different lengths
		int length = 3 + rand.nextInt(4);

   	for(int block = 0; block < length; block++)
   	{
   		if(block == (length - 1))
   		{
				LOG = ModBlocks.BAOBAB_WOOD.getDefaultState();
   		}
   		
   		this.placeLogAt(changedBlocks, worldIn, pos.add(xPos, yPos, zPos), box);
   		if(block == (length - 1)) { break;}
   		
   			xPos += direction.getOffsetX();
			zPos += direction.getOffsetZ();
			yPos += direction.getOffsetY();
   	}
   	
   		// Generate a single layer of leaves on top of the side branch
		BlockPos blockpos = pos.add(xPos, yPos + 1, zPos);
		this.growLeavesLayer(changedBlocks, worldIn, blockpos, box, 2);


   }
   
   
   // This creates each layer of leaves in the acacia pattern of fanning out from the center rather than being a box (like the other trees)
   private void growLeavesLayer(Set<BlockPos> changedBlocks, ModifiableTestableWorld worldIn, BlockPos layerCenter, BlockBox box, int width)
   {
	   
	   // We set a max width from our center (the diameter?) so that if a block falls outside of that area it will not be generated
       int max = width * width;

       for (int x = -width; x <= width; ++x)
       {
           for (int z = -width; z <= width; ++z)
           {
        	   // Mathy stuff, this basically makes sure that the blocks are within the set radius. If you wanted a square I suppose you'd omit this and the max variable.
               if (x * x + z * z <= max)
               {
                   BlockPos blockpos = layerCenter.add(x, 0, z);
                   this.placeLeafAt(changedBlocks, worldIn, blockpos, box);
               }
           }
       }
   }
   
   // Just as the title says, this sets a log (variable) block in the world
   private void placeLogAt(Set<BlockPos> changedBlocks, ModifiableTestableWorld worldIn, BlockPos pos, BlockBox box) {
      if (canTreeReplace(worldIn, pos)) {
         this.setBlockState(changedBlocks, worldIn, pos, LOG, box);
      }

   }

   // Just as the title says, this sets a leaf block in the world
   private void placeLeafAt(Set<BlockPos> changedBlocks, ModifiableTestableWorld worldIn, BlockPos pos, BlockBox box) {
      if (isAir(worldIn, pos)) {
         this.setBlockState(changedBlocks, worldIn, pos, LEAVES, box);
      }

   }
   
   // To test if the tree can grow we first check if there is available space and then if the blocks underneath can sustain our tree
   protected boolean ensureGrowable(ModifiableTestableWorld worldIn, BlockPos treePos, int height)
   {
       return this.isSpaceAt(worldIn, treePos, height) && this.ensureDirtsUnderneath(treePos, worldIn);
   }
   
   // Check if there is space for the tree to grow
	private boolean isSpaceAt(ModifiableTestableWorld worldIn, BlockPos leavesPos, int height)
   {
       boolean flag = true;
       int y = leavesPos.getY();
       int x = leavesPos.getX();
       int z = leavesPos.getZ();
       
       // Obviously we don't want the tree to grow in the void or above the build limit
       if (y >= 1 && y + height + 1 <= 256)
       {
    	   // Extra math in case our tree is taller than expected
           for (int yPos = y; yPos <= y + 1 + height; ++yPos)
           {
               int b0 = 1;

               if (yPos == y)
               {
                   b0 = 0;
               }
               else if (yPos >= y + 1 + height - 2)
               {
                   b0 = 2;
               }

               // Use mutable blocks to test if the location is available
               BlockPos.Mutable mutable = new BlockPos.Mutable();

               // We check each position for future blocks
               for (int xPos = x - b0; xPos <= x + b0 && flag; ++xPos)
               {
                   for (int zPos = z - b0; zPos <= z + b0 && flag; ++zPos)
                   {
                       mutable.set(xPos, yPos, zPos);

                       // We check if our future blocks can be placed in their respective location
                       if (y + yPos < 0 || y + yPos >= 256 || !canTreeReplace(worldIn, mutable))
                       {
                    	   // If a block is in the way we immediately exit the function and return false (ie: tree will not grow) 
                           flag = false;
                       }
                   }
               }
           }

           return flag;
       }
       //Tree was either in void or above build limit
       else
       {
           return false;
       }
   }
	
	// Check if the tree can generate on the block underneath
	private boolean ensureDirtsUnderneath(BlockPos pos, ModifiableTestableWorld worldIn)
   {
       BlockPos down = pos.down();
       BlockState blockstate = Blocks.AIR.getDefaultState();

       // If the block underneath is considered to be a dirt variant then we set all the blocks below our saplings to dirt
       if (isNaturalDirtOrGrass(worldIn, down))
       {
           this.setToDirt(worldIn, down);
           this.setToDirt(worldIn, down.east(1));
           this.setToDirt(worldIn, down.east(2));
           this.setToDirt(worldIn, down.east(3));
           this.setToDirt(worldIn, down.south(1));
           this.setToDirt(worldIn, down.south(1).east(1));
           this.setToDirt(worldIn, down.south(1).east(2));
           this.setToDirt(worldIn, down.south(1).east(3));
           this.setToDirt(worldIn, down.south(2));
           this.setToDirt(worldIn, down.south(2).east(1));
           this.setToDirt(worldIn, down.south(2).east(2));
           this.setToDirt(worldIn, down.south(2).east(3));
           this.setToDirt(worldIn, down.south(3));
           this.setToDirt(worldIn, down.south(3).east(1));
           this.setToDirt(worldIn, down.south(3).east(2));
           this.setToDirt(worldIn, down.south(3).east(3));

           // We also set our saplings to air so that the logs can generate
           this.setBlockState(worldIn, pos, blockstate);
           this.setBlockState(worldIn, pos.east(1), blockstate);
           this.setBlockState(worldIn, pos.east(2), blockstate);
           this.setBlockState(worldIn, pos.east(3), blockstate);
           this.setBlockState(worldIn, pos.south(1), blockstate);
           this.setBlockState(worldIn, pos.south(1).east(1), blockstate);
           this.setBlockState(worldIn, pos.south(1).east(2), blockstate);
           this.setBlockState(worldIn, pos.south(1).east(3), blockstate);
           this.setBlockState(worldIn, pos.south(2), blockstate);
           this.setBlockState(worldIn, pos.south(2).east(1), blockstate);
           this.setBlockState(worldIn, pos.south(2).east(2), blockstate);
           this.setBlockState(worldIn, pos.south(2).east(3), blockstate);
           this.setBlockState(worldIn, pos.south(3), blockstate);
           this.setBlockState(worldIn, pos.south(3).east(1), blockstate);
           this.setBlockState(worldIn, pos.south(3).east(2), blockstate);
           this.setBlockState(worldIn, pos.south(3).east(3), blockstate);

           return true;
       }
       else
       {
           return false;
       }
   }
}