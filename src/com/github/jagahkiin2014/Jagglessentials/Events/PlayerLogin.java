package com.github.jagahkiin2014.Jagglessentials.Events;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.Plugin;

import com.github.jagahkiin2014.Jagglessentials.Utils.Log;

public class PlayerLogin implements Listener {
	Plugin plugin;
	
	public PlayerLogin(Plugin instance) {
		plugin = instance;
	}
	
	@EventHandler
	public void onLogin(PlayerLoginEvent e) {
		UUID uuid = e.getPlayer().getUniqueId();
		File userDir = new File(plugin.getDataFolder() + "/Users");
		File[] files = userDir.listFiles();
		
		if(!files.equals(uuid + ".yml")) {
			File newUser = new File(userDir + "/" + uuid + ".yml");
			Log.LogMessage("User file not found! Creating....", plugin.getServer().getConsoleSender());
			try {
				newUser.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			Log.LogMessage("User file created for " + e.getPlayer().getUniqueId() + " (" + e.getPlayer().getName() + ")", plugin.getServer().getConsoleSender());
		}
	}
}
