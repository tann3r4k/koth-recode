package com.benzimmer123.koth.hooks.scoreboard;

import java.util.List;

import org.bukkit.entity.Player;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.hooks.face.ScoreboardHook;
import com.benzimmer123.koth.util.LoggerUtil;

import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.TabPlayer;
import me.neznamy.tab.api.scoreboard.Scoreboard;
import me.neznamy.tab.api.scoreboard.ScoreboardManager;

public class TabPremium implements ScoreboardHook {

	private static boolean loggedFailure;
	private static final String NAME = "koth";
	private static final int MAX_LINES = 15;

	public boolean showKoth(Player player) {
		ScoreboardManager manager = manager();
		TabPlayer tabPlayer = tabPlayer(player);
		if (manager == null || tabPlayer == null) {
			return false;
		}
		Scoreboard board = manager.getRegisteredScoreboards().get(NAME);
		if (board == null) {
			board = manager.createScoreboard(NAME, title(), lines());
		}
		manager.showScoreboard(tabPlayer, board);
		return true;
	}

	public void reset(Player player) {
		ScoreboardManager manager = manager();
		TabPlayer tabPlayer = tabPlayer(player);
		if (manager == null || tabPlayer == null) {
			return;
		}
		manager.resetScoreboard(tabPlayer);
	}

	private String title() {
		String configured = KOTH.getInstance().getConfig().getString("SCOREBOARD.SCOREBOARD_TITLE");
		return configured == null || configured.isEmpty() ? "KOTH" : configured;
	}

	private List<String> lines() {
		List<String> configured = KOTH.getInstance().getConfig().getStringList("SCOREBOARD.SCOREBOARD_LINES");
		if (configured.size() <= MAX_LINES) {
			return configured;
		}
		return configured.subList(0, MAX_LINES);
	}

	private ScoreboardManager manager() {
		try {
			TabAPI api = TabAPI.getInstance();
			return api == null ? null : api.getScoreboardManager();
		} catch (Throwable thrown) {
			if (!loggedFailure) {
				loggedFailure = true;
				LoggerUtil.warning("[KOTH] TAB scoreboard API failed: " + thrown);
			}
			return null;
		}
	}

	private TabPlayer tabPlayer(Player player) {
		try {
			TabAPI api = TabAPI.getInstance();
			return api == null ? null : api.getPlayer(player.getUniqueId());
		} catch (Throwable thrown) {
			return null;
		}
	}

	@Override
	public void removeScoreboard(Player player) {
		reset(player);
	}

	@Override
	public void giveScoreboard(Player player) {
		reset(player);
	}

	@Override
	public String getAPIName() {
		return "TAB";
	}
}
