package com.github.jagahkiin2014.Jagglessentials.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class Sethome implements CommandExecutor {
	
	Plugin plugin;
	public Sethome(Plugin instance) {
		plugin = instance;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		if(cmdLabel.equalsIgnoreCase("sethome")) {
			
		}
		return false;
	}
}
