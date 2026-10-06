package com.benzimmer123.koth.hooks.scoreboard;

import java.util.List;

import org.bukkit.entity.Player;

import com.benzimmer123.koth.hooks.face.ScoreboardHook;
import com.benzimmer123.koth.util.LoggerUtil;

import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.TabPlayer;
import me.neznamy.tab.api.scoreboard.Scoreboard;
import me.neznamy.tab.api.scoreboard.ScoreboardManager;

public class TabPremium implements ScoreboardHook {

	private static boolean loggedFailure;
	private static final String NAME = "koth";
	private static final String TITLE = "<#FAFAFA>KOTH</#A1A1AA>";
	private static final List<String> LINES = List.of(
			"<#3F3F46>&m                    </#A1A1AA>||",
			"&8&lKOTH||",
			"&7ɴᴀᴍᴇ||&f%koth_active%",
			"&7ᴛɪᴍᴇ||&f%koth_time_left%",
			"&7ᴘʟᴀʏᴇʀ||&f%koth_capping_players%",
			"&7ᴛᴇᴀᴍ||&f%koth_capping_teams%",
			"",
			"&8&lLocation||",
			"&7x||&f%koth_coordinate_x%",
			"&7ʏ||&f%koth_coordinate_y%",
			"&7ᴢ||&f%koth_coordinate_z%",
			"<#A1A1AA>&m                    </#3F3F46>||");

	public boolean showKoth(Player player) {
		ScoreboardManager manager = manager();
		TabPlayer tabPlayer = tabPlayer(player);
		if (manager == null || tabPlayer == null) {
			return false;
		}
		Scoreboard board = manager.getRegisteredScoreboards().get(NAME);
		if (board == null) {
			board = manager.createScoreboard(NAME, TITLE, LINES);
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
