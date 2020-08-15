package io.github.killerjdog51.biome_enhancements.world.biome;

import io.github.killerjdog51.biome_enhancements.world.gen.feature.ModWorldFeatures;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.CountDecoratorConfig;
import net.minecraft.world.gen.decorator.CountExtraChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.MineshaftFeature;
import net.minecraft.world.gen.feature.MineshaftFeatureConfig;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;

public class OasisBiome extends Biome {

	public OasisBiome() {
	      super((new Biome.Settings()).configureSurfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG).precipitation(Biome.Precipitation.NONE).category(Biome.Category.DESERT).depth(-0.3F).scale(0.001F).temperature(0.95F).downfall(0.9F).waterColor(4566514).waterFogColor(267827));

	      this.addStructureFeature(Feature.MINESHAFT, new MineshaftFeatureConfig(0.004D, MineshaftFeature.Type.NORMAL));
	      this.addStructureFeature(Feature.STRONGHOLD, FeatureConfig.DEFAULT);
	      this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Biome.configureFeature(ModWorldFeatures.PALM_TREE, FeatureConfig.DEFAULT, Decorator.COUNT_EXTRA_HEIGHTMAP, new CountExtraChanceDecoratorConfig(1, 0.1F, 1)));
	      this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Biome.configureFeature(ModWorldFeatures.DESERT_FLOWER, FeatureConfig.DEFAULT, Decorator.COUNT_HEIGHTMAP_32, new CountDecoratorConfig(1)));
	      DefaultBiomeFeatures.addLandCarvers(this);
	      DefaultBiomeFeatures.addDungeons(this);
	      DefaultBiomeFeatures.addMineables(this);
	      DefaultBiomeFeatures.addDefaultOres(this);
	      DefaultBiomeFeatures.addDefaultDisks(this);
	      DefaultBiomeFeatures.addDefaultFlowers(this);
	      DefaultBiomeFeatures.addDefaultGrass(this);
	      DefaultBiomeFeatures.addDesertVegetation(this);
	      DefaultBiomeFeatures.addSprings(this);
	      DefaultBiomeFeatures.addDesertFeatures(this);
	      this.addSpawn(EntityCategory.CREATURE, new Biome.SpawnEntry(EntityType.RABBIT, 4, 2, 3));
	      this.addSpawn(EntityCategory.AMBIENT, new Biome.SpawnEntry(EntityType.BAT, 10, 8, 8));
	      this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.SPIDER, 100, 4, 4));
	      this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.SKELETON, 100, 4, 4));
	      this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.ZOMBIE, 19, 4, 4));
	      this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.ZOMBIE_VILLAGER, 1, 1, 1));
	      
	      
	}

}
