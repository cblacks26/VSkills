package com.github.vskills.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.vskills.Main;
import com.github.vskills.util.UserManager;

public class CommandReset implements CommandExecutor{

	UserManager userManager = Main.getUserManager();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("VReset")){
			if(sender instanceof Player){
				Player player = (Player) sender;
				if(args.length == 1){
					OfflinePlayer tplayer = (Bukkit.getServer().getPlayer(args[0]));
					if(Main.isAuthorized(player, "VSkills.reset") == false){
						player.sendMessage(ChatColor.RED + "You don't have the Permissions for this command");
						return true;
					}
					if(tplayer == null){
						tplayer = (Bukkit.getServer().getOfflinePlayerIfCached(args[0]));
						if(tplayer.hasPlayedBefore()){
							userManager.getUser(tplayer.getUniqueId()).resetUser();
							player.sendMessage(ChatColor.GOLD + "You reset " + tplayer.getName());
							return true;
						}
						if(!tplayer.hasPlayedBefore()){
							player.sendMessage("Could not find the player");
							return true;
						}
					}
					if(tplayer.isOnline()){
						Player tp = Bukkit.getServer().getPlayer(args[0]);
						userManager.getUser(tplayer.getUniqueId()).resetUser();
						userManager.getUser(tplayer.getUniqueId()).scoreboard();
						player.sendMessage(ChatColor.GOLD + "You reset " + tp.getName());
						tp.sendMessage(ChatColor.GRAY + "You have been reset");
						return true;
					}
				}else{
					player.sendMessage(ChatColor.RED + "Usage: /VReset <player>");
				}
			}else{
				if(args.length == 1){
					OfflinePlayer tplayer = (Bukkit.getServer().getPlayer(args[0]));
					if(tplayer == null){
						tplayer = (Bukkit.getServer().getOfflinePlayerIfCached(args[0]));
						if(tplayer.hasPlayedBefore()){
							userManager.getUser(tplayer.getUniqueId()).resetUser();
							return true;
						}
						if(!tplayer.hasPlayedBefore()){
							sender.sendMessage("Could not find the player");
							return true;
						}
					}
					if(tplayer.isOnline()){
						Player tp = Bukkit.getServer().getPlayer(args[0]);
						userManager.getUser(tplayer.getUniqueId()).resetUser();
						userManager.getUser(tplayer.getUniqueId()).scoreboard();
						sender.sendMessage(ChatColor.GOLD + "You reset " + tp.getName());
						tp.sendMessage(ChatColor.GRAY + "You have been reset");
						return true;
					}
				}else{
					sender.sendMessage(ChatColor.RED + "Usage: /VReset <player>");
				}
			}
		}
		return true;
	}
	
}
