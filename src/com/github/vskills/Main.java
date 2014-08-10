package com.github.vskills;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.vskills.commands.CommandBoard;
import com.github.vskills.commands.CommandGod;
import com.github.vskills.commands.CommandHelp;
import com.github.vskills.commands.CommandPower;
import com.github.vskills.commands.CommandReset;
import com.github.vskills.commands.CommandSave;
import com.github.vskills.commands.CommandStats;
import com.github.vskills.commands.CommandTokens;
import com.github.vskills.database.Database;
import com.github.vskills.database.MySQL;
import com.github.vskills.database.SQLite;
import com.github.vskills.datatypes.SkillType;
import com.github.vskills.listeners.BlockListener;
import com.github.vskills.listeners.EntityListener;
import com.github.vskills.listeners.PlayerListener;
import com.github.vskills.listeners.VSkillsListener;
import com.github.vskills.runnables.UserSaveTask;
import com.github.vskills.user.User;
import com.github.vskills.util.UserManager;

public class Main extends JavaPlugin{
	
	private final static Logger log = Logger.getLogger("Minecraft");
	private static Economy eco = null;
	private static Permission perms = null;
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
		saveDefaultConfig();
		getMySQL();
		setupEconomy();
		setupPermissions();
		registerEvents();
		getCommands();
		scheduledTasks();
		sql.createTables();
    	userManager.addUsers();
    	userManager.getScoreboards();
	}
	
	private void registerEvents(){
		getServer().getPluginManager().registerEvents(new PlayerListener(), this);
		getServer().getPluginManager().registerEvents(new VSkillsListener(), this);
		getServer().getPluginManager().registerEvents(new BlockListener(), this);
		getServer().getPluginManager().registerEvents(new EntityListener(), this);
	}
	
	private void getCommands(){
		this.getCommand("VGod").setExecutor(new CommandGod());
		this.getCommand("VBoard").setExecutor(new CommandBoard());
		this.getCommand("VPower").setExecutor(new CommandPower());
		this.getCommand("VReset").setExecutor(new CommandReset());
		this.getCommand("VSkills").setExecutor(new CommandHelp());
		this.getCommand("VSave").setExecutor(new CommandSave());
		this.getCommand("VStats").setExecutor(new CommandStats());
		this.getCommand("VTokens").setExecutor(new CommandTokens());
	}
	
	public Database getMySQL(){
		Boolean enabled = this.getConfig().getBoolean("MySQL.Enabled");
        if(enabled == true){
        	this.user = this.getConfig().getString("MySQL.Username"); 
            this.database = this.getConfig().getString("MySQL.Database"); 
            this.password = this.getConfig().getString("MySQL.Password"); 
            this.port = this.getConfig().getString("MySQL.Port"); 
            this.hostname = this.getConfig().getString("MySQL.Hostname"); 
            sql = new MySQL(hostname, port, database, user, password);
            return sql;
        }else{
        	File db = new File(getDataFolder() + File.separator + "Database.db");
        	if(!db.exists()){
        		try {
					db.createNewFile();
				} catch (IOException e) {
					Main.writeError("Error Creating Database file: " + e.getMessage());
				}
        	}
        	sql = new SQLite(db);
        	return sql;
        }
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
	
	public static Permission getPerms(){
		return perms;
	}
	
	public static void depositPlayer(Player player, double val){
		OfflinePlayer p = (OfflinePlayer) player;
		if(!eco.hasAccount(p)){
			eco.createPlayerAccount(p);
		}else{
			eco.depositPlayer(p, val);
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
	
	public static SkillType matchSkill(String s){
		if(s.equalsIgnoreCase("Acrobatics") || s.equalsIgnoreCase("Acrobat")){
			return SkillType.ACROBATICS;
		}else if(s.equalsIgnoreCase("Archery")){
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
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, userSave, 10000L, 10000L);
		Bukkit.getScheduler().runTaskTimerAsynchronously(this, new Runnable()
	    {
			public void run(){
				for (Player p : Bukkit.getOnlinePlayers()){
					User user = userManager.getUser(p.getUniqueId());
					if (user.getCurrentPower() < user.getMaxPower()){
						user.addCurrentPower(1);
						user.scoreboard();
					}
	          	}
			}
	    }
	    , 40L, 40L);
	}
}