package com.github.vskills.listeners;

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
import com.github.vskills.datatypes.JobType;
import com.github.vskills.datatypes.SkillType;
import com.github.vskills.events.JobLevelUpEvent;
import com.github.vskills.events.JobXPGainEvent;
import com.github.vskills.events.RankUpEvent;
import com.github.vskills.events.SkillLevelUpEvent;
import com.github.vskills.events.SkillXPGainEvent;
import com.github.vskills.util.Ranks;
import com.github.vskills.util.UserManager;

public class VSkillsListener implements Listener{

	UserManager userManager = Main.getUserManager();
	Ranks rank = new Ranks();
	
	@EventHandler
	public void onRankUpEvent(RankUpEvent event){
		Player player = event.getPlayer();
		int rank = event.getRank();
		int newrank = rank + 1;
		int tokens = userManager.getTokens(player);
		int newtokens = tokens + 1;
		userManager.setRank(player, newrank);
		userManager.setTokens(player, newtokens);
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
	public void onJobLevelUpEvent(JobLevelUpEvent event){
		Player player = event.getPlayer();
		JobType job = event.getJobType();
		int level = event.getLevel();
		userManager.setJobLvl(player, job, level);
		userManager.setJobXP(player, job, 0);
		player.sendMessage(ChatColor.AQUA + job.getName() + " Level Up!");
		userManager.scoreboard(player);
	}
	
	@EventHandler
	public void onSkillLevelUpEvent(SkillLevelUpEvent event){
		Player player = event.getPlayer();
		SkillType skill = event.getSkillType();
		int level = event.getLevel();
		userManager.setSkillLvl(player, skill, level);
		userManager.setSkillXP(player, skill, 0);
		player.sendMessage(ChatColor.AQUA + skill.getName() + " Level Up!");
		userManager.scoreboard(player);
	}
	
	@EventHandler
	public void onJobXPGainEvent(JobXPGainEvent event){
		Player player = event.getPlayer();
		JobType job = event.getJobType();
		int exp = userManager.getJobXP(player, job);
		int xp = event.getXPGained();
		int val = exp + xp;
		double m = userManager.getMoney(player);
		double money = xp * m;
		Main.depositPlayer(player, money);
		userManager.setJobXP(player, job, val);
		rank.userLevelUp(player, job);
	}
	
	@EventHandler
	public void onSkillXPGainEvent(SkillXPGainEvent event){
		Player player = event.getPlayer();
		SkillType skill = event.getSkillType();
		int exp = userManager.getSkillXP(player, skill);
		int xp = event.getXPGained();
		int val = exp + xp;
		userManager.setSkillXP(player, skill, val);
		rank.userLevelUp(player, skill);
	}
	
}
