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

public class ScheduleDaily extends SubCommand {

	public ScheduleDaily(KOTH instance) {
		super(instance, true);
		addAlias("daily");
		addAlias("scheduledaily");
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		int runTime = 1;

		if (!KOTH.getInstance().getConfig().getBoolean("KOTH_POINTS_SYSTEM.ENABLED")) {
			try {
				runTime = Integer.parseInt(args[2]);
			} catch (NumberFormatException e) {
				runTime = 0;
			}

			if (runTime == 0) {
				LangUtil.sendMessage(sender, LangUtil.INVALID_LENGTH.toString());
				return false;
			}
		}

		int maxRunTime = 0;
		int maxPoints = 0;

		if (KOTH.getInstance().getConfig().getBoolean("KOTH_POINTS_SYSTEM.ENABLED")) {
			if (KOTH.getInstance().getConfig().getBoolean("KOTH_POINTS_SYSTEM.MAX_POINTS") && KOTH.getInstance().getConfig().getBoolean(
					"KOTH_POINTS_SYSTEM.MAX_RUNTIME")) {
				try {
					maxPoints = Integer.parseInt(args[2]);
				} catch (NumberFormatException e) {
					maxPoints = 0;
				}

				try {
					maxRunTime = Integer.parseInt(args[3]);
				} catch (NumberFormatException e) {
					maxRunTime = 0;
				}
			} else if (KOTH.getInstance().getConfig().getBoolean("KOTH_POINTS_SYSTEM.MAX_POINTS")) {
				try {
					maxPoints = Integer.parseInt(args[2]);
				} catch (NumberFormatException e) {
					maxPoints = 0;
				}
			} else {
				try {
					maxRunTime = Integer.parseInt(args[2]);
				} catch (NumberFormatException e) {
					maxRunTime = 0;
				}
			}
		} else {
			if (args.length == 4) {
				try {
					maxRunTime = Integer.parseInt(args[3]);
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

		String[] date = args[1].split(":");

		if (date.length <= 1) {
			LangUtil.sendMessage(sender, LangUtil.INVALID_TIME.toString());
			return false;
		}

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

		if (date[1] != null) {
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
		LocalDateTime scheduledTime = LocalDateTime.of(currentTime.getYear(), currentTime.getMonth(), currentTime.getDayOfMonth(), hour, minute);
		ZonedDateTime zdt = scheduledTime.atZone(zoneId);

		if (args[0].equalsIgnoreCase("-rand") || args[0].equalsIgnoreCase("-random")) {
			RandomScheduleHandler.getInstance().getSchedule().addDaily(zdt, runTime, maxRunTime, maxPoints);
		} else {
			koth.getKOTHScheduler().addDaily(zdt, runTime, maxRunTime, maxPoints);
			koth.save();
		}

		String maxRunTimeString = maxRunTime == 0 ? "not set" : maxRunTime + "";

		LangUtil.sendMessage(sender, LangUtil.SCHEDULED_DAILY.toString().replaceAll("%koth%", args[0]).replaceAll("%length%", runTime + "").replaceAll(
				"%maxruntime%", maxRunTimeString).replaceAll("%time%", args[1]));
		return true;
	}

	@Override
	public boolean validArgumentLength(int length) {
		if (KOTH.getInstance().getConfig().getBoolean("KOTH_POINTS_SYSTEM.ENABLED")) {
			if (KOTH.getInstance().getConfig().getBoolean("KOTH_POINTS_SYSTEM.MAX_POINTS") && KOTH.getInstance().getConfig().getBoolean(
					"KOTH_POINTS_SYSTEM.MAX_RUNTIME")) {
				if (length == 5) {
					return true;
				}
				return false;
			} else {
				if (length == 4) {
					return true;
				}
			}
			return false;
		}
		if (length == 4 || length == 5) {
			return true;
		}
		return false;
	}

	@Override
	public String getHelp() {
		if (KOTH.getInstance().getConfig().getBoolean("KOTH_POINTS_SYSTEM.ENABLED")) {
			if (KOTH.getInstance().getConfig().getBoolean("KOTH_POINTS_SYSTEM.MAX_POINTS") && KOTH.getInstance().getConfig().getBoolean(
					"KOTH_POINTS_SYSTEM.MAX_RUNTIME")) {
				return "/koth daily <KOTH> <TIME_OF_DAY> <MAX_POINTS> <MAX_RUN_TIME>";
			} else if (KOTH.getInstance().getConfig().getBoolean("KOTH_POINTS_SYSTEM.MAX_POINTS")) {
				return "/koth daily <KOTH> <TIME_OF_DAY> <MAX_POINTS>";
			}
			return "/koth daily <KOTH> <TIME_OF_DAY> <MAX_RUN_TIME>";
		}
		return "/koth daily <KOTH> <TIME_OF_DAY> <DURATION> [MAX_RUN_TIME]";
	}

	@Override
	public String getPermission() {
		return "KOTH.DAILYSCHEDULE";
	}

	@Override
	public String getDescription() {
		return "Create an automatic KOTH that will run daily.";
	}
}
