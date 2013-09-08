package com.github.vskills.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;

import com.github.vskills.datatypes.JobType;
import com.github.vskills.datatypes.SkillType;
import com.github.vskills.events.JobXPGainEvent;
import com.github.vskills.events.SkillXPGainEvent;
import com.github.vskills.util.BlockUtil;
import com.github.vskills.util.ItemUtil;

public class BlockListener implements Listener{
	
	ItemUtil itemUtil = new ItemUtil();
	BlockUtil blockUtil = new BlockUtil();

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event){
		Player player = event.getPlayer();
		Material block = event.getBlock().getType();
		ItemStack item = event.getPlayer().getItemInHand();
		PluginManager pm = Bukkit.getServer().getPluginManager();
		if(blockUtil.isDigable(block)){
			int xp = blockUtil.getBlockDestroyXP(block);
			JobXPGainEvent jobevent = new JobXPGainEvent(player, JobType.DIGGING, xp);
			pm.callEvent(jobevent);
			if(itemUtil.isShovel(item)){
				SkillXPGainEvent skillevent = new SkillXPGainEvent(player, SkillType.SHOVEL, xp);
				pm.callEvent(skillevent);
			}else if(itemUtil.isUnarmed(item)){
				SkillXPGainEvent skillevent = new SkillXPGainEvent(player, SkillType.UNARMED, xp);
				pm.callEvent(skillevent);
			}else{
				return;
			}
		}else if(blockUtil.isCuttable(block)){
			int xp = blockUtil.getBlockDestroyXP(block);
			JobXPGainEvent jobevent = new JobXPGainEvent(player, JobType.WOODCUTTING, xp);
			pm.callEvent(jobevent);
			if(itemUtil.isAxe(item)){
				SkillXPGainEvent skillevent = new SkillXPGainEvent(player, SkillType.AXE, xp);
				pm.callEvent(skillevent);
			}else if(itemUtil.isUnarmed(item)){
				SkillXPGainEvent skillevent = new SkillXPGainEvent(player, SkillType.UNARMED, xp);
				pm.callEvent(skillevent);
			}else{
				return;
			}
		}else if(blockUtil.isMinable(block)){
			int xp = blockUtil.getBlockDestroyXP(block);
			JobXPGainEvent jobevent = new JobXPGainEvent(player, JobType.MINING, xp);
			pm.callEvent(jobevent);
			if(itemUtil.isPick(item)){
				SkillXPGainEvent skillevent = new SkillXPGainEvent(player, SkillType.PICKAXE, xp);
				pm.callEvent(skillevent);
			}else if(itemUtil.isUnarmed(item)){
				SkillXPGainEvent skillevent = new SkillXPGainEvent(player, SkillType.UNARMED, xp);
				pm.callEvent(skillevent);
			}else{
				return;
			}
		}else if(blockUtil.isFarmerDestroyable(block)){
			int xp = blockUtil.getFarmerDestroyXP(block);
			JobXPGainEvent jobevent = new JobXPGainEvent(player, JobType.FARMING, xp);
			pm.callEvent(jobevent);
			if(itemUtil.isUnarmed(item)){
				SkillXPGainEvent skillevent = new SkillXPGainEvent(player, SkillType.UNARMED, xp);
				pm.callEvent(skillevent);
			}else{
				return;
			}
		}else{
			return;
		}
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event){
		Player player = event.getPlayer();
		Material block = event.getBlock().getType();
		PluginManager pm = Bukkit.getServer().getPluginManager();
		if(blockUtil.isPlaceable(block)){
			int xp = blockUtil.getBlockPlaceXP(block);
			JobXPGainEvent jobevent = new JobXPGainEvent(player, JobType.BUILDING, xp);
			pm.callEvent(jobevent);
		}else if(blockUtil.isFarmerPlaceable(block)){
			int xp = blockUtil.getFarmerPlaceXP(block);
			JobXPGainEvent jobevent = new JobXPGainEvent(player, JobType.FARMING, xp);
			pm.callEvent(jobevent);
		}else{
			return;
		}
	}
}
