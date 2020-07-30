package io.github.killerjdog51.biome_enhancements.blocks;

import net.minecraft.block.BlockState;

public class PalmLogBlock extends RotatedBlock
{

	public PalmLogBlock(Settings settings) {
		super(settings);
	}

	@Override
	public boolean isOpaque(BlockState state)
	{
		return false;
	}

}
