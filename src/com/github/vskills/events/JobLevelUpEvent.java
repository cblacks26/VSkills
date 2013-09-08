package com.github.vskills.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.github.vskills.datatypes.JobType;

public class JobLevelUpEvent extends Event{

	private static final HandlerList handlers = new HandlerList();
    private Player player;
    private JobType job;
    private int level;
    
    public JobLevelUpEvent(Player player, JobType job, int level) {
        this.player = player;
        this.job = job;
        this.level = level;
    }

	public Player getPlayer() {
        return player;
    }
    
	public JobType getJobType(){
		return job;
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
