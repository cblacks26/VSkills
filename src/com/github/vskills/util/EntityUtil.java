package com.github.vskills.util;

import org.bukkit.entity.EntityType;

public class EntityUtil {

	public int getEntityXP(EntityType entity){
		switch(entity){
			case BAT: return 1;
			case BLAZE: return 5;
			case CAVE_SPIDER: return 3;
			case CHICKEN: return 1;
			case COW: return 2;
			case CREEPER: return 4;
			case DOLPHIN: return 2;
			case DONKEY: return 2;
			case DROWNED: return 3;
			case ELDER_GUARDIAN: return 10;
			case ENDER_DRAGON: return 20;
			case ENDERMAN: return 5;
			case EVOKER: return 5;
			case FOX: return 2;
			case GHAST: return 10;
			case GIANT: return 20;
			case GUARDIAN: return 8;
			case HOGLIN: return 2;
			case HORSE: return 2;
			case HUSK: return 3;
			case ILLUSIONER: return 4;
			case IRON_GOLEM: return 6;
			case LLAMA: return 2;
			case MAGMA_CUBE: return 2;
			case MUSHROOM_COW: return 2;
			case MULE: return 2;
			case OCELOT: return 4;
			case PANDA: return 2;
			case PARROT: return 1;
			case PHANTOM: return 4;
			case PIG: return 2;
			case PIGLIN: return 3;
			case PIGLIN_BRUTE: return 4;
			case PILLAGER: return 4;
			case POLAR_BEAR: return 2;
			case PLAYER: return 10;
			case PUFFERFISH: return 2;
			case RABBIT: return 1;
			case RAVAGER: return 4;
			case SHEEP: return 1;
			case SKELETON: return 3;
			case SKELETON_HORSE: return 2;
			case SLIME: return 2;
			case SNOWMAN: return 2;
			case SPIDER: return 3;
			case SQUID: return 2;
			case STRAY: return 3;
			case STRIDER: return 2;
			case TRADER_LLAMA: return 2;
			case TURTLE: return 2;
			case VEX: return 3;
			case VILLAGER: return 2;
			case VINDICATOR: return 4;
			case WANDERING_TRADER: return 2;
			case WITCH: return 3;
			case WITHER: return 10;
			case WITHER_SKELETON: return 3;
			case WOLF: return 3;
			case ZOGLIN: return 3;
			case ZOMBIE: return 3;
			case ZOMBIE_HORSE: return 2;
			case ZOMBIFIED_PIGLIN: return 3;
			case ZOMBIE_VILLAGER: return 3;
			default: return 1;
		}
	}
	
}
