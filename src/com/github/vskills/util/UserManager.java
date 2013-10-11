package com.github.vskills.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import com.github.vskills.Main;
import com.github.vskills.datatypes.JobType;
import com.github.vskills.datatypes.SkillType;

public class UserManager {

	private Connection c = null;
	private Statement s = null;
	private ResultSet rs = null;
	
	private final HashMap<String, Boolean> god = new HashMap<String, Boolean>();
	private final HashMap<String, String> sb = new HashMap<String, String>();
	private final HashMap<String, Double> money = new HashMap<String, Double>();
	private final HashMap<String, Integer> ranks = new HashMap<String, Integer>();
	private final HashMap<String, Integer> tokens = new HashMap<String, Integer>();
	private final HashMap<String, Integer> kills = new HashMap<String, Integer>();
	private final HashMap<String, Integer> deaths = new HashMap<String, Integer>();
	private final HashMap<String, Integer> digger = new HashMap<String, Integer>();
	private final HashMap<String, Integer> miner = new HashMap<String, Integer>();
	private final HashMap<String, Integer> hunter = new HashMap<String, Integer>();
	private final HashMap<String, Integer> farmer = new HashMap<String, Integer>();
	private final HashMap<String, Integer> builder = new HashMap<String, Integer>();
	private final HashMap<String, Integer> woodcutter = new HashMap<String, Integer>();
	
	private final HashMap<String, Integer> acrobat = new HashMap<String, Integer>();
	private final HashMap<String, Integer> archery = new HashMap<String, Integer>();
	private final HashMap<String, Integer> axe = new HashMap<String, Integer>();
	private final HashMap<String, Integer> hoe = new HashMap<String, Integer>();
	private final HashMap<String, Integer> pickaxe = new HashMap<String, Integer>();
	private final HashMap<String, Integer> shovel = new HashMap<String, Integer>();
	private final HashMap<String, Integer> sword = new HashMap<String, Integer>();
	private final HashMap<String, Integer> unarmed = new HashMap<String, Integer>();
	
	private final HashMap<String, Integer> diggerlvl = new HashMap<String, Integer>();
	private final HashMap<String, Integer> minerlvl = new HashMap<String, Integer>();
	private final HashMap<String, Integer> hunterlvl = new HashMap<String, Integer>();
	private final HashMap<String, Integer> farmerlvl = new HashMap<String, Integer>();
	private final HashMap<String, Integer> builderlvl = new HashMap<String, Integer>();
	private final HashMap<String, Integer> woodcutterlvl = new HashMap<String, Integer>();
	
	private final HashMap<String, Integer> acrobatlvl = new HashMap<String, Integer> ();
	private final HashMap<String, Integer> archerylvl = new HashMap<String, Integer>();
	private final HashMap<String, Integer> axelvl = new HashMap<String, Integer>();
	private final HashMap<String, Integer> hoelvl = new HashMap<String, Integer>();
	private final HashMap<String, Integer> pickaxelvl = new HashMap<String, Integer>();
	private final HashMap<String, Integer> shovellvl = new HashMap<String, Integer>();
	private final HashMap<String, Integer> swordlvl = new HashMap<String, Integer>();
	private final HashMap<String, Integer> unarmedlvl = new HashMap<String, Integer>();
	
    public Scoreboard jobsboard;
    public Scoreboard skillsboard;
    public Scoreboard statsboard;
    public Scoreboard jobexpboard;
    public Scoreboard skillexpboard;
    public Scoreboard powerboard;
    private Objective powers;
    private Objective skills;
	private Objective jobs;
	private Objective stats;
	private Objective jobsexp;
	private Objective skillsexp;
		
