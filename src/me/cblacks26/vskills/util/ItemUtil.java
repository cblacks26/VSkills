package me.cblacks26.vskills.util;

import org.bukkit.inventory.ItemStack;

public class ItemUtil {

	public boolean isShovel(ItemStack item){
		switch (item.getType()){
            case DIAMOND_SPADE: return true;
            case GOLD_SPADE: return true;
            case IRON_SPADE: return true;
            case STONE_SPADE: return true;
            case WOOD_SPADE: return true;
		default:
			return false;
		}
	}
	
	public boolean isPickaxe(ItemStack item) {
        switch (item.getType()) {
            case DIAMOND_PICKAXE: return true;
            case GOLD_PICKAXE: return true;
            case IRON_PICKAXE: return true;
            case STONE_PICKAXE: return true;
            case WOOD_PICKAXE: return true;
        default:
                return false;
        }
	}
	
	public boolean isAxe(ItemStack item) {
        switch (item.getType()) {
            case DIAMOND_AXE: return true;
            case GOLD_AXE: return true;
            case IRON_AXE: return true;
            case STONE_AXE: return true;
            case WOOD_AXE: return true;
        default:
                return false;
        }
	}
	
	public boolean isHoe(ItemStack item) {
        switch (item.getType()) {
            case DIAMOND_HOE: return true;
            case GOLD_HOE: return true;
            case IRON_HOE: return true;
            case STONE_HOE: return true;
            case WOOD_HOE: return true;
        default:
                return false;
        }
	}
	
}
