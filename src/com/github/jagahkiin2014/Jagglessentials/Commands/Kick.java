package com.github.jagahkiin2014.Jagglessentials.Commands;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

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

public class Kick implements CommandExecutor {
	
	Plugin plugin;
	public Kick(Plugin instance) {
		plugin = instance;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		if(cmdLabel.equalsIgnoreCase("kick")) {
			if(args.length == 0) {
				JECommand.tooFewArgs(sender);
			} else if(args.length == 1) {
				Player target = Bukkit.getPlayer(args[0]);
				if(target != null) {
					target.kickPlayer("You were kicked from the server.");
					
					try {
						UUID uuid = target.getUniqueId();
						File userFile = new File(Jagglessentials.UserDir, uuid + ".yml");
						YamlConfiguration userInfo = new YamlConfiguration();
						long now = Calendar.getInstance().getTimeInMillis();
						
						userInfo.load(userFile);
						
						int num = userInfo.getStringList("history.bans").size();
						
						userInfo.set("history.kicks." + num + ".date", now);
						userInfo.set("history.kicks." + num + ".reason", "No reason specified.");
						userInfo.set("history.kicks." + num + ".kicker", sender.getName());
						userInfo.save(userFile);
						
						File logs = new File(plugin.getDataFolder(), "banAndKickHistory.yml");
						YamlConfiguration logyml = new YamlConfiguration();
		
						logyml.load(logs);
						
						DateFormat df = new SimpleDateFormat("dd-MM-yy");
						Date obj = new Date();
						int num1 = logyml.getStringList(df.format(obj) + "." + target.getName()).size();
						
						logyml.set(df.format(obj) + "." + target.getName() + "." + num1 + ".Type", "Kick");
						logyml.set(df.format(obj) + "." + target.getName() + "." + num1 + ".Kicker", sender.getName());
						logyml.set(df.format(obj) + "." + target.getName() + "." + num1 + ".Reason", "No reason specified.");
						
						logyml.save(logs);
						
					} catch (IOException | InvalidConfigurationException e) {
						e.printStackTrace();
					}
					
					for(Player players : Bukkit.getOnlinePlayers()) {
						if(players.hasPermission("je.kicknotify")) {
							JECommand.msg(sender, "&c" + target.getName() + " &6was kicked.");
						}
					}
					
				} else {
					JECommand.noPlayer(sender);
				}
				
			} else if(args.length > 1) {
				Player target = Bukkit.getPlayer(args[0]);
				StringBuilder sb = new StringBuilder();
				for(int i = 1; i < args.length; i++) {
					sb.append(args[i]).append(" ");
				}
				String allArgs = sb.toString().trim();
				
				if(target != null) {
					target.kickPlayer(allArgs);
					
					try {
						UUID uuid = target.getUniqueId();
						File userFile = new File(Jagglessentials.UserDir, uuid + ".yml");
						YamlConfiguration userInfo = new YamlConfiguration();
						long now = Calendar.getInstance().getTimeInMillis();
						
						userInfo.load(userFile);
						
						int num = userInfo.getStringList("history.bans").size();
						
						userInfo.set("history.kicks." + num + ".date", now);
						userInfo.set("history.kicks." + num + ".reason", allArgs);
						userInfo.set("history.kicks." + num + ".kicker", sender.getName());
						userInfo.save(userFile);
						
						File logs = new File(plugin.getDataFolder(), "banAndKickHistory.yml");
						YamlConfiguration logyml = new YamlConfiguration();
		
						logyml.load(logs);
						
						DateFormat df = new SimpleDateFormat("dd-MM-yy");
						Date obj = new Date();
						int num1 = logyml.getStringList(df.format(obj) + "." + target.getName()).size();
						
						logyml.set(df.format(obj) + "." + target.getName() + "." + num1 + ".Type", "Kick");
						logyml.set(df.format(obj) + "." + target.getName() + "." + num1 + ".Kicker", sender.getName());
						logyml.set(df.format(obj) + "." + target.getName() + "." + num1 + ".Reason", allArgs);
						
						logyml.save(logs);
						
					} catch (IOException | InvalidConfigurationException e) {
						e.printStackTrace();
					}
					
					for(Player players : Bukkit.getOnlinePlayers()) {
						if(players.hasPermission("je.kicknotify")) {
							JECommand.msg(sender, "&c" + target.getName() + " &6was kicked. Reason: &c" + allArgs);
						}
					}
					
				} else {
					JECommand.noPlayer(sender);
				}
			}
		}
		
		return false;
	}
}
