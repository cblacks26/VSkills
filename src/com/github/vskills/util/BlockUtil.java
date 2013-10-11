package com.github.vskills.util;

import org.bukkit.Material;

public class BlockUtil {

	public boolean isDigable(Material block){
		switch(block){
			case GRASS: case DIRT: case SAND: case GRAVEL: case SOUL_SAND: case CLAY: case HARD_CLAY: case SOIL:
			case SNOW: case MYCEL: return true;
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
			case LOG: case LEAVES: return true;
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
			case HARD_CLAY: return 3;
			case SOIL: return 1;
			case SNOW: return 1;
			case MYCEL: return 3;
			case LOG: return 1;
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
	
	public int getBlockPlaceXP(Material block){
		switch(block){
			case STONE: return 2;
			case SOUL_SAND: return 2;
			case MYCEL: return 3;
			case LOG: return 2;
			case IRON_BLOCK: return 3;
			case GOLD_BLOCK: return 4;
			case DIAMOND_BLOCK: return 5;
			case EMERALD_BLOCK: return 6;
			case LAPIS_BLOCK: return 4;
			default: return 1;
		}
	}

	public boolean isFarmerPlaceable(Material block){
		switch(block){
			case DIRT: case GRASS: case SEEDS: case PUMPKIN_SEEDS: case MELON_SEEDS: case CACTUS: case CARROT:
			case POTATO: case SUGAR_CANE: case CROPS: return true;
			default: return false;
		}
	}
	
	public int getFarmerPlaceXP(Material block){
		switch(block){
			case SEEDS: case PUMPKIN_SEEDS: case MELON_SEEDS: case CACTUS: case CARROT: case POTATO: 
			case SUGAR_CANE: case CROPS: return 2;
			default: return 1;
		}
	}
	
	public boolean isInstaGrowable(Material block){
		switch(block){
			case CROPS: case PUMPKIN_STEM: case MELON_STEM: case CACTUS: case SUGAR_CANE_BLOCK: return true;
			default: return false;
		}
	}

	public boolean isFarmerDestroyable(Material block){
		switch(block){
			case SUGAR_CANE_BLOCK: case MELON_BLOCK: case WHEAT: case CACTUS: case CARROT: case POTATO:
			case CROPS: return true;
			default: return false;
		}
	}
	
	public int getFarmerDestroyXP(Material block){
		switch(block){
			case SUGAR_CANE_BLOCK: case MELON_BLOCK: case WHEAT: case CACTUS: case CARROT: case POTATO: 
			case CROPS: return 2;
			default: return 1;
		}
	}

	public boolean isPlaceable(Material block){
		switch(block){
			case SOIL: case BOAT: case MINECART: case FIREWORK: case DIRT: case GRASS: case SEEDS: case PUMPKIN_SEEDS: return false;
			case MELON_SEEDS: case CACTUS: case CARROT: case POTATO: case SUGAR_CANE: case CROPS: return false;
			default: return true;
		}
	}
	
}
