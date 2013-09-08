package com.github.vskills.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.github.vskills.datatypes.JobType;

public class JobXPGainEvent extends Event{

	private static final HandlerList handlers = new HandlerList();
    private Player player;
    private JobType job;
    private int xp;
    
    public JobXPGainEvent(Player player, JobType job, int xp) {
        this.player = player;
        this.job = job;
        this.xp = xp;
    }

	public Player getPlayer() {
        return player;
    }
    
	public JobType getJobType(){
		return job;
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