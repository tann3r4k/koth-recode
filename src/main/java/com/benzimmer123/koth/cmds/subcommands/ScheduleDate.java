package com.benzimmer123.koth.cmds.subcommands;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.bukkit.command.CommandSender;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.api.objects.KOTHArena;
import com.benzimmer123.koth.cmds.rootcommand.SubCommand;
import com.benzimmer123.koth.handlers.RandomScheduleHandler;
import com.benzimmer123.koth.util.LangUtil;

public class ScheduleDate extends SubCommand {

	public ScheduleDate(KOTH instance) {
		super(instance, true);
		addAlias("schedule");
		addAlias("scheduledate");
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		int runTime = 1;

		if (!KOTH.getInstance().getConfig().getBoolean("KOTH_POINTS_SYSTEM.ENABLED")) {
			if (args.length > 4) {
				try {
					runTime = Integer.parseInt(args[4]);
				} catch (NumberFormatException e) {
					runTime = 0;
				}

				if (runTime == 0) {
					LangUtil.sendMessage(sender, LangUtil.INVALID_LENGTH.toString());
					return false;
				}
			}
		}

		int maxRunTime = 0;
		int maxPoints = 0;

		if (KOTH.getInstance().getConfig().getBoolean("KOTH_POINTS_SYSTEM.ENABLED")) {
			if (KOTH.getInstance().getConfig().getBoolean("KOTH_POINTS_SYSTEM.MAX_POINTS") && KOTH.getInstance().getConfig().getBoolean(
					"KOTH_POINTS_SYSTEM.MAX_RUNTIME")) {
				try {
					maxPoints = Integer.parseInt(args[4]);
				} catch (NumberFormatException e) {
					maxPoints = 0;
				}
				try {
					maxRunTime = Integer.parseInt(args[5]);
				} catch (NumberFormatException e) {
					maxRunTime = 0;
				}
			} else if (KOTH.getInstance().getConfig().getBoolean("KOTH_POINTS_SYSTEM.MAX_POINTS")) {
				try {
					maxPoints = Integer.parseInt(args[4]);
				} catch (NumberFormatException e) {
					maxPoints = 0;
				}
			} else {
				try {
					maxRunTime = Integer.parseInt(args[4]);
				} catch (NumberFormatException e) {
					maxRunTime = 0;
				}
			}
		} else {
			if (args.length == 6) {
				try {
					maxRunTime = Integer.parseInt(args[5]);
				} catch (NumberFormatException e) {
					maxRunTime = 0;
				}

				if (maxRunTime == 0) {
					LangUtil.sendMessage(sender, LangUtil.INVALID_MAXRUNTIME.toString());
					return false;
				}
			}
		}

		if (maxRunTime == 0 || maxPoints == 0) {
			if (KOTH.getInstance().getConfig().getBoolean("KOTH_POINTS_SYSTEM.MAX_POINTS") && KOTH.getInstance().getConfig().getBoolean(
					"KOTH_POINTS_SYSTEM.MAX_RUNTIME")) {
				if (maxRunTime == 0) {
					LangUtil.sendMessage(sender, LangUtil.MUST_SPECIFY_RUNTIME.toString());
				} else {
					LangUtil.sendMessage(sender, LangUtil.MUST_SPECIFY_MAXPOINTS.toString());
				}
				return false;
			}
		}

		if (maxRunTime == 0 && maxPoints == 0 && KOTH.getInstance().getConfig().getBoolean("KOTH_POINTS_SYSTEM.ENABLED")) {
			if (KOTH.getInstance().getConfig().getBoolean("KOTH_POINTS_SYSTEM.MAX_POINTS")) {
				LangUtil.sendMessage(sender, LangUtil.MUST_SPECIFY_MAXPOINTS.toString());
			} else {
				LangUtil.sendMessage(sender, LangUtil.MUST_SPECIFY_RUNTIME.toString());
			}
			return false;
		}

		KOTHArena koth = KOTH.getInstance().getKOTHManager().getKOTHFromString(args[0]);

		if (!args[0].equalsIgnoreCase("-rand") && !args[0].equalsIgnoreCase("-random")) {
			if (koth == null) {
				LangUtil.sendMessage(sender, LangUtil.INVALID_KOTH.toString());
				return false;
			}
		}

		int day;

		try {
			day = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			day = 0;
		}

		if (day == 0) {
			LangUtil.sendMessage(sender, LangUtil.INVALID_DAY.toString());
			return false;
		}

		int month;

		try {
			month = Integer.parseInt(args[2]);
		} catch (NumberFormatException e) {
			month = 0;
		}

		if (month == 0 || month > 12) {
			LangUtil.sendMessage(sender, LangUtil.INVALID_MONTH.toString());
			return false;
		}

		String[] date = args[3].split(":");

		int hour = 0;

		if (date[0] != null) {
			try {
				hour = Integer.parseInt(date[0]);
			} catch (NumberFormatException e) {
				hour = -1;
			}
		}

		if (hour == -1 || hour > 23) {
			LangUtil.sendMessage(sender, LangUtil.INVALID_HOUR.toString());
			return false;
		}

		int minute = 0;

		if (date.length == 1) {
			minute = 0;
		} else {
			try {
				minute = Integer.parseInt(date[1]);
			} catch (NumberFormatException e) {
				minute = -1;
			}
		}

		if (minute == -1 || minute > 59) {
			LangUtil.sendMessage(sender, LangUtil.INVALID_MINUTE.toString());
			return false;
		}

		ZoneId zoneId = ZoneId.of(KOTH.getInstance().getConfig().getString("CURRENT_TIMEZONE"));
		LocalDateTime currentTime = LocalDateTime.now();
		LocalDateTime scheduledTime = LocalDateTime.of(currentTime.getYear(), month, day, hour, minute);
		ZonedDateTime zdt = scheduledTime.atZone(zoneId);

		if (args[0].equalsIgnoreCase("-rand") || args[0].equalsIgnoreCase("-random")) {
			RandomScheduleHandler.getInstance().getSchedule().addSchedule(zdt, runTime, maxRunTime, maxPoints);
		} else {
			koth.getKOTHScheduler().addSchedule(zdt, runTime, maxRunTime, maxPoints);
			koth.save();
		}

		String maxRunTimeString = maxRunTime == 0 ? "not set" : maxRunTime + "";

		LangUtil.sendMessage(sender, LangUtil.SCHEDULED_DATE.toString().replaceAll("%koth%", args[0]).replaceAll("%time%", args[3]).replaceAll("%day%",
				args[1]).replaceAll("%month%", args[2]).replaceAll("%maxruntime%", maxRunTimeString).replaceAll("%length%", runTime + ""));
		return false;
	}

