package me.cblacks26.vskills.listeners;

import java.sql.SQLException;

import me.cblacks26.vskills.Main;
import me.cblacks26.vskills.util.ItemUtil;
import me.cblacks26.vskills.util.Ranks;
import me.cblacks26.vskills.util.SkillType;
import me.cblacks26.vskills.util.Util;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerListener implements Listener{
	
	private Main main;
	Util util = new Util();
	Ranks ranks = new Ranks();
	ItemUtil itemUtil = new ItemUtil();
	
	double val;
	
	public PlayerListener(Main p){
		main = p;
	}
	
	@EventHandler
 	public void PlayerKill(PlayerDeathEvent event) throws SQLException{
		Player player = event.getEntity().getPlayer();
		String pname = player.getName();
		util.addUserStats(pname, "DEATHS", 1);		
	}
	
	@EventHandler
	public void InteractEvent(PlayerInteractEvent event){
		ItemStack item = event.getItem();
		Player player = event.getPlayer();
		if(item == null){
			return;
		}
		if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
			if(itemUtil.isHoe(item) == true){
				Material b = event.getClickedBlock().getType();
				if(isFarmable(b)){
					main.deposit(player, val);
					ranks.xpGain(player, SkillType.FARMING, 1);
				}
			}
		}
	}
	
	@EventHandler
	public void PlayerDamage(EntityDamageEvent event){
		if(event.getEntity() instanceof Player){
			Player player = (Player)event.getEntity();
			if(main.god.get(player.getName()) != null){
				event.setCancelled(true);
			}
		}
	}
	
	//Checks if block can be used on by a hoe
	public boolean isFarmable(Material b){
		switch(b){
			case DIRT: return true;
			case GRASS: return true;
			case SOIL: return false;
			default: return false;
		}
	}
	
}
