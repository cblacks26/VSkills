package me.cblacks26.vskills.commands;

import me.cblacks26.vskills.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandGod implements CommandExecutor{
	
	private Main main;
	
	public CommandGod(Main p){
		main = p;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("VGod")){
			if(sender instanceof Player){
				Player player = (Player) sender;
				if(args.length == 0){
					if(main.isAuthorized(player, "vskills.god") == false){
						player.sendMessage(ChatColor.RED + "You don't have the Permissions for this command");
						return true;
					}else{
						if(main.god.get(player.getName()) != null){
							main.god.remove(player.getName());
							player.sendMessage(ChatColor.GRAY + "God Mode Disabled");
						}else{
							main.god.put(player.getName(), true);
							player.sendMessage(ChatColor.GRAY + "God Mode Enabled");
						}
					}
				}else if(args.length == 1){
					Player tplayer = Bukkit.getPlayer(args[0]);
					if(main.isAuthorized(player, "vskills.god.others") == false){
						player.sendMessage(ChatColor.RED + "You don't have the Permissions for this command");
						return true;
					}
					if(tplayer != null){
						if(main.god.get(tplayer.getName()) != null){
							main.god.remove(tplayer.getName());
							player.sendMessage(ChatColor.GRAY + "God Mode Disabled For " + tplayer.getName());
							tplayer.sendMessage(ChatColor.GRAY + "God Mode Disabled");
						}else{
							main.god.put(tplayer.getName(), true);
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
						if(main.god.get(tplayer.getName()) != null){
							main.god.remove(tplayer.getName());
							sender.sendMessage(ChatColor.GRAY + "God Mode Disabled For " + tplayer.getName());
							tplayer.sendMessage(ChatColor.GRAY + "God Mode Disabled");
						}else{
							main.god.put(tplayer.getName(), true);
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
