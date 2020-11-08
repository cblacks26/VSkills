package com.github.vskills.util;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import com.github.vskills.Main;
import com.github.vskills.datatypes.AbilityType;
import com.github.vskills.datatypes.SkillType;
import com.github.vskills.events.SkillXPGainEvent;
import com.github.vskills.user.User;

public class AbilitiesManager {
	
	private static UserManager userManager = Main.getUserManager();
	private static PluginManager pm = Bukkit.getServer().getPluginManager();
	
	private static boolean canRunAbility(UUID id, AbilityType a){
		User user = userManager.getUser(id);
		if(user.getAbility()== a){
			int cp = user.getCurrentPower();
			int cost = a.getCost();
			if(cp < cost){
				Bukkit.getPlayer(id).sendMessage("You don't have enough power to use " + a.getName());
				user.setAbility(null);
				return false;
			}else{
				return true;
			}
		}else{
			return false;
		}
	}
	
	private static boolean runAbility(UUID id, AbilityType a){
		if(canRunAbility(id, a)){
			User user = userManager.getUser(id);
			user.subtractCurrentPower(a.getCost());
			user.scoreboard();
			return true;
		}else{
			return false;
		}
	}
	
	public static void runBlazingArrows(UUID id, Arrow arrow){
		if(runAbility(id, AbilityType.BLAZINGARROWS)){
			arrow.setFireTicks(100);
		}
	}
	
	public static double runPowerPunch(UUID id, double damage){
		if(runAbility(id, AbilityType.POWERPUNCH)){
			double dmg = damage + 4;
			return dmg;
		}else{
			return damage;
		}
	}
	
	public static double runPowerSword(UUID id, double damage){
		if(runAbility(id, AbilityType.POWERSWORD)){
			double dmg = damage + 4;
			return dmg;
		}else{
			return damage;
		}
	}
	
	public static void runInstaMine(UUID id, Block block, int xp){
		if(runAbility(id, AbilityType.INSTAMINE)){
			block.breakNaturally();
			SkillXPGainEvent skillevent = new SkillXPGainEvent(id, SkillType.PICKAXE, xp);
			pm.callEvent(skillevent);
		}
	}
	
	public static void runInstaCut(UUID id, Block block, int xp){
		if(runAbility(id, AbilityType.INSTACUT)){
			block.breakNaturally();
			SkillXPGainEvent skillevent = new SkillXPGainEvent(id, SkillType.AXE, xp);
			pm.callEvent(skillevent);
		}
	}
	
	public static void runInstaGrow(UUID id, Block block){
		Material b = block.getType();
		Location loc = block.getLocation();
		Location down = loc.clone().subtract(0, 1, 0);
		Location down2 = loc.clone().subtract(0, 2, 0);
		Location up = loc.clone().add(0, 1, 0);
		Location up2 = loc.clone().add(0, 2, 0);
		Player player = Bukkit.getPlayer(id);
		if(canRunAbility(id, AbilityType.INSTAGROW)){
			if(b == Material.PUMPKIN_STEM){
				block.setType(Material.PUMPKIN);
				runAbility(id, AbilityType.INSTAGROW);
			}else if(b == Material.MELON_STEM){
				block.setType(Material.MELON);
				runAbility(id, AbilityType.INSTAGROW);
			}else if(b == Material.SUGAR_CANE){
				if(down.getBlock().getType() ==  Material.SUGAR_CANE){
					if(up.getBlock().getType() == Material.SUGAR_CANE || down2.getBlock().getType() == Material.SUGAR_CANE){
						player.sendMessage("You cannot use InstaGrow on sugar cane taller than 2 blocks");
						return;
					}
					if(up.getBlock().getType() == Material.AIR){
						up.getBlock().setType(Material.SUGAR_CANE);
						runAbility(id, AbilityType.INSTAGROW);
					}
				}else{
					if(up.getBlock().getType() == Material.AIR){
						if(up2.getBlock().getType() == Material.AIR){
							up.getBlock().setType(Material.SUGAR_CANE);
							up2.getBlock().setType(Material.SUGAR_CANE);
							runAbility(id, AbilityType.INSTAGROW);
						}else{
							up.getBlock().setType(Material.SUGAR_CANE);
							runAbility(id, AbilityType.INSTAGROW);
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
						runAbility(id, AbilityType.INSTAGROW);
					}
				}else{
					if(up.getBlock().getType() == Material.AIR){
						if(up2.getBlock().getType() == Material.AIR){
							up.getBlock().setType(Material.CACTUS);
							up2.getBlock().setType(Material.CACTUS);
							runAbility(id, AbilityType.INSTAGROW);
						}else{
							up.getBlock().setType(Material.CACTUS);
							runAbility(id, AbilityType.INSTAGROW);
						}
					}
				}
			}
		}
	}
	
	public static void runInstaDig(UUID id, Block block, int xp){
		if(runAbility(id, AbilityType.INSTADIG)){
			block.breakNaturally();
			SkillXPGainEvent skillevent = new SkillXPGainEvent(id, SkillType.SHOVEL, xp);
			pm.callEvent(skillevent);
		}
	}
}
