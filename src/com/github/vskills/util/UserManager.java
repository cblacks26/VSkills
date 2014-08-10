package com.github.vskills.util;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.github.vskills.user.User;

public class UserManager {
	
	private final HashMap<UUID, User> users = new HashMap<UUID, User>();
	
	// Returns the User from the HashMap
	public User getUser(UUID id){
		return users.get(id);
	}
	// Adds the User to the HashMap
	public void addUser(UUID id){
		User user = loadUser(id);
		users.put(id, user);
	}
	// Adds all online Users to the HashMap
	public void addUsers(){
		for(Player player: Bukkit.getServer().getOnlinePlayers()){
			addUser(player.getUniqueId());
		}
	}
	// Removes the User form the HashMap
	public void removeUser(UUID id){
		users.remove(id);
	}
	// Removes all the Users from the HashMap
	public void removeUsers(){
		users.clear();
	}
	// Saves all online Users to the Database
	public void saveUsers(){
		for(Player player: Bukkit.getOnlinePlayers()){
			getUser(player.getUniqueId()).saveUser();
		}
	}
	// Returns a loaded or created User
	private User loadUser(UUID id){
		User user = new User(id);
		if(user.checkUser()){
			user.loadUser();
		}else{
			user.createUser();
		}
		return user;
	}
	// Refreshes all online Users' Scoreboards
	public void getScoreboards(){
		for(Player player: Bukkit.getServer().getOnlinePlayers()){
			getUser(player.getUniqueId()).scoreboard();
		}
	}
}