package com.github.vskills.datatypes;

public enum JobType {

	BUILDER("Builder"),
	DIGGER("Digger"),
	FARMER("Farmer"),
	HUNTER("Hunter"),
	MINER("Miner"),
	WOODCUTTER("Woodcutter");
	
	private String name;

	JobType(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
}
