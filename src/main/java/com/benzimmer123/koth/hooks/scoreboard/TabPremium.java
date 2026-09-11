package com.benzimmer123.koth.hooks.scoreboard;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.benzimmer123.koth.hooks.face.ScoreboardHook;

import me.neznamy.tab.api.TabPlayer;
import me.neznamy.tab.shared.TAB;

public class TabPremium implements ScoreboardHook {

	public void removeScoreboard(Player p) {
		File scoreboardConfig = new File(Bukkit.getPluginManager().getPlugin("TAB").getDataFolder(), "config.yml");

		if (!scoreboardConfig.exists())
			return;

		FileConfiguration scoreboardFile = YamlConfiguration.loadConfiguration(scoreboardConfig);

		if (!scoreboardFile.getBoolean("scoreboard.enabled"))
			return;

		TabPlayer tabPlayer = TAB.getInstance().getPlayer(p.getUniqueId());
		
		if (tabPlayer == null)
			return;
		
		TAB.getInstance().getScoreboardManager().setScoreboardVisible(tabPlayer, false, false);
	}

	public void giveScoreboard(Player p) {
		File scoreboardConfig = new File(Bukkit.getPluginManager().getPlugin("TAB").getDataFolder(), "config.yml");

		if (!scoreboardConfig.exists())
			return;

		FileConfiguration scoreboardFile = YamlConfiguration.loadConfiguration(scoreboardConfig);

		if (!scoreboardFile.getBoolean("scoreboard.enabled"))
			return;

		TabPlayer tabPlayer = TAB.getInstance().getPlayer(p.getUniqueId());

		if (tabPlayer == null)
			return;

		TAB.getInstance().getScoreboardManager().setScoreboardVisible(tabPlayer, true, false);
	}

	public String getAPIName() {
		return "TAB";
	}
}
