package me.cblacks26.vskills.commands;

import me.cblacks26.vskills.Main;
import me.cblacks26.vskills.util.Util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandTokens implements CommandExecutor
{

	private Main main;
	Util util = new Util();
	
	public CommandTokens(Main p){
		main = p;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("Tokens")){
			if(sender instanceof Player){
				Player player = (Player) sender;
				int tokens = util.getUserTokens(player);
				if(args.length < 1 || args.length > 1){
					player.sendMessage(ChatColor.RED + "Usage: /Tokens <option>");
					player.sendMessage(ChatColor.RED + "Options: Reset, ");
					return true;
				}
				if(args.length == 1){
					if(main.isAuthorized(player, "vskills.tokens") == false){
						player.sendMessage(ChatColor.RED + "You don't have the Permissions for this command");
						return true;
					}else{
						if(tokens == 0){
							player.sendMessage(ChatColor.RED + "You must have tokens to run this command");
							return true;
						}else{
							if(args[0].equalsIgnoreCase("Reset")){
								util.setUserStats(player, 0, 0, 0);
								util.setUserTokens(player, tokens - 1);
								player.sendMessage("You have Reset your stats!");
								return true;
							}
							if(main.getConfig().get("Tokens." + args[0]) != null){
								int tkn = main.getConfig().getInt("Tokens." + args[0]);
								if(util.getUserTokens(player) >= tkn){
									String iname = args[0].toUpperCase();
									ItemStack item = new ItemStack(Material.matchMaterial(iname), 1);
									item.addEnchantment(Enchantment.DURABILITY, 3);
									player.getInventory().addItem(item);
									util.setUserTokens(player, tokens - tkn);
									return true;
								}else{
									player.sendMessage(ChatColor.RED + "You need " + tkn + " Tokens for " + args[0]);
								}
							}
						}
					}
				}
			}
			if(!(sender instanceof Player)){
				sender.sendMessage(ChatColor.RED + "Sorry this command can only be used by players.");
			}
		}
		return true;
	}
	
}
