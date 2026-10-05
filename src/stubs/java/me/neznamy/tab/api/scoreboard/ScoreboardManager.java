package me.neznamy.tab.api.scoreboard;

import java.util.List;
import java.util.Map;

import me.neznamy.tab.api.TabPlayer;

public interface ScoreboardManager {
	Scoreboard createScoreboard(String name, String title, List<String> lines);

	Map<String, Scoreboard> getRegisteredScoreboards();

	void showScoreboard(TabPlayer player, Scoreboard scoreboard);

	void resetScoreboard(TabPlayer player);
}
