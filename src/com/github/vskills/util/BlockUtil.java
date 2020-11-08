package com.github.vskills.util;

import org.bukkit.Material;

public class BlockUtil {

	public boolean isDigable(Material block){
		switch(block){
			case GRASS: case DIRT: case SAND: case GRAVEL: case SOUL_SAND: case CLAY: case SNOW: case MYCELIUM: return true;
			default: return false;
		}
	}
	
	public boolean isFarmable(Material block){
		switch(block){
			case DIRT: case GRASS: return true;
			default: return false;
		}
	}

	public boolean isCuttable(Material block){
		switch(block){
			case ACACIA_LOG: case BIRCH_LOG: case DARK_OAK_LOG: case JUNGLE_LOG: case OAK_LOG:
			case SPRUCE_LOG: case STRIPPED_ACACIA_LOG: case STRIPPED_BIRCH_LOG: case STRIPPED_DARK_OAK_LOG: 
			case STRIPPED_JUNGLE_LOG: case STRIPPED_OAK_LOG: case STRIPPED_SPRUCE_LOG: case ACACIA_LEAVES: 
			case BIRCH_LEAVES: case DARK_OAK_LEAVES: case JUNGLE_LEAVES: case OAK_LEAVES: case SPRUCE_LEAVES: return true;
			default: return false;
		}
	}
	
	public boolean isMinable(Material block){
		switch(block){
			case STONE: case COAL_ORE: case GOLD_ORE: case DIAMOND_ORE: case IRON_ORE: case EMERALD_ORE: case LAPIS_ORE: case REDSTONE_ORE:
			case SANDSTONE: return true;
			default: return false;
		}
	}
	
	public int getBlockDestroyXP(Material block){
		switch(block){
			case STONE: return 1;
			case SANDSTONE: return 1;
			case GRASS: return 1;
			case DIRT: return 1;
			case SAND: return 1;
			case GRAVEL: return 2;
			case SOUL_SAND: return 3;
			case CLAY: return 3;
			case SNOW: return 1;
			case MYCELIUM: return 3;
			case ACACIA_LOG: return 1;
			case BIRCH_LOG: return 1;
			case DARK_OAK_LOG: return 1;
			case JUNGLE_LOG: return 1;
			case OAK_LOG: return 1;
			case SPRUCE_LOG: return 1;
			case STRIPPED_ACACIA_LOG: return 1;
			case STRIPPED_BIRCH_LOG: return 1;
			case STRIPPED_DARK_OAK_LOG: return 1;
			case STRIPPED_JUNGLE_LOG: return 1;
			case STRIPPED_OAK_LOG: return 1;
			case STRIPPED_SPRUCE_LOG: return 1;
			case COAL_ORE: return 2;
			case IRON_ORE: return 3;
			case GOLD_ORE: return 4;
			case DIAMOND_ORE: return 5;
			case EMERALD_ORE: return 6;
			case LAPIS_ORE: return 4;
			case REDSTONE_ORE: return 4; 
			default: return 1;
		}
	}
	
	public int getFarmerPlaceXP(Material block){
		switch(block){
			case BEETROOT_SEEDS: case PUMPKIN_SEEDS: case MELON_SEEDS: case WHEAT_SEEDS: case CACTUS: case CARROT: case POTATO: 
			case SUGAR_CANE: case COCOA_BEANS:  return 2;
			default: return 1;
		}
	}
	
	public boolean isInstaGrowable(Material block){
		switch(block){
			case PUMPKIN_STEM: case MELON_STEM: case CACTUS: case SUGAR_CANE: return true;
			default: return false;
		}
	}
}
