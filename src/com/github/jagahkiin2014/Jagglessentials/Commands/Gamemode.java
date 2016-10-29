package com.github.jagahkiin2014.Jagglessentials.Commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Gamemode implements CommandExecutor {
	
	Plugin plugin;
	public Gamemode(Plugin instance) {
		plugin = instance;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		if(cmdLabel.equalsIgnoreCase("gamemode")) {
			if (sender.hasPermission("je.gamemode")) {
				if (args.length == 0) {
					JECommand.tooFewArgs(sender);
				} else if (args.length > 2) {
					JECommand.tooManyArgs(sender);
				} else {
					Player target = null;
					if (args.length == 2) {
						target = Bukkit.getPlayer(args[1]);
					} else if (!(sender instanceof Player)) {
						// Just in case this is called from console
						JECommand.noPlayer(sender);
						return false;
					} else {
						target = (Player) sender;
					}
					
					Integer gmNum = Integer.getInteger(args[0]);
					if (gmNum == null) {
						JECommand.msg(sender, "Invalid gamemode -- Not a number");
						return false;
					}
					
					
					// Using a switch here because of the out of order spigot GameMode enum
					GameMode gm;
					switch(gmNum) {	
						case 1:
							gm = GameMode.CREATIVE;
							break;
						case 2:
							gm = GameMode.ADVENTURE;
							break;
						case 3:
							gm = GameMode.SPECTATOR;
							break;
						default:
							gm = GameMode.SURVIVAL;
							break;
					}
					
					target.setGameMode(gm);
					
				}
			} else {
				JECommand.noPerms(sender);
			}
		}
		return false;
	}
}