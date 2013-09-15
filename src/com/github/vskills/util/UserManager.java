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
	private final HashMap<String, Integer> digging = new HashMap<String, Integer>();
	private final HashMap<String, Integer> mining = new HashMap<String, Integer>();
	private final HashMap<String, Integer> hunting = new HashMap<String, Integer>();
	private final HashMap<String, Integer> farming = new HashMap<String, Integer>();
	private final HashMap<String, Integer> building = new HashMap<String, Integer>();
	private final HashMap<String, Integer> woodcutting = new HashMap<String, Integer>();
	
	private final HashMap<String, Integer> archery = new HashMap<String, Integer>();
	private final HashMap<String, Integer> axe = new HashMap<String, Integer>();
	private final HashMap<String, Integer> hoe = new HashMap<String, Integer>();
	private final HashMap<String, Integer> pickaxe = new HashMap<String, Integer>();
	private final HashMap<String, Integer> shovel = new HashMap<String, Integer>();
	private final HashMap<String, Integer> sword = new HashMap<String, Integer>();
	private final HashMap<String, Integer> unarmed = new HashMap<String, Integer>();
	
	private final HashMap<String, Integer> digginglvl = new HashMap<String, Integer>();
	private final HashMap<String, Integer> mininglvl = new HashMap<String, Integer>();
	private final HashMap<String, Integer> huntinglvl = new HashMap<String, Integer>();
	private final HashMap<String, Integer> farminglvl = new HashMap<String, Integer>();
	private final HashMap<String, Integer> buildinglvl = new HashMap<String, Integer>();
	private final HashMap<String, Integer> woodcuttinglvl = new HashMap<String, Integer>();
	
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
			archery.put(pname, rs.getInt("archery"));
			axe.put(pname, rs.getInt("axe"));
			hoe.put(pname, rs.getInt("hoe"));
			pickaxe.put(pname, rs.getInt("pickaxe"));
			shovel.put(pname, rs.getInt("shovel"));
			sword.put(pname, rs.getInt("sword"));
			unarmed.put(pname, rs.getInt("unarmed"));
			building.put(pname, rs.getInt("building"));
			digging.put(pname, rs.getInt("digging"));
			farming.put(pname, rs.getInt("farming"));
			hunting.put(pname, rs.getInt("hunting"));
			mining.put(pname, rs.getInt("mining"));
			woodcutting.put(pname, rs.getInt("woodcutting"));
			rs.close();
			s.close();
			s = c.createStatement();
			rs = s.executeQuery("SELECT * FROM VSkills_levels WHERE name = '" + pname + "'");
			rs.next();
			archerylvl.put(pname, rs.getInt("archery"));
			axelvl.put(pname, rs.getInt("axe"));
			hoelvl.put(pname, rs.getInt("hoe"));
			pickaxelvl.put(pname, rs.getInt("pickaxe"));
			shovellvl.put(pname, rs.getInt("shovel"));
			swordlvl.put(pname, rs.getInt("sword"));
			unarmedlvl.put(pname, rs.getInt("unarmed"));
			buildinglvl.put(pname, rs.getInt("building"));
			digginglvl.put(pname, rs.getInt("digging"));
			farminglvl.put(pname, rs.getInt("farming"));
			huntinglvl.put(pname, rs.getInt("hunting"));
			mininglvl.put(pname, rs.getInt("mining"));
			woodcuttinglvl.put(pname, rs.getInt("woodcutting"));
			rs.close();
			s.close();
			c.close();
			Main.writeMessage("Loaded player: " + pname);
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
		digging.remove(pname);
		building.remove(pname);
		hunting.remove(pname);
		farming.remove(pname);
		mining.remove(pname);
		woodcutting.remove(pname);
		archery.remove(pname);
		axe.remove(pname);
		hoe.remove(pname);
		pickaxe.remove(pname);
		shovel.remove(pname);
		sword.remove(pname);
		unarmed.remove(pname);
		digginglvl.remove(pname);
		buildinglvl.remove(pname);
		huntinglvl.remove(pname);
		farminglvl.remove(pname);
		mininglvl.remove(pname);
		woodcuttinglvl.remove(pname);
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
		try{
			Main.sql.open();
			c = Main.sql.getConnection();
			s = c.createStatement();
			String update = "UPDATE VSkills SET kills = " + kills.get(pname) + ", deaths = " + deaths.get(pname) + ", tokens = " + tokens.get(pname) + 
					", money = " + money.get(pname) + ", rank = " + ranks.get(pname) + ", archery = " + archery.get(pname) + ", axe = " + axe.get(pname) + ", hoe = " + hoe.get(pname) + ", pickaxe = " +
					pickaxe.get(pname) + ", shovel = " + shovel.get(pname) + ", sword = " + sword.get(pname) + ", unarmed = " + unarmed.get(pname) + ", building = " +
					building.get(pname) + ", digging = " + digging.get(pname) + ", farming = " + farming.get(pname) + ", hunting = " + hunting.get(pname) + 
					", mining = " + mining.get(pname) + ", woodcutting = " + woodcutting.get(pname) + " WHERE name = '" + pname + "'";
			String updatelvl = "UPDATE VSkills_levels SET archery = " + archerylvl.get(pname) + ", axe = " + axelvl.get(pname) + ", hoe = " + hoelvl.get(pname) + ", pickaxe = " +
					pickaxelvl.get(pname) + ", shovel = " + shovellvl.get(pname) + ", sword = " + swordlvl.get(pname) + ", unarmed = " + unarmedlvl.get(pname) + ", building = " +
					buildinglvl.get(pname) + ", digging = " + digginglvl.get(pname) + ", farming = " + farminglvl.get(pname) + ", hunting = " + huntinglvl.get(pname) + 
					", mining = " + mininglvl.get(pname) + ", woodcutting = " + woodcuttinglvl.get(pname) + " WHERE name = '" + pname + "'";
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
			s.addBatch("INSERT INTO VSkills (name, kills, deaths, tokens, money, rank, archery, axe, hoe, pickaxe, shovel, sword, unarmed, building" +
					", digging, farming, hunting, mining, woodcutting) " +
					"VALUES ('" + pname + "',0,0,0,0.25,1,0,0,0,0,0,0,0,0,0,0,0,0,0)");
			s.addBatch("INSERT INTO VSkills_levels (name, archery, axe, hoe, pickaxe, shovel, sword, unarmed, building" +
					", digging, farming, hunting, mining, woodcutting) " +
					"VALUES ('" + pname + "',1,1,1,1,1,1,1,1,1,1,1,1,1)");
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
			case BUILDING: return building.get(pname);
			case DIGGING: return digging.get(pname);
			case FARMING: return farming.get(pname);
			case HUNTING: return hunting.get(pname);
			case MINING: return mining.get(pname);
			case WOODCUTTING: return woodcutting.get(pname);
			default: return 0;
		}
	}

	public int getSkillLvl(Player player, SkillType skill){
		String pname = player.getName();
		switch(skill){
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
			case BUILDING: return buildinglvl.get(pname);
			case DIGGING: return digginglvl.get(pname);
			case FARMING: return farminglvl.get(pname);
			case HUNTING: return huntinglvl.get(pname);
			case MINING: return mininglvl.get(pname);
			case WOODCUTTING: return woodcuttinglvl.get(pname);
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
			case BUILDING: 
				building.put(pname, xp);
				break;
			case DIGGING: 
				digging.put(pname, xp);
				break;
			case FARMING: 
				farming.put(pname, xp);
				break;
			case HUNTING: 
				hunting.put(pname, xp);
				break;
			case MINING: 
				mining.put(pname, xp);
				break;
			case WOODCUTTING: 
				woodcutting.put(pname, xp);
				break;
			default: break;
		}
	}
	
	public void setSkillLvl(Player player, SkillType skill, int xp){
		String pname = player.getName();
		switch(skill){
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
			case BUILDING: 
				buildinglvl.put(pname, xp);
				break;
			case DIGGING: 
				digginglvl.put(pname, xp);
				break;
			case FARMING: 
				farminglvl.put(pname, xp);
				break;
			case HUNTING: 
				huntinglvl.put(pname, xp);
				break;
			case MINING: 
				mininglvl.put(pname, xp);
				break;
			case WOODCUTTING: 
				woodcuttinglvl.put(pname, xp);
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
		build.setScore(getJobLvl(player, JobType.BUILDING));
		
		Score dig = jobs.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Digging: "));
		dig.setScore(getJobLvl(player, JobType.DIGGING));
		
		Score farm = jobs.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Farming: "));
		farm.setScore(getJobLvl(player, JobType.FARMING));
		
		Score hunt = jobs.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Hunting: "));
		hunt.setScore(getJobLvl(player, JobType.HUNTING));
		
		Score mine = jobs.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Mining: "));
		mine.setScore(getJobLvl(player, JobType.MINING));
		
		Score wc = jobs.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Woodcutting: "));
		wc.setScore(getJobLvl(player, JobType.WOODCUTTING));
		
		Score buildxp = jobsexp.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Building: "));
		buildxp.setScore(getXPToLevel(player, JobType.BUILDING));
		
		Score digxp = jobsexp.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Digging: "));
		digxp.setScore(getXPToLevel(player, JobType.DIGGING));
		
		Score farmxp = jobsexp.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Farming: "));
		farmxp.setScore(getXPToLevel(player, JobType.FARMING));
		
		Score huntxp = jobsexp.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Hunting: "));
		huntxp.setScore(getXPToLevel(player, JobType.HUNTING));
		
		Score minexp = jobsexp.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Mining: "));
		minexp.setScore(getXPToLevel(player, JobType.MINING));
		
		Score wcxp = jobsexp.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Woodcutting: "));
		wcxp.setScore(getXPToLevel(player, JobType.WOODCUTTING));
		
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
			String update = "UPDATE VSkills SET kills = 0, deaths = 0, tokens = 0, money = 0, rank = 1, archery = 0, axe = 0," +
					" hoe = 0, pickaxe = 0, shovel = 0, sword = 0, unarmed = 0, building = 0, digging = 0," +
					" farming = 0, hunting = 0, mining = 0, woodcutting = 0 WHERE name = '" + pname + "'";
			String updatelvl = "UPDATE VSkills_levels SET archery = 1, axe = 1, hoe = 1, pickaxe = 1," +
					" shovel = 1, sword = 1, unarmed = 1, building = 1, digging = 1, farming = 1," +
					" hunting = 1, mining = 1, woodcutting = 1 WHERE name = '" + pname + "'";
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
		digging.put(pname, 0);
		building.put(pname, 0);
		hunting.put(pname, 0);
		farming.put(pname, 0);
		mining.put(pname, 0);
		woodcutting.put(pname, 0);
		archery.put(pname, 0);
		axe.put(pname, 0);
		hoe.put(pname, 0);
		pickaxe.put(pname, 0);
		shovel.put(pname, 0);
		sword.put(pname, 0);
		unarmed.put(pname, 0);
		digginglvl.put(pname, 1);
		buildinglvl.put(pname, 1);
		huntinglvl.put(pname, 1);
		farminglvl.put(pname, 1);
		mininglvl.put(pname, 1);
		woodcuttinglvl.put(pname, 1);
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