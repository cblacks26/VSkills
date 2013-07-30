package me.cblacks26.vskills.user;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

public class UserManager {

	private Map<String, User> players = new HashMap<String, User>();
	
	public User addUser(Player player){
		
		String pname = player.getName();
		User user = (User)players.get(pname);
		
	    if (user != null) {
	        user.setUser(player);
	    }else {
	    	user = new User(player);
	        players.put(pname, user);
	    }
		
		return user;
	}
	
	public void removeUser(Player player){
		String pname = player.getName();
		players.remove(pname);
	}
	
	public void clearUsers(){
		players.clear();
	}
	
	public void saveUsers(){
		for(User user : players.values()){
			user.getProfile().saveUser();
		}
	}
	
}
