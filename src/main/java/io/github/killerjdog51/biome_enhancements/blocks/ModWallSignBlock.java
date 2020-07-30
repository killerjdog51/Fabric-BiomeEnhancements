package io.github.killerjdog51.biome_enhancements.blocks;

import io.github.killerjdog51.biome_enhancements.blocks.entities.ModSignBlockEntity;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

public class ModWallSignBlock extends WallSignBlock {

	public ModWallSignBlock(Settings settings) {
		super(settings);
	}

	@Override
	public BlockEntity createBlockEntity(BlockView view) {
	      return new ModSignBlockEntity();
	   }
}