	public void addUser(Player player){
		String pname = player.getName();
		sb.put(pname, "jobs");
		try{
			Main.sql.open();
			c = Main.sql.getConnection();
			s = c.createStatement();
			rs = s.executeQuery("SELECT * FROM VSkills WHERE name = '" + pname + "'");
			rs.next();
			money.put(pname, rs.getDouble("money"));
			kills.put(pname, rs.getInt("kills"));
			deaths.put(pname, rs.getInt("deaths"));
			tokens.put(pname, rs.getInt("tokens"));
			ranks.put(pname, rs.getInt("rank"));
			rs.close();
			s.close();
			s = c.createStatement();
			rs = s.executeQuery("SELECT * FROM VSkills_xp WHERE name = '" + pname + "'");
			rs.next();
			acrobat.put(pname, rs.getInt("acrobat")); 
			archery.put(pname, rs.getInt("archery"));
			axe.put(pname, rs.getInt("axe"));
			hoe.put(pname, rs.getInt("hoe"));
			pickaxe.put(pname, rs.getInt("pickaxe"));
			shovel.put(pname, rs.getInt("shovel"));
			sword.put(pname, rs.getInt("sword"));
			unarmed.put(pname, rs.getInt("unarmed"));
			builder.put(pname, rs.getInt("builder"));
			digger.put(pname, rs.getInt("digger"));
			farmer.put(pname, rs.getInt("farmer"));
			hunter.put(pname, rs.getInt("hunter"));
			miner.put(pname, rs.getInt("miner"));
			woodcutter.put(pname, rs.getInt("woodcutter"));
			rs.close();
			s.close();
			s = c.createStatement();
			rs = s.executeQuery("SELECT * FROM VSkills_levels WHERE name = '" + pname + "'");
			rs.next();
			acrobatlvl.put(pname, rs.getInt("acrobat"));
			archerylvl.put(pname, rs.getInt("archery"));
			axelvl.put(pname, rs.getInt("axe"));
			hoelvl.put(pname, rs.getInt("hoe"));
			pickaxelvl.put(pname, rs.getInt("pickaxe"));
			shovellvl.put(pname, rs.getInt("shovel"));
			swordlvl.put(pname, rs.getInt("sword"));
			unarmedlvl.put(pname, rs.getInt("unarmed"));
			builderlvl.put(pname, rs.getInt("builder"));
			diggerlvl.put(pname, rs.getInt("digger"));
			farmerlvl.put(pname, rs.getInt("farmer"));
			hunterlvl.put(pname, rs.getInt("hunter"));
			minerlvl.put(pname, rs.getInt("miner"));
			woodcutterlvl.put(pname, rs.getInt("woodcutter"));
			rs.close();
			s.close();
			c.close();
		}catch(SQLException e){
			Main.writeError("Error Loading User Data: " + e.getMessage());
		}
	}
	
	public void addUsers(){
		for(Player player: Bukkit.getServer().getOnlinePlayers()){
			addUser(player);
		}
	}
	
	public void removeUser(Player player){
		String pname = player.getName();
		sb.remove(pname);
		money.remove(pname);
		ranks.remove(pname);
		tokens.remove(pname);
		kills.remove(pname);
		deaths.remove(pname);
		digger.remove(pname);
		builder.remove(pname);
		hunter.remove(pname);
		farmer.remove(pname);
		miner.remove(pname);
		woodcutter.remove(pname);
		acrobat.remove(pname);
		archery.remove(pname);
		axe.remove(pname);
		hoe.remove(pname);
		pickaxe.remove(pname);
		shovel.remove(pname);
		sword.remove(pname);
		unarmed.remove(pname);
		diggerlvl.remove(pname);
		builderlvl.remove(pname);
		hunterlvl.remove(pname);
		farmerlvl.remove(pname);
		minerlvl.remove(pname);
		woodcutterlvl.remove(pname);
		acrobatlvl.remove(pname);
		archerylvl.remove(pname);
		axelvl.remove(pname);
		hoelvl.remove(pname);
		pickaxelvl.remove(pname);
		shovellvl.remove(pname);
		swordlvl.remove(pname);
		unarmedlvl.remove(pname);
	}
	
