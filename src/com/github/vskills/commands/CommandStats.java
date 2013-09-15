package com.github.vskills.commands;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.vskills.Main;
import com.github.vskills.datatypes.Commands;

public class CommandStats implements CommandExecutor{

	private Connection c;
	private Statement s;
	private ResultSet rs;
	private String line = "========================================";
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("VStats")){
			if(sender instanceof Player){
				Player player = (Player) sender;
				if(args.length == 1){
					if(Main.isAuthorized(player, Commands.VSTATS.getPerm()) == false){
						player.sendMessage(ChatColor.RED + "You don't have the Permissions for this command");
						return true;
					}else{
						if(args[0].equalsIgnoreCase("Kills")) lbKills(sender);
						else if(args[0].equalsIgnoreCase("Rank")) lbRank(sender);
						else if(args[0].equalsIgnoreCase("Building")) lbLevels(sender, args[0]);
						else if(args[0].equalsIgnoreCase("Digging")) lbLevels(sender, args[0]);
						else if(args[0].equalsIgnoreCase("Farming")) lbLevels(sender, args[0]);
						else if(args[0].equalsIgnoreCase("Hunting")) lbLevels(sender, args[0]);
						else if(args[0].equalsIgnoreCase("Mining")) lbLevels(sender, args[0]);
						else if(args[0].equalsIgnoreCase("Woodcutting")) lbLevels(sender, args[0]);
						else if(args[0].equalsIgnoreCase("Archery")) lbLevels(sender, args[0]);
						else if(args[0].equalsIgnoreCase("Axe")) lbLevels(sender, args[0]);
						else if(args[0].equalsIgnoreCase("Hoe")) lbLevels(sender, args[0]);
						else if(args[0].equalsIgnoreCase("Pickaxe")) lbLevels(sender, args[0]);
						else if(args[0].equalsIgnoreCase("Shovel")) lbLevels(sender, args[0]);
						else if(args[0].equalsIgnoreCase("Sword")) lbLevels(sender, args[0]);
						else if(args[0].equalsIgnoreCase("Unarmed")) lbLevels(sender, args[0]);
						else{
							player.sendMessage(ChatColor.RED + "Options: Kills, KD, Rank, Builing, Digging, Farming, Hunting, Logging, Mining");
						}
					}
				}else{
					player.sendMessage(ChatColor.RED + "Usage: " + Commands.VSTATS.getUsage());
				}
			}else{
				if(args.length == 1){
					if(args[0].equalsIgnoreCase("Kills")) lbKills(sender);
					else if(args[0].equalsIgnoreCase("Rank")) lbRank(sender);
					else if(args[0].equalsIgnoreCase("Building")) lbLevels(sender, args[0]);
					else if(args[0].equalsIgnoreCase("Digging")) lbLevels(sender, args[0]);
					else if(args[0].equalsIgnoreCase("Farming")) lbLevels(sender, args[0]);
					else if(args[0].equalsIgnoreCase("Hunting")) lbLevels(sender, args[0]);
					else if(args[0].equalsIgnoreCase("Mining")) lbLevels(sender, args[0]);
					else if(args[0].equalsIgnoreCase("Woodcutting")) lbLevels(sender, args[0]);
					else if(args[0].equalsIgnoreCase("Archery")) lbLevels(sender, args[0]);
					else if(args[0].equalsIgnoreCase("Axe")) lbLevels(sender, args[0]);
					else if(args[0].equalsIgnoreCase("Hoe")) lbLevels(sender, args[0]);
					else if(args[0].equalsIgnoreCase("Pickaxe")) lbLevels(sender, args[0]);
					else if(args[0].equalsIgnoreCase("Shovel")) lbLevels(sender, args[0]);
					else if(args[0].equalsIgnoreCase("Sword")) lbLevels(sender, args[0]);
					else if(args[0].equalsIgnoreCase("Unarmed")) lbLevels(sender, args[0]);
					else{
						sender.sendMessage(ChatColor.RED + "Options: Kills, KD, Rank, Builing, Digging, Farming, Hunting, Logging, Mining");
					}
				}else{
					sender.sendMessage(ChatColor.RED + "Usage: " + Commands.VSTATS.getUsage());
				}
			}
		}
		return true;
	}

	public void lbKills(CommandSender sender){
		int count = 0;
		try{
			sender.sendMessage(ChatColor.BLUE + "=========" + ChatColor.GOLD + " Leaderboards - Kills " + ChatColor.BLUE + "=========");
			Main.sql.open();
			c = Main.sql.getConnection();
			s = c.createStatement();
			rs = s.executeQuery("SELECT * FROM VSkills ORDER BY kills DESC");
			for(count=0;count < 9;count++){
				if(rs.next()){
					sender.sendMessage(ChatColor.GOLD + rs.getString("name") + ": Kills - " + rs.getInt("kills"));
				}
			}
			sender.sendMessage(ChatColor.BLUE + line);
			rs.close();
			s.close();
			c.close();
		}catch(SQLException e){
			Main.writeError("Error at Command VStats: " + e.getMessage());
		}
	}

	public void lbRank(CommandSender sender){
		int count = 0;
		try{
			sender.sendMessage(ChatColor.BLUE + "==========" + ChatColor.GOLD + "Leaderboards -- Rank" + ChatColor.BLUE + "==========");
			Main.sql.open();
			c = Main.sql.getConnection();
			s = c.createStatement();
			rs = s.executeQuery("SELECT * FROM VSkills ORDER BY rank DESC");
			for(count=0;count < 9;count++){
				if(rs.next()){
					sender.sendMessage(ChatColor.GOLD + rs.getString("name") + ": Rank - " + rs.getInt("rank"));
				}
			}
			sender.sendMessage(ChatColor.BLUE + line);
			rs.close();
			s.close();
			c.close();
		}catch(SQLException e){
			Main.writeError("Error at leaderboards: " + e.getMessage());
		}
	}

	public void lbLevels(CommandSender sender, String type){
		int count = 0;
		String t = type.toLowerCase();
		try{
			sender.sendMessage(ChatColor.BLUE + "========" + ChatColor.GOLD + " Leaderboards -- Levels " + ChatColor.BLUE + "========");
			Main.sql.open();
			c = Main.sql.getConnection();
			s = c.createStatement();
			rs = s.executeQuery("SELECT * FROM VSkills_levels ORDER BY " + t + " DESC");
			for(count=0;count < 9;count++){
				if(rs.next()){
					sender.sendMessage(ChatColor.GOLD + rs.getString("name") + ": " + type + " - " + rs.getInt(t));
				}
			}
			sender.sendMessage(ChatColor.BLUE + line);
			rs.close();
			s.close();
			c.close();
		}catch(SQLException e){
			Main.writeError("Error at leaderboards: " + e.getMessage());
		}
	}
	
}
