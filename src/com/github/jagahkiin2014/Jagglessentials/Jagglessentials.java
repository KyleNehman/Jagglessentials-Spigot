package com.github.jagahkiin2014.Jagglessentials;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.jagahkiin2014.Jagglessentials.Events.PlayerLogin;
import com.github.jagahkiin2014.Jagglessentials.Metrics.MetricsLite;
import com.github.jagahkiin2014.Jagglessentials.Utils.Log;

public class Jagglessentials extends JavaPlugin {
	public String version;
	public List<String> authors;
	
	public static File UserDir;
	
	@Override
	public void onDisable() {
		
	}
	
	@Override
	public void onEnable() {
		PluginDescriptionFile pdFile = this.getDescription();
		version = pdFile.getVersion();
		authors = pdFile.getAuthors();
		
		Log.LogMessage("[JE] Starting Jagglessentials v" + version + "...", getServer().getConsoleSender());
		
		createFiles();
		registerEvents();
		registerCommands();
		
		try {
			MetricsLite metrics = new MetricsLite(this);
			metrics.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void createFiles() {
		if(!getDataFolder().exists()) {
			Log.LogMessage("[JE] Data folder not found! Creating....", getServer().getConsoleSender());
			getDataFolder().mkdir();
			Log.LogMessage("[JE] Data folder created.", getServer().getConsoleSender());
		}
		
		File config = new File(getDataFolder(), "config.yml");
		if(!config.exists()) {
			Log.LogMessage("[JE] Configuration file not found! Generating....", getServer().getConsoleSender());
			saveDefaultConfig();
			Log.LogMessage("[JE] Configuration file generated.", getServer().getConsoleSender());
		}
		
		UserDir = new File(getDataFolder() + "/Users");
		if(!UserDir.exists()) {
			Log.LogMessage("[JE] User directory not found! Creating....", getServer().getConsoleSender());
			UserDir.mkdir();
			Log.LogMessage("[JE] User directory created.", getServer().getConsoleSender());
		}
	}
	
	public void registerEvents() {
		PluginManager pm = Bukkit.getServer().getPluginManager();
		PlayerLogin pLogin = new PlayerLogin(this);
		pm.registerEvents(pLogin, this);
	}
	
	public void registerCommands() {
		
	}
}
