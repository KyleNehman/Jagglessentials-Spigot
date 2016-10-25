package com.github.jagahkiin2014.Jagglessentials.Commands;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.github.jagahkiin2014.Jagglessentials.Jagglessentials;
import com.github.jagahkiin2014.Jagglessentials.Utils.UUIDFetcher;

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
					
					try {
						UUID uuid = UUIDFetcher.getUUID(args[0]);
						File userFile = new File(Jagglessentials.UserDir, uuid + ".yml");
						YamlConfiguration userInfo = new YamlConfiguration();
						long now = Calendar.getInstance().getTimeInMillis();
						
						userInfo.load(userFile);
						
						int num = userInfo.getStringList("history.bans").size();
						
						userInfo.set("history.bans." + num + ".date", now);
						userInfo.set("history.bans." + num + ".reason", "No reason specified.");
						userInfo.set("history.bans." + num + ".banner", sender.getName());
						userInfo.save(userFile);
						
						File logs = new File(plugin.getDataFolder(), "banAndKickHistory.yml");
						YamlConfiguration logyml = new YamlConfiguration();
						logyml.load(logs);
						
						DateFormat df = new SimpleDateFormat("dd-MM-yy");
						Date obj = new Date();
						logyml.set(df.format(obj) + "." + target.getName() + ".Type", "Ban");
						logyml.set(df.format(obj) + "." + target.getName() + ".Duration", "inf");
						logyml.set(df.format(obj) + "." + target.getName() + ".Banner", sender.getName());
						logyml.set(df.format(obj) + "." + target.getName() + ".Reason", "No reason specified.");
						
						logyml.save(logs);
						
					} catch (IOException | InvalidConfigurationException e) {
						e.printStackTrace();
					}
					
					for(Player players : Bukkit.getOnlinePlayers()) {
						if(players.hasPermission("je.bannotify")) {
							JECommand.msg(players, "&c" + target.getName() + "&6 was banned.");
						}
					}
					
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
					
					try {
						UUID uuid = UUIDFetcher.getUUID(args[0]);
						File userFile = new File(Jagglessentials.UserDir, uuid + ".yml");
						YamlConfiguration userInfo = new YamlConfiguration();
						long now = Calendar.getInstance().getTimeInMillis();
						
						userInfo.load(userFile);
						
						int num = userInfo.getStringList("history.bans").size();
						
						userInfo.set("history.bans." + num + ".date", now);
						userInfo.set("history.bans." + num + ".reason", allArgs);
						userInfo.set("history.bans." + num + ".banner", sender.getName());
						userInfo.save(userFile);
						
						File logs = new File(plugin.getDataFolder(), "banAndKickHistory.yml");
						YamlConfiguration logyml = new YamlConfiguration();
						logyml.load(logs);
						
						DateFormat df = new SimpleDateFormat("dd-MM-yy");
						Date obj = new Date();
						logyml.set(df.format(obj) + "." + target.getName() + ".Type", "Ban");
						logyml.set(df.format(obj) + "." + target.getName() + ".Duration", "inf");
						logyml.set(df.format(obj) + "." + target.getName() + ".Banner", sender.getName());
						logyml.set(df.format(obj) + "." + target.getName() + ".Reason", allArgs);
						
						logyml.save(logs);
						
					} catch (IOException | InvalidConfigurationException e) {
						e.printStackTrace();
					}
					
					for (Player players : Bukkit.getOnlinePlayers()) {
						if(players.hasPermission("je.bannotify")) {
							JECommand.msg(players, "&c" + target.getName() + " &6was banned.");
							JECommand.msg(players, "&6Reason: &c" + allArgs);
						}
					}
					
				}
			} else {
				JECommand.noPerms(sender);
				
			}
		}
		return false;
	}
}
