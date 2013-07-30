package me.cblacks26.vskills.commands;

import me.cblacks26.vskills.Main;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandMOTD implements CommandExecutor{

	private Main main;
	
	public CommandMOTD(Main p){
		main = p;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("MOTD")){
			if(sender instanceof Player){
				Player player = (Player) sender;
				if(args.length == 0){
					if(main.isAuthorized(player, "vskills.motd") == false){
						player.sendMessage(ChatColor.RED + "You don't have the Permissions for this command");
						return true;
					}else{
						player.sendMessage(ChatColor.GREEN +  main.getConfig().getString("MOTD"));
					}
				}
			}
			if(!(sender instanceof Player)){
				sender.sendMessage(ChatColor.GREEN +  main.getConfig().getString("MOTD"));
			}
		}
		return true;
	}

}
