package io.github.killerjdog51.biome_enhancements.world.gen.feature;

import com.mojang.datafixers.Dynamic;

import io.github.killerjdog51.biome_enhancements.registries.ModBlocks;

import java.util.Random;
import java.util.function.Function;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.FlowerFeature;

public class DesertFlowersFeature extends FlowerFeature {
   public DesertFlowersFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> config) {
      super(config);
   }

   public BlockState getFlowerToPlace(Random random, BlockPos pos) {
      return ModBlocks.DESERT_CANDLE.getDefaultState();
   }
}