	public void saveUser(Player player){
		String pname = player.getName();
		int power = AbilitiesManager.getPlayerMaxPower(player);
		int cpower = AbilitiesManager.getPlayerCurrentPower(player);
		try{
			Main.sql.open();
			c = Main.sql.getConnection();
			c.setAutoCommit(false);
			s = c.createStatement();
			String update = "UPDATE VSkills SET kills = " + kills.get(pname) + ", deaths = " + deaths.get(pname) + ", tokens = " + tokens.get(pname) + 
					", money = " + money.get(pname) + ", rank = " + ranks.get(pname) + ", power = " + power + ", cpower = " + cpower + " WHERE name " + 
					"= '" + pname + "'"; 					
			String updatexp = "UPDATE VSkills_xp SET acrobat = " + acrobat.get(pname) + ", archery = " + archery.get(pname) + ", axe = " + axe.get(pname) + ", hoe = " + hoe.get(pname) + ", pickaxe = " +
					pickaxe.get(pname) + ", shovel = " + shovel.get(pname) + ", sword = " + sword.get(pname) + ", unarmed = " + unarmed.get(pname) + ", builder = " +
					builder.get(pname) + ", digger = " + digger.get(pname) + ", farmer = " + farmer.get(pname) + ", hunter = " + hunter.get(pname) + 
					", miner = " + miner.get(pname) + ", woodcutter = " + woodcutter.get(pname) + " WHERE name = '" + pname + "'";
			String updatelvl = "UPDATE VSkills_levels SET acrobat = " + acrobatlvl.get(pname) + ", archery = " + archerylvl.get(pname) + ", axe = " + axelvl.get(pname) + ", hoe = " + hoelvl.get(pname) + ", pickaxe = " +
					pickaxelvl.get(pname) + ", shovel = " + shovellvl.get(pname) + ", sword = " + swordlvl.get(pname) + ", unarmed = " + unarmedlvl.get(pname) + ", builder = " +
					builderlvl.get(pname) + ", digger = " + diggerlvl.get(pname) + ", farmer = " + farmerlvl.get(pname) + ", hunter = " + hunterlvl.get(pname) + 
					", miner = " + minerlvl.get(pname) + ", woodcutter = " + woodcutterlvl.get(pname) + " WHERE name = '" + pname + "'";
			s.addBatch(update);
			s.addBatch(updatexp);
			s.addBatch(updatelvl);
			s.executeBatch();
			c.commit();
			s.close();
			c.close();
		}catch(SQLException e){
			Main.writeError("Error Saving User Profile: " + e.getMessage());
		}
	}
	
	public void saveUsers(){
		for(Player player: Bukkit.getOnlinePlayers()){
			saveUser(player);
		}
	}

	public void createUser(Player player){
		String pname = player.getName();
		try{
        	Main.sql.open();
			c = Main.sql.getConnection();
			c.setAutoCommit(false);
			s = c.createStatement();
			s.addBatch("INSERT INTO VSkills (name, kills, deaths, tokens, money, rank, power, cpower) "
					+ "VALUES ('" + pname + "',0,0,0,0.25,1,80,80)");
			s.addBatch("INSERT INTO VSkills_xp (name, acrobat, archery, axe, hoe, pickaxe, shovel, sword, unarmed, builder, digger, farmer, hunter, miner, "
					+ "woodcutter) VALUES ('" + pname + "',0,0,0,0,0,0,0,0,0,0,0,0,0,0)");
			s.addBatch("INSERT INTO VSkills_levels (name, acrobat, archery, axe, hoe, pickaxe, shovel, sword, unarmed, builder" +
					", digger, farmer, hunter, miner, woodcutter) " +
					"VALUES ('" + pname + "',1,1,1,1,1,1,1,1,1,1,1,1,1,1)");
			s.executeBatch();
			c.commit();
			s.close();
			c.close();
			Main.writeMessage("Created User: " + pname);
		}catch(SQLException e){
			Main.writeError("Error Adding User: " + e.getMessage());
		}
	}

	public boolean checkUser(Player player){
		String pname = player.getName();
		try{
        	Main.sql.open();
        	c = Main.sql.getConnection();
        	s = c.createStatement();
        	rs = s.executeQuery("SELECT * FROM VSkills WHERE name = '" + pname + "'");
        	if(rs.next()){
        		return true;
        	}else{
        		return false;
        	}
        }catch(SQLException e){
			Main.writeError("Error Checking User: " + e.getMessage());
			return false;
		}finally{
			try{
				rs.close();
				s.close();
				c.close();
			}catch(SQLException e){
				Main.writeError("Error Checking User: " + e.getMessage());
			}
		}
	}
	
