package com.github.vskills.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.vskills.Main;
import com.github.vskills.datatypes.Commands;
import com.github.vskills.util.UserManager;

public class CommandSave implements CommandExecutor{

	UserManager userManager = Main.getUserManager();
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("VSave")){
			if(sender instanceof Player){
				Player player = (Player) sender;
				if(Main.isAuthorized(player, Commands.VSAVE.getPerm()) == false){
					player.sendMessage(ChatColor.RED + "You don't have the Permissions for this command");
					return true;
				}else{
					if(args.length == 0){
						player.sendMessage("Saving Users...");
						userManager.saveUsers();
						player.sendMessage("Save Completed");
					}else{
						player.sendMessage("Usage: " + Commands.VSAVE.getUsage());
					}
				}
			}else{
				if(args.length == 0){
					sender.sendMessage("Saving Users...");
					userManager.saveUsers();
					sender.sendMessage("Save Completed");
				}else{
					sender.sendMessage("Usage: " + Commands.VSAVE.getUsage());
				}
			}
		}
		return true;
	}
}
