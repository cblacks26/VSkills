package com.github.vskills.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.github.vskills.Main;

public class MySQL extends Database{
	
	private String host;
	private String port;
	private String dbname;
	private String username;
	private String password;
	private Connection c;
	private Statement s;
	
	public MySQL(String host, String port, String dbname, String username, String password){
		super();
		this.host = host;
		this.port = port;
		this.dbname = dbname;
		this.username = username;
		this.password = password;
	}

	public boolean initialize(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			return true;
		} catch (ClassNotFoundException e) {
			Main.writeError("Error initializing MySQL: " + e.getMessage());
			return false;
		}
	}
	
	public boolean open(){
	    if (initialize()) {
	      String url = "jdbc:mysql://" + host + ":" + port + "/" + dbname;
	      try {
	        c = DriverManager.getConnection(url, username, password);
	        return true;
	      } catch (SQLException e) {
	        Main.writeError("Could not establish a MySQL connection: " + e.getMessage());
	        return false;
	      }
	    }
	    return false;
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
			String extra = "CREATE TABLE IF NOT EXISTS VSkills (name VARCHAR(50), kills Integer, deaths Integer," +
					" tokens Integer, money Double, rank Integer, power Integer, cpower Integer)";
			String xp = "CREATE TABLE IF NOT EXISTS VSkills_xp (name VARCHAR(50), acrobat Integer, archery Integer, axe Integer, hoe Integer, pickaxe Integer," +
					" shovel Integer, sword Integer, unarmed Integer, builder Integer, digger Integer, farmer Integer," +
					" hunter Integer, miner Integer, woodcutter Integer)";
			String level = "CREATE TABLE IF NOT EXISTS VSkills_levels (name VARCHAR(50), acrobat Integer, archery Integer, axe Integer, hoe Integer, pickaxe Integer," +
					" shovel Integer, sword Integer, unarmed Integer, builder Integer, digger Integer, farmer Integer," +
					" hunter Integer, miner Integer, woodcutter Integer)";
			s.addBatch(extra);
			s.addBatch(xp);
			s.addBatch(level);
			s.executeBatch();
			c.commit();
			s.close();
			c.close();
		} catch (SQLException e) {
			Main.writeError("Error Creating Tables: " + e.getMessage());
		}
	}
}
