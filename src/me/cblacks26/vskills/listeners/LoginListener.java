package me.cblacks26.vskills.listeners;

import java.sql.SQLException;

import me.cblacks26.vskills.Main;
import me.cblacks26.vskills.util.Database;
import me.cblacks26.vskills.util.Util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class LoginListener implements Listener{
	
	private Main main;
	Util util = new Util();
	Database sql = new Database(null);

	public LoginListener(Main p){
		main = p;
	}

	@EventHandler
	public void PlayerJoin(PlayerJoinEvent event) throws SQLException{
		Player player = event.getPlayer();
		String pname = player.getName();
		if(util.checkUserTable(pname, "SKILLS") ==  false){
			sql.write("INSERT INTO SKILLS (PLAYERNAME,DIGGING,MINING,LOGGING,BUILDING,FARMING,HUNTING) "
			+ "VALUES (?, 0, 0, 0, 0, 0, 0);", pname);
		}
		if(util.checkUserTable(pname, "LEVELS") ==  false){
			sql.write("INSERT INTO LEVELS (PLAYERNAME,RANK,DIGGING,MINING,LOGGING,BUILDING,FARMING,HUNTING) "
					+ "VALUES (?, 1, 1, 1, 1, 1, 1, 1);", pname);
		}
		if(util.checkUserTable(pname, "UNLOCKS") ==  false){
			sql.write("INSERT INTO UNLOCKS (PLAYERNAME,TOKENS,DIGGING,MINING,LOGGING,BUILDING,FARMING,HUNTING) "
					+ "VALUES (?, 0, 0, 0, 0, 0, 0, 0);", pname);
		}
		if(util.checkUserTable(pname, "STATS") ==  false){
			sql.write("INSERT INTO STATS (PLAYERNAME,KILLS,DEATHS,KD) VALUES (?, 0, 0, 0);", pname);
		}
		if(main.getConfig().get("MOTDEnabled") != null){
			boolean enabled = main.getConfig().getBoolean("MOTDEnabled");
			if(enabled == true){
				player.sendMessage(ChatColor.GREEN +  main.getConfig().getString("MOTD"));
			}
		}	
	}

	public void PlayerLeave(PlayerQuitEvent event){
		Player player = event.getPlayer();
		if(main.god.get(player.getName()) != null){
			main.god.remove(player.getName());
		}
	}


}