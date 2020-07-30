package io.github.killerjdog51.biome_enhancements.blocks.entities;

import io.github.killerjdog51.biome_enhancements.registries.ModBlockEntities;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.SignBlockEntity;

public class ModSignBlockEntity extends SignBlockEntity
{
	@Override
	public BlockEntityType<?> getType() {
	      return ModBlockEntities.SIGN;
	   }

}
