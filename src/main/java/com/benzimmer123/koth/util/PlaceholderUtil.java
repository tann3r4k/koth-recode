package com.benzimmer123.koth.util;

import java.time.ZonedDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.api.objects.KOTHArena;
import com.benzimmer123.koth.api.objects.KOTHTopPlayer;
import com.benzimmer123.koth.api.objects.KOTHTopTeam;
import com.benzimmer123.koth.handlers.KOTHHandler;
import com.benzimmer123.koth.handlers.TopHandler;

public class PlaceholderUtil {

	public static String getTeams() {
		StringBuilder message = new StringBuilder("");

		if (!KOTH.getInstance().getKOTHManager().isActiveKOTH())
			return LangUtil.NO_KOTH.toString();

		for (KOTHArena koth : KOTH.getInstance().getKOTHManager().getActiveKOTHs()) {
			String teamName = koth.getPlayerCapper() == null ? LangUtil.NO_CAPPER.toString()
					: KOTH.getInstance().getTeamManager().getTeamName(koth.getPlayerCapper());
			String toAppend = message.length() == 0 ? teamName : ", " + teamName;
			message.append(toAppend);
		}

		return message.toString();
	}

	public static String getTeams(KOTHArena koth) {
		StringBuilder message = new StringBuilder("");

		if (!koth.isActive())
			return LangUtil.NO_KOTH.toString();

		String teamName = koth.getPlayerCapper() == null ? LangUtil.NO_CAPPER.toString()
				: KOTH.getInstance().getTeamManager().getTeamName(koth.getPlayerCapper());
		String toAppend = message.length() == 0 ? teamName : ", " + teamName;
		message.append(toAppend);

		return message.toString();
	}

	public static String getTeamLeaders() {
		StringBuilder message = new StringBuilder("");

		if (!KOTH.getInstance().getKOTHManager().isActiveKOTH())
			return LangUtil.NO_KOTH.toString();

		for (KOTHArena koth : KOTH.getInstance().getKOTHManager().getActiveKOTHs()) {
			String teamLeader = koth.getPlayerCapper() == null ? LangUtil.NO_CAPPER.toString()
					: (KOTH.getInstance().getTeamManager().getTeamLeader(koth.getPlayerCapper()) == null ? LangUtil.NO_CAPPER.toString()
							: KOTH.getInstance().getTeamManager().getTeamLeader(koth.getPlayerCapper()));
			String toAppend = message.length() == 0 ? teamLeader : ", " + teamLeader;
			message.append(toAppend);
		}

		return message.toString();
	}

	public static String getKoths() {
		StringBuilder message = new StringBuilder("");

		if (!KOTH.getInstance().getKOTHManager().isActiveKOTH())
			return LangUtil.NO_KOTH.toString();

		for (KOTHArena koth : KOTH.getInstance().getKOTHManager().getActiveKOTHs()) {
			String toAppend = message.length() == 0 ? koth.getName(true) : ", " + koth.getName(true);
			message.append(toAppend);
		}

		return message.toString();
	}

	public static String getTime(KOTHArena koth) {
		StringBuilder message = new StringBuilder("");

		if (!koth.isActive())
			return LangUtil.NO_KOTH.toString();

		String time = koth.getTimeRemainingAsString();
		if (KOTH.getInstance().getKOTHManager().isPointsEnabled()) {
			time = "N/A";
		}
		String toAppend = message.length() == 0 ? time + "" : ", " + time;
		message.append(toAppend);

		return message.toString();
	}

	public static String getTimes() {
		StringBuilder message = new StringBuilder("");

		if (!KOTH.getInstance().getKOTHManager().isActiveKOTH())
			return LangUtil.NO_KOTH.toString();

		for (KOTHArena koth : KOTH.getInstance().getKOTHManager().getActiveKOTHs()) {
			String time = koth.getTimeRemainingAsString();
			if (KOTH.getInstance().getKOTHManager().isPointsEnabled()) {
				time = "N/A";
			}
			String toAppend = message.length() == 0 ? time + "" : ", " + time;
			message.append(toAppend);
		}

		return message.toString();
	}

	public static String getSeconds(KOTHArena koth) {
		StringBuilder message = new StringBuilder("");

		if (!koth.isActive())
			return LangUtil.NO_KOTH.toString();

		int seconds = koth.getKOTHDetails().getCaptureTime();
		String secondString;

		if (KOTH.getInstance().getKOTHManager().isPointsEnabled()) {
			secondString = "N/A";
		} else {
			secondString = seconds + "";
		}

		String toAppend = message.length() == 0 ? secondString + "" : ", " + secondString;
		message.append(toAppend);

		return message.toString();
	}

