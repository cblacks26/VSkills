package me.cblacks26.vskills.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class Util{
	
	Database sql = new Database(null);
	
	Connection c = null;
	Statement s = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	public void setUserTokens(Player player, int tokens){
		sql.write("UPDATE UNLOCKS SET TOKENS = " + tokens + " WHERE PLAYERNAME = ?", player.getName());
	}
	
	public void addUserTokens(Player player){
		int val = sql.getInt(player.getName(), "UNLOCKS", "TOKENS");
		int tokens = val + 1;
		sql.write("UPDATE UNLOCKS SET TOKENS = " + tokens + " WHERE PLAYERNAME = ?", player.getName());
	}
	
	public int getUserTokens(Player player){
		int tokens = sql.getInt(player.getName(), "UNLOCKS", "TOKENS");
		return tokens;
	}
	
	public void updateUserExp(Player player, SkillType skill, int exp){
		int xp = sql.getInt(player.getName(), "SKILLS", skill.getName());
		setUserExp(player, skill, xp + exp);
	}
	
	public void updateUserRank(Player player){
		String pname = player.getName();
		int cRank = sql.getInt(player.getName(), "LEVELS", "RANK");
		sql.write("UPDATE LEVELS SET RANK = ? WHERE PLAYERNAME = ?", cRank + 1, pname);		
	}
	
	public void updateUserLevel(Player player, SkillType skill, int val){
		String pname = player.getName();
		int level = sql.getInt(pname, "LEVELS", skill.getName());
		sql.write("UPDATE LEVELS SET " + skill.getName() + " = ? WHERE PLAYERNAME = ?", level + val, pname);
	}
		
	public void setUserExp(OfflinePlayer player, SkillType skill, int val){
		String pname = player.getName();
		sql.write("UPDATE SKILLS SET " + skill.getName() + " = ? WHERE PLAYERNAME = ?", val, pname);
	}
	
	public void setUserLevel(OfflinePlayer player, SkillType skill, int val){
		int half = val / 2;
		setUserExp(player, skill, 30 * val * half);
		sql.write("UPDATE LEVELS SET " + skill.getName() + " = ? WHERE PLAYERNAME = ?", val, player.getName());
	}
	
	public void setUserRank(OfflinePlayer player, int val){
		SkillType[] skills = {SkillType.BUILDING,SkillType.DIGGING,SkillType.FARMING,SkillType.HUNTING,SkillType.LOGGING,SkillType.MINING};
		for(SkillType skill: skills){
			setUserLevel(player, skill, val);
		}
		sql.write("UPDATE LEVELS SET RANK = ? WHERE PLAYERNAME = ?", val, player.getName());
	}
	
	//Adds the Users Kills or Deaths
	public void addUserStats(String Player, String type, int val){
		int stats = sql.getInt(Player, "STATS", type);
		sql.setInt(Player, "STATS", type, stats + val);
	}
	
	//Sets the Users K/D Ratio
	public void setUserKD(String Player){
		int kills = sql.getInt(Player, "STATS", "KILLS");
		int deaths = sql.getInt(Player, "STATS", "DEATHS");
		if (deaths == 0){
			sql.setDouble(Player, "STATS", "KD", kills);
		}else{
			sql.setDouble(Player, "STATS", "KD", kills / deaths);
		}
	}
	
	public void setUserStats(Player player, int k, int d, double kd){
		sql.write("UPDATE STATS SET KILLS = " + k + " DEATHS = " + d + " KD = " + kd + " WHERE PLAYERNAME = ?", player.getName());
	}
	
	public boolean checkUserTable(String player, String table){
		try{
			c = sql.getConnection();
			s = c.createStatement();
			rs = s.executeQuery("SELECT * FROM " + table + " WHERE PLAYERNAME = '" + player + "';");
			if(rs.next() == true){
				return true;
			}else{
				return false;
			}
		}catch(SQLException e){
			return false;
		}finally{
			sql.close(rs);
			sql.close(s);
			sql.close(c);
		}
	}
	
	public SkillType matchSkill(String skill){
		if(skill == "BUILDING"){
			return SkillType.BUILDING;
		}else if(skill == "DIGGING"){
			return SkillType.DIGGING;
		}else if(skill == "FARMING"){
			return SkillType.FARMING;
		}else if(skill == "HUNTING"){
			return SkillType.HUNTING;
		}else if(skill == "LOGGING"){
			return SkillType.LOGGING;
		}else if(skill == "MINING"){
			return SkillType.MINING;
		}else{
			return null;
		}
	}
}
