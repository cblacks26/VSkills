package com.github.vskills.datatypes;

import org.bukkit.Bukkit;

public enum Commands {

	VBOARD("VBoard", "VSkills.board", Bukkit.getPluginCommand("VBoard").getDescription(), Bukkit.getPluginCommand("VBoard").getUsage()),
	VGOD("VGod", "VSkills.god", Bukkit.getPluginCommand("VGod").getDescription(), Bukkit.getPluginCommand("VGod").getUsage()),
	VRESET("VReset", "VSkills.reset", Bukkit.getPluginCommand("VReset").getDescription(), Bukkit.getPluginCommand("VReset").getUsage()),
	VPOWER("VPower", "VSkills.power", Bukkit.getPluginCommand("VPower").getDescription(), Bukkit.getPluginCommand("VPower").getUsage()),
	VSAVE("VSave", "VSkills.save", Bukkit.getPluginCommand("VSave").getDescription(), Bukkit.getPluginCommand("VSave").getUsage()),
	VSET("VSet", "VSkills.set", Bukkit.getPluginCommand("VSet").getDescription(), Bukkit.getPluginCommand("VSet").getUsage()),
	VSKILLS("VSkills", "VSkills.help", Bukkit.getPluginCommand("VSkills").getDescription(), Bukkit.getPluginCommand("VSkills").getUsage()),
	VSTATS("VStats", "VSkills.stats", Bukkit.getPluginCommand("VStats").getDescription(), Bukkit.getPluginCommand("VStats").getUsage()),
	VTOKENS("VTokens", "VSkills.tokens", Bukkit.getPluginCommand("VTokens").getDescription(), Bukkit.getPluginCommand("VTokens").getUsage());
	
	private String name;
	private String perm;
	private String desc;
	private String usage;
	
	Commands(String name, String perm, String desc, String usage){
		this.name = name;
		this.perm = perm;
		this.desc = desc;
		this.usage = usage;
	}

	public String getName(){
		return name;
	}
	
	public String getPerm(){
		return perm;
	}
	
	public String getDescription(){
		return desc;
	}

	public String getUsage(){
		return usage;
	}
}
