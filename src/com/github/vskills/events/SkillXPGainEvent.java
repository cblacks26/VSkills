package com.github.vskills.events;

import java.util.UUID;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.github.vskills.datatypes.SkillType;

public class SkillXPGainEvent extends Event{

	private static final HandlerList handlers = new HandlerList();
    private UUID id;
    private SkillType skill;
    private int xp;
    
    public SkillXPGainEvent(UUID id, SkillType skill, int xp) {
        this.id = id;
        this.skill = skill;
        this.xp = xp;
    }

	public UUID getUserID() {
        return id;
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
