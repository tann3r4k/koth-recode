package com.benzimmer123.koth.hooks.scoreboard;

import org.bukkit.entity.Player;

import be.maximvdw.featherboard.api.FeatherBoardAPI;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.hooks.face.ScoreboardHook;

public class Featherboard implements ScoreboardHook {

	public boolean displayTrigger(Player p) {
		FeatherBoardAPI.showScoreboard(p, KOTH.getInstance().getConfig().getString("FEATHERBOARD_TRIGGER.SCOREBOARD_NAME"));
		return true;
	}

	public void giveScoreboard(Player p) {
		if (!FeatherBoardAPI.isToggled(p))
			FeatherBoardAPI.toggle(p);
		
		FeatherBoardAPI.resetDefaultScoreboard(p);
	}

	public String getAPIName() {
		return "Featherboard";
	}

	@Override
	public void removeScoreboard(Player p) {
		if (FeatherBoardAPI.isToggled(p))
			FeatherBoardAPI.toggle(p);
	}

}