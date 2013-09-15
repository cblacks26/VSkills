package com.github.vskills.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;

import com.github.vskills.Main;
import com.github.vskills.datatypes.JobType;
import com.github.vskills.datatypes.SkillType;
import com.github.vskills.events.JobXPGainEvent;
import com.github.vskills.events.SkillXPGainEvent;
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
	}
			
	@EventHandler
	public void PlayerDamage(EntityDamageEvent event){
		if(event.getEntity() instanceof Player){
			Player player = (Player)event.getEntity();
			if(userManager.checkGod(player)){
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void InteractEvent(PlayerInteractEvent event){
		ItemStack item = event.getItem();
		Player player = event.getPlayer();
		PluginManager pm = Bukkit.getServer().getPluginManager();
		if(item == null){
			return;
		}
		if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
			if(itemUtil.isHoe(item) == true){
				Material b = event.getClickedBlock().getType();
				if(blockUtil.isFarmable(b)){
					JobXPGainEvent jobevent = new JobXPGainEvent(player, JobType.FARMING, 1);
					pm.callEvent(jobevent);
					SkillXPGainEvent skillevent = new SkillXPGainEvent(player, SkillType.HOE, 1);
					pm.callEvent(skillevent);
				}
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
}