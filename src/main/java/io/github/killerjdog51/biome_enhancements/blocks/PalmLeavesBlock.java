package io.github.killerjdog51.biome_enhancements.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class PalmLeavesBlock extends LeavesBlock {
	   public static final IntProperty NEWDISTANCE;

	   public PalmLeavesBlock(Settings settings) {
	      super(settings);
	      this.setDefaultState((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(NEWDISTANCE, 11)).with(PERSISTENT, false));
	   }

	   @Override
	   public boolean hasRandomTicks(BlockState state) {
	      return state.get(NEWDISTANCE) == 11 && !state.get(PERSISTENT);
	   }

	   @Override
	   public void onRandomTick(BlockState state, World worldIn, BlockPos pos, Random random) {
	      if (!state.get(PERSISTENT) && state.get(NEWDISTANCE) == 11) {
	         dropStacks(state, worldIn, pos);
	         worldIn.removeBlock(pos, false);
	      }

	   }

	   @Override
	   public void onScheduledTick(BlockState state, World worldIn, BlockPos pos, Random random) {
	      worldIn.setBlockState(pos, updateDistance(state, worldIn, pos), 3);
	   }

	   @Override
	   public BlockState getStateForNeighborUpdate(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
	      int i = getDistanceFromLog(facingState) + 1;
	      
	      if (i != 1 || stateIn.get(NEWDISTANCE) != i) {
	         worldIn.getBlockTickScheduler().schedule(currentPos, this, 1);
	      }

	      return stateIn;
	   }

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

	   private static int getDistanceFromLog(BlockState neighbor) {
	      if (BlockTags.LOGS.contains(neighbor.getBlock())) {
	         return 0;
	      } else {
	         return neighbor.getBlock() instanceof PalmLeavesBlock ? neighbor.get(NEWDISTANCE) : 11;
	      }
	   }

	  
	   @Override
	   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
	      builder.add(NEWDISTANCE);
	      super.appendProperties(builder);
	   }
	   
	   static
	   {
		  NEWDISTANCE = IntProperty.of("newdistance", 1, 11); 
	   }
	}