	public static String getMinutes(KOTHArena koth) {
		StringBuilder message = new StringBuilder("");

		if (!koth.isActive())
			return LangUtil.NO_KOTH.toString();

		int minute = koth.getKOTHDetails().getCaptureTime() / 60;
		String minuteString;

		if (KOTH.getInstance().getKOTHManager().isPointsEnabled()) {
			minuteString = "N/A";
		} else {
			minuteString = minute + "";
		}

		String toAppend = message.length() == 0 ? minuteString + "" : ", " + minuteString;
		message.append(toAppend);

		return message.toString();
	}

	public static String getSeconds() {
		StringBuilder message = new StringBuilder("");

		if (!KOTH.getInstance().getKOTHManager().isActiveKOTH())
			return LangUtil.NO_KOTH.toString();

		for (KOTHArena koth : KOTH.getInstance().getKOTHManager().getActiveKOTHs()) {
			int seconds = koth.getKOTHDetails().getCaptureTime();
			String secondString;

			if (KOTH.getInstance().getKOTHManager().isPointsEnabled()) {
				secondString = "N/A";
			} else {
				secondString = seconds + "";
			}

			String toAppend = message.length() == 0 ? secondString + "" : ", " + secondString;
			message.append(toAppend);
		}

		return message.toString();
	}

	public static String getMinutes() {
		StringBuilder message = new StringBuilder("");

		if (!KOTH.getInstance().getKOTHManager().isActiveKOTH())
			return LangUtil.NO_KOTH.toString();

		for (KOTHArena koth : KOTH.getInstance().getKOTHManager().getActiveKOTHs()) {
			int minute = koth.getKOTHDetails().getCaptureTime() / 60;
			String toAppend = message.length() == 0 ? minute + "" : ", " + minute;
			message.append(toAppend);
		}

		return message.toString();
	}

	public static String getHours() {
		StringBuilder message = new StringBuilder("");

		if (!KOTH.getInstance().getKOTHManager().isActiveKOTH())
			return LangUtil.NO_KOTH.toString();

		for (KOTHArena koth : KOTH.getInstance().getKOTHManager().getActiveKOTHs()) {
			int hour = koth.getKOTHDetails().getCaptureTime() / 60 / 60;
			String hourString;

			if (KOTH.getInstance().getKOTHManager().isPointsEnabled()) {
				hourString = "N/A";
			} else {
				hourString = hour + "";
			}
			String toAppend = message.length() == 0 ? hourString + "" : ", " + hourString;
			message.append(toAppend);
		}

		return message.toString();
	}

	public static String getMaxRunTime(KOTHArena koth) {
		StringBuilder message = new StringBuilder("");

		if (koth.getKOTHDetails().getMaxRunTime() != 0 && koth.getKOTHDetails().getMaxRunTime() != -1) {
			int maxRunTime = koth.getKOTHDetails().getMaxRunTime() - koth.getKOTHDetails().getRunTime();
			String toAppend = message.length() == 0 ? maxRunTime + "" : ", " + maxRunTime;
			message.append(toAppend);
		}

		return message.toString();
	}

	public static String getMaxRunTime() {
		StringBuilder message = new StringBuilder("");

		if (!KOTH.getInstance().getKOTHManager().isActiveKOTH())
			return LangUtil.NO_KOTH.toString();

		for (KOTHArena koth : KOTH.getInstance().getKOTHManager().getActiveKOTHs()) {
			if (koth.getKOTHDetails().getMaxRunTime() != 0 && koth.getKOTHDetails().getMaxRunTime() != -1) {
				int maxRunTime = koth.getKOTHDetails().getMaxRunTime() - koth.getKOTHDetails().getRunTime();
				String toAppend = message.length() == 0 ? maxRunTime + "" : ", " + maxRunTime;
				message.append(toAppend);
			} else {
				String toAppend = message.length() == 0 ? "N/A" : ", N/A";
				message.append(toAppend);
			}
		}

		return message.toString();
	}

