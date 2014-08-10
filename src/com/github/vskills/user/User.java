package com.github.vskills.user;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import com.github.vskills.Main;
import com.github.vskills.datatypes.AbilityType;
import com.github.vskills.datatypes.SkillType;

public class User {

	private Connection c = null;
	private Statement s = null;
	private ResultSet rs = null;
	
	private UUID id;
	private boolean isgod;
	private double moneymultiplier;
	private int rank;
	private int tokens;
	private int kills;
	private int deaths;
	private int maxpower;
	private int currentpower;
	private AbilityType ability;
	private String sb;
	private Scoreboard xpboard = Bukkit.getScoreboardManager().getNewScoreboard();
	private Scoreboard lvlboard = Bukkit.getScoreboardManager().getNewScoreboard();
	private Scoreboard powerboard = Bukkit.getScoreboardManager().getNewScoreboard();
	private Scoreboard statsboard = Bukkit.getScoreboardManager().getNewScoreboard();
	private Objective xpobject = xpboard.registerNewObjective("XP to level", "dummy");
	private Objective lvlobject = lvlboard.registerNewObjective("levels", "dummy");
	private Objective statsobject = statsboard.registerNewObjective("Stats", "dummy");
	private Objective po = powerboard.registerNewObjective("Power", "dummy");
	private final HashMap<SkillType, Integer> xp = new HashMap<SkillType, Integer>();
	private final HashMap<SkillType, Integer> lvl = new HashMap<SkillType, Integer>();
	
