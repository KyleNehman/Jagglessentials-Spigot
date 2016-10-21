package com.github.jagahkiin2014.Jagglessentials.Events;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import com.github.jagahkiin2014.Jagglessentials.Jagglessentials;
import com.github.jagahkiin2014.Jagglessentials.Utils.Log;

public class PlayerLogin implements Listener {
	
	Plugin plugin;
	public PlayerLogin(Plugin instance) {
		plugin = instance;
	}
	
	@EventHandler
	public void onLogin(PlayerJoinEvent e) {
		
		UUID uuid = e.getPlayer().getUniqueId();
		File newUser = new File(Jagglessentials.UserDir + File.separator, uuid + ".yml");
		YamlConfiguration userFile = new YamlConfiguration();
		
		if(!newUser.exists()) {

			Log.LogMessage("User file not found! Creating....", plugin.getServer().getConsoleSender());
			try {
				userFile.createSection("known-aliases");
				userFile.set("known-aliases", e.getPlayer().getName());
				
				userFile.createSection("last-seen");
				userFile.set("last-seen.ip", e.getPlayer().getAddress().getAddress().getHostAddress().toString().replaceAll("/", ""));
				
				userFile.createSection("positions");
				userFile.set("positions.login.world", e.getPlayer().getWorld().getName());
				userFile.set("positions.login.x", e.getPlayer().getLocation().getBlockX());
				userFile.set("positions.login.y", e.getPlayer().getLocation().getBlockY());
				userFile.set("positions.login.z", e.getPlayer().getLocation().getBlockZ());
				
				userFile.createSection("history");
				userFile.set("history.kicks", "");
				userFile.set("history.tempbans", "");
				userFile.set("history.bans", "");
				userFile.set("history.unbans", "");
				
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
			try {
				userFile.load(newUser);
				if(userFile.getStringList("known-aliases").isEmpty() || !userFile.getStringList("known-aliases").contains(e.getPlayer().getName())) {
					List<String> names = userFile.getStringList("known-aliases");
					names.add(e.getPlayer().getName());
					userFile.set("known-aliases", names);
					userFile.save(newUser);
				}
			} catch (IOException | InvalidConfigurationException e1) {
				e1.printStackTrace();
			}
			
			userFile.set("last-seen.ip", e.getPlayer().getAddress().getAddress().getHostAddress().toString().replaceAll("/", ""));
		}
	}
}
