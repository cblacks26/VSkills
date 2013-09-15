package com.github.vskills.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.vskills.Main;
import com.github.vskills.datatypes.Commands;
import com.github.vskills.util.UserManager;

public class CommandBoard implements CommandExecutor{

	UserManager userManager = Main.getUserManager();
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("VBoard")){
			if(sender instanceof Player){
				Player player = (Player) sender;
				if(Main.isAuthorized(player, "VSkills.board")){
					if(args.length == 1){
						if(args[0].equalsIgnoreCase("Jobslevel") || args[0].equalsIgnoreCase("JL")) userManager.setScoreboard(player, "jobs");
						else if(args[0].equalsIgnoreCase("SkillsLevel") || args[0].equalsIgnoreCase("SL")) userManager.setScoreboard(player, "skills");
						else if(args[0].equalsIgnoreCase("Stats")) userManager.setScoreboard(player, "stats");
						else if(args[0].equalsIgnoreCase("SkillsXP") || args[0].equalsIgnoreCase("SXP")) userManager.setScoreboard(player, "skillsexp");
						else if(args[0].equalsIgnoreCase("JobsXP") || args[0].equalsIgnoreCase("JXP")) userManager.setScoreboard(player, "jobsexp");
						else return true;
					}else{
						player.sendMessage("Usage: " + Commands.VBOARD.getDescription());
					}
				}else{
					player.sendMessage(ChatColor.RED + "You don't have the Permissions for this command");
				}
			}else{
				Main.writeMessage("Command: /VBoard is only available to Players");
			}
		}
		return true;
	}	
}
