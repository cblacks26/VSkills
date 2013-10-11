package com.github.vskills.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.CropState;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Crops;
import org.bukkit.plugin.PluginManager;

import com.github.vskills.Main;
import com.github.vskills.datatypes.AbilityType;
import com.github.vskills.datatypes.JobType;
import com.github.vskills.datatypes.SkillType;
import com.github.vskills.events.JobXPGainEvent;
import com.github.vskills.events.SkillXPGainEvent;

public class AbilitiesManager {

	private static Connection c;
	private static Statement s;
	private static ResultSet rs;
	
	private static UserManager userManager = Main.getUserManager();
	
	private static HashMap<String, AbilityType> toggled = new HashMap<String, AbilityType>();
	private static HashMap<String, Integer> cpower = new HashMap<String, Integer>();
	private static HashMap<String, Integer> power = new HashMap<String, Integer>();
	private static PluginManager pm = Bukkit.getServer().getPluginManager();
	
	private static boolean canRunAbility(Player player, AbilityType a){
		String name = player.getName();
		if(isToggled(player, a)){
			int cp = cpower.get(name);
			int cost = a.getCost();
			if(cp < cost){
				player.sendMessage("You don't have enough power to use " + a.getName());
				toggled.remove(name);
				return false;
			}else{
				return true;
			}
		}else{
			return false;
		}
	}
	
	private static void runAbility(Player player, AbilityType a){
		String name = player.getName();
		if(isToggled(player, a)){
			int cp = cpower.get(name);
			int cost = a.getCost();
			if(cp < cost){
				player.sendMessage("You don't have enough power to use " + a.getName());
				toggled.remove(name);
			}else{
				cpower.put(name, cp - cost);
				userManager.scoreboard(player);
			}
		}
	}
	
	public static void toggleAbility(Player player, AbilityType ability){
		String name = player.getName();
		if(toggled.containsKey(name)){
			AbilityType a = toggled.get(name);
			if(a == ability){
				toggled.remove(name);
				player.sendMessage("You have turned off " + ability.getName());
			}else if(a != ability){
				player.sendMessage("You have turned off " + a.getName());
				toggled.put(name, ability);
				player.sendMessage("You have turned on " + ability.getName());
			}
		}else{
			toggled.put(name, ability);
			player.sendMessage(ability.getName() + " has been turned on");
		}
	}
	
