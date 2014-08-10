package com.github.vskills.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.vskills.Main;
import com.github.vskills.user.User;
import com.github.vskills.util.UserManager;

public class CommandGod implements CommandExecutor{
	
	UserManager userManager = Main.getUserManager();
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("VGod")){
			if(sender instanceof Player){
				Player player = (Player) sender;
				if(args.length == 0){
					if(Main.isAuthorized(player, "VSkills.god") == false){
						player.sendMessage(ChatColor.RED + "You don't have the Permissions for this command");
						return true;
					}else{
						User user = userManager.getUser(player.getUniqueId());
						if(user.toggleGod()){
							player.sendMessage(ChatColor.GRAY + "God Mode Enabled");
						}else{
							player.sendMessage(ChatColor.GRAY + "God Mode Disabled");
						}
					}
				}else if(args.length == 1){
					Player tplayer = Bukkit.getPlayer(args[0]);
					if(Main.isAuthorized(player, "VSkills.god.others") == false){
						player.sendMessage(ChatColor.RED + "You don't have the Permissions for this command");
						return true;
					}
					if(tplayer != null){
						User user = userManager.getUser(tplayer.getUniqueId());
						if(user.toggleGod()){
							player.sendMessage(ChatColor.GRAY + "God Mode Enabled For " + tplayer.getName());
							tplayer.sendMessage(ChatColor.GRAY + "God Mode Enabled");
						}else{
							player.sendMessage(ChatColor.GRAY + "God Mode Disabled");
							tplayer.sendMessage(ChatColor.GRAY + "God Mode Disabled");
						}
					}
				}else{
					player.sendMessage(ChatColor.RED + "Usage: /God <player>");
				}
			}
			if(!(sender instanceof Player)){
				if(args.length == 1){
					Player tplayer = Bukkit.getPlayer(args[0]);
					if(tplayer != null){
						User user = userManager.getUser(tplayer.getUniqueId());
						if(user.toggleGod()){
							sender.sendMessage(ChatColor.GRAY + "God Mode Enabled For " + tplayer.getName());
							tplayer.sendMessage(ChatColor.GRAY + "God Mode Enabled");
						}else{
							sender.sendMessage(ChatColor.GRAY + "God Mode Disabled");
							tplayer.sendMessage(ChatColor.GRAY + "God Mode Disabled");
						}
					}
				}
			}
		}
		return true;
	}

}
