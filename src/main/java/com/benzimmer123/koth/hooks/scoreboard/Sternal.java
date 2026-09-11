package com.benzimmer123.koth.hooks.scoreboard;

import org.bukkit.entity.Player;

import com.benzimmer123.koth.hooks.face.ScoreboardHook;
import com.xism4.sternalboard.Structure;

public class Sternal implements ScoreboardHook {

	public void removeScoreboard(Player p) {
		Structure.getInstance().getScoreboardManager().removeScoreboard(p);
	}

	public void giveScoreboard(Player p) {
		Structure.getInstance().getScoreboardManager().setScoreboard(p);
	}

	public String getAPIName() {
		return "SternalBoard";
	}

}