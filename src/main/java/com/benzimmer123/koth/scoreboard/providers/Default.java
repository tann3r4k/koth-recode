package com.benzimmer123.koth.scoreboard.providers;

import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.scoreboard.ScoreboardProvider;
import com.benzimmer123.koth.scoreboard.ScoreboardText;
import com.benzimmer123.koth.util.LangUtil;
import com.benzimmer123.koth.util.PlaceholderUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class Default extends ScoreboardProvider {

	@Override
	public String getTitle(Player player) {
		String line = LangUtil.replaceString(KOTH.getInstance().getConfig().getString("SCOREBOARD.SCOREBOARD_TITLE"));
		line = KOTH.getInstance().getPlaceholderManager().parsePlaceholders(player, line);
		return line;
	}

	@Override
	public List<ScoreboardText> getLines(Player player) {
		List<ScoreboardText> lines = Lists.newArrayList();
		List<String> scoreboardLines = KOTH.getInstance().getConfig().getStringList("SCOREBOARD.SCOREBOARD_LINES");
		scoreboardLines.stream().forEach(line -> {
			line = KOTH.getInstance().getPlaceholderManager().parsePlaceholders(player, line);
			line = LangUtil.replaceString(line);
			line = replaceAllPlaceholders(player, line);
			lines.add(new ScoreboardText(line));
		});
		return lines;
	}

	private String replaceAllPlaceholders(Player player, String line) {
		HashMap<String, String> placeholders = Maps.newHashMap();

		// General placeholders

		placeholders.put("%player%", PlaceholderUtil.getCappers());
		placeholders.put("%koth%", PlaceholderUtil.getKoths());
		placeholders.put("%maxruntime%", PlaceholderUtil.getMaxRunTime());

		// Timing placeholders

		placeholders.put("%timeleft%", PlaceholderUtil.getTimes());
		placeholders.put("%secondsleft%", PlaceholderUtil.getSeconds());
		placeholders.put("%minutesleft%", PlaceholderUtil.getMinutes());
		placeholders.put("%hoursleft%", PlaceholderUtil.getHours());

		// Points placeholders

		placeholders.put("%maxpoints%", PlaceholderUtil.getMaxPoints());
		placeholders.put("%koth_points_player%", PlaceholderUtil.getPointsAmount(player));

		for (int i = 1; i <= 5; i++) {
			placeholders.put("%points" + i + "_amount%", PlaceholderUtil.getPointsAmount(i));
			placeholders.put("%points" + i + "_name%", PlaceholderUtil.getPointsPlayer(i));
		}
		
		// Coordinate placeholders
		
		placeholders.put("%distance%", PlaceholderUtil.getDistance(player));
		placeholders.put("%x%", PlaceholderUtil.getXCords());
		placeholders.put("%y%", PlaceholderUtil.getYCords());
		placeholders.put("%z%", PlaceholderUtil.getZCords());
		placeholders.put("%world%", PlaceholderUtil.getWorlds());

		// Team placeholders
		
		placeholders.put("%factionleader%", PlaceholderUtil.getTeamLeaders());
		placeholders.put("%teamleader%", PlaceholderUtil.getTeamLeaders());
		placeholders.put("%team%", PlaceholderUtil.getTeams());
		placeholders.put("%faction%", PlaceholderUtil.getTeams());
		
		for (String replace : placeholders.keySet()) {
			line = line.replaceAll(replace, placeholders.get(replace));
		}
		
		return line;
	}
}
