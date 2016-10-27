package com.github.jagahkiin2014.Jagglessentials.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.github.jagahkiin2014.Jagglessentials.Jagglessentials;

public class Mute implements CommandExecutor {
	
	Plugin plugin;
	public Mute(Plugin instance) {
		plugin = instance;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		if(cmdLabel.equalsIgnoreCase("mute")) {
			
			if (sender.hasPermission("je.mute")) {
				if (args.length == 1) {
					String playerName = args[0];	 // Just for readability
					String msg;						 // Success message for the muter
					
					// This gives the ability to toggle mute with one commmand, 
					// But it could easily be changed to a separate unmute command
					if (Jagglessentials.muted.contains(playerName)) { 
					
						Jagglessentials.muted.remove(playerName);
						msg = playerName + " has been un-muted";
					} else {
						
						Jagglessentials.muted.add(playerName);
						msg = playerName + " has been muted";
					}
					
					sendMessage(sender, msg);
					
				} else if(args.length == 2) {
					
					
				} else if(args.length > 2) {
					JECommand.tooManyArgs(sender);
				} else {
					JECommand.tooFewArgs(sender);
				}
			}
			
		}
		return false;
	}
	
	// A simple function that sends a message to the sender
	// Regardless of if they're a player or console
	private void sendMessage(CommandSender sender, String msg) {
		
		if (sender instanceof Player) {
			((Player) sender).sendMessage(msg);
		} else {
			plugin.getLogger().info(msg);
		}
	}
}
