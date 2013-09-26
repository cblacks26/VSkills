package com.github.vskills.listeners;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;

import com.github.vskills.Main;
import com.github.vskills.datatypes.JobType;
import com.github.vskills.datatypes.SkillType;
import com.github.vskills.events.JobXPGainEvent;
import com.github.vskills.events.SkillXPGainEvent;
import com.github.vskills.util.BlockUtil;
import com.github.vskills.util.ItemUtil;

public class BlockListener implements Listener{
	
	ItemUtil itemUtil = new ItemUtil();
	BlockUtil blockUtil = new BlockUtil();
	private Connection c;
	private Statement s;
	private ResultSet rs;

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event){
		Player player = event.getPlayer();
		Material block = event.getBlock().getType();
		ItemStack item = event.getPlayer().getItemInHand();
		PluginManager pm = Bukkit.getServer().getPluginManager();
		if(blockUtil.isDigable(block)){
			int xp = blockUtil.getBlockDestroyXP(block);
			JobXPGainEvent jobevent = new JobXPGainEvent(player, JobType.DIGGER, xp);
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
			JobXPGainEvent jobevent = new JobXPGainEvent(player, JobType.WOODCUTTER, xp);
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
			JobXPGainEvent jobevent = new JobXPGainEvent(player, JobType.MINER, xp);
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
			JobXPGainEvent jobevent = new JobXPGainEvent(player, JobType.FARMER, xp);
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
			JobXPGainEvent jobevent = new JobXPGainEvent(player, JobType.BUILDER, xp);
			pm.callEvent(jobevent);
		}else if(blockUtil.isFarmerPlaceable(block)){
			int xp = blockUtil.getFarmerPlaceXP(block);
			JobXPGainEvent jobevent = new JobXPGainEvent(player, JobType.FARMER, xp);
			pm.callEvent(jobevent);
		}else{
			return;
		}
	}

	@EventHandler
	public void SignCreate(SignChangeEvent event){
		if(event.getLine(0).equalsIgnoreCase("[VRank]")){
			String[] line = event.getLines();
			if(Main.isInteger(line[1])){
				if(Integer.parseInt(line[1]) > 0){
					lbRank(event);
				}
			}
		}
	}
	
	public void lbRank(SignChangeEvent event){
		int rank = Integer.parseInt(event.getLine(1));
		int rank2 = rank;
		int rank3 = rank + 1;
		event.setLine(0, ChatColor.DARK_BLUE + "[VRank]");
		try{
			Main.sql.open();
			c = Main.sql.getConnection();
			s = c.createStatement();
			rs = s.executeQuery("SELECT * FROM VSkills ORDER BY rank DESC");
			if(rank == 1){
				if(rs.next()){
					event.setLine(1, rs.getString("name") + ": " + rs.getInt("rank"));
				}
				if(rs.next()){
					event.setLine(2, rs.getString("name") + ": " + rs.getInt("rank"));
				}
				if(rs.next()){
					event.setLine(3, rs.getString("name") + ": " + rs.getInt("rank"));
				}
			}else{
				rs.absolute(rank - 1);
				if(rs.next()){
					event.setLine(1, rs.getString("name") + ": " + rs.getInt("rank"));
				}
				rs.absolute(rank2);
				if(rs.next()){
					event.setLine(2, rs.getString("name") + ": " + rs.getInt("rank"));
				}
				rs.absolute(rank3);
				if(rs.next()){
					event.setLine(3, rs.getString("name") + ": " + rs.getInt("rank"));
				}
			}
			rs.close();
			s.close();
			c.close();
		}catch(SQLException e){
			Main.writeError("Error at leaderboards: " + e.getMessage());
		}
	}
	
}
