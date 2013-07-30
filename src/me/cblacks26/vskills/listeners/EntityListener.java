package me.cblacks26.vskills.listeners;

import java.sql.SQLException;

import me.cblacks26.vskills.Main;
import me.cblacks26.vskills.util.Ranks;
import me.cblacks26.vskills.util.SkillType;
import me.cblacks26.vskills.util.Util;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityListener implements Listener{
	
	private Main main;
	Ranks ranks = new Ranks();
	Util util = new Util();
	
	int exp;
	double val;  
	
	public EntityListener(Main p){
		main = p;
	}
	
	@EventHandler
	public void PlayerKillsMob(EntityDeathEvent event) throws SQLException{
		Entity killer = event.getEntity().getKiller();
		EntityType e = event.getEntity().getType();
		if(killer instanceof Player){
			Player k = event.getEntity().getKiller();
			String kname = k.getName();
			if(main.getConfig().get("BasePay") != null ){
				val = main.getConfig().getDouble("BasePay");
				exp = getEntityExp(e);
				main.deposit(k, val * exp);
				ranks.xpGain(k, SkillType.HUNTING, exp);
				util.addUserStats(kname, "KILLS", 1);
			}else{
				val = 0.25;
				exp = getEntityExp(e);
				main.deposit(k, val * exp);
				ranks.xpGain(k, SkillType.HUNTING, exp);
				util.addUserStats(kname, "KILLS", 1);
			}
			
		}		
	}
	
	public int getEntityExp(EntityType e){
		String ename = e.toString();
		if(main.getConfig().get("Hunting.Exp.Kill." + ename) != null){
			exp = main.getConfig().getInt("Hunting.Exp.Kill." + ename);
			return exp;
		}else{
			return 0;
		}
	}
	
}
