package io.github.killerjdog51.biome_enhancements.registries;

import io.github.killerjdog51.biome_enhancements.BiomeEnhancements;
import io.github.killerjdog51.biome_enhancements.items.ModSignItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.TallBlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems
{

	public static Item BAOBAB_DOOR;
	public static Item MANGROVE_DOOR;
	public static Item PALM_DOOR;
	
	public static Item BAOBAB_SIGN;
	public static Item MANGROVE_SIGN;
	public static Item PALM_SIGN;
	
	public static Item BAOBAB_BOAT;
	public static Item MANGROVE_BOAT;
	public static Item PALM_BOAT;
	
	
	public static void init()
	{
		BAOBAB_DOOR = register("baobab_door", new TallBlockItem(ModBlocks.BAOBAB_DOOR, new Item.Settings().group(ItemGroup.REDSTONE)));
		MANGROVE_DOOR = register("mangrove_door", new TallBlockItem(ModBlocks.MANGROVE_DOOR, new Item.Settings().group(ItemGroup.REDSTONE)));
		PALM_DOOR = register("palm_door", new TallBlockItem(ModBlocks.PALM_DOOR, new Item.Settings().group(ItemGroup.REDSTONE)));

		BAOBAB_SIGN = register("baobab_sign", new ModSignItem(new Item.Settings().group(ItemGroup.DECORATIONS), ModBlocks.BAOBAB_SIGN, ModBlocks.BAOBAB_WALL_SIGN));
		MANGROVE_SIGN = register("mangrove_sign", new ModSignItem(new Item.Settings().group(ItemGroup.DECORATIONS), ModBlocks.MANGROVE_SIGN, ModBlocks.MANGROVE_WALL_SIGN));
		PALM_SIGN = register("palm_sign", new ModSignItem(new Item.Settings().group(ItemGroup.DECORATIONS), ModBlocks.PALM_SIGN, ModBlocks.PALM_WALL_SIGN));

	}
	
	public static Item register(String name, Item item)
	{
		return Registry.register(Registry.ITEM, new Identifier(BiomeEnhancements.MOD_ID, name), item);
	}
}
