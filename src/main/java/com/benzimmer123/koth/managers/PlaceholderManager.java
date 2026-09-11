package com.benzimmer123.koth.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.benzimmer123.koth.placeholders.ClipPlaceholderAPI;
import com.benzimmer123.koth.placeholders.MvdwPlaceholderAPI;

public class PlaceholderManager {

	private boolean clipPlaceholderAPIManager;
	private boolean mvdwPlaceholderAPIManager;

	public void setupPlaceholderAPI() {
		Plugin clip = Bukkit.getServer().getPluginManager().getPlugin("PlaceholderAPI");
		if (clip != null) {
			clipPlaceholderAPIManager = true;
			new ClipPlaceholderAPI().register();
		}

		Plugin mvdw = Bukkit.getServer().getPluginManager().getPlugin("MVdWPlaceholderAPI");
		if (mvdw != null) {
			mvdwPlaceholderAPIManager = true;
			new MvdwPlaceholderAPI().register();
		}
	}

	public String parsePlaceholders(Player player, String line) {
		line = line.replace("&", "clr");

		if (isClipPlaceholderAPIHooked()) {
			line = me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(player, line);
		}

		if (isMVdWPlaceholderAPIHooked()) {
			line = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(player, line);
		}

		return line.replace("clr", "&");
	}

	public boolean isClipPlaceholderAPIHooked() {
		return clipPlaceholderAPIManager;
	}

	public boolean isMVdWPlaceholderAPIHooked() {
		return mvdwPlaceholderAPIManager;
	}

}