	public int getSkillXP(Player player, SkillType skill){
		String pname = player.getName();
		switch(skill){
			case ACROBATICS: return acrobat.get(pname);
			case ARCHERY: return archery.get(pname);
			case AXE: return axe.get(pname);
			case HOE: return hoe.get(pname);
			case PICKAXE: return pickaxe.get(pname);
			case SHOVEL: return shovel.get(pname);
			case SWORD: return sword.get(pname);
			case UNARMED: return unarmed.get(pname);
			default: return 0;
		}
	}
	
	public int getJobXP(Player player, JobType job){
		String pname = player.getName();
		switch(job){
			case BUILDER: return builder.get(pname);
			case DIGGER: return digger.get(pname);
			case FARMER: return farmer.get(pname);
			case HUNTER: return hunter.get(pname);
			case MINER: return miner.get(pname);
			case WOODCUTTER: return woodcutter.get(pname);
			default: return 0;
		}
	}

	public int getSkillLvl(Player player, SkillType skill){
		String pname = player.getName();
		switch(skill){
			case ACROBATICS: return acrobatlvl.get(pname);
			case ARCHERY: return archerylvl.get(pname);
			case AXE: return axelvl.get(pname);
			case HOE: return hoelvl.get(pname);
			case PICKAXE: return pickaxelvl.get(pname);
			case SHOVEL: return shovellvl.get(pname);
			case SWORD: return swordlvl.get(pname);
			case UNARMED: return unarmedlvl.get(pname);
			default: return 0;
		}
	}
	
	public int getJobLvl(Player player, JobType job){
		String pname = player.getName();
		switch(job){
			case BUILDER: return builderlvl.get(pname);
			case DIGGER: return diggerlvl.get(pname);
			case FARMER: return farmerlvl.get(pname);
			case HUNTER: return hunterlvl.get(pname);
			case MINER: return minerlvl.get(pname);
			case WOODCUTTER: return woodcutterlvl.get(pname);
			default: return 0;
		}
	}

	public double getMoney(Player player){
		String pname = player.getName();
		double m = money.get(pname);
		return m;
	}
	
	public void setMoney(Player player, double val){
		String pname = player.getName();
		money.put(pname, val);
	}
	
	public void setSkillXP(Player player, SkillType skill, int xp){
		String pname = player.getName();
		switch(skill){
			case ACROBATICS: 
				acrobat.put(pname, xp);
				break;
			case ARCHERY: 
				archery.put(pname, xp);
				break;
			case AXE: 
				axe.put(pname, xp);
				break;
			case HOE: 
				hoe.put(pname, xp);
				break;
			case PICKAXE: 
				pickaxe.put(pname, xp);
				break;
			case SHOVEL: 
				shovel.put(pname, xp);
				break;
			case SWORD: 
				sword.put(pname, xp);
				break;
			case UNARMED: 
				unarmed.put(pname, xp);
				break;
			default: break;
		}
	}

	public void setJobXP(Player player, JobType job, int xp){
		String pname = player.getName();
		switch(job){
			case BUILDER: 
				builder.put(pname, xp);
				break;
			case DIGGER: 
				digger.put(pname, xp);
				break;
			case FARMER: 
				farmer.put(pname, xp);
				break;
			case HUNTER: 
				hunter.put(pname, xp);
				break;
			case MINER: 
				miner.put(pname, xp);
				break;
			case WOODCUTTER: 
				woodcutter.put(pname, xp);
				break;
			default: break;
		}
	}
	
	public void setSkillLvl(Player player, SkillType skill, int xp){
		String pname = player.getName();
		switch(skill){
			case ACROBATICS: 
				acrobatlvl.put(pname, xp);
				break;
			case ARCHERY: 
				archerylvl.put(pname, xp);
				break;
			case AXE: 
				axelvl.put(pname, xp);
				break;
			case HOE: 
				hoelvl.put(pname, xp);
				break;
			case PICKAXE: 
				pickaxelvl.put(pname, xp);
				break;
			case SHOVEL: 
				shovellvl.put(pname, xp);
				break;
			case SWORD: 
				swordlvl.put(pname, xp);
				break;
			case UNARMED: 
				unarmedlvl.put(pname, xp);
				break;
			default: break;
		}
	}

