package com.github.vskills.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.github.vskills.Main;
import com.github.vskills.datatypes.JobType;
import com.github.vskills.datatypes.SkillType;
import com.github.vskills.events.JobLevelUpEvent;
import com.github.vskills.events.RankUpEvent;
import com.github.vskills.events.SkillLevelUpEvent;

public class Ranks {
	
	public void userRankUp(Player player){
		if(canRankUp(player) == true){
			int rank = Main.getUserManager().getRank(player);
			RankUpEvent event = new RankUpEvent(player, rank + 1);
			Bukkit.getServer().getPluginManager().callEvent(event);
		}
	}
	
	public void userLevelUp(Player player, JobType job){
		if(canLevelUp(player, job) == true){
			int lvl = Main.getUserManager().getJobLvl(player, job);
			JobLevelUpEvent event = new JobLevelUpEvent(player, job, lvl + 1);
			Bukkit.getServer().getPluginManager().callEvent(event);
			userRankUp(player);
		}
	}
	
	public void userLevelUp(Player player, SkillType skill){
		if(canLevelUp(player, skill) == true){
			int lvl = Main.getUserManager().getSkillLvl(player, skill);
			SkillLevelUpEvent event = new SkillLevelUpEvent(player, skill, lvl + 1);
			Bukkit.getServer().getPluginManager().callEvent(event);
			userRankUp(player);
		}
	}
	
	private boolean canLevelUp(Player player, SkillType skill){
		int level = Main.getUserManager().getSkillLvl(player, skill);
		int nextLevel = level + 1;
		double half = nextLevel / 2;
		int exp = Main.getUserManager().getSkillXP(player, skill);
		double expNeeded = 30 * nextLevel * half;
		int expNeed = (int)Math.round(expNeeded);
		if(exp >= expNeed){
			return true;
		}else{
			return false;
		}
	}
	
	private boolean canLevelUp(Player player, JobType job){
		int level = Main.getUserManager().getJobLvl(player, job);
		int nextLevel = level + 1;
		double half = nextLevel / 2;
		int exp = Main.getUserManager().getJobXP(player, job);
		double expNeeded = 30 * nextLevel * half;
		int expNeed = (int)Math.round(expNeeded);
		if(exp >= expNeed){
			return true;
		}else{
			return false;
		}
	}
	
	private boolean canRankUp(Player player){
		int cRank = Main.getUserManager().getRank(player);
		int nRank = cRank + 1;
		int lNeeded = nRank * 6;
		int dlevel = Main.getUserManager().getJobLvl(player, JobType.DIGGING);
		int mlevel = Main.getUserManager().getJobLvl(player, JobType.MINING);
		int flevel = Main.getUserManager().getJobLvl(player, JobType.FARMING);
		int hlevel = Main.getUserManager().getJobLvl(player, JobType.HUNTING);
		int blevel = Main.getUserManager().getJobLvl(player, JobType.BUILDING);
		int wclevel = Main.getUserManager().getJobLvl(player, JobType.WOODCUTTING);
		int levels = dlevel + mlevel + flevel + hlevel + blevel + wclevel;
		if(levels >= lNeeded){
			return true;
		}else{
			return false;
		}
	}
		
}
