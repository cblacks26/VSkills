package me.cblacks26.vskills.listeners;

import me.cblacks26.vskills.Main;
import me.cblacks26.vskills.util.Database;
import me.cblacks26.vskills.util.Util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener{
	
	private Main main;
	
	Util util = new Util();
	Database sql = new Database(null);
	
	public ChatListener(Main p){
		main = p;
	}
	
	@EventHandler
	public void ChatEvent(AsyncPlayerChatEvent event){
		Player player = event.getPlayer();
		String name = player.getName();
		int r = sql.getInt(name, "LEVELS", "RANK");
		if(main.getConfig().get("Ranks." + r) != null){
			String rank = main.getConfig().getString("Ranks." + r);
			main.chat.setPlayerPrefix(player, ChatColor.GREEN + "[" + rank + "] " + ChatColor.WHITE + "");
		}else{
			String rank = Integer.toString(r);
			main.chat.setPlayerPrefix(player, ChatColor.GREEN + "[" + rank + "] " + ChatColor.WHITE + "");
		}
	}
}
