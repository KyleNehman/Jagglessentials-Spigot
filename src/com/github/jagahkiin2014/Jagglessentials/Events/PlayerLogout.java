package com.github.jagahkiin2014.Jagglessentials.Events;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

public class PlayerLogout implements Listener {
	
	Plugin plugin;
	public PlayerLogout(Plugin instance) {
		plugin = instance;
	}
	
	@EventHandler
	public void onLogout(PlayerQuitEvent e) {
		UUID uuid = e.getPlayer().getUniqueId();
		File userDir = new File(plugin.getDataFolder() + "/Users");
		
		File newUser = new File(userDir + "/" + uuid + ".yml");
		YamlConfiguration userFile = new YamlConfiguration();
		
		long now = Calendar.getInstance().getTimeInMillis();
		
		// Log logout time in milliseconds and coords
		try {
			userFile.load(newUser);
			
			userFile.set("last-seen.time", now);
			
			userFile.set("positions.logout.world", e.getPlayer().getWorld().getName());
			userFile.set("positions.logout.x", e.getPlayer().getLocation().getBlockX());
			userFile.set("positions.logout.y", e.getPlayer().getLocation().getBlockY());
			userFile.set("positions.logout.z", e.getPlayer().getLocation().getBlockZ());
			
			userFile.save(newUser);
		} catch (IOException | InvalidConfigurationException e1) {
			e1.printStackTrace();
		}
		
	}
}
