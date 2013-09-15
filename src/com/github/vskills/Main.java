package com.github.vskills;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import lib.PatPeter.SQLibrary.Database;
import lib.PatPeter.SQLibrary.MySQL;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.vskills.commands.CommandBoard;
import com.github.vskills.commands.CommandGod;
import com.github.vskills.commands.CommandHelp;
import com.github.vskills.commands.CommandReset;
import com.github.vskills.commands.CommandSave;
import com.github.vskills.commands.CommandSet;
import com.github.vskills.commands.CommandStats;
import com.github.vskills.commands.CommandTokens;
import com.github.vskills.datatypes.JobType;
import com.github.vskills.datatypes.SkillType;
import com.github.vskills.listeners.BlockListener;
import com.github.vskills.listeners.ChatListener;
import com.github.vskills.listeners.EntityListener;
import com.github.vskills.listeners.PlayerListener;
import com.github.vskills.listeners.VSkillsListener;
import com.github.vskills.runnables.UserSaveTask;
import com.github.vskills.util.UserManager;

public class Main extends JavaPlugin{
	
	private final static Logger log = Logger.getLogger("Minecraft");
	private ScheduledExecutorService scheduler;
	private static Economy eco = null;
	private static Permission perms = null;
	private static Chat chat = null;
	private Connection c;
	private Statement s;
	public static Database sql;
	private String user;
	private String password; 
    private String database;
    private String port; 
    private String hostname;
    public static UserManager userManager = new UserManager();
    private UserSaveTask userSave = new UserSaveTask();
	
	public void onDisable(){
		userManager.saveUsers();
		Main.writeMessage("Saving Users...");
		saveConfig();
	}

	public void onEnable(){
		setupEconomy();
		setupPermissions();
		setupChat();
		registerEvents();
		getCommands();
		scheduledTasks();
		saveDefaultConfig();
    	
    	try {
    		sql = getMySQL();
            sql.open();
        	c = sql.getConnection();
        	c.setAutoCommit(false);
			s = c.createStatement();
			String table1 = "CREATE TABLE IF NOT EXISTS VSkills (name VARCHAR(50), kills Integer, deaths Integer," +
					" tokens Integer, money Double, rank Integer, archery Integer, axe Integer, hoe Integer, pickaxe Integer," +
					" shovel Integer, sword Integer, unarmed Integer, building Integer, digging Integer, farming Integer," +
					" hunting Integer, mining Integer, woodcutting Integer)";
			String table2 = "CREATE TABLE IF NOT EXISTS VSkills_levels (name VARCHAR(50), archery Integer, axe Integer, hoe Integer, pickaxe Integer," +
					" shovel Integer, sword Integer, unarmed Integer, building Integer, digging Integer, farming Integer," +
					" hunting Integer, mining Integer, woodcutting Integer)";
			s.addBatch(table1);
			s.addBatch(table2);
			s.executeBatch();
			c.commit();
			s.close();
			c.close();
		} catch (SQLException e) {
			writeError("Error Creating Tables: " + e.getMessage());
		}
    	userManager.addUsers();
    	userManager.getScoreboards();
	}
	
	private void registerEvents(){
		getServer().getPluginManager().registerEvents(new PlayerListener(), this);
		getServer().getPluginManager().registerEvents(new VSkillsListener(), this);
		getServer().getPluginManager().registerEvents(new BlockListener(), this);
		getServer().getPluginManager().registerEvents(new EntityListener(), this);
		getServer().getPluginManager().registerEvents(new ChatListener(this), this);
	}
	
	private void getCommands(){
		this.getCommand("VGod").setExecutor(new CommandGod());
		this.getCommand("VBoard").setExecutor(new CommandBoard());
		this.getCommand("VReset").setExecutor(new CommandReset());
		this.getCommand("VTokens").setExecutor(new CommandTokens());
		this.getCommand("VSkills").setExecutor(new CommandHelp());
		this.getCommand("VSet").setExecutor(new CommandSet());
		this.getCommand("VSave").setExecutor(new CommandSave());
		this.getCommand("VStats").setExecutor(new CommandStats());
	}
	
