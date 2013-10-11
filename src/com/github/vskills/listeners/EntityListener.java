package com.github.vskills.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;

import com.github.vskills.Main;
import com.github.vskills.datatypes.JobType;
import com.github.vskills.datatypes.SkillType;
import com.github.vskills.events.JobXPGainEvent;
import com.github.vskills.events.SkillXPGainEvent;
import com.github.vskills.util.AbilitiesManager;
import com.github.vskills.util.EntityUtil;
import com.github.vskills.util.ItemUtil;
import com.github.vskills.util.UserManager;

public class EntityListener implements Listener{

	ItemUtil itemUtil = new ItemUtil();
	EntityUtil entityUtil = new EntityUtil();
	UserManager userManager = Main.getUserManager();
	
	@EventHandler
	public void onEntityDeathEvent(EntityDeathEvent event){
		PluginManager pm = Bukkit.getServer().getPluginManager();
		EntityType entity = event.getEntity().getType();
		EntityDamageEvent e = event.getEntity().getLastDamageCause();
        if(e instanceof EntityDamageByEntityEvent){
            EntityDamageByEntityEvent entityevent = (EntityDamageByEntityEvent) e;
            if(entityevent.getDamager() instanceof Arrow){
                Arrow a = (Arrow)entityevent.getDamager();
                if(a.getShooter() instanceof Player){
                	Player player = (Player) a.getShooter();
                    int xp = entityUtil.getEntityXP(entity);
                    JobXPGainEvent jobevent = new JobXPGainEvent(player, JobType.HUNTER, xp);
                    pm.callEvent(jobevent);
                    SkillXPGainEvent skillevent = new SkillXPGainEvent(player, SkillType.ARCHERY, xp);
                    pm.callEvent(skillevent);
                }
            }else if(entityevent.getDamager() instanceof Player){
            	Player player = (Player) entityevent.getDamager();
            	ItemStack item = player.getItemInHand();
            	int xp = entityUtil.getEntityXP(entity);
            	JobXPGainEvent jobevent = new JobXPGainEvent(player, JobType.HUNTER, xp);
                pm.callEvent(jobevent);
            	if(itemUtil.isSword(item)){
                    SkillXPGainEvent skillevent = new SkillXPGainEvent(player, SkillType.SWORD, xp);
                    pm.callEvent(skillevent);
            	}else if(itemUtil.isAxe(item)){
                    SkillXPGainEvent skillevent = new SkillXPGainEvent(player, SkillType.AXE, xp);
                    pm.callEvent(skillevent);
            	}else if(itemUtil.isPick(item)){
                    SkillXPGainEvent skillevent = new SkillXPGainEvent(player, SkillType.PICKAXE, xp);
                    pm.callEvent(skillevent);
            	}else if(itemUtil.isHoe(item)){
                    SkillXPGainEvent skillevent = new SkillXPGainEvent(player, SkillType.HOE, xp);
                    pm.callEvent(skillevent);
            	}else if(itemUtil.isShovel(item)){
                    SkillXPGainEvent skillevent = new SkillXPGainEvent(player, SkillType.SHOVEL, xp);
                    pm.callEvent(skillevent);
            	}else if(itemUtil.isUnarmed(item)){
                    SkillXPGainEvent skillevent = new SkillXPGainEvent(player, SkillType.UNARMED, xp);
                    pm.callEvent(skillevent);
            	}else{
            		return;
            	}
            }
        }
	}
	
	@EventHandler
	public void EntityDamagebyPlayer(EntityDamageByEntityEvent event){
		if(event.getDamager() instanceof Player){
			Player player = (Player)event.getDamager();
			double damage = event.getDamage();
			if(itemUtil.isUnarmed(player.getItemInHand())){
				double dmg = AbilitiesManager.runPowerPunch(player, damage);
				event.setDamage(dmg);
				userManager.scoreboard(player);
			}else if(itemUtil.isSword(player.getItemInHand())){
				double dmg = AbilitiesManager.runPowerSword(player, damage);
				event.setDamage(dmg);
				userManager.scoreboard(player);
			}
		}
	}
	
	@EventHandler
	public void ArrowShot(EntityShootBowEvent event){
		if ((!(event.getEntity() instanceof Player)) || (!(event.getProjectile() instanceof Arrow))) {
		      return;
		    }

		Player player = (Player)event.getEntity();
		Arrow arrow = (Arrow)event.getProjectile();
		AbilitiesManager.runBlazingArrows(player, arrow);
	}
	
}
