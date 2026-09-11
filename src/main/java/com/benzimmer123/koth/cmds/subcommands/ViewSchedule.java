package com.benzimmer123.koth.cmds.subcommands;

import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import java.util.EnumSet;
import java.util.Map;

import org.bukkit.command.CommandSender;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.api.objects.KOTHArena;
import com.benzimmer123.koth.cmds.rootcommand.SubCommand;
import com.benzimmer123.koth.handlers.KOTHHandler;
import com.benzimmer123.koth.util.LangUtil;
import com.benzimmer123.koth.util.TextUtil;

public class ViewSchedule extends SubCommand {

	public ViewSchedule(KOTH instance) {
		super(instance, true);
		addAlias("times");
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		Map<ZonedDateTime, KOTHArena> sortedDates = KOTHHandler.getInstance().getUpcomingDates();

		for (DayOfWeek dow : EnumSet.allOf(DayOfWeek.class)) {
			LangUtil.sendMessage(sender, LangUtil.DAY_OF_WEEK.toString().replaceAll("%day%", TextUtil.capitalize(dow.toString())));
			for (ZonedDateTime zdt : sortedDates.keySet()) {
				if (zdt.getDayOfWeek().equals(dow)) {
					String hour = zdt.getHour() <= 9 ? "0" + zdt.getHour() : zdt.getHour() + "";
					String minute = zdt.getMinute() <= 9 ? "0" + zdt.getMinute() : zdt.getMinute() + "";
					if (sortedDates.get(zdt) != null) {
						LangUtil.sendMessage(sender, LangUtil.STARTS_ON.toString().replaceAll("%koth%", sortedDates.get(zdt).getName(true))
								.replaceAll("%hour%", hour).replaceAll("%minute%", minute));
					} else {
						LangUtil.sendMessage(sender, LangUtil.STARTS_ON.toString().replaceAll("%koth%", LangUtil.RANDOM_SCHEDULE.toString())
								.replaceAll("%hour%", hour).replaceAll("%minute%", minute));
					}
				}
			}
		}

		return true;
	}

	@Override
	public boolean validArgumentLength(int length) {
		if (length == 1) {
			return true;
		}
		return false;
	}

	@Override
	public String getHelp() {
		return "/koth times";
	}

	@Override
	public String getPermission() {
		return "KOTH.VIEWTIME";
	}

	@Override
	public String getDescription() {
		return "View the upcoming KOTH schedule.";
	}
}