	public static String getNextScheduledYCords() {
		String kothName = DateUtil.getNextStartName();

		if (kothName == null)
			return LangUtil.NO_KOTH.toString();

		KOTHArena kothArena = KOTH.getInstance().getKOTHManager().getKOTHFromString(kothName);

		if (kothArena == null)
			return LangUtil.NO_KOTH.toString();

		int y = kothArena.getKOTHLocation().getLocation1().getBlockY();
		return y + "";
	}

	public static String getNextScheduledXCords() {
		String kothName = DateUtil.getNextStartName();

		if (kothName == null)
			return LangUtil.NO_KOTH.toString();

		KOTHArena kothArena = KOTH.getInstance().getKOTHManager().getKOTHFromString(kothName);

		if (kothArena == null)
			return LangUtil.NO_KOTH.toString();

		int x = kothArena.getKOTHLocation().getLocation1().getBlockX();
		return x + "";
	}

	public static String getNextScheduledZCords() {
		String kothName = DateUtil.getNextStartName();

		if (kothName == null)
			return LangUtil.NO_KOTH.toString();

		KOTHArena kothArena = KOTH.getInstance().getKOTHManager().getKOTHFromString(kothName);

		if (kothArena == null)
			return LangUtil.NO_KOTH.toString();

		int z = kothArena.getKOTHLocation().getLocation1().getBlockZ();
		return z + "";
	}

	public static String getYCords() {
		StringBuilder message = new StringBuilder("");

		if (!KOTH.getInstance().getKOTHManager().isActiveKOTH())
			return LangUtil.NO_KOTH.toString();

		for (KOTHArena koth : KOTH.getInstance().getKOTHManager().getActiveKOTHs()) {
			int y = koth.getKOTHLocation().getLocation1().getBlockY();
			String toAppend = message.length() == 0 ? y + "" : ", " + y;
			message.append(toAppend);
		}

		return message.toString();
	}

	public static String getXCords() {
		StringBuilder message = new StringBuilder("");

		if (!KOTH.getInstance().getKOTHManager().isActiveKOTH())
			return LangUtil.NO_KOTH.toString();

		for (KOTHArena koth : KOTH.getInstance().getKOTHManager().getActiveKOTHs()) {
			int x = koth.getKOTHLocation().getLocation1().getBlockX();
			String toAppend = message.length() == 0 ? x + "" : ", " + x;
			message.append(toAppend);
		}

		return message.toString();
	}

	public static String getZCords() {
		StringBuilder message = new StringBuilder("");

		if (!KOTH.getInstance().getKOTHManager().isActiveKOTH())
			return LangUtil.NO_KOTH.toString();

		for (KOTHArena koth : KOTH.getInstance().getKOTHManager().getActiveKOTHs()) {
			int z = koth.getKOTHLocation().getLocation1().getBlockZ();
			String toAppend = message.length() == 0 ? z + "" : ", " + z;
			message.append(toAppend);
		}

		return message.toString();
	}

	public static String getWorld(KOTHArena koth) {
		StringBuilder message = new StringBuilder("");

		if (!koth.isActive())
			return LangUtil.NO_KOTH.toString();

		String world = koth.getKOTHLocation().getWorld();
		String toAppend = message.length() == 0 ? world + "" : ", " + world;
		message.append(toAppend);

		return message.toString();
	}

	public static String getWorlds() {
		StringBuilder message = new StringBuilder("");

		if (!KOTH.getInstance().getKOTHManager().isActiveKOTH())
			return LangUtil.NO_KOTH.toString();

		for (KOTHArena koth : KOTH.getInstance().getKOTHManager().getActiveKOTHs()) {
			String world = koth.getKOTHLocation().getWorld();
			String toAppend = message.length() == 0 ? world + "" : ", " + world;
			message.append(toAppend);
		}

		return message.toString();
	}

	public static String getMaxPoints(KOTHArena koth) {
		StringBuilder message = new StringBuilder("");

		if (!koth.isActive())
			return LangUtil.NO_KOTH.toString();

		int points = koth.getKOTHDetails().getMaxPoints();
		String toAppend = message.length() == 0 ? points + "" : ", " + points;
		message.append(toAppend);

		return message.toString();
	}

	public static String getMaxPoints() {
		StringBuilder message = new StringBuilder("");

		if (!KOTH.getInstance().getKOTHManager().isActiveKOTH())
			return LangUtil.NO_KOTH.toString();

		for (KOTHArena koth : KOTH.getInstance().getKOTHManager().getActiveKOTHs()) {
			int points = koth.getKOTHDetails().getMaxPoints();
			String toAppend = message.length() == 0 ? points + "" : ", " + points;
			message.append(toAppend);
		}

		return message.toString();
	}

