package com.github.vskills.datatypes;

public enum SkillType {

	ACROBATICS("Acrobatics", "acrobat"),
	ARCHERY("Archery", "archery"),
	AXE("Axe", "axe"),
	HOE("Hoe", "hoe"),
	PICKAXE("Pickaxe", "pickaxe"),
	SHOVEL("Shovel", "shovel"),
	SWORD("Sword", "sword"),
	UNARMED("Unarmed", "unarmed");
	
	private String name;
	private String sqlname;
	
	SkillType(String name, String sqlname){
		this.name = name;
		this.sqlname = sqlname;
	}
	
	public String getName(){
		return name;
	}
	
	public String getSQLName(){
		return sqlname;
	}
}
