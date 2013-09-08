package com.github.vskills.util;

import org.bukkit.entity.Player;

import com.github.vskills.Main;
import com.github.vskills.datatypes.SkillType;

public class DamageUtil {

	UserManager userManager = Main.getUserManager();
	
	public Double getDamage(Player player, SkillType skill, Double damage){
		int lvl = userManager.getSkillLvl(player, skill);
		double dm = damage + (lvl * .05);
		return dm;
	}
}
