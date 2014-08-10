package com.github.vskills.events;

import java.util.UUID;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class RankUpEvent extends Event{

	private static final HandlerList handlers = new HandlerList();
    private UUID id;
    private int rank;
    
    public RankUpEvent(UUID id, int rank) {
        this.id = id;
        this.rank = rank;
    }

	public UUID getUserID() {
        return id;
    }
    
    public int getRank(){
    	return rank;
    }
 
    public HandlerList getHandlers() {
        return handlers;
    }
 
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
