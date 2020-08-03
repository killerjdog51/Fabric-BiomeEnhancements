package io.github.killerjdog51.biome_enhancements.blocks;

import net.minecraft.block.SaplingBlock;
import net.minecraft.block.sapling.SaplingGenerator;

// Since the Sapling block is protected we need to extend it with our own Sapling Block class to make our own Sapling Blocks
public class ModSaplingBlock extends SaplingBlock {

	public ModSaplingBlock(SaplingGenerator generator, Settings settings) {
		super(generator, settings);
	}

}
