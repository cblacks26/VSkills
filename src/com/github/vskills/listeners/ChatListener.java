package com.github.vskills.listeners;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.github.vskills.Main;
import com.github.vskills.util.UserManager;

public class ChatListener implements Listener{

	private Main main;
	
	UserManager userManager = Main.getUserManager();

	public ChatListener(Main p){
		main = p;
	}

	@EventHandler
	public void ChatEvent(AsyncPlayerChatEvent event){
		Player player = event.getPlayer();
		World world = event.getPlayer().getWorld();
		String name = player.getName();
		String m = event.getMessage();
		int r = userManager.getRank(player);
		if(!Main.getPerms().isEnabled()){
			return;
		}
		if(main.getConfig().get("Ranks." + r) != null){
			String rank = main.getConfig().getString("Ranks." + r);
			if(Main.getPerms().getPrimaryGroup(player) != null){
				String group = Main.getPerms().getPrimaryGroup(player);
				String prefix = ChatColor.translateAlternateColorCodes('&', Main.getChat().getGroupPrefix(world, group));
				event.setFormat(prefix + ChatColor.GRAY + "[" + ChatColor.BLUE + rank + ChatColor.GRAY + "] " + name + ": " + m);
			}else{
				event.setFormat(ChatColor.GRAY + "[" + ChatColor.BLUE + rank + ChatColor.GRAY + "] " + name + ": " + m);
			}
		}else{
			if(Main.getPerms().getPrimaryGroup(player) != null){
				String group = Main.getPerms().getPrimaryGroup(player);
				String prefix = ChatColor.translateAlternateColorCodes('&', Main.getChat().getGroupPrefix(world, group));
				event.setFormat(prefix + ChatColor.GRAY + "[" + ChatColor.BLUE + r + ChatColor.GRAY + "] " + name + ": " + m);
			}else{
				event.setFormat(ChatColor.GRAY + "[" + ChatColor.BLUE + r + ChatColor.GRAY + "] " + name + ": " + m);
			}
		}
	}
}
