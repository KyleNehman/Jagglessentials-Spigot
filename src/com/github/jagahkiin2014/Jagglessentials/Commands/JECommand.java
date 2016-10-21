package com.github.jagahkiin2014.Jagglessentials.Commands;

import org.bukkit.command.CommandSender;

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
	
}