	public Database getMySQL(){
		this.user = this.getConfig().getString("MySQL.Username"); 
        this.database = this.getConfig().getString("MySQL.Database"); 
        this.password = this.getConfig().getString("MySQL.Password"); 
        this.port = this.getConfig().getString("MySQL.Port"); 
        this.hostname = this.getConfig().getString("MySQL.Hostname"); 
    	
        sql = new MySQL(log, "[VSkills]", hostname, Integer.parseInt(port), database, user, password);
		return sql;
	}
	
	private boolean setupChat()
    {
        RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
        if (chatProvider != null) {
            chat = chatProvider.getProvider();
        }

        return (chat != null);
    }
	
	private boolean setupEconomy(){
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            eco = economyProvider.getProvider();
        }

        return (eco != null);
    }
	
	private boolean setupPermissions(){
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            perms = permissionProvider.getProvider();
        }
        return (perms != null);
    }
	
	public static Chat getChat(){
		return chat;
	}

	public static Permission getPerms(){
		return perms;
	}
	
	public static void depositPlayer(Player player, double val){
		String name = player.getName();
		if(!eco.hasAccount(name)){
			eco.createPlayerAccount(name);
		}else{
			eco.depositPlayer(player.getName(), val);
		}
	}
	
	public static boolean isAuthorized(Player player, String perm){
		if(perms.has(player, perm)){
			return true;
		}
		if(!perms.has(player, perm)){
			return false;
		}
		return false;
	}
	
	public static UserManager getUserManager(){
		return userManager;
	}
	
	public static void writeMessage(String s){
		log.info("[VSkills] " + s);
	}
	
	public static void writeError(String s){
		log.severe("[VSkills] " + s);
	}

	public static boolean isInteger(String str) {
		if (str == null) {
			return false;
		}
		int length = str.length();
		if (length == 0) {
			return false;
		}
		int i = 0;
		if (str.charAt(0) == '-') {
			if (length == 1) {
				return false;
			}
			i = 1;
		}
		for (; i < length; i++) {
			char c = str.charAt(i);
			if (c <= '/' || c >= ':') {
				return false;
			}
		}
		return true;
	}

	public static JobType matchJob(String s){
		if(s.equalsIgnoreCase("Building")){
			return JobType.BUILDING;
		}else if(s.equalsIgnoreCase("Digging")){
			return JobType.DIGGING;
		}else if(s.equalsIgnoreCase("Farming")){
			return JobType.FARMING;
		}else if(s.equalsIgnoreCase("Hunting")){
			return JobType.HUNTING;
		}else if(s.equalsIgnoreCase("Mining")){
			return JobType.MINING;
		}else if(s.equalsIgnoreCase("Woodcutting")){
			return JobType.WOODCUTTING;
		}else{
			return null;
		}
	}
	
	public static SkillType matchSkill(String s){
		if(s.equalsIgnoreCase("Archery")){
			return SkillType.ARCHERY;
		}else if(s.equalsIgnoreCase("Axe") || s.equalsIgnoreCase("Axes")){
			return SkillType.AXE;
		}else if(s.equalsIgnoreCase("Hoe") || s.equalsIgnoreCase("Hoes")){
			return SkillType.HOE;
		}else if(s.equalsIgnoreCase("Pickaxe") || s.equalsIgnoreCase("Pickaxes")){
			return SkillType.PICKAXE;
		}else if(s.equalsIgnoreCase("Shovel") || s.equalsIgnoreCase("Shovels")){
			return SkillType.SHOVEL;
		}else if(s.equalsIgnoreCase("Sword") || s.equalsIgnoreCase("Swords")){
			return SkillType.SWORD;
		}else if(s.equalsIgnoreCase("Unarmed")){
			return SkillType.UNARMED;
		}else{
			return null;
		}
	}

	private void scheduledTasks(){
		scheduler = Executors.newScheduledThreadPool(1);
		 scheduler.scheduleAtFixedRate(userSave, 10, 10, TimeUnit.MINUTES);
	}
}