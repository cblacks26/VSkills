package me.cblacks26.vskills.util;

public enum SkillType {
	
	MINING("MINING", "Mining"),
	DIGGING("DIGGING", "Digging"),
	HUNTING("HUNTING", "Hunting"),
	FARMING("FARMING", "Farming"),
	BUILDING("BUILDING", "Building"),
	LOGGING("LOGGING", "Logging");
	
	private String name;
	private String display;

	SkillType(String name, String display){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public String getDisplayName(){
		return display;
	}	
}
