package io.github.killerjdog51.biome_enhancements.blocks.sapling;

import java.util.Random;

import io.github.killerjdog51.biome_enhancements.world.gen.feature.BaobabTreeFeature;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;

public class BaobabSaplingGenerator extends GiantTreeSaplingGenerator {
   protected AbstractTreeFeature<DefaultFeatureConfig> createTreeFeature(Random random) {
      return null;
   }

   protected AbstractTreeFeature<DefaultFeatureConfig> createLargeTreeFeature(Random random) {
      return new BaobabTreeFeature(DefaultFeatureConfig::deserialize);
   }
}