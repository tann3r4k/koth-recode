package com.benzimmer123.koth.managers;

import java.util.Map;

import org.bukkit.Bukkit;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.hooks.face.OtherHook;
import com.benzimmer123.koth.hooks.other.BossBar;
import com.benzimmer123.koth.hooks.other.CombatLog;
import com.benzimmer123.koth.hooks.other.PremiumVanish;
import com.benzimmer123.koth.util.LoggerUtil;
import com.benzimmer123.koth.util.ServerVersionUtil;
import com.google.common.collect.Maps;

public class APIManager {

	private Map<String, OtherHook> additionalHooks;

	public APIManager() {
		additionalHooks = Maps.newHashMap();
	}

	public void setupAllSupportedPlugins() {
		if (Bukkit.getPluginManager().isPluginEnabled("SuperVanish") || Bukkit.getPluginManager().isPluginEnabled("PremiumVanish")) {
			additionalHooks.put("PremiumVanish", new PremiumVanish());
		}

		if (KOTH.getInstance().getConfig().getBoolean("BOSS_BAR.ENABLED") && ServerVersionUtil.isAboveVersion(ServerVersionUtil.v1_9_R1)) {
			additionalHooks.put("BossBar", new BossBar());
		}

		if (Bukkit.getPluginManager().isPluginEnabled("CombatLogX")) {
			additionalHooks.put("CombatLogX", new CombatLog());
			Bukkit.getServer().getPluginManager().registerEvents(new CombatLog(), KOTH.getInstance());
		}

		additionalHooks.values().stream().forEach(plugin -> {
			plugin.setup();
			LoggerUtil.success("[KOTH] Successfully hooked into " + plugin.getAPIName() + ".");
		});
	}
	
	public boolean isHooked(String name) {
		return additionalHooks.containsKey(name);
	}

}
