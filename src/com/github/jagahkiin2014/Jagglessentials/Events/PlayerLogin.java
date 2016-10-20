package com.github.jagahkiin2014.Jagglessentials.Events;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
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
			YamlConfiguration userFile = new YamlConfiguration();
			Log.LogMessage("User file not found! Creating....", plugin.getServer().getConsoleSender());
			try {
				userFile.createSection("known-aliases");
				userFile.set("known-aliases", e.getPlayer().getName());
				
				userFile.createSection("positions");
				userFile.set("positions.login.world", e.getPlayer().getWorld().getName());
				userFile.set("positions.login.x", e.getPlayer().getLocation().getBlockX());
				userFile.set("positions.login.y", e.getPlayer().getLocation().getBlockY());
				userFile.set("positions.login.z", e.getPlayer().getLocation().getBlockZ());
				
				userFile.createSection("misc");
				userFile.set("misc.god", false);
				userFile.set("misc.fly", false);
				userFile.set("misc.gamemode", 0);
				
				userFile.save(newUser);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			Log.LogMessage("User file created for " + e.getPlayer().getUniqueId() + " (" + e.getPlayer().getName() + ")", plugin.getServer().getConsoleSender());
		} else {
			File newUser = new File(userDir + "/" + uuid + ".yml");
			YamlConfiguration userFile = new YamlConfiguration();
			if(!userFile.getStringList("known-aliases").contains(e.getPlayer().getName())) {
				try {
					userFile.load(newUser);
					List<String> names = userFile.getStringList("known-aliases");
					names.add(e.getPlayer().getName());
					userFile.set("known-aliases", names);
					userFile.save(newUser);
				} catch (IOException | InvalidConfigurationException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
