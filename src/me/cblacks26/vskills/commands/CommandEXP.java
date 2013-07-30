package me.cblacks26.vskills.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.cblacks26.vskills.Main;
import me.cblacks26.vskills.util.Database;
import me.cblacks26.vskills.util.Ranks;
import me.cblacks26.vskills.util.SkillType;
import me.cblacks26.vskills.util.Util;

public class CommandEXP implements CommandExecutor{
	
	Database sql = new Database(null);
	Util util = new Util();
	Ranks ranks = new Ranks();
	private Main main;
	
	public CommandEXP(Main p){
		main = p;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("EXP")){
			if(sender instanceof Player){
				Player player = (Player) sender;
				if(main.isAuthorized(player, "vskills.exp") == false){
					player.sendMessage(ChatColor.RED + "You don't have the Permissions to use this command");
					return true;
				}
				if(args.length == 0){
					playerRank(player);
				}
				else if(args.length == 1){
					OfflinePlayer tplayer = (Bukkit.getServer().getPlayer(args[0]));
					if(main.isAuthorized(player, "vskills.exp.others") == false){
						player.sendMessage(ChatColor.RED + "You don't have the Permissions to use this command");
						return true;
					}
					if(tplayer == null){
						tplayer = (Bukkit.getServer().getOfflinePlayer(args[0]));
						if(tplayer.hasPlayedBefore()){
							otherPlayerRank(tplayer, player);
						}
						if(!tplayer.hasPlayedBefore()){
							player.sendMessage("Could not find the player");
						}
					}
					if(tplayer.isOnline()){
						otherPlayerRank(tplayer, player);
					}
				}
				else if(args.length > 1){
					player.sendMessage(ChatColor.RED + "Usage: /kill [player]");
				}
			}
			if(!(sender instanceof Player)){
				if(args.length == 1){
					OfflinePlayer tplayer = (Bukkit.getServer().getPlayer(args[0]));
					if(tplayer == null){
						tplayer = (Bukkit.getServer().getOfflinePlayer(args[0]));
						if(tplayer.hasPlayedBefore()){
							consoleRank(sender, tplayer);
						}
						if(!tplayer.hasPlayedBefore()){
							sender.sendMessage("Could not find the player");
						}
					}
					if(tplayer.isOnline()){
						consoleRank(sender, tplayer);
					}
				}else{
					sender.sendMessage(ChatColor.RED + "Usage: /Exp <player>");
				}
			}
		}
	return true;
	}
		
	public void consoleRank(CommandSender sender, OfflinePlayer player){
		int rank = ranks.getRank(player);
		int dlevel = ranks.getLevel(player, SkillType.DIGGING);
		int dexp = ranks.getExpToLevel(player, SkillType.DIGGING);
		int mlevel = ranks.getLevel(player, SkillType.MINING);
		int mexp = ranks.getExpToLevel(player, SkillType.MINING);
		int clevel = ranks.getLevel(player, SkillType.LOGGING);
		int cexp = ranks.getExpToLevel(player, SkillType.LOGGING);
		int blevel = ranks.getLevel(player, SkillType.BUILDING);
		int bexp = ranks.getExpToLevel(player, SkillType.BUILDING);
		int flevel = ranks.getLevel(player, SkillType.FARMING);
		int fexp = ranks.getExpToLevel(player, SkillType.FARMING);
		int hlevel = ranks.getLevel(player, SkillType.HUNTING);
		int hexp = ranks.getExpToLevel(player, SkillType.HUNTING);
		String pname = player.getName();
		String msg = ChatColor.BLUE + "====" + ChatColor.GOLD + pname + ChatColor.BLUE + "====";
		sender.sendMessage(msg);
		sender.sendMessage(ChatColor.GOLD + "Rank: " + rank);
		sender.sendMessage(ChatColor.GOLD + "Digging Level: " + dlevel + " - XP to Level Up: " + dexp);
		sender.sendMessage(ChatColor.GOLD + "Mining Level: " + mlevel + " - XP to Level Up: " + mexp);
		sender.sendMessage(ChatColor.GOLD + "Logging Level: " + clevel + " - XP to Level Up: " + cexp);
		sender.sendMessage(ChatColor.GOLD + "Building Level: " + blevel + " - XP to Level Up: " + bexp);
		sender.sendMessage(ChatColor.GOLD + "Farming Level: " + flevel + " - XP to Level Up: " + fexp);
		sender.sendMessage(ChatColor.GOLD + "Hunting Level: " + hlevel + " - XP to Level Up: " + hexp);
		sender.sendMessage(ChatColor.BLUE + "==================");
	}
	public void otherPlayerRank(OfflinePlayer offPlayer, Player player){
		int rank = ranks.getRank(offPlayer);
		int dlevel = ranks.getLevel(offPlayer, SkillType.DIGGING);
		int dexp = ranks.getExpToLevel(offPlayer, SkillType.DIGGING);
		int mlevel = ranks.getLevel(offPlayer, SkillType.MINING);
		int mexp = ranks.getExpToLevel(offPlayer, SkillType.MINING);
		int clevel = ranks.getLevel(offPlayer, SkillType.LOGGING);
		int cexp = ranks.getExpToLevel(offPlayer, SkillType.LOGGING);
		int blevel = ranks.getLevel(offPlayer, SkillType.BUILDING);
		int bexp = ranks.getExpToLevel(offPlayer, SkillType.BUILDING);
		int flevel = ranks.getLevel(offPlayer, SkillType.FARMING);
		int fexp = ranks.getExpToLevel(offPlayer, SkillType.FARMING);
		int hlevel = ranks.getLevel(offPlayer, SkillType.HUNTING);
		int hexp = ranks.getExpToLevel(offPlayer, SkillType.HUNTING);
		String pname = offPlayer.getName();
		String msg = ChatColor.BLUE + "====" + ChatColor.GOLD + pname + ChatColor.BLUE + "====";
		player.sendMessage(msg);
		player.sendMessage(ChatColor.GOLD + "Rank: " + rank);
		player.sendMessage(ChatColor.GOLD + "Digging Level: " + dlevel + " - XP to Level Up: " + dexp);
		player.sendMessage(ChatColor.GOLD + "Mining Level: " + mlevel + " - XP to Level Up: " + mexp);
		player.sendMessage(ChatColor.GOLD + "Logging Level: " + clevel + " - XP to Level Up: " + cexp);
		player.sendMessage(ChatColor.GOLD + "Building Level: " + blevel + " - XP to Level Up: " + bexp);
		player.sendMessage(ChatColor.GOLD + "Farming Level: " + flevel + " - XP to Level Up: " + fexp);
		player.sendMessage(ChatColor.GOLD + "Hunting Level: " + hlevel + " - XP to Level Up: " + hexp);
		player.sendMessage(ChatColor.BLUE + "==================");
	}
	public void playerRank(Player player){
		int rank = ranks.getRank(player);
		int dlevel = ranks.getLevel(player, SkillType.DIGGING);
		int dexp = ranks.getExpToLevel(player, SkillType.DIGGING);
		int mlevel = ranks.getLevel(player, SkillType.MINING);
		int mexp = ranks.getExpToLevel(player, SkillType.MINING);
		int clevel = ranks.getLevel(player, SkillType.LOGGING);
		int cexp = ranks.getExpToLevel(player, SkillType.LOGGING);
		int blevel = ranks.getLevel(player, SkillType.BUILDING);
		int bexp = ranks.getExpToLevel(player, SkillType.BUILDING);
		int flevel = ranks.getLevel(player, SkillType.FARMING);
		int fexp = ranks.getExpToLevel(player, SkillType.FARMING);
		int hlevel = ranks.getLevel(player, SkillType.HUNTING);
		int hexp = ranks.getExpToLevel(player, SkillType.HUNTING);
		String pname = player.getName();
		String msg = ChatColor.BLUE + "====" + ChatColor.GOLD + pname + ChatColor.BLUE + "====";
		player.sendMessage(msg);
		player.sendMessage(ChatColor.GOLD + "Rank: " + rank);
		player.sendMessage(ChatColor.GOLD + "Digging Level: " + dlevel + " - XP to Level Up: " + dexp);
		player.sendMessage(ChatColor.GOLD + "Mining Level: " + mlevel + " - XP to Level Up: " + mexp);
		player.sendMessage(ChatColor.GOLD + "Logging Level: " + clevel + " - XP to Level Up: " + cexp);
		player.sendMessage(ChatColor.GOLD + "Building Level: " + blevel + " - XP to Level Up: " + bexp);
		player.sendMessage(ChatColor.GOLD + "Farming Level: " + flevel + " - XP to Level Up: " + fexp);
		player.sendMessage(ChatColor.GOLD + "Hunting Level: " + hlevel + " - XP to Level Up: " + hexp);
		player.sendMessage(ChatColor.BLUE + "==================");
	}
}
