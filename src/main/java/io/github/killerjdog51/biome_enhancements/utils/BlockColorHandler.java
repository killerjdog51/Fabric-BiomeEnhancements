package io.github.killerjdog51.biome_enhancements.utils;

import io.github.killerjdog51.biome_enhancements.registries.ModBlocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.render.ColorProviderRegistry;
import net.minecraft.client.color.block.BlockColorProvider;
import net.minecraft.client.color.item.ItemColorProvider;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.GrassColors;

public class BlockColorHandler
{
	public static void init()
	{
		registerBlockColourHandlers();
		registerItemColourHandlers();
	}
	
	@Environment(EnvType.CLIENT)
	public static void registerBlockColourHandlers()
	{
		// Use the grass colour of the biome or the default grass colour
		final BlockColorProvider grassColourHandler = (state, blockAccess, pos, tintIndex) -> {
			
			if (blockAccess != null && pos != null)
			{
				return BiomeColors.getFoliageColor(blockAccess, pos);
			}

			return GrassColors.getColor(0.5D, 1.0D);
		};

		ColorProviderRegistry.BLOCK.register(grassColourHandler, ModBlocks.BAOBAB_LEAVES, ModBlocks.MANGROVE_LEAVES, ModBlocks.PALM_LEAVES);
	}
	
	@Environment(EnvType.CLIENT)
	public static void registerItemColourHandlers()
	{
		// Use the Block's colour handler for an ItemBlock
		final ItemColorProvider itemBlockColourHandler = (stack, tintIndex) -> {
			return GrassColors.getColor(0.5D, 1.0D);
		};

		ColorProviderRegistry.ITEM.register(itemBlockColourHandler, ModBlocks.BAOBAB_LEAVES, ModBlocks.MANGROVE_LEAVES, ModBlocks.PALM_LEAVES);
	}

}
