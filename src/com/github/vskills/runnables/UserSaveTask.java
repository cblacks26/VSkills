package com.github.vskills.runnables;

import com.github.vskills.Main;
import com.github.vskills.util.UserManager;

public class UserSaveTask implements Runnable{
	
	UserManager userManager = Main.getUserManager();
	
	public void run() {
	    userManager.saveUsers();
	    Main.writeMessage("Saving Users...");
	}
	
}
