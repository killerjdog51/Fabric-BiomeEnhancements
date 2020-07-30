package io.github.killerjdog51.biome_enhancements.items;

import io.github.killerjdog51.biome_enhancements.blocks.entities.ModSignBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SignItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ModSignItem extends SignItem{

	public ModSignItem(Settings settings, Block floorBlock, Block wallBlock) {
		super(settings, floorBlock, wallBlock);
	}

	@Override
	protected boolean postPlacement(BlockPos pos, World worldIn, PlayerEntity player, ItemStack stack, BlockState state) {
	      boolean flag = super.postPlacement(pos, worldIn, player, stack, state);
	      if (!worldIn.isClient && !flag && player != null) {
	         player.openEditSignScreen((ModSignBlockEntity)worldIn.getBlockEntity(pos));
	      }

	      return flag;
	   }
}
