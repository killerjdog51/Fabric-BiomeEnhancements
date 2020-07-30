package io.github.killerjdog51.biome_enhancements.registries;

import io.github.killerjdog51.biome_enhancements.BiomeEnhancements;
import io.github.killerjdog51.biome_enhancements.blocks.entities.ModSignBlockEntity;
import io.github.killerjdog51.biome_enhancements.blocks.entities.ModSignBlockEntityRenderer;
import net.fabricmc.fabric.api.client.render.BlockEntityRendererRegistry;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;

public class ModBlockEntities
{

	public static BlockEntityType<ModSignBlockEntity> SIGN;
	
	public static void init()
	{
		SIGN = Registry.register(Registry.BLOCK_ENTITY, BiomeEnhancements.MOD_ID + ":sign", BlockEntityType.Builder.create(ModSignBlockEntity::new, ModBlocks.BAOBAB_SIGN, ModBlocks.BAOBAB_WALL_SIGN, ModBlocks.MANGROVE_SIGN, ModBlocks.MANGROVE_WALL_SIGN, ModBlocks.PALM_SIGN, ModBlocks.PALM_WALL_SIGN).build(null));
		BlockEntityRendererRegistry.INSTANCE.register(ModSignBlockEntity.class, new ModSignBlockEntityRenderer());
	}
}