	public void setJobLvl(Player player, JobType job, int xp){
		String pname = player.getName();
		switch(job){
			case BUILDER: 
				builderlvl.put(pname, xp);
				break;
			case DIGGER: 
				diggerlvl.put(pname, xp);
				break;
			case FARMER: 
				farmerlvl.put(pname, xp);
				break;
			case HUNTER: 
				hunterlvl.put(pname, xp);
				break;
			case MINER: 
				minerlvl.put(pname, xp);
				break;
			case WOODCUTTER: 
				woodcutterlvl.put(pname, xp);
				break;
			default: break;
		}
	}
	
	public void giveGod(Player player){
		String pname = player.getName();
		god.put(pname, true);
	}
	
	public void takeGod(Player player){
		String pname = player.getName();
		god.remove(pname);
	}
	
	public boolean checkGod(Player player){
		String pname = player.getName();
		if(god.containsKey(pname)){
			return true;
		}else{
			return false;
		}
	}
	
	public void scoreboard(Player player){
		String pname = player.getName();
		jobsboard = Bukkit.getScoreboardManager().getNewScoreboard();
		jobs = jobsboard.registerNewObjective("Jobs", "dummy");
		jobs.setDisplaySlot(DisplaySlot.SIDEBAR);
		jobs.setDisplayName("Job Levels");
		skillsboard = Bukkit.getScoreboardManager().getNewScoreboard();
		skills = skillsboard.registerNewObjective("Skills", "dummy");
		skills.setDisplaySlot(DisplaySlot.SIDEBAR);
		skills.setDisplayName("Skill Levels");
		powerboard = Bukkit.getScoreboardManager().getNewScoreboard();
		powers = powerboard.registerNewObjective("Power", "dummy");
		powers.setDisplaySlot(DisplaySlot.SIDEBAR);
		powers.setDisplayName("Power");
		statsboard = Bukkit.getScoreboardManager().getNewScoreboard();
		stats = statsboard.registerNewObjective("Kills", "dummy");
		stats.setDisplaySlot(DisplaySlot.SIDEBAR);
		stats.setDisplayName("Stats");
		jobexpboard = Bukkit.getScoreboardManager().getNewScoreboard();
		jobsexp = jobexpboard.registerNewObjective("Jobs Exp", "dummy");
		jobsexp.setDisplaySlot(DisplaySlot.SIDEBAR);
		jobsexp.setDisplayName("Jobs Exp to Level");
		skillexpboard = Bukkit.getScoreboardManager().getNewScoreboard();
		skillsexp = skillexpboard.registerNewObjective("Skills Exp", "dummy");
		skillsexp.setDisplaySlot(DisplaySlot.SIDEBAR);
		skillsexp.setDisplayName("Skills Exp to Level");
		
		Score build = jobs.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Building: "));
		build.setScore(getJobLvl(player, JobType.BUILDER));
		
		Score dig = jobs.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Digging: "));
		dig.setScore(getJobLvl(player, JobType.DIGGER));
		
		Score farm = jobs.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Farming: "));
		farm.setScore(getJobLvl(player, JobType.FARMER));
		
		Score hunt = jobs.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Hunting: "));
		hunt.setScore(getJobLvl(player, JobType.HUNTER));
		
		Score mine = jobs.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Mining: "));
		mine.setScore(getJobLvl(player, JobType.MINER));
		
		Score wc = jobs.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Woodcutting: "));
		wc.setScore(getJobLvl(player, JobType.WOODCUTTER));
		
		Score buildxp = jobsexp.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Building: "));
		buildxp.setScore(getXPToLevel(player, JobType.BUILDER));
		
		Score digxp = jobsexp.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Digging: "));
		digxp.setScore(getXPToLevel(player, JobType.DIGGER));
		
		Score farmxp = jobsexp.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Farming: "));
		farmxp.setScore(getXPToLevel(player, JobType.FARMER));
		
		Score huntxp = jobsexp.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Hunting: "));
		huntxp.setScore(getXPToLevel(player, JobType.HUNTER));
		
		Score minexp = jobsexp.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Mining: "));
		minexp.setScore(getXPToLevel(player, JobType.MINER));
		
		Score wcxp = jobsexp.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Woodcutting: "));
		wcxp.setScore(getXPToLevel(player, JobType.WOODCUTTER));
		
		Score acrobats = skills.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Acrobatics: "));
		acrobats.setScore(getSkillLvl(player, SkillType.ACROBATICS));
		
		Score archery = skills.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Archery: "));
		archery.setScore(getSkillLvl(player, SkillType.ARCHERY));
		
		Score axe = skills.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Axes: "));
		axe.setScore(getSkillLvl(player, SkillType.AXE));
		
		Score hoe = skills.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Hoe: "));
		hoe.setScore(getSkillLvl(player, SkillType.HOE));
		
		Score pick = skills.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Pickaxes: "));
		pick.setScore(getSkillLvl(player, SkillType.PICKAXE));
		
		Score shovel = skills.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Shovels: "));
		shovel.setScore(getSkillLvl(player, SkillType.SHOVEL));
		
		Score sword = skills.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Swords: "));
		sword.setScore(getSkillLvl(player, SkillType.SWORD));
		
		Score unarmed = skills.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Unarmed: "));
		unarmed.setScore(getSkillLvl(player, SkillType.UNARMED));
		
		Score acrobatxp = skillsexp.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Acrobatics: "));
		acrobatxp.setScore(getXPToLevel(player, SkillType.ACROBATICS));
		
		Score archeryxp = skillsexp.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Archery: "));
		archeryxp.setScore(getXPToLevel(player, SkillType.ARCHERY));
		
		Score axexp = skillsexp.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Axes: "));
		axexp.setScore(getXPToLevel(player, SkillType.AXE));
		
		Score hoexp = skillsexp.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Hoe: "));
		hoexp.setScore(getXPToLevel(player, SkillType.HOE));
		
		Score pickxp = skillsexp.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Pickaxes: "));
		pickxp.setScore(getXPToLevel(player, SkillType.PICKAXE));
		
		Score shovelxp = skillsexp.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Shovels: "));
		shovelxp.setScore(getXPToLevel(player, SkillType.SHOVEL));
		
		Score swordxp = skillsexp.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Swords: "));
		swordxp.setScore(getXPToLevel(player, SkillType.SWORD));
		
		Score unarmedxp = skillsexp.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Unarmed: "));
		unarmedxp.setScore(getXPToLevel(player, SkillType.UNARMED));
		
		Score kill = stats.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Kills: "));
		kill.setScore(getKills(player));
		
		Score death = stats.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Deaths: "));
		death.setScore(getDeaths(player));
		
		Score kd = stats.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "KD Ratio: "));
		kd.setScore(getKD(player));
		
		Score rank = stats.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Rank: "));
		rank.setScore(getRank(player));
		
		Score token = stats.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Tokens: "));
		token.setScore(getTokens(player));
		
		Score p = powers.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "MaxPower: "));
		p.setScore(AbilitiesManager.getPlayerMaxPower(player));
		
		Score cp = powers.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "CurrentPower: "));
		cp.setScore(AbilitiesManager.getPlayerCurrentPower(player));
		
		if(sb.get(pname) == "jobs"){
			player.setScoreboard(jobsboard);
		}else if(sb.get(pname) == "skills"){
			player.setScoreboard(skillsboard);
		}else if(sb.get(pname) == "stats"){
			player.setScoreboard(statsboard);
		}else if(sb.get(pname) == "jobsexp"){
			player.setScoreboard(jobexpboard);
		}else if(sb.get(pname) == "skillsexp"){
			player.setScoreboard(skillexpboard);
		}else if(sb.get(pname) == "power"){
			player.setScoreboard(powerboard);
		}else{
			return;
		}
	}
	
	public void setScoreboard(Player player, String type){
		String pname = player.getName();
		if(type == "jobs"){
			sb.put(pname, "jobs");
			player.setScoreboard(jobsboard);
		}else if(type == "skills"){
			sb.put(pname, "skills");
			player.setScoreboard(skillsboard);
		}else if(type == "stats"){
			sb.put(pname, "stats");
			player.setScoreboard(statsboard);
		}else if(type == "jobsexp"){
			sb.put(pname, "jobsexp");
			player.setScoreboard(jobexpboard);
		}else if(type == "skillsexp"){
			sb.put(pname, "skillsexp");
			player.setScoreboard(skillexpboard);
		}else if(type == "power"){
			sb.put(pname, "power");
			player.setScoreboard(powerboard);
		}else{
			return;
		}
	}
	
	public void getScoreboards(){
		for(Player player: Bukkit.getServer().getOnlinePlayers()){
			scoreboard(player);
		}
	}
	
	public int getKD(Player player){
		int k = getKills(player);
		int d = getDeaths(player);
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

	public void resetOfflineAll(OfflinePlayer player){
		String pname = player.getName();
		try{
			Main.sql.open();
			c = Main.sql.getConnection();
			s = c.createStatement();
			String update = "UPDATE VSkills SET kills = 0, deaths = 0, tokens = 0, money = 0, rank = 1, acrobat = 0, archery = 0, axe = 0," +
					" hoe = 0, pickaxe = 0, shovel = 0, sword = 0, unarmed = 0, builder = 0, digger = 0," +
					" farmer = 0, hunter = 0, miner = 0, woodcutter = 0 WHERE name = '" + pname + "'";
			String updatelvl = "UPDATE VSkills_levels SET acrobat = 1, archery = 1, axe = 1, hoe = 1, pickaxe = 1," +
					" shovel = 1, sword = 1, unarmed = 1, builder = 1, digger = 1, farmer = 1," +
					" hunter = 1, miner = 1, woodcutter = 1 WHERE name = '" + pname + "'";
			s.executeUpdate(update);
			s.close();
			s = c.createStatement();
			s.executeUpdate(updatelvl);
			s.close();
			c.close();
		}catch(SQLException e){
			Main.writeError("Error Saving User Profile: " + e.getMessage());
		}
	}

	public void resetOnlineAll(Player player){
		String pname = player.getName();
		money.put(pname, 0.25);
		ranks.put(pname, 1);
		tokens.put(pname, 0);
		kills.put(pname, 0);
		deaths.put(pname, 0);
		digger.put(pname, 0);
		builder.put(pname, 0);
		hunter.put(pname, 0);
		farmer.put(pname, 0);
		miner.put(pname, 0);
		woodcutter.put(pname, 0);
		acrobat.put(pname, 0);
		archery.put(pname, 0);
		axe.put(pname, 0);
		hoe.put(pname, 0);
		pickaxe.put(pname, 0);
		shovel.put(pname, 0);
		sword.put(pname, 0);
		unarmed.put(pname, 0);
		diggerlvl.put(pname, 1);
		builderlvl.put(pname, 1);
		hunterlvl.put(pname, 1);
		farmerlvl.put(pname, 1);
		minerlvl.put(pname, 1);
		woodcutterlvl.put(pname, 1);
		acrobatlvl.put(pname, 0);
		archerylvl.put(pname, 1);
		axelvl.put(pname, 1);
		hoelvl.put(pname, 1);
		pickaxelvl.put(pname, 1);
		shovellvl.put(pname, 1);
		swordlvl.put(pname, 1);
		unarmedlvl.put(pname, 1);
	}

	public int getRank(Player player){
		int rank = ranks.get(player.getName());
		return rank;
	}
	
	public void setRank(Player player, int rank){
		ranks.put(player.getName(), rank);
	}
	
	public int getKills(Player player){
		int kill = kills.get(player.getName());
		return kill;
	}
	
	public void setKills(Player player, int kill){
		kills.put(player.getName(), kill);
	}

	public int getDeaths(Player player){
		int death = deaths.get(player.getName());
		return death;
	}
	
	public void setDeaths(Player player, int death){
		deaths.put(player.getName(), death);
	}

	public int getTokens(Player player){
		int token = tokens.get(player.getName());
		return token;
	}
	
	public void setTokens(Player player, int token){
		tokens.put(player.getName(), token);
	}

	public int getXPToLevel(Player player, JobType job){
		int level = getJobLvl(player, job);
		int xp = getJobXP(player, job);
		int nextLevel = level + 1;
		double half = nextLevel / 2;
		double expNeeded = 30 * nextLevel * half;
		int expNeed = (int)Math.round(expNeeded);
		int exp = expNeed - xp;
		return exp;
	}
	
	public int getXPToLevel(Player player, SkillType skill){
		int level = getSkillLvl(player, skill);
		int xp = getSkillXP(player, skill);
		int nextLevel = level + 1;
		double half = nextLevel / 2;
		double expNeeded = 30 * nextLevel * half;
		int expNeed = (int)Math.round(expNeeded);
		int exp = expNeed - xp;
		return exp;
	}
}