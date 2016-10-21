package com.github.jagahkiin2014.Jagglessentials.Commands;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import com.github.jagahkiin2014.Jagglessentials.Jagglessentials;
import com.github.jagahkiin2014.Jagglessentials.Utils.Log;
import com.github.jagahkiin2014.Jagglessentials.Utils.UUIDFetcher;

public class Seen implements CommandExecutor {
	
	Plugin plugin;
	public Seen(Plugin instance) {
		plugin = instance;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		
		if(cmdLabel.equalsIgnoreCase("seen")) {
			if(args.length < 1) {
				JECommand.tooFewArgs(sender);
				
			} else if(args.length > 1) {
				JECommand.tooManyArgs(sender);
				
			} else if(args.length == 1) {
				if(sender.hasPermission("je.seen.staff")) {
					seenStaff(sender, args[0]);
				} else if(sender.hasPermission("je.seen.normal")) {
					seenNorm(sender, args[0]);
				} else {
					JECommand.noPerms(sender);
				}
			}
		}
		return false;
	}
	
	private void seenTop(CommandSender sender) {
		sender.sendMessage(Log.ColorMessage("&6+----------&bUser&6---&bInfo&6----------+"));
	}
	
	private void seenNorm(CommandSender sender, String args) {
		Player target = Bukkit.getPlayer(args);
		if(target != null) {
			seenTop(sender);
			
			JECommand.msg(sender, "&6Status: &aOnline");
			JECommand.msg(sender, "&6UUID:&e " + target.getUniqueId());
			
			seenBot(sender);
			
		} else {

			seenTop(sender);
		
			JECommand.msg(sender, "&6Status: &cOffline");
			JECommand.msg(sender, "&6UUID:&e " + UUIDFetcher.getUUID(args));
			getTimeSince(sender, args);
			
			seenBot(sender);
		}
	}
	
	private void seenStaff(CommandSender sender, String args) {
		Player target = Bukkit.getPlayer(args);
		if(target != null) {
			try {
				
				File userFile = new File(Jagglessentials.UserDir + File.separator, target.getUniqueId() + ".yml");
				YamlConfiguration userInfo = new YamlConfiguration();
				userInfo.load(userFile);
				
				seenTop(sender);
				
				JECommand.msg(sender, "&6Status: &aOnline");
				JECommand.msg(sender, "&6UUID:&e " + target.getUniqueId());
				getAliases(sender, args);
				JECommand.msg(sender, "&6IP Address:&e " + target.getAddress().getAddress().getHostAddress().toString().replace("/", ""));
				JECommand.msg(sender, "");
				JECommand.msg(sender, "&6+------------&bHistory&6------------+");
				JECommand.msg(sender, "&6Kicks:&e " + userInfo.getStringList("history.kicks").size());
				JECommand.msg(sender, "&6Tempbans:&e " + userInfo.getStringList("history.tempbans").size());
				JECommand.msg(sender, "&6Bans:&e " + userInfo.getStringList("history.bans").size());
				JECommand.msg(sender, "&6Unbans:&e " + userInfo.getStringList("history.unbans").size());
				JECommand.msg(sender, "&8Use '&7/history <player>&8' for a detailed synopsis.");
				
				seenBot(sender);
			} catch (IOException | InvalidConfigurationException e) {
				e.printStackTrace();
			}
		} else {
			try {
				
				File userFile = new File(Jagglessentials.UserDir + File.separator, UUIDFetcher.getUUID(args) + ".yml");
				YamlConfiguration userInfo = new YamlConfiguration();
				userInfo.load(userFile);
				
				seenTop(sender);
				
				JECommand.msg(sender, "&6Status: &cOffline");
				JECommand.msg(sender, "&6UUID:&e " + UUIDFetcher.getUUID(args));
				getAliases(sender, args);
				getTimeSince(sender, args);
				if(sender.hasPermission("je.seen.ip")) {
					JECommand.msg(sender, "&6Last Known IP:&e " + userInfo.get("last-seen.ip"));
				}
				
				seenBot(sender);
			} catch (IOException | InvalidConfigurationException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void seenBot(CommandSender sender) {
		PluginDescriptionFile pdFile = plugin.getDescription();
		String ver = pdFile.getVersion();
		sender.sendMessage(Log.ColorMessage("&6+-------&b[JE]&6-&bv" + ver + "&6-------+"));
	}
	
	private void getAliases(CommandSender sender, String target) {
		File f = new File(Jagglessentials.UserDir + File.separator, UUIDFetcher.getUUID(target) + ".yml");
		YamlConfiguration userFile = new YamlConfiguration();
		
		try {
			userFile.load(f);
			
			String aliases = userFile.getStringList("known-aliases").toString().replace("[", "").replace("]", "");
			JECommand.msg(sender, "&6Known Aliases: &e" + aliases);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	private void getTimeSince(CommandSender sender, String target) {
		File userFile = new File(Jagglessentials.UserDir + File.separator, UUIDFetcher.getUUID(target) + ".yml");
		YamlConfiguration userInfo = new YamlConfiguration();

		try {
			userInfo.load(userFile);
			
			DateTime then = new DateTime(userInfo.getLong("last-seen.time"));
			DateTime now = new DateTime();
			Period period = new Period(then, now);
			
			PeriodFormatter formatter = new PeriodFormatterBuilder()
					.appendYears().appendSuffix(" year, ", " years, ")
					.appendMonths().appendSuffix(" month, ", " months, ")
					.appendWeeks().appendSuffix(" week, ", " weeks, ")
					.appendDays().appendSuffix(" day, ", " days, ")
					.appendHours().appendSuffix(" hour, ", " hours, ")
					.appendMinutes().appendSuffix(" minute, ", " minutes ")
					.appendSeconds().appendSuffix(" second", " seconds").printZeroNever().toFormatter();
			
			String time = formatter.print(period);
			JECommand.msg(sender, "&6Last online:&a " + time + " ago.");
			
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}
}
