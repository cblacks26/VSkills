package com.github.vskills.datatypes;

public enum AbilityType {

	BLAZINGARROWS("BlazingArrows", 10),
	INSTACUT("InstaCut", 5),
	INSTADIG("InstaDig", 5),
	INSTAGROW("InstaGrow", 9),
	INSTAMINE("InstaMine", 5),
	POWERPUNCH("PowerPunch", 10),
	POWERSWORD("PowerSword", 10);
	
	private String name;
	private int cost;
	
	AbilityType(String name, int cost){
		this.name = name;
		this.cost = cost;
	}
	
	public String getName(){
		return name;
	}
	
	public int getCost(){
		return cost;
	}
	
}
