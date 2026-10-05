package me.neznamy.tab.api;

import java.util.UUID;

import me.neznamy.tab.api.scoreboard.ScoreboardManager;

public interface TabAPI {
	static TabAPI getInstance() {
		return null;
	}

	TabPlayer getPlayer(UUID uuid);

	ScoreboardManager getScoreboardManager();
}
