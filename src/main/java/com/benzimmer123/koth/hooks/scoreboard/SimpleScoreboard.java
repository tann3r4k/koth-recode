package com.benzimmer123.koth.hooks.scoreboard;

import org.bukkit.entity.Player;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.hooks.face.ScoreboardHook;
import com.r4g3baby.simplescore.SimpleScore;

public class SimpleScoreboard implements ScoreboardHook {

	public void removeScoreboard(Player p) {
		if (SimpleScore.Api.getManager().getPlayersData().get(p) != null)
			SimpleScore.Api.getManager().getPlayersData().get(p).disable(KOTH.getInstance());
	}

	public void giveScoreboard(Player p) {
		if (SimpleScore.Api.getManager().getPlayersData().get(p) != null)
			SimpleScore.Api.getManager().getPlayersData().get(p).enable(KOTH.getInstance());
	}

	public String getAPIName() {
		return "SimpleScore";
	}
}
