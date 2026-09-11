package com.benzimmer123.koth.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.api.objects.KOTHArena;
import com.benzimmer123.koth.handlers.KOTHHandler;
import com.benzimmer123.koth.util.PlaceholderUtil;

public class ClipPlaceholderAPI extends PlaceholderExpansion {

	// Identifier for this expansion
	@Override
	public String getIdentifier() {
		return "koth";
	}

	@Override
	public boolean persist() {
		return true;
	}

	@Override
	public String getAuthor() {
		return "Benzimmer";
	}

	// Since we are registering this expansion from the dependency, this can be
	// null
	@Override
	public String getPlugin() {
		return null;
	}

	// Return the plugin version since this expansion is bundled with the
	// dependency
	@Override
	public String getVersion() {
		return KOTH.getInstance().getDescription().getVersion();
	}

	@Override
	public String onPlaceholderRequest(Player player, String placeholder) {
		if (placeholder == null) {
			return "";
		}

		switch (placeholder) {
		case "total_wins":
			if (player != null) {
				return PlaceholderUtil.getPlayerWinAmount(player) + "";
			}
		case "time_left":
			return PlaceholderUtil.getTimes();
		case "maxruntime":
			return PlaceholderUtil.getMaxRunTime();
		case "active":
			return PlaceholderUtil.getKoths();
		case "capping_players":
			return PlaceholderUtil.getCappers();
		case "coordinate_x":
			return PlaceholderUtil.getXCords();
		case "coordinate_y":
			return PlaceholderUtil.getYCords();
		case "coordinate_z":
			return PlaceholderUtil.getZCords();
		case "next_coordinate_x":
			return PlaceholderUtil.getNextScheduledXCords();
		case "next_coordinate_y":
			return PlaceholderUtil.getNextScheduledYCords();
		case "next_coordinate_z":
			return PlaceholderUtil.getNextScheduledZCords();
		case "worlds":
			return PlaceholderUtil.getWorlds();
		case "minutes_left":
			return PlaceholderUtil.getMinutes();
		case "seconds_left":
			return PlaceholderUtil.getSeconds();
		case "capping_teams":
			return PlaceholderUtil.getTeams();
		case "status":
			return PlaceholderUtil.getStatus();
		case "distance":
			if (player != null) {
				return PlaceholderUtil.getDistance(player);
			}
		case "scheduled_next":
			return PlaceholderUtil.getScheduledNext();
		case "scheduled_countdown":
			return PlaceholderUtil.timeUntilNextSchedule();
		case "scheduled_name":
			return PlaceholderUtil.getScheduledNextName();
		case "last_capped_team":
			return KOTHHandler.getInstance().getLastCappedTeam();
		case "last_capped_player":
			return KOTHHandler.getInstance().getLastCappedPlayer();
		case "max_points":
			return PlaceholderUtil.getMaxPoints();
		case "points_player":
			if (player != null) {
				return PlaceholderUtil.getPointsAmount(player);
			}
		}

		if (placeholder.startsWith("topteam_")) {
			try {
				int position = Integer.parseInt(placeholder.split("_")[1]);
				if (placeholder.endsWith("_name")) {
					return PlaceholderUtil.getTopTeam(position);
				} else if (placeholder.endsWith("_amount")) {
					return PlaceholderUtil.getTopTeamAmount(position) + "";
				}
			} catch (NumberFormatException e) {
				return "Error";
			}
		} else if (placeholder.startsWith("topplayer_")) {
			try {
				int position = Integer.parseInt(placeholder.split("_")[1]);
				if (placeholder.endsWith("_name")) {
					return PlaceholderUtil.getTopPlayer(position);
				} else if (placeholder.endsWith("_amount")) {
					return PlaceholderUtil.getTopPlayerAmount(position) + "";
				}
			} catch (NumberFormatException e) {
				return "Error";
			}
		} else if (placeholder.startsWith("points_")) {
			try {
				int position = Integer.parseInt(placeholder.split("_")[1]);
				if (placeholder.endsWith("_name")) {
					return PlaceholderUtil.getPointsPlayer(position);
				} else if (placeholder.endsWith("_amount")) {
					return PlaceholderUtil.getPointsAmount(position);
				}
			} catch (NumberFormatException e) {
				return "Error";
			}
		} else if (placeholder.startsWith("totalpoints_")) {
			try {
				int position = Integer.parseInt(placeholder.split("_")[1]);
				if (placeholder.endsWith("_name")) {
					return KOTHHandler.getInstance().getTotalPointsPosition(position);
				} else if (placeholder.endsWith("_amount")) {
					return KOTHHandler.getInstance().getTotalPoints(KOTHHandler.getInstance().getTotalPointsPosition(position)) + "";
				}
			} catch (NumberFormatException e) {
				return "Error";
			}
		} else if (placeholder.startsWith("wins_team")) {
			String teamName = placeholder.split("_")[2];
			return PlaceholderUtil.getTeamWinAmount(teamName) + "";
		} else if (placeholder.startsWith("wins_player")) {
			String playerName = placeholder.split("_")[2];
			if (Bukkit.getPlayer(playerName) != null) {
				return PlaceholderUtil.getPlayerWinAmount(player) + "";
			}
		} else if (placeholder.startsWith("specific_")) {
			String kothName = placeholder.split("_")[1];
			KOTHArena koth = KOTH.getInstance().getKOTHManager().getKOTHFromString(kothName);
			if (placeholder.equalsIgnoreCase("specific_" + kothName + "_max_points")) {
				return PlaceholderUtil.getMaxPoints(koth);
			} else if (placeholder.equalsIgnoreCase("specific_" + kothName + "_maxruntime")) {
				return PlaceholderUtil.getMaxRunTime(koth);
			} else if (placeholder.equalsIgnoreCase("specific_" + kothName + "_time_left")) {
				return PlaceholderUtil.getTime(koth);
			} else if (placeholder.equalsIgnoreCase("specific_" + kothName + "_seconds_left")) {
				return PlaceholderUtil.getSeconds(koth);
			} else if (placeholder.equalsIgnoreCase("specific_" + kothName + "_minutes_left")) {
				return PlaceholderUtil.getMinutes(koth);
			} else if (placeholder.equalsIgnoreCase("specific_" + kothName + "_capping_players")) {
				return PlaceholderUtil.getCappers(koth);
			} else if (placeholder.equalsIgnoreCase("specific_" + kothName + "_capping_teams")) {
				return PlaceholderUtil.getTeams(koth);
			} else if (placeholder.equalsIgnoreCase("specific_" + kothName + "_coordinate_x")) {
				return koth.getKOTHLocation().getLocation1().getBlockX() + "";
			} else if (placeholder.equalsIgnoreCase("specific_" + kothName + "_coordinate_y")) {
				return koth.getKOTHLocation().getLocation1().getBlockY() + "";
			} else if (placeholder.equalsIgnoreCase("specific_" + kothName + "_coordinate_z")) {
				return koth.getKOTHLocation().getLocation1().getBlockZ() + "";
			} else if (placeholder.equalsIgnoreCase("specific_" + kothName + "_worlds")) {
				return PlaceholderUtil.getWorld(koth);
			} else if (placeholder.equalsIgnoreCase("specific_" + kothName + "_status")) {
				return PlaceholderUtil.getStatus(koth);
			}
		}

		/**
		 * points_1_amount_total -> totalpoints_1_amount
		 * points_1_name_total -> totalpoints_1_name
		 * 
		 * topteam_1 -> topteam_1_name
		 * topplayer_1 -> topplayer_1_name
		 * 
		 * topteam_1_amount
		 * topplayer_1_amount
		 * 
		 * points_1_name
		 * points_1_amount
		 * 
		 * KOTH specific placeholders
		 * 
		 * %koth_wins_faction_{faction}% & %koth_wins_player_{player}%
		 * 
		 */

		return null;
	}
}