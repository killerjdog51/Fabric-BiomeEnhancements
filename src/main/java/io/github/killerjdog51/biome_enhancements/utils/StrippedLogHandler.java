package io.github.killerjdog51.biome_enhancements.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import io.github.killerjdog51.biome_enhancements.events.BlockEventHandler;
import io.github.killerjdog51.biome_enhancements.registries.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.AxeItem;

public class StrippedLogHandler {

	/**
	 * Explanation of Handler
	 * 
	 * This class uses reflection to access a private final field in the Minecraft AxeItem class.
	 * By accessing the STRIPPED_BLOCKS mapping field, we are able to add our custom log/wood blocks to the map.
	 * This allows Minecraft to do the work for stripping our custom logs (at least our blocks that are registered as log/pillar blocks).
	 * 
	 * For those unfamiliar with reflection, we basically copy the class/field/method from another class to be modified.
	 * Rather than an extending the class or using the class's native methods, we copy the specific field we want and manually change it. 
	 * Don't use reflection unless you know what you are doing. 
	 * If possible, opt for using events rather than reflection because this can potentially (irreversibly) break things. 
	 */
	
	private static Map<Block, Block> TEMP_BLOCK_STRIPPING_MAP = new HashMap<>();
	
	public static void init()
	{
		initMap();
		registerStrippableBlocks(ModBlocks.BAOBAB_LOG, ModBlocks.STRIPPED_BAOBAB_LOG);
		registerStrippableBlocks(ModBlocks.BAOBAB_WOOD, ModBlocks.STRIPPED_BAOBAB_WOOD);
		registerStrippableBlocks(ModBlocks.MANGROVE_LOG, ModBlocks.STRIPPED_MANGROVE_LOG);
		registerStrippableBlocks(ModBlocks.MANGROVE_WOOD, ModBlocks.STRIPPED_MANGROVE_WOOD);
		registerStrippableBlocks(ModBlocks.PALM_WOOD, ModBlocks.STRIPPED_PALM_WOOD);
		BlockEventHandler.StripLog();

	}

	@SuppressWarnings("unchecked")
	private static void initMap()
	{
		try {
			// Get the field we want access to
			Field field = AxeItem.class.getDeclaredField("STRIPPED_BLOCKS");
			
			// We want to access the values within that field
			field.setAccessible(true);
			
			// We copy those values into a local variable map (These are the logs that are currently mapped in Minecraft)
			Map<Block, Block> immutableMap = (Map<Block, Block>) field.get(null);
	
			// We copy those values into a global variable map
			for (Map.Entry<Block, Block> e : immutableMap.entrySet()) {
				TEMP_BLOCK_STRIPPING_MAP.put(e.getKey(), e.getValue());
			}
		}
		// Just in case we encounter problems the game won't crash
		catch (IllegalAccessException e) {
			System.out.println("Illegal access");
		}
		catch (NoSuchFieldException e) {
			System.out.println("No such field");
		}
		catch (Exception e) {
			System.out.println("Other exception");
		}
	}

	private static void registerStrippableBlocks(Block log, Block strippedLog)
	{
		try {
			// We add our custom logs to the global variable map (for stripping logs)
			TEMP_BLOCK_STRIPPING_MAP.put(log, strippedLog);

			// Get the field we want to change (we don't want to access it, we want to replace it)
			Field field = AxeItem.class.getDeclaredField("STRIPPED_BLOCKS");

			// Get the modifier field from the Field class
			Field modifiersField = Field.class.getDeclaredField("modifiers");
			
			// We want to access the value in that field
			modifiersField.setAccessible(true);
			
			// We use the modifier field from the Field class to remove the final modifier from the Minecraft Axe field so we can replace it
			modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

			// Replace Minecraft's stripping map with our custom stripping map
			field.set(null, TEMP_BLOCK_STRIPPING_MAP);
		}
		catch (IllegalAccessException e) {
			System.out.println("Illegal access");
		}
		catch (NoSuchFieldException e) {
			System.out.println("No such field");
		}
		catch (Exception e) {
			System.out.println("Other exception");
		}
	}
}
