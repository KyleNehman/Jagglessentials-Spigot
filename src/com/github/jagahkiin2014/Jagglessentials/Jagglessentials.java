package com.github.jagahkiin2014.Jagglessentials;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.jagahkiin2014.Jagglessentials.Metrics.Metrics;
import com.github.jagahkiin2014.Jagglessentials.Utils.Log;

public class Jagglessentials extends JavaPlugin {
	public String version;
	public List<String> authors;
	
	public static File SettingsDir;
	public static File SettingsFile;
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
		
		if(!getDataFolder().exists()) {
			getDataFolder().mkdir();
		}
		SettingsDir = new File(getDataFolder() + "/Settings");
		SettingsFile = new File(SettingsDir, "settings.yml");
		if(!SettingsFile.exists()) {
			saveResource("Settings/settings.yml", false);
		}
		UserDir = new File(getDataFolder() + "/Users");
		
		try {
			Metrics metrics = new Metrics(this);
			metrics.start();
		} catch (IOException e) {
			
		}
	}
}
