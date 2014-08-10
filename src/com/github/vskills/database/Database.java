package com.github.vskills.database;

import java.sql.Connection;

public abstract class Database {
	
	public Database(){
	}
	
	public abstract Connection getConnection();
	
	public abstract void closeConnection();

	public abstract boolean initialize();
	
	public abstract boolean open();
	
	public abstract void createTables();
	
}
