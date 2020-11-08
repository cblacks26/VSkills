package com.github.vskills.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemUtil {

	public boolean isAxe(ItemStack item){
		switch(item.getType()){
			case GOLDEN_AXE: case DIAMOND_AXE: case IRON_AXE: case WOODEN_AXE: case STONE_AXE: return true;
			default: return false;
		}
	}
	
	public boolean isBow(ItemStack item){
		if(item.getType().equals(Material.BOW)){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean isHoe(ItemStack item) {
        switch (item.getType()) {
            case DIAMOND_HOE: return true;
            case GOLDEN_HOE: return true;
            case IRON_HOE: return true;
            case STONE_HOE: return true;
            case WOODEN_HOE: return true;
            default: return false;
        }
	}
	
	public boolean isPick(ItemStack item){
		switch(item.getType()){
			case GOLDEN_PICKAXE: case DIAMOND_PICKAXE: case IRON_PICKAXE: case WOODEN_PICKAXE: case STONE_PICKAXE: return true;
			default: return false;
		}
	}
	
	public boolean isShovel(ItemStack item){
		switch(item.getType()){
			case GOLDEN_SHOVEL: case DIAMOND_SHOVEL: case IRON_SHOVEL: case WOODEN_SHOVEL: case STONE_SHOVEL: return true;
			default: return false;
		}
	}
	
	public boolean isSword(ItemStack item){
		switch(item.getType()){
			case GOLDEN_SWORD: case DIAMOND_SWORD: case IRON_SWORD: case WOODEN_SWORD: case STONE_SWORD: return true;
			default: return false;
		}
	}

	public boolean isUnarmed(ItemStack item){
		switch(item.getType()){
			case AIR: return true;
			default: return false;
		}
	}
}
