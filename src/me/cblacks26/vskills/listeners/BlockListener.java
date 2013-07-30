package me.cblacks26.vskills.listeners;

import me.cblacks26.vskills.Main;
import me.cblacks26.vskills.util.ItemUtil;
import me.cblacks26.vskills.util.Ranks;
import me.cblacks26.vskills.util.SkillType;
import me.cblacks26.vskills.util.Util;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class BlockListener implements Listener{
	
	private Main p;
	Ranks ranks = new Ranks();
	Util util = new Util();
	ItemUtil itemUtil = new ItemUtil();
	
	int exp;
	double val;
	
	public BlockListener(Main instance){
		p = instance;
	}
	
	@EventHandler
	public void BlockBreak(BlockBreakEvent event){
		Material b = event.getBlock().getType();
		Player player = event.getPlayer();
		ItemStack item = player.getItemInHand();
		if(player.getGameMode().equals(GameMode.SURVIVAL)){
			if(itemUtil.isShovel(item) == true){
				if(isDiggable(b) == true){
					if(p.getConfig().get("BasePay") != null){
						val = p.getConfig().getDouble("BasePay");
						exp = getBlockDestroyExp(b, "Digging");
						p.deposit(player, val * exp);
						ranks.xpGain(player, SkillType.DIGGING, exp);
					}
					else{
						val = 0.25;
						exp = getBlockDestroyExp(b, "Digging");
						p.deposit(player, val * exp);
						ranks.xpGain(player, SkillType.DIGGING, exp);
					}
				}	
			}
			
			if(itemUtil.isPickaxe(item) == true){
				if(isMinable(b) == true){
					if(p.getConfig().get("BasePay") != null){
						val = p.getConfig().getDouble("BasePay");
						exp = getBlockDestroyExp(b, "Mining");
						p.deposit(player, val * exp);
						ranks.xpGain(player, SkillType.MINING, exp);
					}
					else{
						val = 0.25;
						exp = getBlockDestroyExp(b, "Mining");
						p.deposit(player, val * exp);
						ranks.xpGain(player, SkillType.MINING, exp);
					}
				}	
			}
			
			if(itemUtil.isAxe(item) == true){
				if(isCuttable(b) == true){
					if(p.getConfig().get("BasePay") != null){
						val = p.getConfig().getDouble("BasePay");
						exp = getBlockDestroyExp(b, "Logging");
						p.deposit(player, val * exp);
						ranks.xpGain(player, SkillType.LOGGING, exp);
					}
					else{
						val = 0.25;
						exp = getBlockDestroyExp(b, "Logging");
						p.deposit(player, val * exp);
						ranks.xpGain(player, SkillType.LOGGING, exp);
					}
				}	
			}
			
			if(isFarmerDestroyable(b) == true){
				if(p.getConfig().get("BasePay") != null){
					val = p.getConfig().getDouble("BasePay");
					exp = getBlockDestroyExp(b, "Farming");
					p.deposit(player, val * exp);
					ranks.xpGain(player, SkillType.FARMING, exp);
				}
				else{
					val = 0.25;
					exp = getBlockDestroyExp(b, "Farming");
					p.deposit(player, val * exp);
					ranks.xpGain(player, SkillType.FARMING, exp);
				}
			}
		}
	}
	
	@EventHandler
	public void BlockPlace(BlockPlaceEvent e){
		Material b = e.getBlock().getType();
		Player player = e.getPlayer();
		if(isPlaceable(b, "Building") == true){
			if(p.getConfig().get("BasePay") != null){
				val = p.getConfig().getDouble("BasePay");
				exp = getBlockDestroyExp(b, "Building");
				p.deposit(player, val * exp);
				ranks.xpGain(player, SkillType.BUILDING, exp);
			}
			else{
				val = 0.25;
				exp = getBlockDestroyExp(b, "Building");
				p.deposit(player, val * exp);
				ranks.xpGain(player, SkillType.BUILDING, exp);
			}
		}
		if(isPlaceable(b, "Farming") == true){
			if(p.getConfig().get("BasePay") != null){
				exp = getBlockPlaceExp(b, "Farming");
				val = p.getConfig().getDouble("BasePay");
				p.deposit(player, val * exp);
				ranks.xpGain(player, SkillType.FARMING, exp);
			}else{
				exp = getBlockPlaceExp(b, "Building");
				val = 0.25;
				p.deposit(player, val * exp);
				ranks.xpGain(player, SkillType.BUILDING, exp);
			}
		}	
	}
		
	//Checks if block can be mined
	public boolean isMinable(Material b){
		if(p.getConfig().get("Mining.Exp.Destroy." + b.toString()) != null){
			return true;
		}else{
			return false;
		}
	}
		
	//Checks if block can be dug
	public boolean isDiggable(Material b){
		if(p.getConfig().get("Digging.Exp.Destroy." + b.toString()) != null){
			return true;
		}else{
			return false;
		}
	}
		
	//Checks if block can be cut down
	public boolean isCuttable(Material b){
		if(p.getConfig().get("Logging.Exp.Destroy." + b.toString()) != null){
			return true;
		}else{
			return false;
		}
	}
	
	//Blocks destroyed that only count towards Farmers exp
	public boolean isFarmerDestroyable(Material b){
		if(p.getConfig().get("Farming.Exp.Destroy." + b.toString()) != null){
			return true;
		}else{
			return false;
		}
	}
	
	//Blocks placed that only count towards builders exp
	public boolean isPlaceable(Material b, String skill){
		if(p.getConfig().get(skill + ".Exp.Place." + b.toString()) != null){
			return true;
		}else{
			return false;
		}
	}
		
	//Gets the exp from blocks Mined, Dug, and Cut
	public int getBlockDestroyExp(Material b, String skill){
		String bname = b.toString();
		if(p.getConfig().get(skill + ".Exp.Destroy." + bname) != null){
			exp = p.getConfig().getInt(skill + ".Exp.Destroy." + bname);
			return exp;
		}else{
			return 0;
		}
	}
		
	//Gets the exp from blocks all blocks placed by Builder, and Farmer
	public int getBlockPlaceExp(Material b, String skill){
		String bname = b.toString();
		if(p.getConfig().get(skill + ".Exp.Place." + bname) != null){
			int exp = p.getConfig().getInt(skill + ".Exp.Place." + bname);
			return exp;
		}else{
			return 0;
		}
	}
}
