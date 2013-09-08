package com.github.vskills;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import lib.PatPeter.SQLibrary.Database;
import lib.PatPeter.SQLibrary.MySQL;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.vskills.commands.CommandGod;
import com.github.vskills.commands.CommandReset;
import com.github.vskills.commands.CommandStats;
import com.github.vskills.commands.CommandTokens;
import com.github.vskills.listeners.BlockListener;
import com.github.vskills.listeners.EntityListener;
import com.github.vskills.listeners.PlayerListener;
import com.github.vskills.listeners.VSkillsListener;
import com.github.vskills.util.UserManager;

public class Main extends JavaPlugin{
	
	private final static Logger log = Logger.getLogger("Minecraft");
	private static Economy eco = null;
	private static Permission perms = null;
	private Connection c = null;
	private Statement s = null;
	public static Database sql;
	private String user;
	private String password; 
    private String database;
    private String port; 
    private String hostname;
    public static UserManager userManager = new UserManager();
	
	public void onDisable(){
		userManager.saveUsers();
	}

	public void onEnable(){
		setupEconomy();
		setupPermissions();
		registerEvents();
		getCommands();
		
		this.saveDefaultConfig();
    	
    	try {
    		sql = getMySQL();
            sql.open();
        	c = sql.getConnection();
			s = c.createStatement();
			String update = "CREATE TABLE IF NOT EXISTS VSkills (name VARCHAR(50), kills Integer, deaths Integer," +
					" tokens Integer, money Double, rank Integer, archery Integer, axe Integer, hoe Integer, pickaxe Integer," +
					" shovel Integer, sword Integer, unarmed Integer, building Integer, digging Integer, farming Integer," +
					" hunting Integer, mining Integer, woodcutting Integer)";
			s.execute(update);
			s.close();
			s = c.createStatement();
			String updatelvl = "CREATE TABLE IF NOT EXISTS VSkills_levels (name VARCHAR(50), archery Integer, axe Integer, hoe Integer, pickaxe Integer," +
					" shovel Integer, sword Integer, unarmed Integer, building Integer, digging Integer, farming Integer," +
					" hunting Integer, mining Integer, woodcutting Integer)";
			s.execute(updatelvl);
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
	}
	
	private void getCommands(){
		this.getCommand("VGod").setExecutor(new CommandGod());
		this.getCommand("VStats").setExecutor(new CommandStats());
		this.getCommand("VReset").setExecutor(new CommandReset());
		this.getCommand("VTokens").setExecutor(new CommandTokens());
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
	
	public static void depositPlayer(Player player, double val){
		eco.depositPlayer(player.getName(), val);
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

}