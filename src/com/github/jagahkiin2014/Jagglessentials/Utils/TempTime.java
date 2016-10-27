package com.github.jagahkiin2014.Jagglessentials.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TempTime {
	public static Map<String, Long> bans = new HashMap<String, Long>();
	public static Map<String, Long> mute = new HashMap<String, Long>();
	
	// Add player to tempmute hashmap
	public static void setMuteTime(String name, String time) {
		mute.put(name, System.currentTimeMillis() + time(time));
	}
	
	// Add player to tempban hashmap
	public static void setBanTime(String name, String time) {
		bans.put(name, System.currentTimeMillis() + time(time));
	}
	
	/* For commands that require a time (e.g. tempban, tempmute, etc.)
	 * 1s = 1 second
	 * 1m = 1 minute
	 * 1h = 1 hour
	 * 1d1h = 1 day, 1 hour
	 * 88y = 88 years
	 */
	public static long time(String time) {
		if(time.equalsIgnoreCase(""));
		final char[] fms = {'s', 'm', 'h', 'd', 'w', 'y'};
		List<Character> fm = new ArrayList<Character>();
		
		for(Character c : fms) {
			fm.add(c);
		}
		
		long t = 0;
		for(int i = 0; i < time.length(); i++) {
			if(fm.contains(time.charAt(i))) {
				try {
					
					t = Integer.parseInt(time.substring(0, i));
					switch(time.substring(i, i + 1)) {
					case "s": t *= 1000; break;
					case "m": t *= 1000 * 60; break;
					case "h": t *= (1000 * 60) * 60; break;
					case "d": t *= ((1000 * 60) * 60) * 24; break;
					case "w": t *= (((1000 * 60) * 60) * 24) * 7; break;
					case "y": t *= ((((1000 * 60) * 60) * 24) * 7) * 52; break;
					}
					t += time(time.substring(i + 1));
					
				} catch (Exception e) {
					
				}
			}
		}
		
		return t;
	}
}
