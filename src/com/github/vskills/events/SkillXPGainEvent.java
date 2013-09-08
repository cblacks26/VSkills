package com.github.vskills.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.github.vskills.datatypes.SkillType;

public class SkillXPGainEvent extends Event{

	private static final HandlerList handlers = new HandlerList();
    private Player player;
    private SkillType skill;
    private int xp;
    
    public SkillXPGainEvent(Player player, SkillType skill, int xp) {
        this.player = player;
        this.skill = skill;
        this.xp = xp;
    }

	public Player getPlayer() {
        return player;
    }
    
	public SkillType getSkillType(){
		return skill;
	}
    
    public int getXPGained(){
    	return xp;
    }
 
    public HandlerList getHandlers() {
        return handlers;
    }
 
    public static HandlerList getHandlerList() {
        return handlers;
    }
	
}