	public static boolean isToggled(Player player, AbilityType ability){
		if(toggled.containsKey(player.getName())){
			AbilityType a = toggled.get(player.getName());
			if(a == ability){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	public static void runBlazingArrows(Player player, Arrow arrow){
		if(canRunAbility(player, AbilityType.BLAZINGARROWS)){
			arrow.setFireTicks(100);
			runAbility(player, AbilityType.BLAZINGARROWS);
		}
	}
	
	public static double runPowerPunch(Player player, double damage){
		if(canRunAbility(player, AbilityType.POWERPUNCH)){
			double dmg = damage + 4;
			runAbility(player, AbilityType.POWERPUNCH);
			return dmg;
		}else{
			return damage;
		}
	}
	
	public static double runPowerSword(Player player, double damage){
		if(canRunAbility(player, AbilityType.POWERSWORD)){
			double dmg = damage + 4;
			runAbility(player, AbilityType.POWERSWORD);
			return dmg;
		}else{
			return damage;
		}
	}
	
	public static void runInstaMine(Player player, Block block, int xp){
		if(canRunAbility(player, AbilityType.INSTAMINE)){
			Material b = block.getType();
			Location loc = block.getLocation();
			block.setType(Material.AIR);
			block.getWorld().dropItemNaturally(loc, new ItemStack(b));
			JobXPGainEvent jobevent = new JobXPGainEvent(player, JobType.MINER, xp);
			pm.callEvent(jobevent);
			SkillXPGainEvent skillevent = new SkillXPGainEvent(player, SkillType.PICKAXE, xp);
			pm.callEvent(skillevent);
			runAbility(player, AbilityType.INSTAMINE);
		}
	}
	
	public static void runInstaCut(Player player, Block block, int xp){
		if(canRunAbility(player, AbilityType.INSTACUT)){
			Material b = block.getType();
			Location loc = block.getLocation();
			block.setType(Material.AIR);
			block.getWorld().dropItemNaturally(loc, new ItemStack(b));
			JobXPGainEvent jobevent = new JobXPGainEvent(player, JobType.WOODCUTTER, xp);
			pm.callEvent(jobevent);
			SkillXPGainEvent skillevent = new SkillXPGainEvent(player, SkillType.AXE, xp);
			pm.callEvent(skillevent);
			runAbility(player, AbilityType.INSTACUT);
		}
	}
	
	public static void runInstaGrow(Player player, Block block){
		Material b = block.getType();
		BlockState bs = block.getState();
		Location loc = block.getLocation();
		Location down = loc.clone().subtract(0, 1, 0);
		Location down2 = loc.clone().subtract(0, 2, 0);
		Location up = loc.clone().add(0, 1, 0);
		Location up2 = loc.clone().add(0, 2, 0);
		if(canRunAbility(player, AbilityType.INSTAGROW)){
			if(b == Material.PUMPKIN_STEM){
				block.setType(Material.PUMPKIN);
				runAbility(player, AbilityType.INSTAGROW);
			}else if(b == Material.MELON_STEM){
				block.setType(Material.MELON_BLOCK);
				runAbility(player, AbilityType.INSTAGROW);
			}else if(b == Material.SUGAR_CANE_BLOCK){
				if(down.getBlock().getType() ==  Material.SUGAR_CANE_BLOCK){
					if(up.getBlock().getType() == Material.SUGAR_CANE_BLOCK || down2.getBlock().getType() == Material.SUGAR_CANE_BLOCK){
						player.sendMessage("You cannot use InstaGrow on sugar cane taller than 2 blocks");
						return;
					}
					if(up.getBlock().getType() == Material.AIR){
						up.getBlock().setType(Material.SUGAR_CANE_BLOCK);
						runAbility(player, AbilityType.INSTAGROW);
					}
				}else{
					if(up.getBlock().getType() == Material.AIR){
						if(up2.getBlock().getType() == Material.AIR){
							up.getBlock().setType(Material.SUGAR_CANE_BLOCK);
							up2.getBlock().setType(Material.SUGAR_CANE_BLOCK);
							runAbility(player, AbilityType.INSTAGROW);
						}else{
							up.getBlock().setType(Material.SUGAR_CANE_BLOCK);
							runAbility(player, AbilityType.INSTAGROW);
						}
					}
				}
			}else if(b == Material.CACTUS){
				if(down.getBlock().getType() ==  Material.CACTUS){
					if(up.getBlock().getType() == Material.CACTUS || down2.getBlock().getType() == Material.CACTUS){
						player.sendMessage("You cannot use InstaGrow on sugar cane taller than 2 blocks");
						return;
					}
					if(up.getBlock().getType() == Material.AIR){
						runAbility(player, AbilityType.INSTAGROW);
					}
				}else{
					if(up.getBlock().getType() == Material.AIR){
						if(up2.getBlock().getType() == Material.AIR){
							up.getBlock().setType(Material.CACTUS);
							up2.getBlock().setType(Material.CACTUS);
							runAbility(player, AbilityType.INSTAGROW);
						}else{
							up.getBlock().setType(Material.CACTUS);
							runAbility(player, AbilityType.INSTAGROW);
						}
					}
				}
			}else if(b == Material.CROPS){
				Crops c = new Crops(b);
				c.setState(CropState.RIPE);
				bs.setData(c);
				bs.update();
				runAbility(player, AbilityType.INSTAGROW);
			}
		}
	}
	
	public static void runInstaDig(Player player, Block block, int xp){
		if(canRunAbility(player, AbilityType.INSTADIG)){
			Material b = block.getType();
			Location loc = block.getLocation();
			block.setType(Material.AIR);
			block.getWorld().dropItemNaturally(loc, new ItemStack(b));
			JobXPGainEvent jobevent = new JobXPGainEvent(player, JobType.DIGGER, xp);
			pm.callEvent(jobevent);
			SkillXPGainEvent skillevent = new SkillXPGainEvent(player, SkillType.SHOVEL, xp);
			pm.callEvent(skillevent);
			runAbility(player, AbilityType.INSTADIG);
		}
	}
	
	public static void addUser(Player player){
		String pname = player.getName();
		if(power.containsKey(player.getName())){
			if(cpower.containsKey(player.getName())){
				return;
			}
		}else{
			try{
				Main.sql.open();
				c = Main.sql.getConnection();
				s = c.createStatement();
				rs = s.executeQuery("SELECT * FROM VSkills WHERE name = '" + pname + "'");
				rs.next();
				power.put(pname, rs.getInt("power"));
				cpower.put(pname, rs.getInt("cpower"));
				rs.close();
				s.close();
				c.close();
			}catch(SQLException e){
				Main.writeError("Error loading user Power: " + e.getMessage());
			}
		}
	}
	
	public static void removeUser(Player player){
		if(power.containsKey(player.getName())){
			power.remove(player.getName());
			if(toggled.containsKey(player.getName())){
				toggled.remove(player.getName());
			}
		}
	}
	
	public static void removeUsers(){
		for(Player player : Bukkit.getServer().getOnlinePlayers()){
			removeUser(player);
		}
	}
	
	public static void addUsers(){
		for(Player player: Bukkit.getServer().getOnlinePlayers()){
			addUser(player);
		}
	}

	public static int getPlayerMaxPower(Player player){
		if(power.containsKey(player.getName())){
			int p = power.get(player.getName());
			return p;
		}else{
			return 0;
		}
	}
	
	public static int getPlayerCurrentPower(Player player){
		if(cpower.containsKey(player.getName())){
			int p = cpower.get(player.getName());
			return p;
		}else{
			return 0;
		}
	}
	
	public static void refreshPower(Player player){
		int max = power.get(player.getName());
		cpower.put(player.getName(), max);
	}
	
	public static void setPlayerMaxPower(Player player, int val){
		cpower.put(player.getName(), val);
		power.put(player.getName(), val);
	}
	
	public static void addPlayerMaxPower(Player player, int val){
		int p = power.get(player.getName());
		int np = p + val;
		power.put(player.getName(), np);
		cpower.put(player.getName(), np);
	}
	
	public static void setPlayerCurrentPower(Player player, int val){
		cpower.put(player.getName(), val);
	}
	
	public static void addPlayerCurrentPower(Player player, int val){
		int p = cpower.get(player.getName());
		int np = p + val;
		cpower.put(player.getName(), np);
	}
}
