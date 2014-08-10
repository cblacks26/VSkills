package com.github.vskills.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.meta.FireworkMeta;

import com.github.vskills.Main;
import com.github.vskills.datatypes.SkillType;
import com.github.vskills.events.RankUpEvent;
import com.github.vskills.events.SkillLevelUpEvent;
import com.github.vskills.events.SkillXPGainEvent;
import com.github.vskills.user.User;
import com.github.vskills.util.Ranks;
import com.github.vskills.util.UserManager;

public class VSkillsListener implements Listener{

	UserManager userManager = Main.getUserManager();
	Ranks rank = new Ranks();
	
	@EventHandler
	public void onRankUpEvent(RankUpEvent event){
		Player player = Bukkit.getPlayer(event.getUserID());
		User user = userManager.getUser(event.getUserID());
		user.addRank();
		user.addToken();
		user.scoreboard();
		player.sendMessage(ChatColor.AQUA + "You Ranked up!");
		Location loc = player.getLocation();
		Firework firework = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
		FireworkMeta meta = firework.getFireworkMeta();
		FireworkEffect fe = FireworkEffect.builder().with(Type.BALL_LARGE).withColor(Color.BLUE).build();
		meta.addEffect(fe);
		meta.setPower(3);
		firework.setFireworkMeta(meta);
	}
	
	@EventHandler
	public void onSkillLevelUpEvent(SkillLevelUpEvent event){
		Player player = Bukkit.getPlayer(event.getUserID());
		User user = userManager.getUser(event.getUserID());
		SkillType skill = event.getSkillType();
		user.addLevel(skill);
		user.setXp(skill, 0);
		player.sendMessage(ChatColor.AQUA + skill.getName() + " Level Up!");
		rank.userRankUp(event.getUserID());
		user.scoreboard();
	}
	
	@EventHandler
	public void onSkillXPGainEvent(SkillXPGainEvent event){
		User user = userManager.getUser(event.getUserID());
		SkillType skill = event.getSkillType();
		user.addXp(skill, event.getXPGained());
		rank.userLevelUp(event.getUserID(), skill);
		user.scoreboard();
	}
}
