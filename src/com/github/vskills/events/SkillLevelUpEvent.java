package com.github.vskills.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.github.vskills.datatypes.SkillType;

public class SkillLevelUpEvent extends Event{

	private static final HandlerList handlers = new HandlerList();
    private Player player;
    private SkillType skill;
    private int level;
    
    public SkillLevelUpEvent(Player player, SkillType skill, int level) {
        this.player = player;
        this.skill = skill;
        this.level = level;
    }

	public Player getPlayer() {
        return player;
    }
    
	public SkillType getSkillType(){
		return skill;
	}
    
    public int getLevel(){
    	return level;
    }
 
    public HandlerList getHandlers() {
        return handlers;
    }
 
    public static HandlerList getHandlerList() {
        return handlers;
    }

	
	
}
