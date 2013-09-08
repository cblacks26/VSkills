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
			case ENDER_DRAGON: return 20;
			case ENDERMAN: return 5;
			case GHAST: return 10;
			case GIANT: return 20;
			case IRON_GOLEM: return 4;
			case MUSHROOM_COW: return 2;
			case OCELOT: return 4;
			case PIG: return 2;
			case PIG_ZOMBIE: return 2;
			case PLAYER: return 10;
			case SHEEP: return 1;
			case SKELETON: return 4;
			case SLIME: return 3;
			case SPIDER: return 3;
			case SQUID: return 2;
			case WITCH: return 3;
			case WITHER: return 3;
			case WOLF: return 3;
			case ZOMBIE: return 3;
			default: return 1;
		}
	}
	
}
