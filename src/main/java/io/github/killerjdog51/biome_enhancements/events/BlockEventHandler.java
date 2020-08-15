package io.github.killerjdog51.biome_enhancements.events;

import java.util.HashMap;
import java.util.Map;

import io.github.killerjdog51.biome_enhancements.blocks.PalmLogBlock;
import io.github.killerjdog51.biome_enhancements.registries.ModBlocks;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.AxeItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;

public class BlockEventHandler
{
	
    public static Map<Block, Block> BLOCK_STRIPPING_MAP = new HashMap<>();

    static {
        BLOCK_STRIPPING_MAP.put(ModBlocks.PALM_LOG, ModBlocks.STRIPPED_PALM_LOG);
    }

    // I need to use an event for Palm logs because they aren't an instance of LogBlock or PillarBlock
	public static void StripLog()
	{
		UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
			
			// If the player's holding an axe item continue
			if ( player.getMainHandStack().getItem() instanceof AxeItem)
			{
				BlockPos pos = hitResult.getBlockPos();
				BlockState state = world.getBlockState(pos);
				// If the block clicked on is in our map we return the stripped version, otherwise null
				Block block = BLOCK_STRIPPING_MAP.get(state.getBlock());
				
				// If block isn't null continue
				if(block != null)
				{
					// Play the sound for stripping the log
					world.playSound(player, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
					
					if (!world.isClient)
					{
						// Replace our log block with the stripped version with the correct orientation
						world.setBlockState(pos, block.getDefaultState().with(PalmLogBlock.FACING, state.get(PalmLogBlock.FACING)), 11);
						
						// Damage the axe if the player isn't in creative
						if(!player.isCreative())
						{
							player.getMainHandStack().damage(1, player, (consumer) -> {consumer.sendToolBreakStatus(hand);});
						}
					}
				}
			}
			
			return ActionResult.PASS;
			
		});
	}
}
