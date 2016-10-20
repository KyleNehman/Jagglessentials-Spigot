package com.github.jagahkiin2014.Jagglessentials.Commands;

import java.io.File;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
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
					File userFile = new File(Jagglessentials.UserDir + File.separator + getUUID(sender, target) + ".yml");
					YamlConfiguration userInfo = new YamlConfiguration();
					
					if(sender.hasPermission("je.seen.normal")) {
						
						if(!target.isOnline()) {
							seenTop(sender);
							
							JECommand.msg(sender, "&6Status: &cOffline");
							JECommand.msg(sender, "&6UUID:&a " + getUUID(sender, target));
							JECommand.msg(sender, "&6Known Aliases: &a" + JECommand.getAliases(userFile, userInfo, getUUID(sender, target)));
							
							seenBot(sender);
							
						} else {
							seenTop(sender);
							
							JECommand.msg(sender, "&6Status: &aOnline");
							JECommand.msg(sender, "&6UUID:&a" + getUUID(sender, target));
							JECommand.msg(sender, "&6Known Aliases: &a" + JECommand.getAliases(userFile, userInfo, getUUID(sender, target)));
							
							seenBot(sender);
						}
						
					} else if(sender.hasPermission("je.seen.moderator")) {
						
						if(!target.isOnline()) {
							seenTop(sender);
							
							JECommand.msg(sender, "&6Status: &cOffline");
							JECommand.msg(sender, "&6UUID:&a " + getUUID(sender, target));
							JECommand.msg(sender, "&6Known Aliases: &a" + JECommand.getAliases(userFile, userInfo, getUUID(sender, target)));
							
							seenBot(sender);
							
						} else {
							seenTop(sender);
							
							JECommand.msg(sender, "&6Status: &aOnline");
							JECommand.msg(sender, "&6UUID:&a " + getUUID(sender, target));
							JECommand.msg(sender, "&6Known Aliases: &a" + JECommand.getAliases(userFile, userInfo, getUUID(sender, target)));
							
							seenBot(sender);
						}
						
					} else if(sender.hasPermission("je.seen.admin")) {
						
						if(!target.isOnline()) {
							seenTop(sender);
							
							JECommand.msg(sender, "&6Status: &cOffline");
							JECommand.msg(sender, "&6UUID:&a " + getUUID(sender, target));
							JECommand.msg(sender, "&6Known Aliases: &a" + JECommand.getAliases(userFile, userInfo, getUUID(sender, target)));
							JECommand.msg(sender, "&6Last Known IP:&a ");
							
							seenBot(sender);
							
						} else {
							seenTop(sender);
							
							JECommand.msg(sender, "&6Status: &aOnline");
							JECommand.msg(sender, "&6UUID:&a" + getUUID(sender, target));
							JECommand.msg(sender, "&6Known Aliases: &a" + JECommand.getAliases(userFile, userInfo, getUUID(sender, target)));
							JECommand.msg(sender, "&6IP Address:&a " + target.getServer().getIp());
							JECommand.msg(sender, "");
							JECommand.msg(sender, "&6+----&bHistory&6----+");
							JECommand.msg(sender, "&6Kicks:&a " + (userInfo.getStringList("history.kicks").size() - 1));
							JECommand.msg(sender, "&6Tempbans:&a " + (userInfo.getStringList("history.tempbans").size() - 1));
							JECommand.msg(sender, "&6Bans:&a " + (userInfo.getStringList("history.bans").size() - 1));
							JECommand.msg(sender, "&6Unbans:&a " + (userInfo.getStringList("history.unbans").size() - 1));
							JECommand.msg(sender, "&8Use '&7/history <player>&8' for a detailed synopsis.");
							
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
	
	private void seenTop(CommandSender sender) {
		sender.sendMessage(Log.ColorMessage("&6+-----&bUser&6---&bInfo&6-----+"));
	}
	
	private void seenBot(CommandSender sender) {
		PluginDescriptionFile pdFile = plugin.getDescription();
		String ver = pdFile.getVersion();
		sender.sendMessage(Log.ColorMessage("&6+--&b[JE]&6-&bv" + ver + "&6---+"));
	}
	
	private UUID getUUID(CommandSender sender, OfflinePlayer target) {
		if(target.hasPlayedBefore()) {
			UUID uuid = target.getUniqueId();
			return uuid;
		} else {
			JECommand.msg(sender, "&cError: No record of player found.");
		}
		return null;
	}
}
