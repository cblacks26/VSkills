package me.cblacks26.vskills.util;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class Ranks {

	Database sql = new Database(null);
	Util util = new Util();
	
	public void xpGain(Player player, SkillType skill, int exp){
		util.updateUserExp(player, skill, exp);
		userLevelUp(player, skill);
	}
	
	public void userRankUp(Player player){
		if(checkUserCanRankUp(player) == true){
			util.updateUserRank(player);
			util.addUserTokens(player);
			player.sendMessage("Rank Up! Current Rank: "  + sql.getInt(player.getName(), "LEVELS", "RANK"));
		}
	}
	
	public void userLevelUp(Player player, SkillType skill){
		if(checkUserCanLevelUp(player, skill) == true){
			util.updateUserLevel(player, skill, 1);
			player.sendMessage(ChatColor.GREEN + skill.getName() + " Level Up. Current Level: "  + sql.getInt(player.getName(), "LEVELS", skill.getName()));
			userRankUp(player);
		}
	}
	
	public boolean checkUserCanLevelUp(Player player, SkillType skill){
		int level = sql.getInt(player.getName(), "LEVELS", skill.getName());
		int nextLevel = level + 1;
		int oldHalf = level / 2;
		double half = nextLevel / 2;
		int exp = sql.getInt(player.getName(), "SKILLS", skill.getName());
		double oldExpNeeded = 30 * level * oldHalf;
		double expNeeded = 30 * nextLevel * half + oldExpNeeded;
		int expNeed = (int)Math.round(expNeeded);
		if(exp >= expNeed){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean checkUserCanRankUp(Player player){
		int cRank = getRank(player);
		int nRank = cRank + 1;
		int lNeeded = nRank * 6;
		int dlevel = getLevel(player, SkillType.DIGGING);
		int mlevel = getLevel(player, SkillType.MINING);
		int flevel = getLevel(player, SkillType.FARMING);
		int hlevel = getLevel(player, SkillType.HUNTING);
		int blevel = getLevel(player, SkillType.BUILDING);
		int clevel = getLevel(player, SkillType.LOGGING);
		int levels = dlevel + mlevel + flevel + hlevel + blevel + clevel;
		if(levels >= lNeeded){
			return true;
		}else{
			return false;
		}
	}
	
	public int getExpToLevel(OfflinePlayer player, SkillType skill){
		int level = getLevel(player, skill);
		int nextLevel = level + 1;
		int oldHalf = level / 2;
		double half = nextLevel / 2;
		int exp = sql.getInt(player.getName(), "SKILLS", skill.getName());
		double oldExpNeeded = 30 * level * oldHalf;
		double expNeeded = 30 * nextLevel * half + oldExpNeeded;
		int expNeed = (int)Math.round(expNeeded);
		return expNeed - exp;
	}
	
	public int getLevel(OfflinePlayer player, SkillType skill){
		int level = sql.getInt(player.getName(), "LEVELS", skill.getName());
		return level;
	}
	
	public int getRank(OfflinePlayer player){
		int rank = sql.getInt(player.getName(), "LEVELS", "RANK");
		return rank;
	}
	
}
