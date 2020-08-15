package io.github.killerjdog51.biome_enhancements.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import io.github.killerjdog51.biome_enhancements.registries.ModBlocks;

public class PalmLeavesBlock extends LeavesBlock {
	   public static final IntProperty NEWDISTANCE;
	   public static final EnumProperty<Type> TYPE;

	   public PalmLeavesBlock(Settings settings) {
	      super(settings);
	      this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(NEWDISTANCE, 11)).with(PERSISTENT, false)).with(TYPE, PalmLeavesBlock.Type.NORMAL));;
	   }

	   // Allows the block to utilize ticks and update regularly
	   @Override
	   public boolean hasRandomTicks(BlockState state) {
	      return state.get(NEWDISTANCE) == 11 && !state.get(PERSISTENT);
	   }

	   // Also updates the block regularly
	   @Override
	   public void onRandomTick(BlockState state, World worldIn, BlockPos pos, Random random) {
	      if (!state.get(PERSISTENT) && state.get(NEWDISTANCE) == 11) {
	         dropStacks(state, worldIn, pos);
	         worldIn.removeBlock(pos, false);
	      }

	   }

	   // Updates the block regularly
	   @Override
	   public void onScheduledTick(BlockState state, World worldIn, BlockPos pos, Random random) {
	      worldIn.setBlockState(pos, updateDistance(state, worldIn, pos), 3);
	   }

	   // This initially sets the value for how far away the leaf block is from the log block
	   @Override
	   public BlockState getStateForNeighborUpdate(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
	      int i = getDistanceFromLog(facingState) + 1;
	      
	      if (i != 1 || stateIn.get(NEWDISTANCE) != i) {
	         worldIn.getBlockTickScheduler().schedule(currentPos, this, 1);
	      }

	      return stateIn;
	   }

	   // This updates how far away the leaf block is from the log block
	   private static BlockState updateDistance(BlockState state, IWorld world, BlockPos pos) {
	      int i = 11;
	      BlockPos.PooledMutable pooledMutable = BlockPos.PooledMutable.get();
	      Throwable var5 = null;

	      try {
	         Direction[] var6 = Direction.values();
	         int var7 = var6.length;

	         for(int var8 = 0; var8 < var7; ++var8) {
	            Direction direction = var6[var8];
	            pooledMutable.set((Vec3i)pos).setOffset(direction);
	            i = Math.min(i, getDistanceFromLog(world.getBlockState(pooledMutable)) + 1);
	            if (i == 1) {
	               break;
	            }
	         }
	      } catch (Throwable var17) {
	         var5 = var17;
	         throw var17;
	      } finally {
	         if (pooledMutable != null) {
	            if (var5 != null) {
	               try {
	                  pooledMutable.close();
	               } catch (Throwable var16) {
	                  var5.addSuppressed(var16);
	               }
	            } else {
	               pooledMutable.close();
	            }
	         }

	      }
	      return state.with(NEWDISTANCE, Integer.valueOf(i));
	   }

	   // Check how many blocks away a leaf block is from a log block
	   private static int getDistanceFromLog(BlockState neighbor) {
	      if (BlockTags.LOGS.contains(neighbor.getBlock())) {
	         return 0;
	      } else {
	         return neighbor.getBlock() instanceof PalmLeavesBlock ? neighbor.get(NEWDISTANCE) : 11;
	      }
	   }

	  
	   // This is how we implement the properties into the block
	   @Override
	   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
	      builder.add(NEWDISTANCE);
	      builder.add(TYPE);
	      super.appendProperties(builder);
	   }
	   
	   // This is where we set the property values
	   static
	   {
		  NEWDISTANCE = IntProperty.of("newdistance", 1, 11); 
		  TYPE = EnumProperty.of("type", PalmLeavesBlock.Type.class);
	   }
	   
	   // We want Palm trees to grow two different crops (or no crops too)
	   public static enum Type implements StringIdentifiable {
		      NORMAL(Blocks.AIR, "normal"),
		      COCONUT(ModBlocks.COCONUT, "coconut"),
		      DATE(ModBlocks.DATES, "date");

		      private final String name;
		      private final Block baseBlock;

		      private Type(Block block, String string2) {
		         this.name = string2;
		         this.baseBlock = block;
		      }

		      public String getName() {
		         return this.name;
		      }

		      public Block getBaseBlock() {
		         return this.baseBlock;
		      }

		      public String toString() {
		         return this.name;
		      }

		      public static PalmLeavesBlock.Type getType(int i) {
		         PalmLeavesBlock.Type[] types = values();
		         if (i < 0 || i >= types.length) {
		            i = 0;
		         }

		         return types[i];
		      }

		      public static PalmLeavesBlock.Type getType(String string) {
		         PalmLeavesBlock.Type[] types = values();

		         for(int i = 0; i < types.length; ++i) {
		            if (types[i].getName().equals(string)) {
		               return types[i];
		            }
		         }

		         return types[0];
		      }

			@Override
			public String asString() {
				return this.name;
			}
		   }

	}