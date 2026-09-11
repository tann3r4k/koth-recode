package com.benzimmer123.koth.hooks.scoreboard;

import me.tade.quickboard.api.QuickBoardAPI;

import org.bukkit.entity.Player;

import com.benzimmer123.koth.hooks.face.ScoreboardHook;

public class QuickBoard implements ScoreboardHook {

	public void removeScoreboard(Player p) {
		QuickBoardAPI.removeBoard(p);
	}

	public void giveScoreboard(Player p) {
		QuickBoardAPI.createBoard(p, "scoreboard.default");
	}
	
	public String getAPIName() {
		return "QuickBoard";
	}

}
