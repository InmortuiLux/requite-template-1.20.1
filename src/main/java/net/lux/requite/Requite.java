package net.lux.requite;

import net.fabricmc.api.ModInitializer;

import net.lux.requite.block.ModBlockEntities;
import net.lux.requite.block.ModBlocks;
import net.lux.requite.item.ModItemGroups;
import net.lux.requite.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Requite implements ModInitializer {
	public static final String MOD_ID = "requite";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		ModBlocks.registerModBlocks();
		ModBlockEntities.registerBlockEntities(); // <- ADD THIS
		ModItemGroups.registerItemGroups();
		ModItems.registerModItems();
	}
}