	public User(UUID id){
		setScoreboard("level");
		setID(id);
		disableGod();
		setMoneyMultiplier(0.25);
		setRank(1);
		setTokens(0);
		setKills(0);
		setDeaths(0);
		setMaxPower(80);
		setCurrentPower(80);
		setAbility(null);
		for(SkillType s: SkillType.values()){
			setXp(s, 0);
			setLevel(s, 1);
		}
	}
	// Loads the User's data from the database
	public void loadUser(){
		try{
			Main.sql.open();
			c = Main.sql.getConnection();
			s = c.createStatement();
			rs = s.executeQuery("SELECT * FROM VSkills WHERE id='"+getID()+"'");
			rs.next();
			setMoneyMultiplier(rs.getDouble("money"));
			setKills(rs.getInt("kills"));
			setDeaths(rs.getInt("deaths"));
			setTokens(rs.getInt("tokens"));
			setRank(rs.getInt("rank"));
			setMaxPower(rs.getInt("power"));
			setCurrentPower(rs.getInt("cpower"));
			rs.close();
			rs = s.executeQuery("SELECT * FROM VSkills_xp WHERE id='"+getID()+"'");
			rs.next();
			for(SkillType s: SkillType.values()){
				setXp(s, rs.getInt(s.getSQLName()));
			}
			rs.close();
			rs = s.executeQuery("SELECT * FROM VSkills_lvl WHERE id='"+getID()+"'");
			rs.next();
			for(SkillType s: SkillType.values()){
				setLevel(s, rs.getInt(s.getSQLName()));
			}
			rs.close();
			s.close();
			c.close();
		}catch(SQLException e){
			Main.writeError("Error Loading User: " + e.getMessage());
		}
	}
	// Saves the User's data to the database
	public void saveUser(){
		try{
			Main.sql.open();
			c = Main.sql.getConnection();
			c.setAutoCommit(false);
			s = c.createStatement();
			String update = "UPDATE VSkills SET kills = " + getKills() + ", deaths = " + getDeaths() + ", tokens = " + getTokens() + 
					", money = " + getMoneyMultiplier() + ", rank = " + getRank() + ", power = " + getMaxPower() + ", cpower = " + getCurrentPower() + " WHERE id " + 
					"= '" + getID() + "'"; 
			StringBuilder updatexp = new StringBuilder();
			updatexp.append("UPDATE VSkills_xp SET ");
			for(SkillType s: SkillType.values()){
				if (updatexp.length() > 23) {
					updatexp.append(", ");
			    }
				updatexp.append(s.getSQLName());
				updatexp.append(" = ");
				updatexp.append(getXp(s));
			}
			updatexp.append(" WHERE id ='");
			updatexp.append(getID());
			updatexp.append("'");
			StringBuilder updatelvl = new StringBuilder();
			updatelvl.append("UPDATE VSkills_lvl SET ");
			for(SkillType s: SkillType.values()){
				if (updatelvl.length() > 24) {
					updatelvl.append(", ");
			    }
				updatelvl.append(s.getSQLName());
				updatelvl.append("=");
				updatelvl.append(getLevel(s));
			}
			updatelvl.append(" WHERE id='");
			updatelvl.append(getID());
			updatelvl.append("'");
			s.addBatch(update);
			s.addBatch(updatexp.toString());
			s.addBatch(updatelvl.toString());
			s.executeBatch();
			c.commit();
			s.close();
			c.close();
		}catch(SQLException e){
			Main.writeError("Error Saving User Profile: " + e.getMessage());
		}
	}
	// Inserts the User into the database
	public void createUser(){
		try{
        	Main.sql.open();
        	if(Main.sql.getConnection() == null){
        		Main.writeError("Not connected");
        	}
			c = Main.sql.getConnection();
			c.setAutoCommit(false);
			s = c.createStatement();
			s.addBatch("INSERT INTO VSkills (id, kills, deaths, tokens, money, rank, power, cpower) "
					+ "VALUES ('"+getID()+"',0,0,0,0.25,1,80,80)");
			s.addBatch("INSERT INTO VSkills_xp (id, acrobat, archery, axe, hoe, pickaxe, shovel, sword, unarmed) "
					+ "VALUES ('"+getID()+"',0,0,0,0,0,0,0,0)");
			s.addBatch("INSERT INTO VSkills_lvl (id, acrobat, archery, axe, hoe, pickaxe, shovel, sword, unarmed) "
					+ "VALUES ('"+getID()+"',1,1,1,1,1,1,1,1)");
			s.executeBatch();
			c.commit();
			s.close();
			c.close();
			Main.writeMessage("Created User: " + Bukkit.getPlayer(getID()).getName() + " With the id: " + getID());
		}catch(SQLException e){
			Main.writeError("Error Creating User: " + e.getMessage());
		}
	}
	// Checks if the User is in the database
	public boolean checkUser(){
		try{
        	Main.sql.open();
        	c = Main.sql.getConnection();
        	s = c.createStatement();
        	rs = s.executeQuery("SELECT * FROM VSkills WHERE id = '"+ getID() +"'");
        	if(rs.next()){
        		rs.close();
        		s.close();
        		c.close();
        		return true;
        	}else{
        		rs.close();
        		s.close();
        		c.close();
        		return false;
        	}
        }catch(SQLException e){
			Main.writeError("Error Checking User:" + e.getMessage());
			return false;
		}
	}
	// Resets the User's Stats
	public void resetUser(){
		if(Bukkit.getPlayer(getID()).isOnline()){
			setMoneyMultiplier(0.25);
			setRank(1);
			setTokens(0);
			setKills(0);
			setDeaths(0);
			for(SkillType s: SkillType.values()){
				setXp(s, 0);
				setLevel(s, 1);
			}
		}else{
			try{
				Main.sql.open();
				c = Main.sql.getConnection();
				c.setAutoCommit(false);
				s = c.createStatement();
				String update = "UPDATE VSkills SET kills=0,deaths=0,tokens=0,money=0,rank=1 WHERE id='"+getID()+"'";
				StringBuilder updatexp = new StringBuilder();
				updatexp.append("UPDATE VSkills_xp SET ");
				for(SkillType s: SkillType.values()){
					if (updatexp.length() > 1) {
						updatexp.append(",");
				    }
					updatexp.append(s.getSQLName());
					updatexp.append("=");
					updatexp.append(getXp(s));
				}
				updatexp.append(" WHERE id='");
				updatexp.append(getID());
				updatexp.append("'");
				StringBuilder updatelvl = new StringBuilder();
				updatelvl.append("UPDATE VSkills_lvl SET ");
				for(SkillType s: SkillType.values()){
					if (updatelvl.length() > 1) {
						updatelvl.append(",");
				    }
					updatelvl.append(s.getSQLName());
					updatelvl.append("=");
					updatelvl.append(getLevel(s));
				}
				updatelvl.append(" WHERE id='");
				updatelvl.append(getID());
				updatelvl.append("'");
				s.addBatch(update);
				s.addBatch(updatexp.toString());
				s.addBatch(updatelvl.toString());
				s.executeBatch();
				c.commit();
				s.close();
				c.close();
			}catch(SQLException e){
				Main.writeError("Error Reseting User: " + e.getMessage());
			}
		}
	}
	// Returns the User's UUID
	public UUID getID() {
		return id;
	}
	// Sets the User's UUID
	public void setID(UUID id) {
		this.id = id;
	}
	
