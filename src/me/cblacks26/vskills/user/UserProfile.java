package me.cblacks26.vskills.user;

import java.util.HashMap;
import java.util.Map;

import me.cblacks26.vskills.util.Database;
import me.cblacks26.vskills.util.SkillType;
import me.cblacks26.vskills.util.Util;

import org.bukkit.entity.Player;

public class UserProfile {

	Util util = new Util();
	Database sql = new Database(null);
	
	private String pname;
	private int kills;
	private int deaths;
	private double kd;
	private int userRank;
	private Map<SkillType, Integer> userSkillsLvl;
	private Map<SkillType, Integer> userSkillsExp;
	
	
	public UserProfile(Player player){
		this.setKills(0);
		this.setDeaths(0);
		this.setKd(0);
		this.setUserRank(0);
		this.userSkillsLvl = new HashMap<SkillType, Integer>();
		this.userSkillsExp = new HashMap<SkillType, Integer>();
		this.pname = player.getName();
		for(SkillType skill : SkillType.values()){
			userSkillsLvl.put(skill, 0);
			userSkillsExp.put(skill, 0);
		}
	}
	
	public void loadUser(){
		for(SkillType skill : SkillType.values()){
			userSkillsLvl.put(skill, sql.getInt(pname, "LEVELS", skill.getName()));
		}
		for(SkillType skill : SkillType.values()){
			userSkillsExp.put(skill, sql.getInt(pname, "SKILLS", skill.getName()));
		}
		setKills(sql.getInt(pname, "STATS", "KILLS"));
		setDeaths(sql.getInt(pname, "STATS", "DEATHS"));
		setKd(sql.getDouble(pname, "STATS", "KD"));
		setUserRank(sql.getInt(pname, "LEVELS", "RANK"));
	}
	
	public void saveUser(){
		for(SkillType skill : SkillType.values()){
			int val = userSkillsLvl.get(skill);
			sql.setInt(pname, "LEVELS", skill.getName(), val);
		}
		for(SkillType skill : SkillType.values()){
			int val = userSkillsExp.get(skill);
			sql.setInt(pname, "SKILLS", skill.getName(), val);
		}
		
		sql.setInt(pname, "STATS", "KILLS", getKills());
		sql.setInt(pname, "STATS", "DEATHS", getDeaths());
		sql.setDouble(pname, "STATS", "KD", getKd());
		sql.setInt(pname, "LEVELS", "RANK", getUserRank());
		
	}

	public int getUserRank() {
		return userRank;
	}

	public void setUserRank(int userRank) {
		this.userRank = userRank;
	}

	public double getKd() {
		return kd;
	}

	public void setKd(double kd) {
		this.kd = kd;
	}

	public int getDeaths() {
		return deaths;
	}

	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}

	public int getKills() {
		return kills;
	}

	public void setKills(int kills) {
		this.kills = kills;
	}
	
}
