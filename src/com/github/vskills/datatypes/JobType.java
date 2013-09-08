package com.github.vskills.datatypes;

public enum JobType {

	BUILDING("Building"),
	DIGGING("Digging"),
	FARMING("Farming"),
	HUNTING("Hunting"),
	MINING("Mining"),
	WOODCUTTING("Woodcutting");
	
	private String name;

	JobType(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
}
