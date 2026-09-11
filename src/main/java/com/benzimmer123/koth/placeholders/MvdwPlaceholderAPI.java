package com.benzimmer123.koth.placeholders;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.handlers.KOTHHandler;
import com.benzimmer123.koth.util.PlaceholderUtil;

import be.maximvdw.placeholderapi.PlaceholderAPI;
import be.maximvdw.placeholderapi.PlaceholderReplaceEvent;
import be.maximvdw.placeholderapi.PlaceholderReplacer;

public class MvdwPlaceholderAPI {

	public void register() {
		PlaceholderAPI.registerPlaceholder(KOTH.getInstance(), "koth_total_wins", new PlaceholderReplacer() {
			@Override
			public String onPlaceholderReplace(PlaceholderReplaceEvent e) {
				return PlaceholderUtil.getPlayerWinAmount(e.getPlayer()) + "";
			}
		});

		PlaceholderAPI.registerPlaceholder(KOTH.getInstance(), "koth_time_left", new PlaceholderReplacer() {
			@Override
			public String onPlaceholderReplace(PlaceholderReplaceEvent e) {
				return PlaceholderUtil.getTimes();
			}
		});

		PlaceholderAPI.registerPlaceholder(KOTH.getInstance(), "koth_maxruntime", new PlaceholderReplacer() {
			@Override
			public String onPlaceholderReplace(PlaceholderReplaceEvent e) {
				return PlaceholderUtil.getMaxRunTime();
			}
		});

		PlaceholderAPI.registerPlaceholder(KOTH.getInstance(), "koth_active", new PlaceholderReplacer() {
			@Override
			public String onPlaceholderReplace(PlaceholderReplaceEvent e) {
				return PlaceholderUtil.getKoths();
			}
		});

		PlaceholderAPI.registerPlaceholder(KOTH.getInstance(), "koth_capping_players", new PlaceholderReplacer() {
			@Override
			public String onPlaceholderReplace(PlaceholderReplaceEvent e) {
				return PlaceholderUtil.getCappers();
			}
		});

		PlaceholderAPI.registerPlaceholder(KOTH.getInstance(), "koth_coordinate_x", new PlaceholderReplacer() {
			@Override
			public String onPlaceholderReplace(PlaceholderReplaceEvent e) {
				return PlaceholderUtil.getXCords();
			}
		});

		PlaceholderAPI.registerPlaceholder(KOTH.getInstance(), "koth_coordinate_y", new PlaceholderReplacer() {
			@Override
			public String onPlaceholderReplace(PlaceholderReplaceEvent e) {
				return PlaceholderUtil.getYCords();
			}
		});

		PlaceholderAPI.registerPlaceholder(KOTH.getInstance(), "koth_coordinate_z", new PlaceholderReplacer() {
			@Override
			public String onPlaceholderReplace(PlaceholderReplaceEvent e) {
				return PlaceholderUtil.getZCords();
			}
		});

		PlaceholderAPI.registerPlaceholder(KOTH.getInstance(), "koth_next_coordinate_x", new PlaceholderReplacer() {
			@Override
			public String onPlaceholderReplace(PlaceholderReplaceEvent e) {
				return PlaceholderUtil.getNextScheduledXCords();
			}
		});

		PlaceholderAPI.registerPlaceholder(KOTH.getInstance(), "koth_next_coordinate_y", new PlaceholderReplacer() {
			@Override
			public String onPlaceholderReplace(PlaceholderReplaceEvent e) {
				return PlaceholderUtil.getNextScheduledYCords();
			}
		});

		PlaceholderAPI.registerPlaceholder(KOTH.getInstance(), "koth_next_coordinate_z", new PlaceholderReplacer() {
			@Override
			public String onPlaceholderReplace(PlaceholderReplaceEvent e) {
				return PlaceholderUtil.getNextScheduledZCords();
			}
		});

		PlaceholderAPI.registerPlaceholder(KOTH.getInstance(), "koth_worlds", new PlaceholderReplacer() {
			@Override
			public String onPlaceholderReplace(PlaceholderReplaceEvent e) {
				return PlaceholderUtil.getWorlds();
			}
		});

		PlaceholderAPI.registerPlaceholder(KOTH.getInstance(), "koth_minutes_left", new PlaceholderReplacer() {
			@Override
			public String onPlaceholderReplace(PlaceholderReplaceEvent e) {
				return PlaceholderUtil.getMinutes();
			}
		});

		PlaceholderAPI.registerPlaceholder(KOTH.getInstance(), "koth_seconds_left", new PlaceholderReplacer() {
			@Override
			public String onPlaceholderReplace(PlaceholderReplaceEvent e) {
				return PlaceholderUtil.getSeconds();
			}
		});

		PlaceholderAPI.registerPlaceholder(KOTH.getInstance(), "koth_capping_teams", new PlaceholderReplacer() {
			@Override
			public String onPlaceholderReplace(PlaceholderReplaceEvent e) {
				return PlaceholderUtil.getTeams();
			}
		});

		PlaceholderAPI.registerPlaceholder(KOTH.getInstance(), "koth_status", new PlaceholderReplacer() {
			@Override
			public String onPlaceholderReplace(PlaceholderReplaceEvent e) {
				return PlaceholderUtil.getStatus();
			}
		});

		PlaceholderAPI.registerPlaceholder(KOTH.getInstance(), "koth_distance", new PlaceholderReplacer() {
			@Override
			public String onPlaceholderReplace(PlaceholderReplaceEvent e) {
				return PlaceholderUtil.getDistance(e.getPlayer());
			}
		});

		PlaceholderAPI.registerPlaceholder(KOTH.getInstance(), "koth_scheduled_next", new PlaceholderReplacer() {
			@Override
			public String onPlaceholderReplace(PlaceholderReplaceEvent e) {
				return PlaceholderUtil.getScheduledNext();
			}
		});

		PlaceholderAPI.registerPlaceholder(KOTH.getInstance(), "koth_scheduled_countdown", new PlaceholderReplacer() {
			@Override
			public String onPlaceholderReplace(PlaceholderReplaceEvent e) {
				return PlaceholderUtil.timeUntilNextSchedule();
			}
		});

		PlaceholderAPI.registerPlaceholder(KOTH.getInstance(), "koth_scheduled_name", new PlaceholderReplacer() {
			@Override
			public String onPlaceholderReplace(PlaceholderReplaceEvent e) {
				return PlaceholderUtil.getScheduledNextName();
			}
		});

		PlaceholderAPI.registerPlaceholder(KOTH.getInstance(), "koth_last_capped_team", new PlaceholderReplacer() {
			@Override
			public String onPlaceholderReplace(PlaceholderReplaceEvent e) {
				return KOTHHandler.getInstance().getLastCappedTeam();
			}
		});

		PlaceholderAPI.registerPlaceholder(KOTH.getInstance(), "koth_last_capped_player", new PlaceholderReplacer() {
			@Override
			public String onPlaceholderReplace(PlaceholderReplaceEvent e) {
				return KOTHHandler.getInstance().getLastCappedPlayer();
			}
		});

		PlaceholderAPI.registerPlaceholder(KOTH.getInstance(), "koth_max_points", new PlaceholderReplacer() {
			@Override
			public String onPlaceholderReplace(PlaceholderReplaceEvent e) {
				return PlaceholderUtil.getMaxPoints();
			}
		});

		PlaceholderAPI.registerPlaceholder(KOTH.getInstance(), "koth_points_player", new PlaceholderReplacer() {
			@Override
			public String onPlaceholderReplace(PlaceholderReplaceEvent e) {
				return PlaceholderUtil.getPointsAmount(e.getPlayer());
			}
		});
	}

}
