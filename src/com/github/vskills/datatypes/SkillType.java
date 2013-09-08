package com.github.vskills.datatypes;

public enum SkillType {

	ARCHERY("Archery"),
	AXE("Axe"),
	HOE("Hoe"),
	PICKAXE("Pickaxe"),
	SHOVEL("Shovel"),
	SWORD("Sword"),
	UNARMED("Unarmed");
	
	private String name;
	
	SkillType(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
}
