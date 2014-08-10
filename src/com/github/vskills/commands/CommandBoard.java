package com.github.vskills.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.vskills.Main;
import com.github.vskills.datatypes.Commands;
import com.github.vskills.user.User;
import com.github.vskills.util.UserManager;

public class CommandBoard implements CommandExecutor{

	UserManager userManager = Main.getUserManager();
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("VBoard")){
			if(sender instanceof Player){
				Player player = (Player) sender;
				User user = userManager.getUser(player.getUniqueId());
				if(Main.isAuthorized(player, "VSkills.board")){
					if(args.length == 1){
						if(args[0].equalsIgnoreCase("Level") || args[0].equalsIgnoreCase("Lvl")){ 
							user.setScoreboard("level");
							user.scoreboard();
						}else if(args[0].equalsIgnoreCase("Stats")) {
							user.setScoreboard("stats");
							user.scoreboard();
						}else if(args[0].equalsIgnoreCase("XP") || args[0].equalsIgnoreCase("XP")) {
							user.setScoreboard("xp");
							user.scoreboard();
						}else if(args[0].equalsIgnoreCase("Power") || args[0].equalsIgnoreCase("Powers")){
							user.setScoreboard("power");
							user.scoreboard();
						}
						else {
							player.sendMessage("VBoard Options: Lvl, XP, Power, Stats");
						}
					}else{
						player.sendMessage("Usage: " + Commands.VBOARD.getUsage());
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
