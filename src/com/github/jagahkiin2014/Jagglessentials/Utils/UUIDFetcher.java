package com.github.jagahkiin2014.Jagglessentials.Utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.plugin.Plugin;

public class UUIDFetcher {
	
	static Plugin plugin;
	public static HashMap<String, UUID> uuid = new HashMap<String, UUID>();
	
	public static UUID getUUID(String playerName) {
		if(uuid.containsKey(playerName)) {
			
		}
		return null;
	}
	
	public static void saveUUIDHashmap() {
		File file = new File(plugin.getDataFolder(), "");
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			for(String p:uuid.keySet()) {
				bw.write(p + "," + uuid.get(p));
				bw.newLine();
			}
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void loadUUIDHashmap() {
		File file = new File(plugin.getDataFolder(), "");
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String l;
			while((l = br.readLine()) != null) {
				String[] args = l.split("[,]", 2);
				if(args.length != 2) continue;
				String p = args[0].replaceAll(" ", "");
				UUID u = UUID.fromString(args[1].replaceAll(" ", ""));
				uuid.put(p, u);
			}
			br.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}