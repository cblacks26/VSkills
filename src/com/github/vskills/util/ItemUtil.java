package com.github.vskills.util;

import org.bukkit.inventory.ItemStack;

public class ItemUtil {

	public boolean isAxe(ItemStack item){
		switch(item.getType()){
			case GOLD_AXE: case DIAMOND_AXE: case IRON_AXE: case WOOD_AXE: case STONE_AXE: return true;
			default: return false;
		}
	}
	
	public boolean isHoe(ItemStack item) {
        switch (item.getType()) {
            case DIAMOND_HOE: return true;
            case GOLD_HOE: return true;
            case IRON_HOE: return true;
            case STONE_HOE: return true;
            case WOOD_HOE: return true;
            default: return false;
        }
	}
	
	public boolean isPick(ItemStack item){
		switch(item.getType()){
			case GOLD_PICKAXE: case DIAMOND_PICKAXE: case IRON_PICKAXE: case WOOD_PICKAXE: case STONE_PICKAXE: return true;
			default: return false;
		}
	}
	
	public boolean isShovel(ItemStack item){
		switch(item.getType()){
			case GOLD_SPADE: case DIAMOND_SPADE: case IRON_SPADE: case WOOD_SPADE: case STONE_SPADE: return true;
			default: return false;
		}
	}
	
	public boolean isSword(ItemStack item){
		switch(item.getType()){
			case GOLD_SWORD: case DIAMOND_SWORD: case IRON_SWORD: case WOOD_SWORD: case STONE_SWORD: return true;
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
