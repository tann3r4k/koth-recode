package me.neznamy.tab.api;

import java.util.UUID;

import me.neznamy.tab.api.scoreboard.ScoreboardManager;

public abstract class TabAPI {

	public static TabAPI getInstance() {
		return null;
	}

	public abstract TabPlayer getPlayer(UUID uuid);

	public abstract ScoreboardManager getScoreboardManager();
}
