package com.benzimmer123.koth.hooks.scoreboard;

import org.bukkit.entity.Player;

import com.benzimmer123.koth.hooks.face.ScoreboardHook;

import rien.bijl.Scoreboard.r.Board.BoardPlayer;
import rien.bijl.Scoreboard.r.Plugin.Session;

public class Revision implements ScoreboardHook {

	public void removeScoreboard(Player p) {
		BoardPlayer.getBoardPlayer(p).kill();
	}

	public void giveScoreboard(Player p) {
		BoardPlayer.getBoardPlayer(p).attachConfigBoard((Session.getSession()).defaultBoard);
	}

	public String getAPIName() {
		return "Scoreboard-revision";
	}

}