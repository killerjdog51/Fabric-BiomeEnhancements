package io.github.killerjdog51.biome_enhancements.world.gen.feature;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import io.github.killerjdog51.biome_enhancements.blocks.PalmSaplingBlock;
import io.github.killerjdog51.biome_enhancements.registries.ModBlocks;
import io.github.killerjdog51.biome_enhancements.registries.ModWorldGeneration;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.IWorld;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class OasisFeature extends Feature<DefaultFeatureConfig> {

	public OasisFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> configFactoryIn)
	{
		super(configFactoryIn);
	}

	@Override
	public boolean generate(IWorld worldIn, ChunkGenerator<? extends ChunkGeneratorConfig> chunkGenerator, Random rand, BlockPos pos, DefaultFeatureConfig config) {
		
		// We set the size of our oasis
		int size = 1 + rand.nextInt(2);
		
		// Make sure the oasis spawns above sea level but not in the air
		while(pos.getY() > 65 && worldIn.isAir(pos))
		{
	         pos = pos.down();
	      }
		
		// If the block is (for some odd reason) under ground we want to be on the surface
		while(!(worldIn.getLightLevel(LightType.SKY, pos) > 0))
		{
			pos = pos.up();
		}
		
		// The oasis was below sea level
	      if (pos.getY() <= 65)
	      {
	         return false;
	      } else {
	    	  
	    	  // The Y position is fine, now we check to make sure there aren't any structures in the way
	    	  ChunkPos chunkpos = new ChunkPos(pos);
	          if (!worldIn.getChunk(chunkpos.x, chunkpos.z, ChunkStatus.STRUCTURE_REFERENCES).getStructureReferences(Feature.VILLAGE.getName()).isEmpty()) {
	             return false;
	          }
	          
	          if (!worldIn.getChunk(chunkpos.x, chunkpos.z, ChunkStatus.STRUCTURE_REFERENCES).getStructureReferences(Feature.DESERT_PYRAMID.getName()).isEmpty()) {
		             return false;
		          }
	          if (!worldIn.getChunk(chunkpos.x, chunkpos.z, ChunkStatus.STRUCTURE_REFERENCES).getStructureReferences(Feature.PILLAGER_OUTPOST.getName()).isEmpty()) {
		             return false;
		          }
	          
	          // There weren't any structures obstructing the oasis so now we check if there's enough space
	    	  for (int xPos = -(11*size); xPos <= (12*size); ++xPos)
		       {
		           for (int zPos = -(11*size); zPos <= (12*size); ++zPos)
		           {
		        	   for (int yPos = -(8*size); yPos <0; ++yPos)
		        	   {
		        		   // If the area isn't solid then we exit
			               if (xPos * xPos + zPos * zPos >= (11*(size*size)) && !worldIn.getBlockState(pos.add(xPos, yPos, zPos)).getMaterial().isSolid())
			               {
			                   return false;
			               }
			               
			               // assuming there is enough space for the oasis and that all the blocks in the area were solid we check all nearby biomes
			               else if (xPos * xPos + zPos * zPos >= (11*(size*size)))
			               {
			            	   for (int x = 0; x < 50; x++)
			            	   {
			            		   for (int z = 0; z < 50; z++)
			            		   {
			            			   try
	            		        	   {
			            				   // We don't want our oasis to spawn too close to non-desert biomes like oceans, rivers, plains, etc.
			            				   // This includes other oasis, we don't want them to spawn too closely. (was originally 100 blocks, but that caused too much lag)
			            				   BlockPos blockpos = pos.add((xPos + x), 0, (zPos + z));
	            			        	   if ( !(worldIn.getBiome(blockpos).equals(Biomes.DESERT) || worldIn.getBiome(blockpos).equals(Biomes.DESERT_HILLS) || worldIn.getBiome(blockpos).equals(Biomes.DESERT_LAKES) || worldIn.getBiome(blockpos).equals(Biomes.BEACH)) )
	            			        	   {
	            			        		   return false;
	            			        	   }
	            			        	   
	            			        	   // We want to check both positive and negative x/z coordinates 
	            			        	   blockpos = pos.add((xPos - x), 0, (zPos - z));
	            			        	   if ( !(worldIn.getBiome(blockpos).equals(Biomes.DESERT) || worldIn.getBiome(blockpos).equals(Biomes.DESERT_HILLS) || worldIn.getBiome(blockpos).equals(Biomes.DESERT_LAKES) || worldIn.getBiome(blockpos).equals(Biomes.BEACH)) )
	            			        	   {
	            			        		   return false;
	            			        	   }
	            		        	   }
	            		        	   catch (Exception e) 
	            		        	   {
	            		        	   }
			            		   }
			            	   }
			               }
		        	   }
		           }
		       }
	          
	    	  // Assuming everything is good, we spawn the oasis as a recursive feature (this allows for interesting shapes sometimes)
	        	 this.GenerateOasis(worldIn, chunkGenerator, pos, size, rand);
	         	            
	            return true;
	         }
	      
	}
	
	// Start generating the oasis
	private void GenerateOasis (IWorld worldIn, ChunkGenerator<? extends ChunkGeneratorConfig> chunkGenerator, BlockPos pos, int size, Random rand)
	{
		// First set the entire area to sand
		for (int depth = 0; depth <= (5*size); depth++)
		{
			Layer(worldIn, pos.down(depth), (11*size), Blocks.SAND.getDefaultState());
		}
		
		// Then set the top layer to grass
         Layer(worldIn, pos, (10*size), Blocks.GRASS_BLOCK.getDefaultState());
         
         // Set each layer underneath to dirt and curve inwards (to make a lake/pond
         for (int depth = 1; depth <= (4*size); depth++)
         {
        	 Layer(worldIn, pos.down(depth), ((10-depth)*size), Blocks.DIRT.getDefaultState());
         }
         
         // Set the water for the lake/pond
         for (int depth = 0; depth < (3*size); depth++)
         {
        	 Layer(worldIn, pos.down(depth), ((8-depth)*size), Blocks.WATER.getDefaultState());
         }
         
         // Set all blocks above the oasis to air to remove any obstructions
         for (int air = 1; air <= 12; air++)
         {
        	 Layer(worldIn, pos.up(air), (11*size), Blocks.AIR.getDefaultState());
         }
         
         // Generate oasis plants
         GeneratePlant (worldIn, pos.up(), (10*size), ((3*size) + rand.nextInt(3)), ModBlocks.PALM_SAPLING.getDefaultState(), rand);
         GeneratePlant (worldIn, pos.up(), (8*size), ((3*size) + rand.nextInt(3)), Blocks.LILY_PAD.getDefaultState(), rand);
         GeneratePlant (worldIn, pos.up(), (10*size), ((20*size) + rand.nextInt(5*size)), Blocks.GRASS.getDefaultState(), rand);
         GeneratePlant (worldIn, pos.up(), (10*size), ((3*size) + rand.nextInt(3)), ModBlocks.DESERT_CANDLE.getDefaultState(), rand);
         GeneratePlant (worldIn, pos.up(), (10*size), ((4*size) + rand.nextInt(3)), Blocks.SUGAR_CANE.getDefaultState(), rand);

         // randomly decide to enlargen the oasis by placing another one nearby
         if (rand.nextBoolean())
         {
        	 int x = randomInt((5*size), rand);
        	 int z = randomInt((5*size), rand);
        	 BlockPos blockpos = pos.add(x, 0, z);
        	 this.GenerateOasis(worldIn, chunkGenerator, blockpos, (1 + rand.nextInt(2)), rand);
         }
	}
	
	// Place down the blocks that form the oasis
	private void Layer(IWorld worldIn, BlockPos layerCenter, int width, BlockState state)
   {
       int max = width * width;

       for (int x = -width; x <= width; ++x)
       {
           for (int z = -width; z <= width; ++z)
           {
               if (x * x + z * z <= max)
               {
                   BlockPos blockpos = layerCenter.add(x, 0, z);
                   
                   // We don't want to replace the water
                   if (worldIn.getBlockState(blockpos).getBlock() == Blocks.WATER)
                   {
                	   continue;
                   }
                   
                   // We also don't want to replace grass with sand
                   if (worldIn.getBlockState(blockpos).getBlock() == Blocks.GRASS_BLOCK && state.matches(BlockTags.SAND))
                   {
                	   continue;
                   }
                   
					worldIn.setBlockState(blockpos, state, 2);
					
					// We do want to change the chunk's biome to our custom oasis biome for that nice water/foliage color
					try {
						worldIn.getChunk(blockpos).getBiomeArray()[(blockpos.getX() & 15) << 4 | (blockpos.getZ() & 15)] = ModWorldGeneration.OASIS;
					} catch (Exception e) {
						continue;
					}
               }
           }
       }
   }

	// Generate the plants in the oasis area
	private void GeneratePlant (IWorld worldIn, BlockPos layerCenter, int width, int amount, BlockState state, Random rand)
	{
		int max = width * width;
		int plant = 0;

		// Make sure there's a set amount of plants (not too many or too few)
		while (plant <= amount)
		{
			// Get a random position within the area (we don't want all the plants lined up)
			int x = randomInt(width, rand);
			int z = randomInt(width, rand);
			
	        if (x * x + z * z <= max)
	        {
				BlockPos blockpos = layerCenter.add(x, 0, z);
				
				// If the block is water we place a liy pad
				if (worldIn.getBlockState(blockpos.down()).getBlock() == Blocks.WATER)
				{
					if (state.getBlock() == Blocks.LILY_PAD)
					{
						worldIn.setBlockState(blockpos, state, 2);
						plant++;
						continue;
					}
	
				}
				
				// Otherwise we place one of the other plants
				if (worldIn.getBlockState(blockpos.down()).getBlock() == Blocks.GRASS_BLOCK)
				{
	            	   
					// If our plant is a palm sapling we make sure there isn't another tree directly besides it
					if (state.getBlock() == ModBlocks.PALM_SAPLING)
					{
						 boolean tree = false;

						   for (Direction direction : Direction.Type.HORIZONTAL)
						   {
							   if (worldIn.getBlockState(blockpos.offset(direction)).getBlock() == ModBlocks.PALM_SAPLING || worldIn.getBlockState(blockpos.offset(direction)).getBlock() == ModBlocks.PALM_LOG)
							   {
								   tree = true;
							   }
						   }
						   
						   // If there was a tree then we find a new position
						   if (tree)
						   {
							   continue;
						   }
						   
						// Otherwise we plant/grow the tree and raise the amount
						GenerateTree(worldIn, blockpos, state, rand);
						plant++;
						continue;
					}
	           	   
					// If our plant is sugarcane then we make sure it is next to water
					if (state.getBlock() == Blocks.SUGAR_CANE)
					{
						GenerateReed(worldIn, blockpos, state, rand);
						plant++;
						continue;
					}
					
					// Otherwise if its a normal plant like grass we just place it down
					worldIn.setBlockState(blockpos, state, 2);
					plant++;
				}
        	}
        }
	}
	
	// To plant sugarcane it needs to be next to water
	private void GenerateReed (IWorld worldIn, BlockPos pos, BlockState state, Random rand)
	{
		 boolean water = false;

		   for (Direction direction : Direction.Type.HORIZONTAL)
		   {
			   if (worldIn.getBlockState(pos.down().offset(direction)).getBlock() == Blocks.WATER)
			   {
				   water = true;
			   }
		   }
		   
		   if (!water)
		   {
			   return;
		   }
		   
		   worldIn.setBlockState(pos, state, 2);
		   
		   // This is to vary the height
		   if (rand.nextInt(10) == 1)
		   {
			   worldIn.setBlockState(pos.up(), state, 2);
		   }
	   }
	
	// If the plant is a palm sapling we want to place it in the world at the maximum growth stage and then try to grow it into a tree
	private void GenerateTree (IWorld worldIn, BlockPos pos, BlockState state, Random rand)
	{
		state = state.with(PalmSaplingBlock.STAGE, Integer.valueOf(1));
		worldIn.setBlockState(pos, state, 2);
		PalmSaplingBlock block = (PalmSaplingBlock) state.getBlock();
		
		// two different growth functions depending on the worldIn
		try {
			
			if (worldIn instanceof ChunkRegion)
			{
				block.generate(worldIn, pos, state, rand);
			}
			else if (worldIn instanceof World)
			{
				World world = (World) worldIn;
				block.grow(world, rand, pos, state);
			}
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		
	}
	   
	// randomly chooses an integer depending on the number in and then if it is negative or positive
	private static int randomInt (int num, Random rand)
	{
		int pos = rand.nextInt(num);
		
		if(rand.nextBoolean())
		{
			pos = -pos;
		}
		
		return pos;
	}
}
