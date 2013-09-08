package com.github.vskills.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.vskills.Main;
import com.github.vskills.util.UserManager;

public class CommandStats implements CommandExecutor{

	UserManager userManager = Main.getUserManager();
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("VStats")){
			if(sender instanceof Player){
				Player player = (Player) sender;
				if(Main.isAuthorized(player, "VSkills.stats")){
					if(args.length == 1){
						if(args[0].equalsIgnoreCase("Jobs")) userManager.setScoreboard(player, "jobs");
						else if(args[0].equalsIgnoreCase("Skills")) userManager.setScoreboard(player, "skills");
						else if(args[0].equalsIgnoreCase("Stats")) userManager.setScoreboard(player, "stats");
						else return true;
					}else{
						player.sendMessage("Usage: /vstats [jobs, skills, kills]");
					}
				}else{
					player.sendMessage(ChatColor.RED + "You don't have the Permissions for this command");
				}
			}else{
				Main.writeMessage("Command /VStats Only Available to Players");
			}
		}
		return true;
	}	
}