	public static String getCappers() {
		StringBuilder message = new StringBuilder("");

		if (!KOTH.getInstance().getKOTHManager().isActiveKOTH())
			return LangUtil.NO_KOTH.toString();

		for (KOTHArena koth : KOTH.getInstance().getKOTHManager().getActiveKOTHs()) {
			String capper = koth.getPlayerCapper() == null ? LangUtil.NO_CAPPER.toString() : koth.getPlayerCapper().getName();
			String toAppend = message.length() == 0 ? capper : ", " + capper;
			message.append(toAppend);
		}

		return message.toString();
	}

	public static String getCappers(KOTHArena koth) {
		StringBuilder message = new StringBuilder("");

		if (!koth.isActive())
			return LangUtil.NO_KOTH.toString();

		String capper = koth.getPlayerCapper() == null ? LangUtil.NO_CAPPER.toString() : koth.getPlayerCapper().getName();
		String toAppend = message.length() == 0 ? capper : ", " + capper;
		message.append(toAppend);

		return message.toString();
	}

	public static String getDistance(Player p) {
		StringBuilder message = new StringBuilder("");
		if (!KOTH.getInstance().getKOTHManager().isActiveKOTH())
			return "0";

		for (KOTHArena koth : KOTH.getInstance().getKOTHManager().getActiveKOTHs()) {
			Location l = koth.getKOTHLocation().getLocation1();
			int distance = koth.getPlayerCapper() != null && koth.getPlayerCapper().equals(p) ? 0
					: KOTH.getInstance().getRegionManager().getDistance(l, p.getLocation());
			String toAppend = message.length() == 0 ? distance + "" : ", " + distance;
			message.append(toAppend);
		}

		return message.toString();
	}

	public static String timeUntilNextSchedule() {
		List<KOTHArena> koths = KOTHHandler.getInstance().getKOTHS();

		if (koths.isEmpty())
			return LangUtil.NO_KOTH.toString();

		if (DateUtil.getNextStart() == null)
			return LangUtil.NO_SCHEDULE.toString();

		return DateUtil.getScheduleCountdown();
	}

	public static String getPointsAmount(Player player) {
		StringBuilder message = new StringBuilder("");

		for (KOTHArena koth : KOTH.getInstance().getKOTHManager().getActiveKOTHs()) {
			boolean useTeam = KOTH.getInstance().getKOTHManager().isUsingTeams();
			String toAppend = "";
			if (useTeam) {
				toAppend = message.length() == 0 ? koth.getKOTHPoints().getPointsDouble(KOTH.getInstance().getTeamManager().getTeamName(player)) + ""
						: ", " + "" + koth.getKOTHPoints().getPointsDouble(KOTH.getInstance().getTeamManager().getTeamName(player));
			} else {
				toAppend = message.length() == 0 ? koth.getKOTHPoints().getPointsDouble(player.getName()) + ""
						: ", " + koth.getKOTHPoints().getPointsDouble(player.getName());
			}
			message.append(toAppend);
		}

		if (message.length() == 0)
			return "0";

		return message.toString();
	}

	public static String getPointsPlayer(int position) {
		if (!KOTH.getInstance().getKOTHManager().isActiveKOTH())
			LangUtil.NO_CAPPER.toString();

		StringBuilder message = new StringBuilder("");

		for (KOTHArena koth : KOTH.getInstance().getKOTHManager().getActiveKOTHs()) {
			if (koth.getKOTHPoints().getPosition(position) != null) {
				String toAppend = message.length() == 0 ? koth.getKOTHPoints().getPosition(position) + ""
						: ", " + koth.getKOTHPoints().getPosition(position);
				message.append(toAppend);
			}
		}

		if (message.length() == 0)
			return LangUtil.NO_CAPPER.toString();

		return message.toString();

	}

	public static String getPointsAmount(int position) {
		if (!KOTH.getInstance().getKOTHManager().isActiveKOTH())
			return "0";

		StringBuilder message = new StringBuilder("");

		for (KOTHArena koth : KOTH.getInstance().getKOTHManager().getActiveKOTHs()) {
			if (koth.getKOTHPoints().getAmountPosition(position) != null) {
				String toAppend = message.length() == 0 ? koth.getKOTHPoints().getAmountPosition(position) + ""
						: ", " + koth.getKOTHPoints().getAmountPosition(position);
				message.append(toAppend);
			}
		}

		if (message.length() == 0)
			return "0";

		return message.toString();
	}

