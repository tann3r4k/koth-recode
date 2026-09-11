package com.benzimmer123.koth.hooks.scoreboard;

import me.jasperjh.animatedscoreboard.AnimatedScoreboard;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.benzimmer123.koth.hooks.face.ScoreboardHook;

public class Animated implements ScoreboardHook {

	public void removeScoreboard(Player p) {
		AnimatedScoreboard scoreboardPlugin = (AnimatedScoreboard) JavaPlugin.getPlugin(AnimatedScoreboard.class);
		scoreboardPlugin.getScoreboardHandler().getPlayer(p.getUniqueId()).disableScoreboard();
	}

	public void giveScoreboard(Player p) {
		AnimatedScoreboard scoreboardPlugin = (AnimatedScoreboard) JavaPlugin.getPlugin(AnimatedScoreboard.class);
		if (p == null || scoreboardPlugin.getScoreboardHandler() == null
				|| scoreboardPlugin.getScoreboardHandler().getPlayer(p.getUniqueId()) == null)
			return;
		scoreboardPlugin.getScoreboardHandler().getPlayer(p.getUniqueId()).enableScoreboard();
	}

	public String getAPIName() {
		return "AnimatedScoreboard";
	}
	
}
