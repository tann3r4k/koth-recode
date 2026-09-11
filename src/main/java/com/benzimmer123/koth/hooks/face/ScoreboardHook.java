package com.benzimmer123.koth.hooks.face;

import org.bukkit.entity.Player;

public interface ScoreboardHook {

	void giveScoreboard(Player p);
	void removeScoreboard(Player p);
	String getAPIName();

}
