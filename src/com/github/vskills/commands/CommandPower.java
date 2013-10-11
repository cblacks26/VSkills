package com.github.vskills.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.vskills.Main;
import com.github.vskills.datatypes.Commands;
import com.github.vskills.util.AbilitiesManager;
import com.github.vskills.util.UserManager;

public class CommandPower implements CommandExecutor {

	UserManager userManager = Main.getUserManager();
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("VPower")){
			if(sender instanceof Player){
				Player player = (Player) sender;
				if(args.length == 1){
					if(Main.isAuthorized(player, "VSkills.power") == false){
						player.sendMessage(ChatColor.RED + "You don't have the Permissions for this command");
						return true;
					}else{
						if(args[0].equalsIgnoreCase("refresh")){
							AbilitiesManager.refreshPower(player);
							userManager.scoreboard(player);
							player.sendMessage(ChatColor.GREEN + "Your power has been refreshed!");
						}
					}
				}else if(args.length == 2){
					if(args[0].equalsIgnoreCase("refresh")){
						if(Main.isAuthorized(player, "VSkills.power.others") == false){
							player.sendMessage(ChatColor.RED + "You don't have the Permissions for this command");
							return true;
						}
						Player tplayer = Bukkit.getPlayer(args[1]);
						if(tplayer != null){
							AbilitiesManager.refreshPower(tplayer);
							userManager.scoreboard(tplayer);
							tplayer.sendMessage(ChatColor.GREEN + "Your power has been refreshed!");
							player.sendMessage(ChatColor.GREEN + "Your refreshed " + args[1] + "'s power!");
						}else{
							player.sendMessage(ChatColor.RED + args[1] + " was not found (make sure the player is online)");
						}
					}
				}else if(args.length == 3){
					if(args[0].equalsIgnoreCase("set")){
						if(Main.isAuthorized(player, "VSkills.power.others") == false){
							player.sendMessage(ChatColor.RED + "You don't have the Permissions for this command");
							return true;
						}
						Player tplayer = Bukkit.getPlayer(args[1]);
						if(tplayer != null){
							if(Main.isInteger(args[2])){
								int p = Integer.parseInt(args[2]);
								AbilitiesManager.setPlayerMaxPower(tplayer, p);;
								userManager.scoreboard(tplayer);
								tplayer.sendMessage(ChatColor.GREEN + "Your max power has been set to " + p);
								player.sendMessage(ChatColor.GREEN + "Your set " + args[1] + "'s max power to " + p);
							}			
						}else{
							player.sendMessage(ChatColor.RED + args[1] + " was not found (make sure the player is online)");
						}
					}
				}else{
					player.sendMessage(ChatColor.RED + "Usage: " + Commands.VPOWER.getUsage());
				}
			}
			if(!(sender instanceof Player)){
				if(args.length == 2){
					if(args[0].equalsIgnoreCase("refresh")){
						Player tplayer = Bukkit.getPlayer(args[1]);
						if(tplayer != null){
							AbilitiesManager.refreshPower(tplayer);
							userManager.scoreboard(tplayer);
							tplayer.sendMessage(ChatColor.GREEN + "Your power has been refreshed!");
							sender.sendMessage(ChatColor.GREEN + "Your refreshed " + args[1] + "'s power!");
						}else{
							sender.sendMessage(ChatColor.RED + args[1] + " was not found (make sure the player is online)");
						}
					}
				}else if(args.length == 3){
					if(args[0].equalsIgnoreCase("set")){
						Player tplayer = Bukkit.getPlayer(args[1]);
						if(tplayer != null){
							if(Main.isInteger(args[2])){
								int p = Integer.parseInt(args[2]);
								AbilitiesManager.setPlayerMaxPower(tplayer, p);;
								userManager.scoreboard(tplayer);
								tplayer.sendMessage(ChatColor.GREEN + "Your max power has been set to " + p);
								sender.sendMessage(ChatColor.GREEN + "Your set " + args[1] + "'s max power to " + p);
							}			
						}else{
							sender.sendMessage(ChatColor.RED + args[1] + " was not found (make sure the player is online)");
						}
					}
				}else{
					sender.sendMessage(ChatColor.RED + "Usage: " + Commands.VPOWER.getUsage());
				}
			}
		}
		return true;
	}
	
}
