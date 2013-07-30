package me.cblacks26.vskills.user;

import org.bukkit.entity.Player;

public class User {

	private Player player;
	private UserProfile profile;
	
	public User(Player player){
		this.setUser(player);
		this.profile = new UserProfile(player);
	}
	
	public UserProfile getProfile(){
		return this.profile;
	}
	
	public void setUser(Player player){
		this.setUser(player);
	}

	public Player getUser() {
		return player;
	}
	
}
