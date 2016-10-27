package com.github.jagahkiin2014.Jagglessentials.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

import com.github.jagahkiin2014.Jagglessentials.Jagglessentials;

public class PlayerChat implements Listener {
	
	Plugin plugin;
	public PlayerChat(Plugin instance) {
		plugin = instance;
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		
		Player player = e.getPlayer();
		String playerName = player.getName();
		
		if (Jagglessentials.muted.contains(playerName)) {
			e.setCancelled(true);
			player.sendMessage("You are muted!");
		}	
	}
}
