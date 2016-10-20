package com.github.jagahkiin2014.Jagglessentials.Commands;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.UUID;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import com.github.jagahkiin2014.Jagglessentials.Utils.Log;

public class JECommand {
	
	public static final void tooManyArgs(CommandSender sender) {
		sender.sendMessage(Log.ColorMessage("&cError: Too many arguments."));
	}
	
	public static final void tooFewArgs(CommandSender sender) {
		sender.sendMessage(Log.ColorMessage("&cError: Not enough arguments."));
	}
	
	public static final void noPerms(CommandSender sender) {
		sender.sendMessage(Log.ColorMessage("&cError: No permission to use that command."));
	}
	
	public static void msg(CommandSender sender, String msg) {
		sender.sendMessage(Log.ColorMessage(msg));
	}
	
	public static String getAliases(File user, YamlConfiguration userFile, UUID uuid) {
		
		try {
			userFile.load(user);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		
		Set<String> aliasList = userFile.getConfigurationSection("known-aliases").getKeys(false);
		StringBuilder sb = new StringBuilder();
		for(String alias : aliasList) {
			sb.append(alias);
			sb.append(", ");
		}
		String aliases = sb.toString().substring(0, sb.toString().length() - 2);
		return aliases;
	}
}
