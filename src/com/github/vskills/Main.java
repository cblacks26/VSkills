package com.github.vskills;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

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
import com.github.vskills.database.Database;
import com.github.vskills.database.MySQL;
import com.github.vskills.database.SQLite;
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
		getMySQL();
		setupEconomy();
		setupPermissions();
		setupChat();
		registerEvents();
		getCommands();
		scheduledTasks();
		saveDefaultConfig();
		sql.createTables();
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
		if(s.equalsIgnoreCase("Builder")){
			return JobType.BUILDER;
		}else if(s.equalsIgnoreCase("Digger")){
			return JobType.DIGGER;
		}else if(s.equalsIgnoreCase("Farmer")){
			return JobType.FARMER;
		}else if(s.equalsIgnoreCase("Hunter")){
			return JobType.HUNTER;
		}else if(s.equalsIgnoreCase("Miner")){
			return JobType.MINER;
		}else if(s.equalsIgnoreCase("Woodcutter")){
			return JobType.WOODCUTTER;
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