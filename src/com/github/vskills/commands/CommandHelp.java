package com.github.vskills.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.vskills.Main;
import com.github.vskills.datatypes.Commands;

public class CommandHelp implements CommandExecutor{
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("VSkills")){
			if(sender instanceof Player){
				Player player = (Player) sender;
				if(args.length == 0){
					String line = "===============";
					String line2 = "=============";
					player.sendMessage(ChatColor.GREEN + line + ChatColor.GOLD + " VSkills Help " + ChatColor.GREEN + line);
					for(Commands cmds : Commands.values()){
						if(Main.isAuthorized(player, cmds.getPerm())){
							player.sendMessage(ChatColor.GOLD + cmds.getName() + ": " + cmds.getDescription());
							player.sendMessage(ChatColor.GOLD + "  Usage: " + cmds.getUsage());
						}
					}
					player.sendMessage(ChatColor.GREEN + line + line + line2);
				}else{
					player.sendMessage(ChatColor.RED + "Usage: /VSkills");
				}
			}else{
				if(args.length == 0){
					String line = "===============";
					String line2 = "=============";
					sender.sendMessage(ChatColor.GREEN + line + ChatColor.GOLD + " VSkills Help " + ChatColor.GREEN + line);
					for(Commands cmds : Commands.values()){
						sender.sendMessage(ChatColor.GOLD + cmds.getName() + ": " + cmds.getDescription());
						sender.sendMessage(ChatColor.GOLD + "  Usage: " + cmds.getUsage());
					}
					sender.sendMessage(ChatColor.GREEN + line + line + line2);
				}else{
					sender.sendMessage(ChatColor.RED + "Usage: /VSkills");
				}
			}
		}
		return true;
	}
}
