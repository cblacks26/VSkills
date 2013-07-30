package me.cblacks26.vskills.commands;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import me.cblacks26.vskills.Main;
import me.cblacks26.vskills.util.Database;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandLeaderboards implements CommandExecutor{

	private Main main;
	Logger log = Logger.getLogger("minecraft");
	Database sql = new Database(null);
	
	Connection c;
	Statement s;
	ResultSet rs;
	
	public CommandLeaderboards(Main p){
		main = p;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("Leaderboards")){
			if(sender instanceof Player){
				Player player = (Player) sender;
				if(args.length == 1){
					String t = args[0];
					String type = t.toUpperCase();
					if(main.isAuthorized(player, "vskills.leaderboards") == false){
						player.sendMessage(ChatColor.RED + "You don't have the Permissions for this command");
						return true;
					}else{
						switch(type){
						case "KILLS": lbKills(player);
						case "KD": lbKD(player);
						case "RANK": lbRank(player);
						case "BUILDING": lbLevels(player, type);
						case "DIGGING": lbLevels(player, type);
						case "FARMING": lbLevels(player, type);
						case "HUNTING": lbLevels(player, type);
						case "LOGGING": lbLevels(player, type);
						case "MINING": lbLevels(player, type);
						default: player.sendMessage(ChatColor.RED + "Options: Kills, KD, Rank, Builing, Digging, Farming, Hunting, Logging, Mining");
						}
					}
				}
				if(args.length == 0 || args.length > 1){
					player.sendMessage(ChatColor.RED + "Usage: /Leaderboards <options>");
				}
			}
			if(!(sender instanceof Player)){
				if(args.length == 1){
					String t = args[0];
					String type = t.toUpperCase();
					switch(type){
					case "KILLS": lbKills(sender);
					case "KD": lbKD(sender);
					case "RANK": lbRank(sender);
					case "BUILDING": lbLevels(sender, type);
					case "DIGGING": lbLevels(sender, type);
					case "FARMING": lbLevels(sender, type);
					case "HUNTING": lbLevels(sender, type);
					case "LOGGING": lbLevels(sender, type);
					case "MINING": lbLevels(sender, type);
					default: sender.sendMessage(ChatColor.RED + "Options: Kills, KD, Rank, Builing, Digging, Farming, Hunting, Logging, Mining");
					}
				}
				if(args.length == 0 || args.length > 1){
					sender.sendMessage(ChatColor.RED + "Usage: /Leaderboards <options>");
				}
			}
		}
		return true;
	}
	
	public void lbKills(CommandSender sender){
		int count = 0;
		try{
			sender.sendMessage(ChatColor.BLUE + "====" + ChatColor.GOLD + "Leaderboards - Kills" + ChatColor.BLUE + "====");
			c = sql.getConnection();
			s = c.createStatement();
			rs = s.executeQuery("SELECT * FROM STATS ORDER BY KILLS DESC");
			while(count < 9){
				while(rs.next()){
					count++;
					sender.sendMessage(ChatColor.GOLD + rs.getString(1) + ": Kills - " + rs.getInt(2) + " K/D - " + rs.getInt(4));
				}
			}
			sender.sendMessage(ChatColor.BLUE + "============================");
		}catch(SQLException e){
			log.severe("[Skills] Error at leaderboards: " + e.getMessage());
		}finally{
			sql.close(rs);
			sql.close(s);
			sql.close(c);
		}
	}

	public void lbKD(CommandSender sender){
		int count = 0;
		try{
			sender.sendMessage(ChatColor.BLUE + "====" + ChatColor.GOLD + "Leaderboards - K/D" + ChatColor.BLUE + "====");
			c = sql.getConnection();
			s = c.createStatement();
			rs = s.executeQuery("SELECT * FROM STATS ORDER BY KD DESC");
			while(count < 9){
				while(rs.next()){
					count++;
					sender.sendMessage(ChatColor.GOLD + rs.getString(1) + ": K/D Ratio - " + rs.getInt(4) + " Kills- " + rs.getInt(2));
				}
			}
			sender.sendMessage(ChatColor.BLUE + "==========================");
		}catch(SQLException e){
			log.severe("[Skills] Error at leaderboards: " + e.getMessage());
		}finally{
			sql.close(rs);
			sql.close(s);
			sql.close(c);
		}
	}

	public void lbRank(CommandSender sender){
		int count = 0;
		try{
			sender.sendMessage(ChatColor.BLUE + "====" + ChatColor.GOLD + "Leaderboards - Rank" + ChatColor.BLUE + "====");
			c = sql.getConnection();
			s = c.createStatement();
			rs = s.executeQuery("SELECT * FROM LEVELS ORDER BY RANK DESC");
			while(count < 9){
				while(rs.next()){
					count++;
					sender.sendMessage(ChatColor.GOLD + rs.getString(1) + ": " + rs.getInt("RANK"));
				}
			}
			sender.sendMessage(ChatColor.BLUE + "===========================");
		}catch(SQLException e){
			log.severe("[Skills] Error at leaderboards: " + e.getMessage());
		}finally{
			sql.close(rs);
			sql.close(s);
			sql.close(c);
		}
	}

	public void lbLevels(CommandSender sender, String type){
		int count = 0;
		try{
			sender.sendMessage(ChatColor.BLUE + "====" + ChatColor.GOLD + "Leaderboards - Levels" + ChatColor.BLUE + "====");
			c = sql.getConnection();
			s = c.createStatement();
			rs = s.executeQuery("SELECT * FROM LEVELS ORDER BY " + type + " DESC");
			while(count < 9){
				while(rs.next()){
					count++;
					sender.sendMessage(ChatColor.GOLD + rs.getString(1) + ": " + type + " - " + rs.getInt(type));
				}
			}
			sender.sendMessage(ChatColor.BLUE + "=============================");
		}catch(SQLException e){
			log.severe("[Skills] Error at leaderboards: " + e.getMessage());
		}finally{
			sql.close(rs);
			sql.close(s);
			sql.close(c);
		}
	}

}
