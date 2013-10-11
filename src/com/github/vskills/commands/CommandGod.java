package com.github.vskills.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.vskills.Main;
import com.github.vskills.util.UserManager;

public class CommandGod implements CommandExecutor{
	
	UserManager userManager = Main.getUserManager();
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("VGod")){
			if(sender instanceof Player){
				Player player = (Player) sender;
				if(args.length == 0){
					if(Main.isAuthorized(player, "VSkills.god") == false){
						player.sendMessage(ChatColor.RED + "You don't have the Permissions for this command");
						return true;
					}else{
						if(userManager.checkGod(player)){
							userManager.takeGod(player);
							player.sendMessage(ChatColor.GRAY + "God Mode Disabled");
						}else{
							userManager.giveGod(player);
							player.sendMessage(ChatColor.GRAY + "God Mode Enabled");
						}
					}
				}else if(args.length == 1){
					Player tplayer = Bukkit.getPlayer(args[0]);
					if(Main.isAuthorized(player, "VSkills.god.others") == false){
						player.sendMessage(ChatColor.RED + "You don't have the Permissions for this command");
						return true;
					}
					if(tplayer != null){
						if(userManager.checkGod(tplayer)){
							userManager.takeGod(tplayer);
							player.sendMessage(ChatColor.GRAY + "God Mode Disabled For " + tplayer.getName());
							tplayer.sendMessage(ChatColor.GRAY + "God Mode Disabled");
						}else{
							userManager.giveGod(tplayer);
							player.sendMessage(ChatColor.GRAY + "God Mode Enabled");
							tplayer.sendMessage(ChatColor.GRAY + "God Mode Enabled");
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
						if(userManager.checkGod(tplayer)){
							userManager.takeGod(tplayer);
							sender.sendMessage(ChatColor.GRAY + "God Mode Disabled For " + tplayer.getName());
							tplayer.sendMessage(ChatColor.GRAY + "God Mode Disabled");
						}else{
							userManager.giveGod(tplayer);
							sender.sendMessage(ChatColor.GRAY + "God Mode Enabled");
							tplayer.sendMessage(ChatColor.GRAY + "God Mode Enabled");
						}
					}
				}
			}
		}
		return true;
	}

}
