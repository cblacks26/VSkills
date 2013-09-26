package com.github.vskills.commands;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.vskills.Main;
import com.github.vskills.datatypes.Commands;
import com.github.vskills.datatypes.JobType;
import com.github.vskills.datatypes.SkillType;
import com.github.vskills.util.UserManager;

public class CommandSet implements CommandExecutor{

	private Connection c;
	private Statement s;
	UserManager userManager = Main.getUserManager();
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("VSet")){
			if(sender instanceof Player){
				Player player = (Player) sender;
				if(Main.isAuthorized(player, "VSkills.set") == false){
					player.sendMessage(ChatColor.RED + "You don't have the Permissions for this command");
					return true;
				}else{
					if(args.length == 3){
						if(args[1].equalsIgnoreCase("Rank") || args[1].equalsIgnoreCase("Ranks")){
							if(Main.isInteger(args[2])){
								OfflinePlayer tplayer = (Bukkit.getServer().getOfflinePlayer(args[0]));
								if(!tplayer.isOnline()){
									if(tplayer.hasPlayedBefore()){
										setOfflinePlayerRank(tplayer, Integer.parseInt(args[2]));
										player.sendMessage(ChatColor.BLUE + "You have set " + tplayer.getName() + "'s Rank and Job Levels to " + args[2]);
										return true;
									}else{
										player.sendMessage("Could not find the player " + args[0]);
										return true;
									}
								}else{
									Player tp = Bukkit.getServer().getPlayer(args[0]);
									setOnlinePlayerRank(tp, Integer.parseInt(args[2]));
									userManager.scoreboard(tp);
									player.sendMessage(ChatColor.BLUE + "You have set " + tp.getName() + "'s Rank and Job Levels to " + args[2]);
									return true;
								}
							}
						}else{
							
						}
					}else if(args.length == 5){
						OfflinePlayer tplayer = (Bukkit.getServer().getPlayer(args[0]));
						if(tplayer == null){
							tplayer = (Bukkit.getServer().getOfflinePlayer(args[0]));
							if(tplayer.hasPlayedBefore()){
								if(Main.isInteger(args[4])){
									if(args[1].equalsIgnoreCase("Jobs") || args[1].equalsIgnoreCase("Job")){
										if(args[2].equalsIgnoreCase("all")){
											if(args[3].equalsIgnoreCase("Level") || args[3].equalsIgnoreCase("Levels")){
												for(JobType job : JobType.values()){
													setOfflinePlayerLevel(tplayer, job, Integer.parseInt(args[4]));
												}
												player.sendMessage("You Changed " + tplayer.getName() + "'s Job Levels to " + args[4]);
											}else if(args[3].equalsIgnoreCase("EXP") || args[3].equalsIgnoreCase("XP")){
												for(JobType job : JobType.values()){
													setOfflinePlayerXP(tplayer, job, Integer.parseInt(args[4]));
												}
												player.sendMessage("You Changed " + tplayer.getName() + "'s Job Experience to " + args[4]);
											}else{
												player.sendMessage("Usage: " + Commands.VSET.getUsage());
											}
										}else if(Main.matchJob(args[2]) == null){
											player.sendMessage(args[2] + " is not a Job");
										}else{
											JobType job = Main.matchJob(args[2]);
											if(args[3].equalsIgnoreCase("Level") || args[3].equalsIgnoreCase("Levels")){
												setOfflinePlayerLevel(tplayer, job, Integer.parseInt(args[4]));
												player.sendMessage("You Changed " + tplayer.getName() + "'s " + job.getName() + " Level to " + args[4]);
											}
										}
									}else if(args[1].equalsIgnoreCase("Skills") || args[1].equalsIgnoreCase("Skill")){
										if(args[2].equalsIgnoreCase("all")){
											for(SkillType skill : SkillType.values()){
												setOfflinePlayerLevel(tplayer, skill, Integer.parseInt(args[4]));
											}
											player.sendMessage("You Changed " + tplayer.getName() + "'s Job Levels to " + args[4]);
										}else if(args[3].equalsIgnoreCase("EXP") || args[3].equalsIgnoreCase("XP")){
											for(SkillType skill : SkillType.values()){
												setOfflinePlayerXP(tplayer, skill, Integer.parseInt(args[4]));
											}
											player.sendMessage("You Changed " + tplayer.getName() + "'s Job Experience to " + args[4]);
										}else if(Main.matchSkill(args[2]) == null){
											player.sendMessage(args[2] + " is not a skill");
										}else{
											SkillType skill = Main.matchSkill(args[2]);
											if(args[3].equalsIgnoreCase("Level") || args[3].equalsIgnoreCase("Levels")){
												setOfflinePlayerLevel(tplayer, skill, Integer.parseInt(args[4]));
												player.sendMessage("You Changed " + tplayer.getName() + "'s " + skill.getName() + " Level to " + args[4]);
											}else if(args[3].equalsIgnoreCase("EXP") || args[3].equalsIgnoreCase("XP")){
												setOfflinePlayerXP(tplayer, skill, Integer.parseInt(args[4]));
												player.sendMessage("You Changed " + tplayer.getName() + "'s " + skill.getName() + " Experience to " + args[4]);
											}else{
												player.sendMessage("Usage: " + Commands.VSET.getUsage());
											}
										}
									}else{
										player.sendMessage("Usage: " + Commands.VSET.getUsage());
									}
								}
							}else{
								player.sendMessage("Couldn't find player " + args[0]);
							}
						}else{
							Player tp = Bukkit.getServer().getPlayer(args[0]);
							if(Main.isInteger(args[4])){
								if(args[1].equalsIgnoreCase("Jobs") || args[1].equalsIgnoreCase("Job")){
									if(args[2].equalsIgnoreCase("all")){
										if(args[3].equalsIgnoreCase("Level") || args[3].equalsIgnoreCase("Levels")){
											for(JobType job : JobType.values()){
												setOnlinePlayerLevel(tp, job, Integer.parseInt(args[4]));
											}
											player.sendMessage("You Changed " + tp.getName() + "'s Job Levels to " + args[4]);
										}else if(args[3].equalsIgnoreCase("EXP") || args[3].equalsIgnoreCase("XP")){
											for(JobType job : JobType.values()){
												setOnlinePlayerXP(tp, job, Integer.parseInt(args[4]));
											}
											player.sendMessage("You Changed " + tp.getName() + "'s Job Experience to " + args[4]);
										}else{
											player.sendMessage("Usage: " + Commands.VSET.getUsage());
										}
									}else if(Main.matchJob(args[2]) == null){
										player.sendMessage(args[2] + " is not a Job");
									}else{
										JobType job = Main.matchJob(args[2]);
										if(args[3].equalsIgnoreCase("Level") || args[3].equalsIgnoreCase("Levels")){
											setOnlinePlayerLevel(tp, job, Integer.parseInt(args[4]));
											player.sendMessage("You Changed " + tp.getName() + "'s " + job.getName() + " Level to " + args[4]);
										}
									}
								}else if(args[1].equalsIgnoreCase("Skills") || args[1].equalsIgnoreCase("Skill")){
									if(args[2].equalsIgnoreCase("all")){
										if(args[3].equalsIgnoreCase("Level") || args[3].equalsIgnoreCase("Levels")){
											for(SkillType skill : SkillType.values()){
												setOnlinePlayerLevel(tp, skill, Integer.parseInt(args[4]));
											}
											player.sendMessage("You Changed " + tp.getName() + "'s Job Levels to " + args[4]);
										}else if(args[3].equalsIgnoreCase("EXP") || args[3].equalsIgnoreCase("XP")){
											for(SkillType skill : SkillType.values()){
												setOnlinePlayerXP(tp, skill, Integer.parseInt(args[4]));
											}
											player.sendMessage("You Changed " + tp.getName() + "'s Job Experience to " + args[4]);
										}
									}else if(Main.matchSkill(args[2]) == null){
										player.sendMessage(args[2] + " is not a skill");
									}else{
										SkillType skill = Main.matchSkill(args[2]);
										if(args[3].equalsIgnoreCase("Level") || args[3].equalsIgnoreCase("Levels")){
											setOnlinePlayerLevel(tp, skill, Integer.parseInt(args[4]));
											player.sendMessage("You Changed " + tp.getName() + "'s " + skill.getName() + " Level to " + args[4]);
										}else if(args[3].equalsIgnoreCase("EXP") || args[3].equalsIgnoreCase("XP")){
											setOnlinePlayerXP(tp, skill, Integer.parseInt(args[4]));
											player.sendMessage("You Changed " + tp.getName() + "'s " + skill.getName() + " Experience to " + args[4]);
										}else{
											player.sendMessage("Usage: " + Commands.VSET.getUsage());
										}
									}
								}
							}
						}
					}else{
						player.sendMessage("Usage: " + Commands.VSET.getUsage());
					}
				}
			}else{
				if(args.length == 3){
					if(args[1].equalsIgnoreCase("Rank") || args[1].equalsIgnoreCase("Ranks")){
						if(Main.isInteger(args[2])){
							OfflinePlayer tplayer = (Bukkit.getServer().getOfflinePlayer(args[0]));
							if(!tplayer.isOnline()){
								if(tplayer.hasPlayedBefore()){
									setOfflinePlayerRank(tplayer, Integer.parseInt(args[2]));
									sender.sendMessage(ChatColor.BLUE + "You have set " + tplayer.getName() + "'s Rank and Job Levels to " + args[2]);
									return true;
								}else{
									sender.sendMessage("Could not find the player " + args[0]);
									return true;
								}
							}else{
								Player tp = Bukkit.getServer().getPlayer(args[0]);
								setOnlinePlayerRank(tp, Integer.parseInt(args[2]));
								userManager.scoreboard(tp);
								sender.sendMessage(ChatColor.BLUE + "You have set " + tp.getName() + "'s Rank and Job Levels to " + args[2]);
								tp.sendMessage(ChatColor.GRAY + "Your Rank and Job Levels have been set to " + args[2]);
								return true;
							}
						}
					}
				}else if(args.length == 5){
					OfflinePlayer tplayer = (Bukkit.getServer().getOfflinePlayer(args[0]));
					if(!tplayer.isOnline()){
						if(tplayer.hasPlayedBefore()){
							if(Main.isInteger(args[4])){
								if(args[1].equalsIgnoreCase("Jobs") || args[1].equalsIgnoreCase("Job")){
									if(args[2].equalsIgnoreCase("all")){
										if(args[3].equalsIgnoreCase("Level") || args[3].equalsIgnoreCase("Levels")){
											for(JobType job : JobType.values()){
												setOfflinePlayerLevel(tplayer, job, Integer.parseInt(args[4]));
											}
											sender.sendMessage("You Changed " + tplayer.getName() + "'s Job Levels to " + args[4]);
										}else if(args[3].equalsIgnoreCase("EXP") || args[3].equalsIgnoreCase("XP")){
											for(JobType job : JobType.values()){
												setOfflinePlayerXP(tplayer, job, Integer.parseInt(args[4]));
											}
											sender.sendMessage("You Changed " + tplayer.getName() + "'s Job Experience to " + args[4]);
										}else{
											sender.sendMessage("Usage: " + Commands.VSET.getUsage());
										}
									}else if(Main.matchJob(args[2]) == null){
										sender.sendMessage(args[2] + " is not a Job");
									}else{
										JobType job = Main.matchJob(args[2]);
										if(args[3].equalsIgnoreCase("Level") || args[3].equalsIgnoreCase("Levels")){
											setOfflinePlayerLevel(tplayer, job, Integer.parseInt(args[4]));
											sender.sendMessage("You Changed " + tplayer.getName() + "'s " + job.getName() + " Level to " + args[4]);
										}
									}
								}else if(args[1].equalsIgnoreCase("Skills") || args[1].equalsIgnoreCase("Skill")){
									if(args[2].equalsIgnoreCase("all")){
										if(args[3].equalsIgnoreCase("Level") || args[3].equalsIgnoreCase("Levels")){
											for(SkillType skill : SkillType.values()){
												setOfflinePlayerLevel(tplayer, skill, Integer.parseInt(args[4]));
											}
											sender.sendMessage("You Changed " + tplayer.getName() + "'s Job Levels to " + args[4]);
										}else if(args[3].equalsIgnoreCase("EXP") || args[3].equalsIgnoreCase("XP")){
											for(SkillType skill : SkillType.values()){
												setOfflinePlayerXP(tplayer, skill, Integer.parseInt(args[4]));
											}
											sender.sendMessage("You Changed " + tplayer.getName() + "'s Job Experience to " + args[4]);
										}
									}else if(args[3].equalsIgnoreCase("EXP") || args[3].equalsIgnoreCase("XP")){
										for(SkillType skill : SkillType.values()){
											setOfflinePlayerXP(tplayer, skill, Integer.parseInt(args[4]));
										}
										sender.sendMessage("You Changed " + tplayer.getName() + "'s Job Experience to " + args[4]);
									}else if(Main.matchSkill(args[2]) == null){
										sender.sendMessage(args[2] + " is not a skill");
									}else{
										SkillType skill = Main.matchSkill(args[2]);
										if(args[3].equalsIgnoreCase("Level") || args[3].equalsIgnoreCase("Levels")){
											setOfflinePlayerLevel(tplayer, skill, Integer.parseInt(args[4]));
											sender.sendMessage("You Changed " + tplayer.getName() + "'s " + skill.getName() + " Level to " + args[4]);
										}else if(args[3].equalsIgnoreCase("EXP") || args[3].equalsIgnoreCase("XP")){
											setOfflinePlayerXP(tplayer, skill, Integer.parseInt(args[4]));
											sender.sendMessage("You Changed " + tplayer.getName() + "'s " + skill.getName() + " Experience to " + args[4]);
										}else{
											sender.sendMessage("Usage: " + Commands.VSET.getUsage());
										}
									}
								}else{
									sender.sendMessage("Usage: " + Commands.VSET.getUsage());
								}
							}
						}else{
							sender.sendMessage("Couldn't find the player " + args[0]);
						}
					}else{
						Player tp = Bukkit.getServer().getPlayer(args[0]);
						if(Main.isInteger(args[4])){
							if(args[1].equalsIgnoreCase("Jobs") || args[1].equalsIgnoreCase("Job")){
								if(args[2].equalsIgnoreCase("all")){
									if(args[3].equalsIgnoreCase("Level") || args[3].equalsIgnoreCase("Levels")){
										for(JobType job : JobType.values()){
											setOnlinePlayerLevel(tp, job, Integer.parseInt(args[4]));
										}
										sender.sendMessage("You Changed " + tp.getName() + "'s Job Levels to " + args[4]);
									}else if(args[3].equalsIgnoreCase("EXP") || args[3].equalsIgnoreCase("XP")){
										for(JobType job : JobType.values()){
											setOnlinePlayerXP(tp, job, Integer.parseInt(args[4]));
										}
										sender.sendMessage("You Changed " + tp.getName() + "'s Job Experience to " + args[4]);
									}else{
										sender.sendMessage("Usage: " + Commands.VSET.getUsage());
									}
								}else if(Main.matchJob(args[2]) == null){
									sender.sendMessage(args[2] + " is not a Job");
								}else{
									JobType job = Main.matchJob(args[2]);
									if(args[3].equalsIgnoreCase("Level") || args[3].equalsIgnoreCase("Levels")){
										setOnlinePlayerLevel(tp, job, Integer.parseInt(args[4]));
										sender.sendMessage("You Changed " + tp.getName() + "'s " + job.getName() + " Level to " + args[4]);
									}
								}
							}else if(args[1].equalsIgnoreCase("Skills") || args[1].equalsIgnoreCase("Skill")){
								if(args[2].equalsIgnoreCase("all")){
									for(SkillType skill : SkillType.values()){
										setOnlinePlayerLevel(tp, skill, Integer.parseInt(args[4]));
									}
									sender.sendMessage("You Changed " + tp.getName() + "'s Job Levels to " + args[4]);
								}else if(args[3].equalsIgnoreCase("EXP") || args[3].equalsIgnoreCase("XP")){
									for(SkillType skill : SkillType.values()){
										setOnlinePlayerXP(tp, skill, Integer.parseInt(args[4]));
									}
									sender.sendMessage("You Changed " + tp.getName() + "'s Job Experience to " + args[4]);
								}else if(Main.matchSkill(args[2]) == null){
									sender.sendMessage(args[2] + " is not a skill");
								}else{
									SkillType skill = Main.matchSkill(args[2]);
									if(args[3].equalsIgnoreCase("Level") || args[3].equalsIgnoreCase("Levels")){
										setOnlinePlayerLevel(tp, skill, Integer.parseInt(args[4]));
										sender.sendMessage("You Changed " + tp.getName() + "'s " + skill.getName() + " Level to " + args[4]);
									}else if(args[3].equalsIgnoreCase("EXP") || args[3].equalsIgnoreCase("XP")){
										setOnlinePlayerXP(tp, skill, Integer.parseInt(args[4]));
										sender.sendMessage("You Changed " + tp.getName() + "'s " + skill.getName() + " Experience to " + args[4]);
									}else{
										sender.sendMessage("Usage: " + Commands.VSET.getUsage());
									}
								}
							}
						}
					}
				}else{
					sender.sendMessage("Usage: " + Commands.VSET.getUsage());
				}
			}
		}
		return true;
	}
	
	public void setOnlinePlayerRank(Player player, int rank){
		userManager.setRank(player, rank);
		for(JobType job : JobType.values()){
			userManager.setJobLvl(player, job, rank);
			userManager.setJobXP(player, job, 0);
		}
		userManager.scoreboard(player);
		player.sendMessage(ChatColor.BLUE + "Your Rank and Job Levels have been set to " + rank);
	}
	
	public void setOnlinePlayerLevel(Player player, JobType job, int lvl){
		userManager.setJobLvl(player, job, lvl);
		userManager.setJobXP(player, job, 0);
		userManager.scoreboard(player);
		player.sendMessage(ChatColor.BLUE + "Your " + job.getName() + " Level has been set to " + lvl);
	}
	
	public void setOnlinePlayerLevel(Player player, SkillType skill, int lvl){
		userManager.setSkillLvl(player, skill, lvl);
		userManager.setSkillXP(player, skill, 0);
		userManager.scoreboard(player);
		player.sendMessage(ChatColor.BLUE + "Your " + skill.getName() + " Level has been set to " + lvl);
	}

	public void setOnlinePlayerXP(Player player, JobType job, int xp){
		userManager.setJobXP(player, job, xp);
		userManager.scoreboard(player);
		player.sendMessage(ChatColor.BLUE + "Your " + job.getName() + " Experience has been set to " + xp);
	}
	
	public void setOnlinePlayerXP(Player player, SkillType skill, int xp){
		userManager.setSkillXP(player, skill, xp);
		userManager.scoreboard(player);
		player.sendMessage(ChatColor.BLUE + "Your " + skill.getName() + " Experience has been set to " + xp);
	}

	public void setOfflinePlayerRank(OfflinePlayer player, int rank){
		String pname = player.getName();
		try{
			Main.sql.open();
			c = Main.sql.getConnection();
			s = c.createStatement();
			String update = "UPDATE VSkills SET rank = " + rank + ", builder = 0, digger = 0, farmer = 0, hunter = 0, miner = 0, " +
					"woodcutter = 0 WHERE name = '" + pname + "'";
			String updatelvl = "UPDATE VSkills_levels SET builder = " + rank + ", digger = " + rank + ", farmer = " + rank + 
					", hunter = " + rank + ", miner = " + rank + ", woodcutter = " + rank + " WHERE name = '" + pname + "'";
			s.executeUpdate(update);
			s.close();
			s = c.createStatement();
			s.executeUpdate(updatelvl);
			s.close();
			c.close();
		}catch(SQLException e){
			Main.writeError("Error Upadating User Profile: " + e.getMessage());
		}
	}
	
	public void setOfflinePlayerLevel(OfflinePlayer player, JobType job, int lvl){
		String jname = job.getName().toLowerCase();
		String pname = player.getName();
		String update = "UPDATE VSkills SET " + jname + " = 0 WHERE name = '" + pname + "'";
		String updatelvl = "UPDATE VSkills_levels SET " + jname + " = " + lvl + " WHERE name = '" + pname + "'";
		try{
			Main.sql.open();
			c = Main.sql.getConnection();
			s = c.createStatement();
			s.executeUpdate(update);
			s.close();
			s = c.createStatement();
			s.executeUpdate(updatelvl);
			s.close();
			c.close();
		}catch(SQLException e){
			Main.writeError("Error Upadating User Profile: " + e.getMessage());
		}
	}
	
	public void setOfflinePlayerLevel(OfflinePlayer player, SkillType skill, int lvl){
		String sname = skill.getName().toLowerCase();
		String pname = player.getName();
		String update = "UPDATE VSkills SET " + sname + " = 0 WHERE name = '" + pname + "'";
		String updatelvl = "UPDATE VSkills_levels SET " + sname + " = " + lvl + " WHERE name = '" + pname + "'";
		try{
			Main.sql.open();
			c = Main.sql.getConnection();
			s = c.createStatement();
			s.executeUpdate(update);
			s.close();
			s = c.createStatement();
			s.executeUpdate(updatelvl);
			s.close();
			c.close();
		}catch(SQLException e){
			Main.writeError("Error Upadating User Profile: " + e.getMessage());
		}
	}
	
	public void setOfflinePlayerXP(OfflinePlayer player, JobType job, int xp){
		String jname = job.getName().toLowerCase();
		String pname = player.getName();
		String update = "UPDATE VSkills SET " + jname + " = " + xp + " WHERE name = '" + pname + "'";
		try{
			Main.sql.open();
			c = Main.sql.getConnection();
			s = c.createStatement();
			s.executeUpdate(update);
			s.close();
			c.close();
		}catch(SQLException e){
			Main.writeError("Error Upadating User Profile: " + e.getMessage());
		}
	}
	
	public void setOfflinePlayerXP(OfflinePlayer player, SkillType skill, int xp){
		String sname = skill.getName().toLowerCase();
		String pname = player.getName();
		String update = "UPDATE VSkills SET " + sname + " = " + xp + " WHERE name = '" + pname + "'";
		try{
			Main.sql.open();
			c = Main.sql.getConnection();
			s = c.createStatement();
			s.executeUpdate(update);
			s.close();
			c.close();
		}catch(SQLException e){
			Main.writeError("Error Upadating User Profile: " + e.getMessage());
		}
	}
}
