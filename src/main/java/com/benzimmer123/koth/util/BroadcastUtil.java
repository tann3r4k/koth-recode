package com.benzimmer123.koth.util;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.benzimmer123.koth.KOTH;

public class BroadcastUtil {
	
	private BroadcastUtil() {
	}
	
	public static void sendMessage(String message) {
		List<String> disabledWorlds = KOTH.getInstance().getConfig().getStringList("DISABLE_BROADCASTS_IN_WORLDS");
		List<String> whitelistedWorlds = KOTH.getInstance().getConfig().getStringList("WHITELISTED_BROADCASTS_WORLDS.WORLD_NAMES");
		boolean isWhitelistEnabled = KOTH.getInstance().getConfig().getBoolean("WHITELISTED_BROADCASTS_WORLDS.ENABLED");
		
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (KOTH.getInstance().getConfig().getBoolean("BROADCAST_PERMISSION.ENABLED") && !player.hasPermission("KOTH.MESSAGES"))
				continue;

			if (isWhitelistEnabled) {
				if(whitelistedWorlds != null && whitelistedWorlds.contains(player.getWorld().getName())) {
					LangUtil.sendMessage(player, message);
				}
				continue;
			}

			if (disabledWorlds != null && !disabledWorlds.contains(player.getWorld().getName())) {
				LangUtil.sendMessage(player, message);
			}
		}
	}
}
