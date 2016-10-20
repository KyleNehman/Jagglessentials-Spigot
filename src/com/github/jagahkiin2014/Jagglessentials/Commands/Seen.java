package com.github.jagahkiin2014.Jagglessentials.Commands;

import java.io.File;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

import com.github.jagahkiin2014.Jagglessentials.Jagglessentials;
import com.github.jagahkiin2014.Jagglessentials.Utils.Log;

public class Seen implements CommandExecutor {
	
	Plugin plugin;
	public Seen(Plugin instance) {
		plugin = instance;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		
		if(cmdLabel.equalsIgnoreCase("seen")) {
			if(sender.hasPermission("je.seen")) {
				if(args.length < 1) {
					JECommand.tooFewArgs(sender);
					
				} else if(args.length > 1) {
					JECommand.tooManyArgs(sender);
					
				} else if(args.length == 1) {
					Player target = plugin.getServer().getPlayer(args[0]);
					UUID uuid = target.getUniqueId();
					File userFile = new File(Jagglessentials.UserDir + File.separator + uuid + ".yml");
					YamlConfiguration userInfo = new YamlConfiguration();
					
					if(sender.hasPermission("je.seen.normal")) {
						
						if(!target.isOnline()) {
							seenTop(sender);
							
							seenBot(sender);
							
						} else {
							seenTop(sender);
							
							JECommand.msg(sender, "&6Status: &aOnline");
							JECommand.msg(sender, "&6UUID:&a" + uuid);
							JECommand.msg(sender, "&6Known Aliases: &a" + JECommand.getAliases(userFile, userInfo, uuid));
							
							seenBot(sender);
						}
						
					} else if(sender.hasPermission("je.seen.moderator")) {
						
						if(!target.isOnline()) {
							seenTop(sender);
							
							seenBot(sender);
							
						} else {
							seenTop(sender);
							
							JECommand.msg(sender, "&6Status: &aOnline");
							JECommand.msg(sender, "&6UUID:&a" + uuid);
							JECommand.msg(sender, "&6Known Aliases: &a" + JECommand.getAliases(userFile, userInfo, uuid));
							
							seenBot(sender);
						}
						
					} else if(sender.hasPermission("je.seen.admin")) {
						
						if(!target.isOnline()) {
							seenTop(sender);
							
							seenBot(sender);
							
						} else {
							seenTop(sender);
							
							JECommand.msg(sender, "&6Status: &aOnline");
							JECommand.msg(sender, "&6UUID:&a" + uuid);
							JECommand.msg(sender, "&6Known Aliases: &a" + JECommand.getAliases(userFile, userInfo, uuid));
							
							seenBot(sender);
							
						}
					}
				}
			} else {
				
				JECommand.noPerms(sender);
			}
		}
		return false;
	}
	
	public void seenTop(CommandSender sender) {
		sender.sendMessage(Log.ColorMessage("&6+-----&bUser&6---&bInfo&6-----+"));
	}
	
	public void seenBot(CommandSender sender) {
		PluginDescriptionFile pdFile = plugin.getDescription();
		String ver = pdFile.getVersion();
		sender.sendMessage(Log.ColorMessage("&6+--&b[JE]&6-&bv" + ver + "&6---+"));
	}
}
