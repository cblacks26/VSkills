package com.github.vskills.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.vskills.Main;
import com.github.vskills.util.AbilitiesManager;
import com.github.vskills.util.UserManager;

public class CommandTokens implements CommandExecutor{

	UserManager userManager = Main.getUserManager();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("VTokens")){
			if(sender instanceof Player){
				Player player = (Player) sender;
				int tokens = userManager.getTokens(player);
				if(args.length < 1 || args.length > 1){
					player.sendMessage(ChatColor.RED + "Usage: /Tokens <option>");
					player.sendMessage(ChatColor.RED + "Options: Reset, ");
					return true;
				}
				if(args.length == 1){
					if(Main.isAuthorized(player, "VSkills.tokens") == false){
						player.sendMessage(ChatColor.RED + "You don't have the Permissions for this command");
						return true;
					}else{
						if(tokens == 0){
							player.sendMessage(ChatColor.RED + "You must have tokens to run this command");
							return true;
						}else{
							if(args[0].equalsIgnoreCase("money")){
								double money = userManager.getMoney(player);
								double newmoney = money + 0.01;
								userManager.setMoney(player, newmoney);
								userManager.setTokens(player, tokens - 1);
								userManager.scoreboard(player);
							}if(args[0].equalsIgnoreCase("power")){
								AbilitiesManager.addPlayerMaxPower(player, 2);
								userManager.setTokens(player, tokens - 1);
								userManager.scoreboard(player);
							}
						}
					}
				}
			}
			if(!(sender instanceof Player)){
				sender.sendMessage(ChatColor.RED + "Sorry this command can only be used by players.");
			}
		}
		return true;
	}
	
}
