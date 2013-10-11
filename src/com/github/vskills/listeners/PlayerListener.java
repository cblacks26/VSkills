package com.github.vskills.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;

import com.github.vskills.Main;
import com.github.vskills.datatypes.AbilityType;
import com.github.vskills.datatypes.JobType;
import com.github.vskills.datatypes.SkillType;
import com.github.vskills.events.JobXPGainEvent;
import com.github.vskills.events.SkillXPGainEvent;
import com.github.vskills.util.AbilitiesManager;
import com.github.vskills.util.BlockUtil;
import com.github.vskills.util.DamageUtil;
import com.github.vskills.util.ItemUtil;
import com.github.vskills.util.Ranks;
import com.github.vskills.util.UserManager;

public class PlayerListener implements Listener{
	
	UserManager userManager = Main.getUserManager();
	ItemUtil itemUtil = new ItemUtil();
	BlockUtil blockUtil = new BlockUtil();
	Ranks rank = new Ranks();
	DamageUtil damageUtil = new DamageUtil();
	
	@EventHandler
	public void PlayerJoin(PlayerJoinEvent event){
		Player player = event.getPlayer();
		if(userManager.checkUser(player) == false){
			userManager.createUser(player);
		}
		userManager.addUser(player);
		AbilitiesManager.addUser(player);
		userManager.scoreboard(player);
	}
	
	@EventHandler
	public void PlayerLeave(PlayerQuitEvent event){
		Player player = event.getPlayer();
		if(userManager.checkGod(player)){
			userManager.takeGod(player);
		}
		userManager.saveUser(player);
		userManager.removeUser(player);
		AbilitiesManager.removeUser(player);
	}
				
	@EventHandler
	public void InteractEvent(PlayerInteractEvent event){
		ItemStack item = event.getItem();
		Player player = event.getPlayer();
		PluginManager pm = Bukkit.getServer().getPluginManager();
		if(item == null){
			if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
				AbilitiesManager.toggleAbility(player, AbilityType.POWERPUNCH);
				return;
			}else{
				return;
			}
		}else if(itemUtil.isPick(item)){
			if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
				AbilitiesManager.toggleAbility(player, AbilityType.INSTAMINE);
				return;
			}else{
				return;
			}
		}else if(itemUtil.isShovel(item)){
			if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
				AbilitiesManager.toggleAbility(player, AbilityType.INSTADIG);
				return;
			}else{
				return;
			}
		}else if(itemUtil.isAxe(item)){
			if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
				AbilitiesManager.toggleAbility(player, AbilityType.INSTACUT);
				return;
			}else{
				return;
			}
		}else if(itemUtil.isSword(item)){
			if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
				AbilitiesManager.toggleAbility(player, AbilityType.POWERSWORD);
				return;
			}else{
				return;
			}
		}else if(itemUtil.isBow(item)){
			if(event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)){
				AbilitiesManager.toggleAbility(player, AbilityType.BLAZINGARROWS);
				return;
			}else{
				return;
			}
		}else if(itemUtil.isHoe(item) == true){
			if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
				Block block = event.getClickedBlock();
				Material b = block.getType();
				if(blockUtil.isFarmable(b)){
					JobXPGainEvent jobevent = new JobXPGainEvent(player, JobType.FARMER, 1);
					pm.callEvent(jobevent);
					SkillXPGainEvent skillevent = new SkillXPGainEvent(player, SkillType.HOE, 1);
					pm.callEvent(skillevent);
					return;
				}
				if(blockUtil.isInstaGrowable(b)){
					AbilitiesManager.runInstaGrow(player, block);
				}
			}else if(event.getAction().equals(Action.LEFT_CLICK_AIR)){
				AbilitiesManager.toggleAbility(player, AbilityType.INSTAGROW);
			}
		}else if(item.getType() == Material.BONE){
			if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
				Block block = event.getClickedBlock();
				Material b = block.getType();
				BlockState bs = block.getState();
				player.sendMessage(b.name());
				player.sendMessage(bs.toString());
			}
		}
	}
	
	@EventHandler
 	public void PlayerKill(PlayerDeathEvent event){
		Player player = event.getEntity().getPlayer();
		int deaths = userManager.getDeaths(player);
		int newdeaths = deaths + 1;
		userManager.setDeaths(player, newdeaths);
		userManager.scoreboard(player);
		if(player.getKiller() instanceof Player){
			Player killer = player.getKiller();
			int kills = userManager.getKills(killer);
			int newkills = kills + 1;
			userManager.setKills(killer, newkills);
			userManager.scoreboard(killer);
		}
	}
		
	@EventHandler
	public void PlayerDamage(EntityDamageEvent event){
		PluginManager pm = Bukkit.getServer().getPluginManager();
		if(event.getEntity() instanceof Player){
			Player player = (Player)event.getEntity();
			if(userManager.checkGod(player)){
				event.setCancelled(true);
			}
		    if (event.getCause() == DamageCause.FALL) {
		    	double dmg = event.getDamage();
		    	int count =1;
		    	while(dmg>count){
		    		count++;
		    	}
		    	SkillXPGainEvent skillevent = new SkillXPGainEvent(player, SkillType.ACROBATICS, count);
		    	pm.callEvent(skillevent);
		    	event.setDamage(damageUtil.getFallDamage(player, event.getDamage()));
		    }
		}
	}
	
}