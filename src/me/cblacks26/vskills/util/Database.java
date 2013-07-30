package me.cblacks26.vskills.util;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import me.cblacks26.vskills.Main;

public class Database {

	private Main main;
	private Connection c = null;
	private Statement s = null;
	private ResultSet rs = null;
	private PreparedStatement ps = null;
	private DatabaseMetaData md;
	
	String path = "plugins" + File.separator + "VSkills" + File.separator + "Database.db";
	File dbfile = new File(path);
	
	public Database(Main plugin){
		this.main = plugin;		
	}
	
	public boolean intialize(){
		try{
			Class.forName("org.sqlite.JDBC");
			main.writeMessage("Has initialized SQLite");
			return true;
		}catch(ClassNotFoundException e){
			main.writeError("Error initializing SQLite: " + e.getMessage());
			return false;
		}
	}
	
	public Connection getConnection(){
		try { 
			c = DriverManager.getConnection("jdbc:sqlite:" + path);
			return c;
		} catch (SQLException e) {
			main.writeError("Error Connecting to Database: " + e.getMessage());
			return null;
		}
	}
	
	public void createTables(){
		write("CREATE TABLE IF NOT EXISTS SKILLS (PLAYERNAME VARCHAR(50), DIGGING INTEGER, MINING INTEGER," +
    			" LOGGING INTEGER, BUILDING INTEGER, FARMING INTEGER, HUNTING INTEGER)");
		
    	write("CREATE TABLE IF NOT EXISTS LEVELS (PLAYERNAME VARCHAR(50), DIGGING INTEGER, MINING INTEGER," +
    			" LOGGING INTEGER, BUILDING INTEGER, FARMING INTEGER, HUNTING INTEGER)");
    	
    	write("CREATE TABLE IF NOT EXISTS STATS (PlAYERNAME VARCHAR(50), KILLS INTEGER, DEATHS INTEGER," +
    			" KD DOUBLE, RANK INTEGER, TOKENS INTEGER)");
	}

	public boolean checkTable(String table){
		try {
			c = getConnection();
			md = c.getMetaData();
			rs = md.getTables(null, null, table, null);
			if(rs.next()){
				return true;
			}else{
				return false;
			}
		} catch (SQLException e) {
			main.writeError("Error at CheckTable" + e.getMessage());
			return false;
		}finally{
			close(rs);
			close(c);
		}
	}
	
	public boolean checkColumns(String table, String col){
		try{
			c = getConnection();
			md = c.getMetaData();
			rs = md.getColumns(null, null, table, col);
			if(rs.next()){
				return true;
			}else{
				return false;
			}
		}catch(SQLException e){
			main.writeError("Error at check Columns: " + e.getMessage());
			return false;
		}finally{
			close(rs);
			close(c);
		}
	}
	
	public void write(String sql){
		try{
			c = getConnection();
			s = c.createStatement();
			s.executeUpdate(sql);
			close(s);
			close(c);
		}catch(SQLException e){
			main.writeError("Error at Write: " +  e.getMessage());
		}
	}
	
	public void write(String sql, String arg){
		try{
			c = getConnection();
			ps = c.prepareStatement(sql);
			ps.setString(1, arg);
			ps.executeUpdate();
			close(ps);
			close(c);
		}catch(SQLException e){
			main.writeError("Error at Write: " + e.getMessage());
		}
	}
	
	public void write(String sql, int val, String player){
		try{
			c = getConnection();
			ps = c.prepareStatement(sql);
			ps.setInt(1, val);
			ps.setString(2, player);
			ps.executeUpdate();
			close(ps);
			close(c);
		}catch(SQLException e){
			main.writeError("Error at Write: " + e.getMessage());
		}
	}
	
	public void close(ResultSet rs){
		try{
			if(rs != null){
				rs.close();
			}else{
				return;
			}
		}catch(SQLException e){
			main.writeError("Error at Close: " + e.getMessage());
		}
	}
	
	public void close(Statement s){
		try{
			if(s != null){
				s.close();
			}else{
				return;
			}
		}catch(SQLException e){
			main.writeError("Error closing Statement: " + e.getMessage());
		}
	}
	
	public void close(Connection c){
		try{
			if(c != null){
				c.close();
			}else{
				return;
			}
		}catch(SQLException e){
			main.writeError("Error closing Connection: " + e.getMessage());
		}
	}
	
	public void close(PreparedStatement ps){
		try{
			if(ps != null){
				ps.close();
			}else{
				return;
			}
		}catch(SQLException e){
			main.writeError("Error closing PreparedStatement: " + e.getMessage());
		}
	}
	
	public void setInt(String Player, String table, String type, int val){
		try{
			c = getConnection();
			ps = c.prepareStatement("UPDATE " + table + " SET " + type + " = ? WHERE PLAYERNAME = ?");
			ps.setInt(1, val);
			ps.setString(2, Player);
			ps.executeUpdate();
			close(ps);
			close(c);
		}catch(SQLException e){
			main.writeError("Error at setInt: " + e.getMessage());
		}
	}
	
	public void setDouble(String Player, String table, String type, double val){
		try{
			c = getConnection();
			ps = c.prepareStatement("UPDATE " + table + " SET " + type + " = ? WHERE PLAYERNAME = ?");
			ps.setDouble(1, val);
			ps.setString(2, Player);
			ps.executeUpdate();
			close(ps);
			close(c);
		}catch(SQLException e){
			main.writeError("Error at setDouble: " + e.getMessage());
		}
	}
	
	public int getInt(String Player, String table, String type){
		try {
			c = getConnection();
			s = c.createStatement();
			rs = s.executeQuery("SELECT * FROM " + table + " where PLAYERNAME = '"+Player+"';");
			rs.next();
			int val = rs.getInt(type);
			close(rs);
			close(s);
			close(c);
			return val;
		} catch (SQLException e) {
			main.writeError("Error at getInt: " + e.getMessage());
			return 0;
		}
	}
	
	public int getDouble(String Player, String table, String type){
		try {
			c = getConnection();
			s = c.createStatement();
			rs = s.executeQuery("SELECT * FROM " + table + " where PLAYERNAME = '"+Player+"';");
			rs.next();
			int val = rs.getInt(type);
			close(rs);
			close(s);
			close(c);
			return val;
		} catch (SQLException e) {
			main.writeError("Error at getDouble: " + e.getMessage());
			return 0;
		}
	}
}
