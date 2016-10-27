package com.github.jagahkiin2014.Jagglessentials.Events;

import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class PlayerChat implements Listener {
	
	Plugin plugin;
	public PlayerChat(Plugin instance) {
		plugin = instance;
	}
	
	@EventHandler
	public void onChat(PlayerChatEvent e) {
		
		Player player = e.getPlayer();
		String playerName = player.getName();
		
		if (Jagglessentials.muted.contains(playerName) {
			e.setCancelled(true);
			player.sendMessage("You are muted!");
		}	
	}
}
