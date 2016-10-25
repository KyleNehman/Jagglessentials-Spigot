package com.github.jagahkiin2014.Jagglessentials.Commands;

import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Ban implements CommandExecutor {
	
	Plugin plugin;
	public Ban(Plugin instance) {
		plugin = instance;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		if(cmdLabel.equalsIgnoreCase("ban")) {
			
			if(sender.hasPermission("je.ban")) {
				if(args.length == 0) {
					JECommand.tooFewArgs(sender);
					
				} else if(args.length == 1) {
					Player target = Bukkit.getPlayer(args[0]);
					Bukkit.getBanList(Type.NAME).addBan(target.getName(), "No reason specified.", null, sender.getName());
					if(target != null)
						target.kickPlayer(args[0] + " was banned from the server.");
					
				} else if(args.length > 1) {
					Player target = Bukkit.getPlayer(args[0]);
					StringBuilder sb = new StringBuilder();
					for (int i = 1; i < args.length; i++) {
						sb.append(args[i]).append(" ");
					}
					String allArgs = sb.toString().trim();
					
					Bukkit.getBanList(Type.NAME).addBan(target.getName(), allArgs, null, sender.getName());
					if(target != null)
						target.kickPlayer(args[0] + " was banned from the server.");
					
				}
			} else {
				JECommand.noPerms(sender);
				
			}
		}
		return false;
	}
}
