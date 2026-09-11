package com.benzimmer123.koth.hooks.scoreboard;

import org.bukkit.entity.Player;

import com.benzimmer123.koth.hooks.face.ScoreboardHook;
import com.meteorite.scoreboard.MeteoriteScoreboard;

public class Meteorite implements ScoreboardHook {

	public void removeScoreboard(Player p) {
		MeteoriteScoreboard.getInstance().getScoreboardManager().removePlayerScoreboard(p);
	}

	public void giveScoreboard(Player p) {
		MeteoriteScoreboard.getInstance().getScoreboardManager().removePlayerScoreboard(p);
		MeteoriteScoreboard.getInstance().getScoreboardManager().setPlayerScoreboard(p);
	}

	public String getAPIName() {
		return "MeteoriteScoreboard";
	}

}