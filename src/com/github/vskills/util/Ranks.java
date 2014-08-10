package com.github.vskills.util;

import java.util.UUID;

import org.bukkit.Bukkit;

import com.github.vskills.Main;
import com.github.vskills.datatypes.SkillType;
import com.github.vskills.events.RankUpEvent;
import com.github.vskills.events.SkillLevelUpEvent;
import com.github.vskills.user.User;

public class Ranks {
	
	public void userRankUp(UUID id){
		if(canRankUp(id) == true){
			int rank = Main.getUserManager().getUser(id).getRank();
			RankUpEvent event = new RankUpEvent(id, rank + 1);
			Bukkit.getServer().getPluginManager().callEvent(event);
		}
	}
	public void userLevelUp(UUID id, SkillType skill){
		if(canLevelUp(id, skill) == true){
			int lvl = Main.getUserManager().getUser(id).getLevel(skill);
			SkillLevelUpEvent event = new SkillLevelUpEvent(id, skill, lvl + 1);
			Bukkit.getServer().getPluginManager().callEvent(event);
			userRankUp(id);
		}
	}
	
	private boolean canLevelUp(UUID id, SkillType skill){
		int level = Main.getUserManager().getUser(id).getLevel(skill);
		int nextLevel = level + 1;
		double half = nextLevel / 2;
		int exp = Main.getUserManager().getUser(id).getXp(skill);
		double expNeeded = 30 * nextLevel * half;
		int expNeed = (int)Math.round(expNeeded);
		if(exp >= expNeed){
			return true;
		}else{
			return false;
		}
	}
	
	private boolean canRankUp(UUID id){
		User user = Main.getUserManager().getUser(id);
		int cRank = user.getRank();
		cRank++;
		int n = 0;
		int levels = 0;
		for(SkillType s: SkillType.values()){
			n++;
			levels = levels + user.getLevel(s);
		}
		int needed = cRank * n;
		if(levels >= needed){
			return true;
		}else{
			return false;
		}
	}
		
}
