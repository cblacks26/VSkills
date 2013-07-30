package me.cblacks26.vskills.commands;

import me.cblacks26.vskills.Main;
import me.cblacks26.vskills.util.Database;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandStats implements CommandExecutor{
	
	Database sql = new Database(null);
	private Main main;
	
	public CommandStats(Main p){
		main = p;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("Stats")){
			if(sender instanceof Player){
				Player player = (Player) sender;
				if(args.length == 0){
					if(main.isAuthorized(player, "vskills.stats") == false){
						player.sendMessage(ChatColor.RED + "You don't have the Permissions for this command");
						return true;
					}else{
						playerStats(player);
					}
				}
				if(args.length == 1){
					OfflinePlayer tplayer = (Bukkit.getServer().getPlayer(args[0]));
					if(main.isAuthorized(player, "vskills.stats.others") == false){
						player.sendMessage(ChatColor.RED + "You don't have the Permissions for this command");
						return true;
					}
					if(tplayer == null){
						tplayer = (Bukkit.getServer().getOfflinePlayer(args[0]));
						if(tplayer.hasPlayedBefore()){
							otherPlayerStats(tplayer, player);
						}
						if(!tplayer.hasPlayedBefore()){
							player.sendMessage("Could not find the player");
						}
					}
					if(tplayer.isOnline()){
						otherPlayerStats(tplayer, player);
					}
				}
				if(args.length > 1){
					player.sendMessage("Usage: /Stats [player]");
				}
			}
			if(!(sender instanceof Player)){
				if(args.length == 1){
					OfflinePlayer tplayer = (Bukkit.getServer().getPlayer(args[0]));
					if(tplayer == null){
						tplayer = (Bukkit.getServer().getOfflinePlayer(args[0]));
						if(tplayer.hasPlayedBefore()){
							consoleStats(sender, tplayer);
						}
						if(!tplayer.hasPlayedBefore()){
							sender.sendMessage("Could not find the player");
						}
					}
					if(tplayer.isOnline()){
						consoleStats(sender, tplayer);
					}
				}else{
					sender.sendMessage(ChatColor.RED + "Usage: /Stats <player>");
				}
			}
		}
		return true;
	}
	
	public void playerStats(Player player){
		int kills = sql.getInt(player.getName(), "STATS", "KILLS");
		int deaths = sql.getInt(player.getName(), "STATS", "DEATHS");
		double kd = sql.getDouble(player.getName(), "STATS", "KD");
		String pname = player.getName();
		String msg = ChatColor.BLUE + "====" + ChatColor.GOLD + pname + ChatColor.BLUE + "====";
		player.sendMessage(msg);
		player.sendMessage(ChatColor.GOLD + "Kills: " + kills);
		player.sendMessage(ChatColor.GOLD + "Deaths: " + deaths);
		player.sendMessage(ChatColor.GOLD + "KD: " + kd);
		player.sendMessage(ChatColor.BLUE + "=================");
	}
	
	public void otherPlayerStats(OfflinePlayer offPlayer, Player player){
		int kills = sql.getInt(offPlayer.getName(), "STATS", "KILLS");
		int deaths = sql.getInt(offPlayer.getName(), "STATS", "DEATHS");
		double kd = sql.getDouble(offPlayer.getName(), "STATS", "KD");
		String pname = offPlayer.getName();
		String msg = ChatColor.BLUE + "====" + ChatColor.GOLD + pname + ChatColor.BLUE + "====";
		player.sendMessage(msg);
		player.sendMessage(ChatColor.GOLD + "Kills: " + kills);
		player.sendMessage(ChatColor.GOLD + "Deaths: " + deaths);
		player.sendMessage(ChatColor.GOLD + "KD: " + kd);
		player.sendMessage(ChatColor.BLUE + "=================");
	}
	
	public void consoleStats(CommandSender sender, OfflinePlayer tplayer){
		int kills = sql.getInt(tplayer.getName(), "STATS", "KILLS");
		int deaths = sql.getInt(tplayer.getName(), "STATS", "DEATHS");
		double kd = sql.getDouble(tplayer.getName(), "STATS", "KD");
		String pname = tplayer.getName();
		String msg = ChatColor.BLUE + "====" + ChatColor.GOLD + pname + ChatColor.BLUE + "====";
		sender.sendMessage(msg);
		sender.sendMessage(ChatColor.GOLD + "Kills: " + kills);
		sender.sendMessage(ChatColor.GOLD + "Deaths: " + deaths);
		sender.sendMessage(ChatColor.GOLD + "KD: " + kd);
		sender.sendMessage(ChatColor.BLUE + "=================");
	}
	
}