	public boolean isGod() {
		return isgod;
	}

	public void enableGod() {
		this.isgod = true;
	}
	
	public void disableGod() {
		this.isgod = false;
	}
	
	public boolean toggleGod(){
		if(isGod()){
			disableGod();
			return false;
		}else{
			enableGod();
			return true;
		}
	}

	public double getMoneyMultiplier() {
		return moneymultiplier;
	}

	public void setMoneyMultiplier(double moneymultiplier) {
		this.moneymultiplier = moneymultiplier;
	}
	
	public void addMoneyMultiplier() {
		this.moneymultiplier = getMoneyMultiplier() + 0.01;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}
	
	public void addRank() {
		this.rank += 1;
	}

	public int getTokens() {
		return tokens;
	}

	public void setTokens(int tokens) {
		this.tokens = tokens;
	}
	
	public void addToken() {
		this.tokens += 1;
	}
	
	public void subtractTokens(int tokens) {
		this.tokens = getTokens() - tokens;
	}

	public int getKills() {
		return kills;
	}

	public void setKills(int kills) {
		this.kills = kills;
	}
	
	public void addKill() {
		this.kills += 1;
	}

	public int getDeaths() {
		return deaths;
	}

	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}
	
	public void addDeath() {
		this.deaths += 1;
	}
	
	public int getMaxPower() {
		return maxpower;
	}

	public void setMaxPower(int maxpower) {
		this.maxpower = maxpower;
	}
	
	public void addMaxPower(int power) {
		int max = getMaxPower();
		setMaxPower(max+power);
	}

	public int getCurrentPower() {
		return currentpower;
	}

	public void setCurrentPower(int currentpower) {
		this.currentpower = currentpower;
	}
	
	public void addCurrentPower(int power) {
		int current = getCurrentPower();
		setCurrentPower(current+power);
	}
	
	public void subtractCurrentPower(int power){
		int current = getCurrentPower();
		setCurrentPower(current-power);
	}
	
	public void refreshPower(){
		setCurrentPower(getMaxPower());
	}
	
	public void unToggle(){
		setAbility(null);
	}
	
	public void setAbility(AbilityType a){
		this.ability = a;
	}
	// Returns the Toggled Ability
	public AbilityType getAbility(){
		return ability;
	}
	// Returns the User's specific skill experience
	public int getXp(SkillType skill){
		return xp.get(skill);
	}
	// Returns the User's specific skill level
	public int getLevel(SkillType skill){
		return lvl.get(skill);
	}
	// Sets the User's specific skill experience
	public void setXp(SkillType s, int x){
		xp.put(s, x);
	}
	// Sets the User's specific skill level
	public void setLevel(SkillType s, int x){
		lvl.put(s, x);
	}
	// Adds experience to the User's specific skill
	public void addXp(SkillType s, int x){
		int c = getXp(s);
		xp.put(s, c+x);
	}
	// Adds a level to the User's specific skill
	public void addLevel(SkillType s){
		int c = getLevel(s);
		lvl.put(s, c+1);
	}
	// Returns the User's Kill/Death Ratio
	public int getKD(){
		int k = getKills();
		int d = getDeaths();
		if(d == 0){
			if(k == 0){
				int kd = 0;
				return kd;
			}else{
				int kd = k/1;
				return kd;
			}
		}else{
			int kd = k/d;
			return kd;
		}
	}
	
	public int getXPToLevel(SkillType skill){
		int level = getLevel(skill);
		int xp = getXp(skill);
		int nextLevel = level + 1;
		double half = nextLevel / 2;
		double expNeeded = 30 * nextLevel * half;
		int expNeed = (int)Math.round(expNeeded);
		int exp = expNeed - xp;
		return exp;
	}
	
	public String getScoreboard(){
		return sb;
	}
	
	public void setScoreboard(String sb){
		this.sb = sb;
	}
	
	public void scoreboard(){
		Player player = Bukkit.getPlayer(getID());		
		if(getScoreboard() == "xp"){
			xpobject.setDisplaySlot(DisplaySlot.SIDEBAR);
			xpobject.setDisplayName("XP to Level");
			Score acrobatxp = xpobject.getScore(ChatColor.GREEN + "Acrobat: ");
			Score archerxp = xpobject.getScore(ChatColor.GREEN + "Archery: ");
			Score axexp = xpobject.getScore(ChatColor.GREEN + "Axe: ");
			Score hoexp = xpobject.getScore(ChatColor.GREEN + "Hoe: ");
			Score pickaxexp = xpobject.getScore(ChatColor.GREEN + "Pickaxe: ");
			Score shovelxp = xpobject.getScore(ChatColor.GREEN + "Shovel: ");
			Score swordxp = xpobject.getScore(ChatColor.GREEN + "Sword: ");
			Score unarmedxp = xpobject.getScore(ChatColor.GREEN + "Unarmed: ");
			acrobatxp.setScore(getXPToLevel(SkillType.ACROBATICS));
			archerxp.setScore(getXPToLevel(SkillType.ARCHERY));
			axexp.setScore(getXPToLevel(SkillType.AXE));
			hoexp.setScore(getXPToLevel(SkillType.HOE));
			pickaxexp.setScore(getXPToLevel(SkillType.PICKAXE));
			shovelxp.setScore(getXPToLevel(SkillType.SHOVEL));
			swordxp.setScore(getXPToLevel(SkillType.SWORD));
			unarmedxp.setScore(getXPToLevel(SkillType.UNARMED));
			player.setScoreboard(xpboard);
		}else if(getScoreboard() == "level"){
			lvlobject.setDisplaySlot(DisplaySlot.SIDEBAR);
			lvlobject.setDisplayName("Skill Levels");
			Score acrobatlvl = lvlobject.getScore(ChatColor.GREEN + "Acrobat: ");
			Score archerlvl = lvlobject.getScore(ChatColor.GREEN + "Archery: ");
			Score axelvl = lvlobject.getScore(ChatColor.GREEN + "Axe: ");
			Score hoelvl = lvlobject.getScore(ChatColor.GREEN + "Hoe: ");
			Score pickaxelvl = lvlobject.getScore(ChatColor.GREEN + "Pickaxe: ");
			Score shovellvl = lvlobject.getScore(ChatColor.GREEN + "Shovel: ");
			Score swordlvl = lvlobject.getScore(ChatColor.GREEN + "Sword: ");
			Score unarmedlvl = lvlobject.getScore(ChatColor.GREEN + "Unarmed: ");
			acrobatlvl.setScore(getLevel(SkillType.ACROBATICS));
			archerlvl.setScore(getLevel(SkillType.ARCHERY));
			axelvl.setScore(getLevel(SkillType.AXE));
			hoelvl.setScore(getLevel(SkillType.HOE));
			pickaxelvl.setScore(getLevel(SkillType.PICKAXE));
			shovellvl.setScore(getLevel(SkillType.SHOVEL));
			swordlvl.setScore(getLevel(SkillType.SWORD));
			unarmedlvl.setScore(getLevel(SkillType.UNARMED));
			player.setScoreboard(lvlboard);
		}else if(getScoreboard() == "stats"){
			statsobject.setDisplaySlot(DisplaySlot.SIDEBAR);
			statsobject.setDisplayName("Stats");
			Score kill = statsobject.getScore(ChatColor.GREEN + "Kills: ");
			Score death = statsobject.getScore(ChatColor.GREEN + "Deaths: ");
			Score kd = statsobject.getScore(ChatColor.GREEN + "KD Ratio: ");
			Score rank = statsobject.getScore(ChatColor.GREEN + "Rank: ");
			Score token = statsobject.getScore(ChatColor.GREEN + "Tokens: ");
			kill.setScore(getKills());
			death.setScore(getDeaths());
			kd.setScore(getKD());
			rank.setScore(getRank());
			token.setScore(getTokens());
			player.setScoreboard(statsboard);
		}else if(getScoreboard() == "power"){
			po.setDisplaySlot(DisplaySlot.SIDEBAR);
			po.setDisplayName("Power");
			Score p = po.getScore(ChatColor.GREEN + "MaxPower: ");
			Score cp = po.getScore(ChatColor.GREEN + "CurrentPower: ");
			Score ab = po.getScore(ChatColor.GREEN + "Ability Cost: ");
			for(AbilityType a: AbilityType.values()){
				Score abil = po.getScore(ChatColor.GREEN + a.getName() + ":");
				abil.setScore(0);
				if(getAbility() == a){
					abil.setScore(1);
				}
			}
			if(getAbility() != null){
				ab.setScore(getAbility().getCost());
			}else{
				ab.setScore(0);
			}
			p.setScore(getMaxPower());
			cp.setScore(getCurrentPower());
			player.setScoreboard(powerboard);
		}else{
			return;
		}
	}	
}