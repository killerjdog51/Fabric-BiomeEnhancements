package io.github.killerjdog51.biome_enhancements.blocks.sapling;

import java.util.Random;

import io.github.killerjdog51.biome_enhancements.world.gen.feature.PalmTreeFeature;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;

public class PalmSaplingGenerator extends SaplingGenerator {
   
   protected AbstractTreeFeature<DefaultFeatureConfig> createTreeFeature(Random random) {
      return new PalmTreeFeature(DefaultFeatureConfig::deserialize);
   }
}