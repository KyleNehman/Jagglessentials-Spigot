package com.github.jagahkiin2014.Jagglessentials.Commands;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class BanIP implements CommandExecutor {
	
	Plugin plugin;
	public BanIP(Plugin instance) {
		plugin = instance;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		if(cmdLabel.equalsIgnoreCase("banip")) {
			if(sender.hasPermission("je.banip")) {
				if(args.length == 0) {
					JECommand.tooFewArgs(sender);
				} else if(args.length == 1) {
					String ipRegex = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
					if(args[0].matches(ipRegex)) {
						Bukkit.getBanList(Type.IP).addBan(args[0], "No reason specified.", null, sender.getName());
						
						for(Player players : Bukkit.getOnlinePlayers()) {
							if(players.hasPermission("je.banipnotify")) {
								JECommand.msg(players, "&c" + args[0] + "&6 was banned.");
							}
							
							String ip = players.getAddress().getAddress().getHostAddress().replace("/", "");
							if(ip == args[0]) {
								players.kickPlayer("Your IP address was banned.");
							}
						}
						
						try {
							File logs = new File(plugin.getDataFolder(), "banAndKickHistory.yml");
							YamlConfiguration logyml = new YamlConfiguration();
							logyml.load(logs);
							DateFormat df = new SimpleDateFormat("dd-MM-yy");
							Date obj = new Date();
							int num1 = logyml.getStringList(df.format(obj) + "." + args[0]).size();
							
							logyml.set(df.format(obj) + "." + args[0] + "." + num1 + ".Type", "Ban");
							logyml.set(df.format(obj) + "." + args[0] + "." + num1 + ".Duration", "inf");
							logyml.set(df.format(obj) + "." + args[0] + "." + num1 + ".Banner", sender.getName());
							logyml.set(df.format(obj) + "." + args[0] + "." + num1 + ".Reason", "No reason specified.");
							
							logyml.save(logs);
						} catch(IOException | InvalidConfigurationException e) {
							e.printStackTrace();
						}
					} else {
						JECommand.msg(sender, "&cInvalid IP address format.");
					}
					
				} else if(args.length > 1) {
					String ipRegex = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
					if(args[0].matches(ipRegex)) {
						StringBuilder sb = new StringBuilder();
						for(int i = 1; i < args.length; i++) {
							sb.append(args[i]).append(" ");
						}
						String allArgs = sb.toString().trim();
						
						Bukkit.getBanList(Type.IP).addBan(args[0], allArgs, null, sender.getName());
						
						for(Player players : Bukkit.getOnlinePlayers()) {
							if(players.hasPermission("je.banipnotify")) {
								JECommand.msg(players, "&c" + args[0] + "&6 was banned.");
								JECommand.msg(players, "&6Reason: &c" + allArgs);
							}
							
							String ip = players.getAddress().getAddress().getHostAddress().replace("/", "");
							if(ip == args[0]) {
								players.kickPlayer("Your IP address was banned. Reason: " + allArgs);
							}
						}
						
						try {
							File logs = new File(plugin.getDataFolder(), "banAndKickHistory.yml");
							YamlConfiguration logyml = new YamlConfiguration();
							logyml.load(logs);
							DateFormat df = new SimpleDateFormat("dd-MM-yy");
							Date obj = new Date();
							int num1 = logyml.getStringList(df.format(obj) + "." + args[0]).size();
							
							logyml.set(df.format(obj) + "." + args[0] + "." + num1 + ".Type", "Ban");
							logyml.set(df.format(obj) + "." + args[0] + "." + num1 + ".Duration", "inf");
							logyml.set(df.format(obj) + "." + args[0] + "." + num1 + ".Banner", sender.getName());
							logyml.set(df.format(obj) + "." + args[0] + "." + num1 + ".Reason", allArgs);
							
							logyml.save(logs);
						} catch(IOException | InvalidConfigurationException e) {
							e.printStackTrace();
						}
						
					} else {
						JECommand.msg(sender, "&cInvalid IP address format.");
					}
					
				}
			}
		}
		
		return false;
	}
}
