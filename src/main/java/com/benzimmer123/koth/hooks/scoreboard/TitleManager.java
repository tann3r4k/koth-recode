package com.benzimmer123.koth.hooks.scoreboard;

import io.puharesource.mc.titlemanager.api.v2.TitleManagerAPI;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.benzimmer123.koth.hooks.face.ScoreboardHook;

public class TitleManager implements ScoreboardHook {

	public void removeScoreboard(Player p) {
		if (!Bukkit.getPluginManager().getPlugin("TitleManager").getConfig().getBoolean("scoreboard.enabled"))
			return;

		TitleManagerAPI titleManager = (TitleManagerAPI) Bukkit.getPluginManager().getPlugin("TitleManager");
		titleManager.removeScoreboard(p);
	}

	public void giveScoreboard(Player p) {
		if (!Bukkit.getPluginManager().getPlugin("TitleManager").getConfig().getBoolean("scoreboard.enabled"))
			return;

		TitleManagerAPI titleManager = (TitleManagerAPI) Bukkit.getPluginManager().getPlugin("TitleManager");
		titleManager.giveDefaultScoreboard(p);
	}

	public String getAPIName() {
		return "TitleManager";
	}

}
