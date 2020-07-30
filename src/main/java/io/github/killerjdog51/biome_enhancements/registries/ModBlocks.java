package io.github.killerjdog51.biome_enhancements.registries;

import io.github.killerjdog51.biome_enhancements.BiomeEnhancements;
import io.github.killerjdog51.biome_enhancements.blocks.ModDoorBlock;
import io.github.killerjdog51.biome_enhancements.blocks.ModPressurePlateBlock;
import io.github.killerjdog51.biome_enhancements.blocks.ModStairsBlock;
import io.github.killerjdog51.biome_enhancements.blocks.ModStandingSignBlock;
import io.github.killerjdog51.biome_enhancements.blocks.ModTrapDoorBlock;
import io.github.killerjdog51.biome_enhancements.blocks.ModWallSignBlock;
import io.github.killerjdog51.biome_enhancements.blocks.ModWoodButtonBlock;
import io.github.killerjdog51.biome_enhancements.blocks.PalmLeavesBlock;
import io.github.killerjdog51.biome_enhancements.blocks.PalmLogBlock;
import io.github.killerjdog51.biome_enhancements.blocks.RotatedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.LogBlock;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlocks
{
	
	public static Block BAOBAB_PLANKS;
	public static Block MANGROVE_PLANKS;
	public static Block PALM_PLANKS;
	
	public static Block BAOBAB_LOG;
	public static Block MANGROVE_LOG;
	public static Block PALM_LOG;
	
	public static Block BAOBAB_WOOD;
	public static Block MANGROVE_WOOD;
	public static Block PALM_WOOD;
	
	public static Block STRIPPED_BAOBAB_LOG;
	public static Block STRIPPED_MANGROVE_LOG;
	public static Block STRIPPED_PALM_LOG;
	
	public static Block STRIPPED_BAOBAB_WOOD;
	public static Block STRIPPED_MANGROVE_WOOD;
	public static Block STRIPPED_PALM_WOOD;
	
	public static Block BAOBAB_LEAVES;
	public static Block MANGROVE_LEAVES;
	public static Block PALM_LEAVES;
	
	public static Block BAOBAB_STAIRS;
	public static Block MANGROVE_STAIRS;
	public static Block PALM_STAIRS;
	
	public static Block BAOBAB_SLAB;
	public static Block MANGROVE_SLAB;
	public static Block PALM_SLAB;
	
	public static Block BAOBAB_FENCE;
	public static Block MANGROVE_FENCE;
	public static Block PALM_FENCE;
	
	public static Block BAOBAB_FENCE_GATE;
	public static Block MANGROVE_FENCE_GATE;
	public static Block PALM_FENCE_GATE;
	
	public static Block BAOBAB_BUTTON;
	public static Block MANGROVE_BUTTON;
	public static Block PALM_BUTTON;
	
	public static Block BAOBAB_PRESSURE_PLATE;
	public static Block MANGROVE_PRESSURE_PLATE;
	public static Block PALM_PRESSURE_PLATE;
	
	public static Block BAOBAB_TRAPDOOR;
	public static Block MANGROVE_TRAPDOOR;
	public static Block PALM_TRAPDOOR;
	
	public static Block BAOBAB_DOOR;
	public static Block MANGROVE_DOOR;
	public static Block PALM_DOOR;
	
	public static Block BAOBAB_SIGN;
	public static Block BAOBAB_WALL_SIGN;
	public static Block MANGROVE_SIGN;
	public static Block MANGROVE_WALL_SIGN;
	public static Block PALM_SIGN;
	public static Block PALM_WALL_SIGN;
	
	public static Block BAOBAB_SAPLING;
	public static Block MANGROVE_SAPLING;
	public static Block PALM_SAPLING;
	public static Block DESERT_CANDLE;
	
	public static Block POTTED_BAOBAB_SAPLING;
	public static Block POTTED_MANGROVE_SAPLING;
	public static Block POTTED_PALM_SAPLING;
	public static Block POTTED_DESERT_CANDLE;
	
	
	public static void init()
	{
		BAOBAB_PLANKS = register("baobab_planks", new Block(Block.Settings.copy(Blocks.ACACIA_PLANKS)), ItemGroup.BUILDING_BLOCKS);
		MANGROVE_PLANKS = register("mangrove_planks", new Block(Block.Settings.copy(Blocks.SPRUCE_PLANKS)), ItemGroup.BUILDING_BLOCKS);
		PALM_PLANKS = register("palm_planks", new Block(Block.Settings.copy(Blocks.OAK_PLANKS)), ItemGroup.BUILDING_BLOCKS);
		
		BAOBAB_LOG = register("baobab_log", new LogBlock(MaterialColor.ORANGE, Block.Settings.copy(Blocks.ACACIA_LOG)), ItemGroup.BUILDING_BLOCKS);
		MANGROVE_LOG = register("mangrove_log", new LogBlock(MaterialColor.GREEN, Block.Settings.copy(Blocks.SPRUCE_LOG)), ItemGroup.BUILDING_BLOCKS);
		PALM_LOG = register("palm_log", new PalmLogBlock(Block.Settings.copy(Blocks.OAK_LOG)), ItemGroup.BUILDING_BLOCKS);
		
		BAOBAB_WOOD = register("baobab_wood", new RotatedBlock(Block.Settings.copy(Blocks.ACACIA_WOOD)), ItemGroup.BUILDING_BLOCKS);
		MANGROVE_WOOD = register("mangrove_wood", new RotatedBlock(Block.Settings.copy(Blocks.SPRUCE_WOOD)), ItemGroup.BUILDING_BLOCKS);
		PALM_WOOD = register("palm_wood", new RotatedBlock(Block.Settings.copy(Blocks.OAK_WOOD)), ItemGroup.BUILDING_BLOCKS);
		
		STRIPPED_BAOBAB_LOG = register("stripped_baobab_log", new LogBlock(MaterialColor.ORANGE, Block.Settings.copy(Blocks.ACACIA_LOG)), ItemGroup.BUILDING_BLOCKS);
		STRIPPED_MANGROVE_LOG = register("stripped_mangrove_log", new LogBlock(MaterialColor.GREEN, Block.Settings.copy(Blocks.SPRUCE_LOG)), ItemGroup.BUILDING_BLOCKS);
		STRIPPED_PALM_LOG = register("stripped_palm_log", new PalmLogBlock(Block.Settings.copy(Blocks.OAK_LOG)), ItemGroup.BUILDING_BLOCKS);
		
		STRIPPED_BAOBAB_WOOD = register("stripped_baobab_wood", new RotatedBlock(Block.Settings.copy(Blocks.ACACIA_WOOD)), ItemGroup.BUILDING_BLOCKS);
		STRIPPED_MANGROVE_WOOD = register("stripped_mangrove_wood", new RotatedBlock(Block.Settings.copy(Blocks.SPRUCE_WOOD)), ItemGroup.BUILDING_BLOCKS);
		STRIPPED_PALM_WOOD = register("stripped_palm_wood", new RotatedBlock(Block.Settings.copy(Blocks.OAK_WOOD)), ItemGroup.BUILDING_BLOCKS);
		
		BAOBAB_LEAVES = register("baobab_leaves", new LeavesBlock(Block.Settings.copy(Blocks.ACACIA_LEAVES)), ItemGroup.DECORATIONS);
		MANGROVE_LEAVES = register("mangrove_leaves", new LeavesBlock(Block.Settings.copy(Blocks.SPRUCE_LEAVES)), ItemGroup.DECORATIONS);
		PALM_LEAVES = register("palm_leaves", new PalmLeavesBlock(Block.Settings.copy(Blocks.JUNGLE_LEAVES)), ItemGroup.DECORATIONS);
		
		BAOBAB_STAIRS = register("baobab_stairs", new ModStairsBlock(BAOBAB_PLANKS.getDefaultState(), Block.Settings.copy(BAOBAB_PLANKS)), ItemGroup.BUILDING_BLOCKS);
		MANGROVE_STAIRS = register("mangrove_stairs", new ModStairsBlock(MANGROVE_PLANKS.getDefaultState(), Block.Settings.copy(MANGROVE_PLANKS)), ItemGroup.BUILDING_BLOCKS);
		PALM_STAIRS = register("palm_stairs", new ModStairsBlock(PALM_PLANKS.getDefaultState(), Block.Settings.copy(PALM_PLANKS)), ItemGroup.BUILDING_BLOCKS);
		
		BAOBAB_SLAB = register("baobab_slab", new SlabBlock(Block.Settings.copy(BAOBAB_PLANKS)), ItemGroup.BUILDING_BLOCKS);
		MANGROVE_SLAB = register("mangrove_slab", new SlabBlock(Block.Settings.copy(MANGROVE_PLANKS)), ItemGroup.BUILDING_BLOCKS);
		PALM_SLAB = register("palm_slab", new SlabBlock(Block.Settings.copy(PALM_PLANKS)), ItemGroup.BUILDING_BLOCKS);
		
		BAOBAB_FENCE = register("baobab_fence", new FenceBlock(Block.Settings.copy(BAOBAB_PLANKS)), ItemGroup.DECORATIONS);
		MANGROVE_FENCE = register("mangrove_fence", new FenceBlock(Block.Settings.copy(MANGROVE_PLANKS)), ItemGroup.DECORATIONS);
		PALM_FENCE = register("palm_fence", new FenceBlock(Block.Settings.copy(PALM_PLANKS)), ItemGroup.DECORATIONS);
		
		BAOBAB_FENCE_GATE = register("baobab_fence_gate", new FenceGateBlock(Block.Settings.copy(BAOBAB_PLANKS)), ItemGroup.REDSTONE);
		MANGROVE_FENCE_GATE = register("mangrove_fence_gate", new FenceGateBlock(Block.Settings.copy(MANGROVE_PLANKS)), ItemGroup.REDSTONE);
		PALM_FENCE_GATE = register("palm_fence_gate", new FenceGateBlock(Block.Settings.copy(PALM_PLANKS)), ItemGroup.REDSTONE);
		
		BAOBAB_BUTTON = register("baobab_button", new ModWoodButtonBlock(Block.Settings.copy(Blocks.ACACIA_BUTTON)), ItemGroup.REDSTONE);
		MANGROVE_BUTTON = register("mangrove_button", new ModWoodButtonBlock(Block.Settings.copy(Blocks.SPRUCE_BUTTON)), ItemGroup.REDSTONE);
		PALM_BUTTON = register("palm_button", new ModWoodButtonBlock(Block.Settings.copy(Blocks.OAK_BUTTON)), ItemGroup.REDSTONE);
		
		BAOBAB_PRESSURE_PLATE = register("baobab_pressure_plate", new ModPressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, Block.Settings.copy(Blocks.ACACIA_PRESSURE_PLATE)), ItemGroup.REDSTONE);
		MANGROVE_PRESSURE_PLATE = register("mangrove_pressure_plate", new ModPressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, Block.Settings.copy(Blocks.SPRUCE_PRESSURE_PLATE)), ItemGroup.REDSTONE);
		PALM_PRESSURE_PLATE = register("palm_pressure_plate", new ModPressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, Block.Settings.copy(Blocks.OAK_PRESSURE_PLATE)), ItemGroup.REDSTONE);
		
		BAOBAB_TRAPDOOR = register("baobab_trapdoor", new ModTrapDoorBlock(Block.Settings.copy(Blocks.ACACIA_TRAPDOOR)), ItemGroup.REDSTONE);
		MANGROVE_TRAPDOOR = register("mangrove_trapdoor", new ModTrapDoorBlock(Block.Settings.copy(Blocks.SPRUCE_TRAPDOOR)), ItemGroup.REDSTONE);
		PALM_TRAPDOOR = register("palm_trapdoor", new ModTrapDoorBlock(Block.Settings.copy(Blocks.OAK_TRAPDOOR)), ItemGroup.REDSTONE);
		
		BAOBAB_DOOR = new ModDoorBlock(Block.Settings.copy(Blocks.ACACIA_DOOR));
		Registry.register(Registry.BLOCK, new Identifier(BiomeEnhancements.MOD_ID, "baobab_door"), BAOBAB_DOOR);
		
		MANGROVE_DOOR = new ModDoorBlock(Block.Settings.copy(Blocks.SPRUCE_DOOR));
		Registry.register(Registry.BLOCK, new Identifier(BiomeEnhancements.MOD_ID, "mangrove_door"), MANGROVE_DOOR);
		
		PALM_DOOR = new ModDoorBlock(Block.Settings.copy(Blocks.OAK_DOOR));
		Registry.register(Registry.BLOCK, new Identifier(BiomeEnhancements.MOD_ID, "palm_door"), PALM_DOOR);
		
		BAOBAB_SIGN = new ModStandingSignBlock(Block.Settings.copy(Blocks.ACACIA_SIGN));
		BAOBAB_WALL_SIGN = new ModWallSignBlock(Block.Settings.copy(Blocks.ACACIA_WALL_SIGN));
		Registry.register(Registry.BLOCK, new Identifier(BiomeEnhancements.MOD_ID, "baobab_sign"), BAOBAB_SIGN);
		Registry.register(Registry.BLOCK, new Identifier(BiomeEnhancements.MOD_ID, "baobab_wall_sign"), BAOBAB_WALL_SIGN);
		
		MANGROVE_SIGN = new ModStandingSignBlock(Block.Settings.copy(Blocks.SPRUCE_SIGN));
		MANGROVE_WALL_SIGN = new ModWallSignBlock(Block.Settings.copy(Blocks.SPRUCE_WALL_SIGN));
		Registry.register(Registry.BLOCK, new Identifier(BiomeEnhancements.MOD_ID, "mangrove_sign"), MANGROVE_SIGN);
		Registry.register(Registry.BLOCK, new Identifier(BiomeEnhancements.MOD_ID, "mangrove_wall_sign"), MANGROVE_WALL_SIGN);
		
		PALM_SIGN = new ModStandingSignBlock(Block.Settings.copy(Blocks.OAK_SIGN));
		PALM_WALL_SIGN = new ModWallSignBlock(Block.Settings.copy(Blocks.OAK_WALL_SIGN));
		Registry.register(Registry.BLOCK, new Identifier(BiomeEnhancements.MOD_ID, "palm_sign"), PALM_SIGN);
		Registry.register(Registry.BLOCK, new Identifier(BiomeEnhancements.MOD_ID, "palm_wall_sign"), PALM_WALL_SIGN);

	}
	
	public static Block register(String name, Block block, ItemGroup group)
	{
		Registry.register(Registry.ITEM, new Identifier(BiomeEnhancements.MOD_ID, name), new BlockItem(block, new Item.Settings().group(group)));
		return Registry.register(Registry.BLOCK, new Identifier(BiomeEnhancements.MOD_ID, name), block);
	}
}
