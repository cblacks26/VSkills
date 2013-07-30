package me.cblacks26.vskills.commands;

import me.cblacks26.vskills.Main;
import me.cblacks26.vskills.util.Util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandReset implements CommandExecutor{

	Util util = new Util();
	
	private Main main;
	
	public CommandReset(Main p){
		main = p;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("Reset")){
			if(sender instanceof Player){
				Player player = (Player) sender;
				if(args.length == 1){
					OfflinePlayer tplayer = (Bukkit.getServer().getPlayer(args[0]));
					if(main.isAuthorized(player, "vskills.reset") == false){
						player.sendMessage(ChatColor.RED + "You don't have the Permissions for this command");
						return true;
					}
					if(tplayer == null){
						tplayer = (Bukkit.getServer().getOfflinePlayer(args[0]));
						if(tplayer.hasPlayedBefore()){
							util.setUserRank(tplayer, 1);
							player.sendMessage(ChatColor.GOLD + "You reset " + tplayer.getName());
							return true;
						}
						if(!tplayer.hasPlayedBefore()){
							player.sendMessage("Could not find the player");
							return true;
						}
					}
					if(tplayer.isOnline()){
						Player tp = Bukkit.getServer().getPlayer(args[0]);
						util.setUserRank(tplayer, 1);
						player.sendMessage(ChatColor.GOLD + "You reset " + tplayer.getName());
						tp.sendMessage(ChatColor.GRAY + "You have been reset");
						return true;
					}
				}
			}else{
				if(args.length == 1){
					OfflinePlayer tplayer = (Bukkit.getServer().getPlayer(args[0]));
					if(tplayer == null){
						tplayer = (Bukkit.getServer().getOfflinePlayer(args[0]));
						if(tplayer.hasPlayedBefore()){
							util.setUserRank(tplayer, 1);
							return true;
						}
						if(!tplayer.hasPlayedBefore()){
							sender.sendMessage("Could not find the player");
							return true;
						}
					}
					if(tplayer.isOnline()){
						util.setUserRank(tplayer, 1);
						return true;
					}
				}
			}
		}
		return true;
	}
}
