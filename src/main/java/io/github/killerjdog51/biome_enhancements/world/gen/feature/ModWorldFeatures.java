package io.github.killerjdog51.biome_enhancements.world.gen.feature;

import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FlowerFeature;

public class ModWorldFeatures {

	public static Feature<DefaultFeatureConfig> BAOBAB_TREE = new BaobabTreeFeature(DefaultFeatureConfig::deserialize);
	public static AbstractTreeFeature<DefaultFeatureConfig> MANGROVE_TREE = new MangroveTreeFeature(DefaultFeatureConfig::deserialize);
	public static AbstractTreeFeature<DefaultFeatureConfig> PALM_TREE = new PalmTreeFeature(DefaultFeatureConfig::deserialize);
	public static Feature<DefaultFeatureConfig> OASIS = new OasisFeature(DefaultFeatureConfig::deserialize);
	public static FlowerFeature DESERT_FLOWER = new DesertFlowersFeature(DefaultFeatureConfig::deserialize);


}