	public static String getTopPlayer(int position) {
		LinkedHashMap<String, KOTHTopPlayer> kothMap = TopHandler.getInstance().getPlayers();
		List<KOTHTopPlayer> kothPlayers = kothMap.values().stream().collect(Collectors.toList());

		if (kothPlayers.size() >= position) {
			return kothPlayers.get(position - 1).getPlayerName();
		}

		return LangUtil.NO_CAPPER.toString();
	}

	public static String getTopTeam(int position) {
		Map<String, KOTHTopTeam> kothMap = TopHandler.getInstance().getTeams();
		List<KOTHTopTeam> kothTeams = kothMap.values().stream().collect(Collectors.toList());

		if (kothTeams.size() >= position) {
			return kothTeams.get(position - 1).getTeamName();
		}

		return LangUtil.NO_TEAM.toString();
	}

	public static int getTopPlayerAmount(int position) {
		LinkedHashMap<String, KOTHTopPlayer> kothMap = TopHandler.getInstance().getPlayers();
		List<KOTHTopPlayer> kothPlayers = kothMap.values().stream().collect(Collectors.toList());

		if (kothPlayers.size() >= position) {
			return kothPlayers.get(position - 1).getWins();
		}

		return 0;
	}

	public static int getTopTeamAmount(int position) {
		Map<String, KOTHTopTeam> kothMap = TopHandler.getInstance().getTeams();
		List<KOTHTopTeam> kothTeams = kothMap.values().stream().collect(Collectors.toList());

		if (kothTeams.size() >= position) {
			return kothTeams.get(position - 1).getWins();
		}

		return 0;
	}

	public static int getTeamWinAmount(String team) {
		Map<String, KOTHTopTeam> kothMap = TopHandler.getInstance().getTeams();

		for (KOTHTopTeam kothTeam : kothMap.values()) {
			if (kothTeam.getTeamName().equalsIgnoreCase(team)) {
				return kothTeam.getWins();
			}
		}

		return 0;
	}

	public static int getPlayerWinAmount(Player player) {
		Map<String, KOTHTopPlayer> kothMap = TopHandler.getInstance().getPlayers();

		if (kothMap.containsKey(player.getUniqueId().toString())) {
			return kothMap.get(player.getUniqueId().toString()).getWins();
		}

		return 0;
	}

	public static String getScheduledNext() {
		List<KOTHArena> koths = KOTHHandler.getInstance().getKOTHS();

		if (koths.isEmpty())
			return LangUtil.NO_KOTH.toString();

		if (KOTH.getInstance().getKOTHManager().isActiveKOTH()) {
			return LangUtil.ACTIVE_NOW.toString().replaceAll("%koth%", KOTH.getInstance().getKOTHManager().getActiveKOTHs().get(0).getName(true));
		}

		ZonedDateTime zdt = DateUtil.getNextStart();

		if (zdt == null)
			return LangUtil.NO_SCHEDULE.toString();

		return LangUtil.NEXT_SCHEDULE.toString().replaceAll("%month%", zdt.getMonthValue() + "").replaceAll("%day%", zdt.getDayOfMonth() + "")
				.replaceAll("%hour%", zdt.getHour() + "").replaceAll("%minute%", zdt.getMinute() + "");
	}

	public static String getScheduledNextName() {
		List<KOTHArena> koths = KOTHHandler.getInstance().getKOTHS();

		if (koths.isEmpty())
			return LangUtil.NO_KOTH.toString();

		String koth = DateUtil.getNextStartName();

		if (koth == null)
			return LangUtil.NO_SCHEDULE.toString();

		return LangUtil.NEXT_SCHEDULED_NAME.toString().replaceAll("%koth%", koth);
	}

	public static String getStatus() {
		if (KOTH.getInstance().getKOTHManager().isActiveKOTH())
			return LangUtil.ACTIVE_STATUS.toString();
		return LangUtil.NONE_ACTIVE_STATUS.toString();
	}

	public static String getStatus(KOTHArena koth) {
		if (koth.isActive())
			return LangUtil.ACTIVE_STATUS.toString();
		return LangUtil.NONE_ACTIVE_STATUS.toString();
	}
}
