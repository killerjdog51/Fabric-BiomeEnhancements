package io.github.killerjdog51.biome_enhancements.registries;

import java.util.Iterator;

import io.github.killerjdog51.biome_enhancements.BiomeEnhancements;
import io.github.killerjdog51.biome_enhancements.world.biome.OasisBiome;
import io.github.killerjdog51.biome_enhancements.world.gen.feature.ModWorldFeatures;
import net.fabricmc.fabric.api.biomes.v1.FabricBiomes;
import net.fabricmc.fabric.api.biomes.v1.OverworldBiomes;
import net.fabricmc.fabric.api.biomes.v1.OverworldClimate;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.CountExtraChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DecoratedFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;


public class ModWorldGeneration {

	// Register our biome to exist
	public static final Biome OASIS = Registry.register(Registry.BIOME, new Identifier(BiomeEnhancements.MOD_ID, "oasis"), new OasisBiome());
	
	
	public static void init()
	{
		// Uses reflection to remove Minecraft swamp trees from Swamp water generation
		// Gets all vegetation features for the Swamp biome
		Iterator<ConfiguredFeature<?>> it = Biomes.SWAMP.getFeaturesForStep(GenerationStep.Feature.VEGETAL_DECORATION).iterator();
		
		// Iterates through each feature
		while(it.hasNext()) {
			ConfiguredFeature<?> feature = it.next();
			
			// Checks if the feature is the swamp tree
			if(feature.config instanceof DecoratedFeatureConfig) {
				DecoratedFeatureConfig dfconfig = (DecoratedFeatureConfig)feature.config;
				if(dfconfig.feature.config instanceof FeatureConfig) {
					Feature<?> featureconfig = dfconfig.feature.feature;
					if(featureconfig == Feature.SWAMP_TREE)
					{
						it.remove();
						break;
					}
				}
			}
		}
		
		// Uses reflection to remove Minecraft swamp trees from Swamp ground generation
		// Gets all vegatation features for the swamp biome
		it = Biomes.SWAMP_HILLS.getFeaturesForStep(GenerationStep.Feature.VEGETAL_DECORATION).iterator();
		
		// Iterates through each feature
		while(it.hasNext()) {
			ConfiguredFeature<?> feature = it.next();
			
			// Checks if the feature is the swamp tree
			if(feature.config instanceof DecoratedFeatureConfig) {
				DecoratedFeatureConfig dfconfig = (DecoratedFeatureConfig)feature.config;
				if(dfconfig.feature.config instanceof FeatureConfig) {
					Feature<?> featureconfig = dfconfig.feature.feature;
					if(featureconfig == Feature.SWAMP_TREE)
					{
						it.remove();
						break;
					}
				}
			}
		}
		
		// Adds mangrove trees to the Swamp
		Biomes.SWAMP.addFeature(
				GenerationStep.Feature.VEGETAL_DECORATION,
		                Biome.configureFeature(
		                        ModWorldFeatures.MANGROVE_TREE,
		                        FeatureConfig.DEFAULT,
		                        Decorator.COUNT_EXTRA_HEIGHTMAP,
		                        new CountExtraChanceDecoratorConfig(2, 0.1F, 1))
		        );
		
		// Adds mangrove trees to the Swamp Hills
		Biomes.SWAMP_HILLS.addFeature(
				GenerationStep.Feature.VEGETAL_DECORATION,
                Biome.configureFeature(
                        ModWorldFeatures.MANGROVE_TREE,
                        FeatureConfig.DEFAULT,
                        Decorator.COUNT_EXTRA_HEIGHTMAP,
                        new CountExtraChanceDecoratorConfig(2, 0.1F, 1))
        );
		
		// Adds baobab trees to the Savanna
		Biomes.SAVANNA.addFeature(
				GenerationStep.Feature.VEGETAL_DECORATION,
		                Biome.configureFeature(
		                        ModWorldFeatures.BAOBAB_TREE,
		                        FeatureConfig.DEFAULT,
		                        Decorator.COUNT_EXTRA_HEIGHTMAP,
		                        new CountExtraChanceDecoratorConfig(0, 0.05F, 1))
		        );
		
		// Adds palm trees to beaches
		Biomes.BEACH.addFeature(
				GenerationStep.Feature.VEGETAL_DECORATION,
                Biome.configureFeature(
                        ModWorldFeatures.PALM_TREE,
                        FeatureConfig.DEFAULT,
                        Decorator.COUNT_EXTRA_HEIGHTMAP,
                        new CountExtraChanceDecoratorConfig(0, 0.06F, 1))
        );
					
		// Adds oasis to the desert
		Biomes.DESERT.addFeature(
                GenerationStep.Feature.LOCAL_MODIFICATIONS,
                Biome.configureFeature(
                        ModWorldFeatures.OASIS,
                        FeatureConfig.DEFAULT,
                        Decorator.COUNT_EXTRA_HEIGHTMAP,
                        new CountExtraChanceDecoratorConfig(0, 0.1F, 1))
        );

	}
}
