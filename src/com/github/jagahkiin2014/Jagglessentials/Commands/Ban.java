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
					if(target != null) {
						UUID uuid = target.getUniqueId();
						Bukkit.getBanList(Type.NAME).addBan(target.getName(), "No reason specified.", null, sender.getName());
						target.kickPlayer("You were banned from this server.");
					
						try {
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
							
							DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
							Date obj = new Date();
							int num1 = logyml.getStringList(df.format(obj) + "." + target.getName()).size();
							
							logyml.set(df.format(obj) + "." + target.getName() + "." + num1 + ".Type", "Ban");
							logyml.set(df.format(obj) + "." + target.getName() + "." + num1 + ".Duration", "inf");
							logyml.set(df.format(obj) + "." + target.getName() + "." + num1 + ".Banner", sender.getName());
							logyml.set(df.format(obj) + "." + target.getName() + "." + num1 + ".Reason", "No reason specified.");
							
							logyml.save(logs);
							
						} catch (IOException | InvalidConfigurationException e) {
							e.printStackTrace();
						} 
					} else if(target == null) {
						
						
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
					
					if(target != null) {
						UUID uuid = target.getUniqueId();
						Bukkit.getBanList(Type.NAME).addBan(target.getName(), allArgs, null, sender.getName());
						target.kickPlayer("You were banned from this server.");
					
						try {
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
							
							DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
							Date obj = new Date();
							int num1 = logyml.getStringList(df.format(obj) + "." + target.getName()).size();
							
							logyml.set(df.format(obj) + "." + target.getName() + "." + num1 + ".Type", "Ban");
							logyml.set(df.format(obj) + "." + target.getName() + "." + num1 + ".Duration", "inf");
							logyml.set(df.format(obj) + "." + target.getName() + "." + num1 + ".Banner", sender.getName());
							logyml.set(df.format(obj) + "." + target.getName() + "." + num1 + ".Reason", allArgs);
							
							logyml.save(logs);
							
						} catch (IOException | InvalidConfigurationException e) {
							e.printStackTrace();
						}
					} else if(target == null) {
						
						
					}
					
					// Ban notifications if player has permissions
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
