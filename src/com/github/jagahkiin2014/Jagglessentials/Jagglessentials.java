package com.github.jagahkiin2014.Jagglessentials;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.jagahkiin2014.Jagglessentials.Commands.Announce;
import com.github.jagahkiin2014.Jagglessentials.Commands.Ban;
import com.github.jagahkiin2014.Jagglessentials.Commands.BanIP;
import com.github.jagahkiin2014.Jagglessentials.Commands.Clear;
import com.github.jagahkiin2014.Jagglessentials.Commands.ClearBanKickHistory;
import com.github.jagahkiin2014.Jagglessentials.Commands.Feed;
import com.github.jagahkiin2014.Jagglessentials.Commands.Fly;
import com.github.jagahkiin2014.Jagglessentials.Commands.Gamemode;
import com.github.jagahkiin2014.Jagglessentials.Commands.Give;
import com.github.jagahkiin2014.Jagglessentials.Commands.God;
import com.github.jagahkiin2014.Jagglessentials.Commands.Heal;
import com.github.jagahkiin2014.Jagglessentials.Commands.Kick;
import com.github.jagahkiin2014.Jagglessentials.Commands.MOTD;
import com.github.jagahkiin2014.Jagglessentials.Commands.Mail;
import com.github.jagahkiin2014.Jagglessentials.Commands.Mute;
import com.github.jagahkiin2014.Jagglessentials.Commands.OnlinePlayers;
import com.github.jagahkiin2014.Jagglessentials.Commands.Seen;
import com.github.jagahkiin2014.Jagglessentials.Commands.Sethome;
import com.github.jagahkiin2014.Jagglessentials.Commands.Setspawn;
import com.github.jagahkiin2014.Jagglessentials.Commands.Spawn;
import com.github.jagahkiin2014.Jagglessentials.Commands.Speed;
import com.github.jagahkiin2014.Jagglessentials.Commands.Tempban;
import com.github.jagahkiin2014.Jagglessentials.Commands.Time;
import com.github.jagahkiin2014.Jagglessentials.Commands.Weather;
import com.github.jagahkiin2014.Jagglessentials.Events.PlayerLogin;
import com.github.jagahkiin2014.Jagglessentials.Events.PlayerLogout;
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
		startup();
	}
	
	private void startup() {
		PluginDescriptionFile pdFile = this.getDescription();
		version = pdFile.getVersion();
		authors = pdFile.getAuthors();
		
		Log.LogMessage("[JE] Starting Jagglessentials v" + version + "...", getServer().getConsoleSender());
		
		createFiles();
		registerEvents();
		registerCommands();
		enableMetrics();
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
		
		File logs = new File(getDataFolder(), "banAndKickHistory.yml");
		YamlConfiguration bklogs = new YamlConfiguration();
		if(!logs.exists()) {
			try {
				bklogs.save(logs);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void registerEvents() {
		PluginManager pm = Bukkit.getServer().getPluginManager();
		
		PlayerLogin pLogin = new PlayerLogin(this);
		PlayerLogout pLogout = new PlayerLogout(this);
		
		pm.registerEvents(pLogin, this);
		pm.registerEvents(pLogout, this);
	}
	
	private void registerCommands() {
		Announce announce = new Announce(this);
		Ban ban = new Ban(this);
		BanIP banip = new BanIP(this);
		Clear clear = new Clear(this);
		ClearBanKickHistory cbkh = new ClearBanKickHistory(this);
		Feed feed = new Feed(this);
		Fly fly = new Fly(this);
		Gamemode gm = new Gamemode(this);
		Give give = new Give(this);
		God god = new God(this);
		Heal heal = new Heal(this);
		Kick kick = new Kick(this);
		Mail mail = new Mail(this);
		MOTD motd = new MOTD(this);
		Mute mute = new Mute(this);
		OnlinePlayers onplayers = new OnlinePlayers(this);
		Seen seen = new Seen(this);
		Sethome sethome = new Sethome(this);
		Setspawn setspawn = new Setspawn(this);
		Spawn spawn = new Spawn(this);
		Speed speed = new Speed(this);
		Tempban tban = new Tempban(this);
		Time time = new Time(this);
		Weather weather = new Weather(this);
		
		getCommand("announce").setExecutor(announce);
		getCommand("ban").setExecutor(ban);
		getCommand("banip").setExecutor(banip);
		getCommand("clear").setExecutor(clear);
		getCommand("clearhistory").setExecutor(cbkh);
		getCommand("feed").setExecutor(feed);
		getCommand("fly").setExecutor(fly);
		getCommand("gamemode").setExecutor(gm);
		getCommand("give").setExecutor(give);
		getCommand("god").setExecutor(god);
		getCommand("heal").setExecutor(heal);
		getCommand("kick").setExecutor(kick);
		getCommand("mail").setExecutor(mail);
		getCommand("motd").setExecutor(motd);
		getCommand("mute").setExecutor(mute);
		getCommand("onlineplayers").setExecutor(onplayers);
		getCommand("seen").setExecutor(seen);
		getCommand("sethome").setExecutor(sethome);
		getCommand("setspawn").setExecutor(setspawn);
		getCommand("spawn").setExecutor(spawn);
		getCommand("speed").setExecutor(speed);
		getCommand("tempban").setExecutor(tban);
		getCommand("time").setExecutor(time);
		getCommand("weather").setExecutor(weather);
	}
	
	private void enableMetrics() {
		try {
			MetricsLite metrics = new MetricsLite(this);
			metrics.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