	@Override
	public boolean validArgumentLength(int length) {
		if (KOTH.getInstance().getConfig().getBoolean("KOTH_POINTS_SYSTEM.ENABLED")) {
			if (KOTH.getInstance().getConfig().getBoolean("KOTH_POINTS_SYSTEM.MAX_POINTS") && KOTH.getInstance().getConfig().getBoolean(
					"KOTH_POINTS_SYSTEM.MAX_RUNTIME")) {
				if (length == 7) {
					return true;
				}
			} else {
				if (length == 6) {
					return true;
				}
			}
			return false;
		} else {
			if (length == 6 || length == 7) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getHelp() {
		if (KOTH.getInstance().getConfig().getBoolean("KOTH_POINTS_SYSTEM.ENABLED")) {
			if (KOTH.getInstance().getConfig().getBoolean("KOTH_POINTS_SYSTEM.MAX_POINTS") && KOTH.getInstance().getConfig().getBoolean(
					"KOTH_POINTS_SYSTEM.MAX_RUNTIME")) {
				return "/koth schedule <KOTH> <DAY_OF_MONTH> <MONTH> <TIME_OF_DAY> <MAX_POINTS> <MAX_RUN_TIME>";
			} else if (KOTH.getInstance().getConfig().getBoolean("KOTH_POINTS_SYSTEM.MAX_POINTS")) {
				return "/koth schedule <KOTH> <DAY_OF_MONTH> <MONTH> <TIME_OF_DAY> <MAX_POINTS>";
			}
			return "/koth schedule <KOTH> <DAY_OF_MONTH> <MONTH> <TIME_OF_DAY> <MAX_RUN_TIME>";
		}
		return "/koth schedule <KOTH> <DAY_OF_MONTH> <MONTH> <TIME_OF_DAY> <DURATION> [MAX_RUN_TIME]";
	}

	@Override
	public String getPermission() {
		return "KOTH.SCHEDULER";
	}

	@Override
	public String getDescription() {
		return "Schedule a KOTH for a specific time.";
	}
}
