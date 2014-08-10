package com.github.vskills.events;

import java.util.UUID;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.github.vskills.datatypes.SkillType;

public class SkillLevelUpEvent extends Event{

	private static final HandlerList handlers = new HandlerList();
    private UUID id;
    private SkillType skill;
    private int level;
    
    public SkillLevelUpEvent(UUID id, SkillType skill, int level) {
        this.id = id;
        this.skill = skill;
        this.level = level;
    }

	public UUID getUserID() {
        return id;
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
