package com.github.vskills.listeners;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;

import com.github.vskills.Main;
import com.github.vskills.datatypes.AbilityType;
import com.github.vskills.datatypes.SkillType;
import com.github.vskills.events.SkillXPGainEvent;
import com.github.vskills.util.AbilitiesManager;
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
		UUID id = event.getPlayer().getUniqueId();
		Material block = event.getBlock().getType();
		ItemStack item = event.getPlayer().getItemInHand();
		PluginManager pm = Bukkit.getServer().getPluginManager();
		if(blockUtil.isDigable(block)){
			int xp = blockUtil.getBlockDestroyXP(block);
			if(itemUtil.isShovel(item)){
				SkillXPGainEvent skillevent = new SkillXPGainEvent(id, SkillType.SHOVEL, xp);
				pm.callEvent(skillevent);
			}else if(itemUtil.isUnarmed(item)){
				SkillXPGainEvent skillevent = new SkillXPGainEvent(id, SkillType.UNARMED, xp);
				pm.callEvent(skillevent);
			}else{
				return;
			}
		}else if(blockUtil.isCuttable(block)){
			int xp = blockUtil.getBlockDestroyXP(block);
			if(itemUtil.isAxe(item)){
				SkillXPGainEvent skillevent = new SkillXPGainEvent(id, SkillType.AXE, xp);
				pm.callEvent(skillevent);
			}else if(itemUtil.isUnarmed(item)){
				SkillXPGainEvent skillevent = new SkillXPGainEvent(id, SkillType.UNARMED, xp);
				pm.callEvent(skillevent);
			}else{
				return;
			}
		}else if(blockUtil.isMinable(block)){
			int xp = blockUtil.getBlockDestroyXP(block);
			if(itemUtil.isPick(item)){
				SkillXPGainEvent skillevent = new SkillXPGainEvent(id, SkillType.PICKAXE, xp);
				pm.callEvent(skillevent);
			}else if(itemUtil.isUnarmed(item)){
				SkillXPGainEvent skillevent = new SkillXPGainEvent(id, SkillType.UNARMED, xp);
				pm.callEvent(skillevent);
			}else{
				return;
			}
		}else{
			return;
		}
	}
	
	@EventHandler
	public void onBlockDamage(BlockDamageEvent event){
		Player player = event.getPlayer();
		UUID id = player.getUniqueId();
		Block block = event.getBlock();
		Material b = block.getType();
		ItemStack item = player.getItemInHand();
		if(blockUtil.isMinable(b)){
			if(itemUtil.isPick(item)){
				if(Main.userManager.getUser(id).getAbility()== AbilityType.INSTAMINE){
					int xp = blockUtil.getBlockDestroyXP(b);
					event.setCancelled(true);
					AbilitiesManager.runInstaMine(id, block, xp);
				}
			}
		}else if(blockUtil.isDigable(b)){
			if(itemUtil.isShovel(item)){
				if(Main.userManager.getUser(id).getAbility()== AbilityType.INSTADIG){
					int xp = blockUtil.getBlockDestroyXP(b);
					event.setCancelled(true);
					AbilitiesManager.runInstaDig(id, block, xp);
				}
			}
		}else if(blockUtil.isCuttable(b)){
			if(itemUtil.isAxe(item)){
				if(Main.userManager.getUser(id).getAbility()== AbilityType.INSTACUT){
					int xp = blockUtil.getBlockDestroyXP(b);
					event.setCancelled(true);
					AbilitiesManager.runInstaCut(id, block, xp);
				}
			}
		}
		
	}

	@EventHandler
	public void SignCreate(SignChangeEvent event){
		Player player = event.getPlayer();
		if(event.getLine(0).equalsIgnoreCase("[VRank]")){
			if(Main.isAuthorized(player, "VSkills.signs.rank")){
				player.sendMessage(ChatColor.RED + "You don't have the permissions to use this type of sign");
				return;
			}
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
					event.setLine(1, rank + ". " + rs.getString("name") + ": " + rs.getInt("rank"));
				}
				if(rs.next()){
					event.setLine(2, rank2 + ". " + rs.getString("name") + ": " + rs.getInt("rank"));
				}
				if(rs.next()){
					event.setLine(3, rank3 + ". " + rs.getString("name") + ": " + rs.getInt("rank"));
				}
			}else{
				rs.absolute(rank - 1);
				if(rs.next()){
					event.setLine(1, rank + ". " + rs.getString("name") + ": " + rs.getInt("rank"));
				}
				rs.absolute(rank2);
				if(rs.next()){
					event.setLine(2, rank2 + ". " + rs.getString("name") + ": " + rs.getInt("rank"));
				}
				rs.absolute(rank3);
				if(rs.next()){
					event.setLine(3, rank3 + ". " + rs.getString("name") + ": " + rs.getInt("rank"));
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
