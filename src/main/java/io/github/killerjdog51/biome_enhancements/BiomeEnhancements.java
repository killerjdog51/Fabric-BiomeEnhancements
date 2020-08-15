package io.github.killerjdog51.biome_enhancements;

import io.github.killerjdog51.biome_enhancements.registries.ModBlockEntities;
import io.github.killerjdog51.biome_enhancements.registries.ModBlocks;
import io.github.killerjdog51.biome_enhancements.registries.ModItems;
import io.github.killerjdog51.biome_enhancements.registries.ModWorldGeneration;
import io.github.killerjdog51.biome_enhancements.utils.BlockColorHandler;
import io.github.killerjdog51.biome_enhancements.utils.StrippedLogHandler;
import net.fabricmc.api.ModInitializer;

public class BiomeEnhancements implements ModInitializer
{

	public static final String MOD_ID = "biome_enhancements";
	
	@Override
	public void onInitialize()
	{
		System.out.println("Initializing Biome Enhancements");
		ModBlocks.init();
		ModBlockEntities.init();
		ModItems.init();
		ModWorldGeneration.init();
		
		
		BlockColorHandler.init();
		StrippedLogHandler.init();
	}
}
