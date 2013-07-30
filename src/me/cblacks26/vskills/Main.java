package me.cblacks26.vskills;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import me.cblacks26.vskills.commands.CommandEXP;
import me.cblacks26.vskills.commands.CommandGod;
import me.cblacks26.vskills.commands.CommandLeaderboards;
import me.cblacks26.vskills.commands.CommandMOTD;
import me.cblacks26.vskills.commands.CommandReset;
import me.cblacks26.vskills.commands.CommandStats;
import me.cblacks26.vskills.commands.CommandTokens;
import me.cblacks26.vskills.listeners.BlockListener;
import me.cblacks26.vskills.listeners.ChatListener;
import me.cblacks26.vskills.listeners.EntityListener;
import me.cblacks26.vskills.listeners.LoginListener;
import me.cblacks26.vskills.listeners.PlayerListener;
import me.cblacks26.vskills.util.Database;
import me.cblacks26.vskills.util.Util;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{

	public final Logger log = Logger.getLogger("Minecraft");
	
	Database sql = new Database(this);
	Util util = new Util();
	
	public static Permission perms = null;
	public Chat chat = null;
	public static Economy eco = null;
	
	public Map<String, Boolean> god = new HashMap<String, Boolean>();
	
	@Override
    public void onDisable() {
    	writeMessage("has been Disabled");
    	this.saveConfig();
    }
	
	@Override
    public void onEnable() {
    	writeMessage("Has been Enabled");
    	this.saveDefaultConfig();
    	
    	setupPermissions();
    	setupChat();
    	setupEconomy();
    	this.RegisterEvents();
    	this.getCommands();
    	sql.intialize();
    	sql.write("CREATE TABLE IF NOT EXISTS SKILLS (PLAYERNAME VARCHAR(50), MINING INTEGER, DIGGING INTEGER," +
    			"LOGGING INTEGER, BUILDING INTEGER, FARMING INTEGER, HUNTING INTEGER)");
		
    	sql.write("CREATE TABLE IF NOT EXISTS LEVELS (PLAYERNAME VARCHAR(50), RANK INTEGER, DIGGING INTEGER," +
    			" MINING INTEGER, LOGGING INTEGER, BUILDING INTEGER, FARMING INTEGER, HUNTING INTEGER)");
		
    	sql.write("CREATE TABLE IF NOT EXISTS STATS (PlAYERNAME VARCHAR(50), KILLS INTEGER, DEATHS INTEGER," +
		" KD DOUBLE)");
    	
    	sql.write("CREATE TABLE IF NOT EXISTS UNLOCKS (PLAYERNAME VARCHAR(50), TOKENS INTEGER, DIGGING INTEGER," +
    			" MINING INTEGER, LOGGING INTEGER, BUILDING INTEGER, FARMING INTEGER, HUNTING INTEGER)");
    }

    private boolean setupChat()
    {
        RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
        if (chatProvider != null) {
            chat = chatProvider.getProvider();
        }

        return (chat != null);
    }

    private boolean setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            eco = economyProvider.getProvider();
        }

        return (eco != null);
    }
	
	private boolean setupPermissions()
    {
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            perms = permissionProvider.getProvider();
        }
        return (perms != null);
    }
	
	public boolean isAuthorized(Player player, String perm){
		if(perms.has(player, perm)){
			return true;
		}
		if(!perms.has(player, perm)){
			return false;
		}
		return false;
	}
	
	private void RegisterEvents(){
		getServer().getPluginManager().registerEvents(new LoginListener(this), this);
		getServer().getPluginManager().registerEvents(new BlockListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
		getServer().getPluginManager().registerEvents(new EntityListener(this), this);
		getServer().getPluginManager().registerEvents(new ChatListener(this), this);
	}
	
	private void getCommands(){
		this.getCommand("MOTD").setExecutor(new CommandMOTD(this));
		this.getCommand("EXP").setExecutor(new CommandEXP(this));
		this.getCommand("Stats").setExecutor(new CommandStats(this));
		this.getCommand("Leaderboards").setExecutor(new CommandLeaderboards(this));
		this.getCommand("Tokens").setExecutor(new CommandTokens(this));
		this.getCommand("Reset").setExecutor(new CommandReset(this));
		this.getCommand("VGod").setExecutor(new CommandGod(this));
	}
	
	public void writeMessage(String s){
		log.info("[VSkills] " + s);
	}
	
	public void writeError(String s){
		log.severe("[VSkills] " + s);
	}
	
	public void deposit(Player player, double val){
		eco.depositPlayer(player.getName(), val);
	}
}
	
