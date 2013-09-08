package com.github.vskills.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class RankUpEvent extends Event{

	private static final HandlerList handlers = new HandlerList();
    private Player player;
    private int rank;
    
    public RankUpEvent(Player player, int rank) {
        this.player = player;
        this.rank = rank;
    }

	public Player getPlayer() {
        return player;
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
