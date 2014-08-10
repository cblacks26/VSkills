package com.github.vskills.listeners;

import java.util.UUID;

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
import com.github.vskills.datatypes.SkillType;
import com.github.vskills.events.SkillXPGainEvent;
import com.github.vskills.user.User;
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
		UUID id = event.getPlayer().getUniqueId();
		userManager.addUser(id);
		userManager.getUser(id).scoreboard();
	}
	
	@EventHandler
	public void PlayerLeave(PlayerQuitEvent event){
		UUID id = event.getPlayer().getUniqueId();
		userManager.getUser(id).saveUser();
		userManager.removeUser(id);
	}
				
	@EventHandler
	public void InteractEvent(PlayerInteractEvent event){
		ItemStack item = event.getItem();
		User user = userManager.getUser(event.getPlayer().getUniqueId());
		PluginManager pm = Bukkit.getServer().getPluginManager();
		if(item == null){
			if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
				if(user.getAbility() == AbilityType.POWERPUNCH){
					user.unToggle();
				}else{
					user.setAbility(AbilityType.POWERPUNCH);
				}
				user.scoreboard();
				return;
			}
		}else if(itemUtil.isPick(item)){
			if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
				if(user.getAbility() == AbilityType.INSTAMINE){
					user.unToggle();
				}else{
					user.setAbility(AbilityType.INSTAMINE);
				}
				user.scoreboard();
				return;
			}
		}else if(itemUtil.isShovel(item)){
			if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
				if(user.getAbility() == AbilityType.INSTADIG){
					user.unToggle();
				}else{
					user.setAbility(AbilityType.INSTADIG);
				}
				user.scoreboard();
				return;
			}
		}else if(itemUtil.isAxe(item)){
			if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
				if(user.getAbility() == AbilityType.INSTACUT){
					user.unToggle();
				}else{
					user.setAbility(AbilityType.INSTACUT);
				}
				user.scoreboard();
				return;
			}
		}else if(itemUtil.isSword(item)){
			if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
				if(user.getAbility() == AbilityType.POWERSWORD){
					user.unToggle();
				}else{
					user.setAbility(AbilityType.POWERSWORD);
				}
				user.scoreboard();
				return;
			}
		}else if(itemUtil.isBow(item)){
			if(event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)){
				if(user.getAbility() == AbilityType.BLAZINGARROWS){
					user.unToggle();
				}else{
					user.setAbility(AbilityType.BLAZINGARROWS);
				}
				user.scoreboard();
				return;
			}
		}else if(itemUtil.isHoe(item) == true){
			if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
				Block block = event.getClickedBlock();
				Material b = block.getType();
				if(blockUtil.isFarmable(b)){
					SkillXPGainEvent skillevent = new SkillXPGainEvent(user.getID(), SkillType.HOE, 1);
					pm.callEvent(skillevent);
					return;
				}
				if(blockUtil.isInstaGrowable(b)){
					AbilitiesManager.runInstaGrow(user.getID(), block);
				}
			}else if(event.getAction().equals(Action.LEFT_CLICK_AIR)){
				if(user.getAbility() == AbilityType.INSTAGROW){
					user.unToggle();
				}else{
					user.setAbility(AbilityType.INSTAGROW);
				}
				user.scoreboard();
			}
		}else if(item.getType() == Material.BONE){
			if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
				Block block = event.getClickedBlock();
				Material b = block.getType();
				BlockState bs = block.getState();
				event.getPlayer().sendMessage(b.name());
				event.getPlayer().sendMessage(bs.toString());
			}
		}
	}
	
	@EventHandler
 	public void PlayerKill(PlayerDeathEvent event){
		Player player = event.getEntity().getPlayer();
		User user = userManager.getUser(player.getUniqueId());
		user.addDeath();
		user.scoreboard();
		if(player.getKiller() instanceof Player){
			User killer = userManager.getUser(player.getKiller().getUniqueId());
			killer.addKill();
			killer.scoreboard();
		}
	}
		
	@EventHandler
	public void PlayerDamage(EntityDamageEvent event){
		PluginManager pm = Bukkit.getServer().getPluginManager();
		if(event.getEntity() instanceof Player){
			Player player = (Player)event.getEntity();
			if(userManager.getUser(player.getUniqueId()).isGod()){
				event.setCancelled(true);
			}
		    if (event.getCause() == DamageCause.FALL) {
		    	double dmg = event.getDamage();
		    	int count =1;
		    	while(dmg>count){
		    		count++;
		    	}
		    	SkillXPGainEvent skillevent = new SkillXPGainEvent(player.getUniqueId(), SkillType.ACROBATICS, count);
		    	pm.callEvent(skillevent);
		    	event.setDamage(damageUtil.getFallDamage(player, event.getDamage()));
		    }
		}
	}
	
}