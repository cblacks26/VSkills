package com.github.vskills.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.github.vskills.Main;

public class SQLite extends Database{

	private Connection c;
	private Statement s;
	private File f;
	
	public SQLite(File file) {
		super();
		this.f = file;
	}

	public boolean initialize(){
		try{
			Class.forName("org.sqlite.JDBC");
			return true;
		} catch (ClassNotFoundException e) {
			Main.writeError("Error initializing SQLite: " + e.getMessage());
			return false;
		}
	}
	
	public boolean open(){
		if (initialize()) {
			try {
				c = DriverManager.getConnection("jdbc:sqlite:" + f.getPath());
				return true;
			} catch (SQLException e) {
				Main.writeError("Error connecting to SQLite: " + e.getMessage());
				return false;
			}
		} else {
			return false;
		}
	}
	
	public void closeConnection(){
		try{
			if(c != null){
				c.close();
			}
		}catch(SQLException e){
			Main.writeError("Error closing Connection: " + e.getMessage());
		}
	}
	
	public Connection getConnection(){
		if(open()){
			return c;
		}else{
			Main.writeError("Connection is null");
			return null;
		}
	}
	
	public void createTables(){
		try {
			open();
        	c = getConnection();
        	c.setAutoCommit(false);
			s = c.createStatement();
			String table1 = "CREATE TABLE IF NOT EXISTS VSkills (name VARCHAR(50), kills Integer, deaths Integer," +
					" tokens Integer, money Double, rank Integer, archery Integer, axe Integer, hoe Integer, pickaxe Integer," +
					" shovel Integer, sword Integer, unarmed Integer, builder Integer, digger Integer, farmer Integer," +
					" hunter Integer, miner Integer, woodcutter Integer)";
			String table2 = "CREATE TABLE IF NOT EXISTS VSkills_levels (name VARCHAR(50), archery Integer, axe Integer, hoe Integer, pickaxe Integer," +
					" shovel Integer, sword Integer, unarmed Integer, builder Integer, digger Integer, farmer Integer," +
					" hunter Integer, miner Integer, woodcutter Integer)";
			s.addBatch(table1);
			s.addBatch(table2);
			s.executeBatch();
			c.commit();
			s.close();
			c.close();
		} catch (SQLException e) {
			Main.writeError("Error Creating Tables: " + e.getMessage());
		}
	}
}
