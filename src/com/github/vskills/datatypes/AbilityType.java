package com.github.vskills.datatypes;

public enum AbilityType {

	BLAZINGARROWS("BlazeArrow", SkillType.ARCHERY, 10),
	INSTACUT("InstaCut", SkillType.AXE, 5),
	INSTADIG("InstaDig", SkillType.SHOVEL, 5),
	INSTAGROW("InstaGrow", SkillType.HOE, 9),
	INSTAMINE("InstaMine", SkillType.PICKAXE, 5),
	POWERPUNCH("PowerPunch", SkillType.UNARMED, 10),
	POWERSWORD("PowerSword", SkillType.SWORD, 10);
	
	private String name;
	private SkillType skill;
	private int cost;
	
	AbilityType(String name, SkillType skill, int cost){
		this.name = name;
		this.cost = cost;
	}
	
	public String getName(){
		return name;
	}
	
	public SkillType getSkill(){
		return skill;
	}
	
	public int getCost(){
		return cost;
	}
	
}
