package com.benzimmer123.koth.cmds.subcommands;

import java.time.DayOfWeek;
import java.util.EnumSet;

import org.bukkit.command.CommandSender;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.api.objects.KOTHArena;
import com.benzimmer123.koth.api.objects.Schedule;
import com.benzimmer123.koth.cmds.rootcommand.SubCommand;
import com.benzimmer123.koth.handlers.RandomScheduleHandler;
import com.benzimmer123.koth.util.LangUtil;
import com.benzimmer123.koth.util.TextUtil;

public class ScheduleTaskIDs extends SubCommand {

	public ScheduleTaskIDs(KOTH instance) {
		super(instance, true);
		addAlias("scheduled");
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		KOTHArena koth = KOTH.getInstance().getKOTHManager().getKOTHFromString(args[0]);

		if (!args[0].equalsIgnoreCase("-rand") && !args[0].equalsIgnoreCase("-random")) {
			if (koth == null) {
				LangUtil.sendMessage(sender, LangUtil.INVALID_KOTH.toString());
				return false;
			}
		}

		EnumSet<DayOfWeek> dows = EnumSet.allOf(DayOfWeek.class);

		if (args[0].equalsIgnoreCase("-rand") || args[0].equalsIgnoreCase("-random")) {
			for (DayOfWeek dow : dows) {
				LangUtil.sendMessage(sender, LangUtil.LIST_KOTH_IDS_TITLE.toString().replaceAll("%day%", TextUtil.capitalize(dow.toString())));
				for (Schedule schedule : RandomScheduleHandler.getInstance().getSchedule().getScheduled()) {
					if (schedule.getDate().getDayOfWeek() == dow) {
						LangUtil.sendMessage(sender, LangUtil.LIST_KOTH_IDS_ENTRY.toString().replaceAll("%id%", "" + schedule.getId()).replaceAll("%hour%",
								schedule.getDate().getHour() + "").replaceAll("%minute%", schedule.getDate().getMinute() + "").replaceAll("%type%",
										schedule.getScheduleType().toString()));
					}
				}
			}
		} else {
			for (DayOfWeek dow : dows) {
				LangUtil.sendMessage(sender, LangUtil.LIST_KOTH_IDS_TITLE.toString().replaceAll("%day%", TextUtil.capitalize(dow.toString())));
				for (Schedule schedule : koth.getKOTHScheduler().getScheduled()) {
					if (schedule.getDate().getDayOfWeek() == dow) {
						LangUtil.sendMessage(sender, LangUtil.LIST_KOTH_IDS_ENTRY.toString().replaceAll("%id%", "" + schedule.getId()).replaceAll("%hour%",
								schedule.getDate().getHour() + "").replaceAll("%minute%", schedule.getDate().getMinute() + "").replaceAll("%type%",
										schedule.getScheduleType().toString()));
					}
				}
			}
		}

		return true;
	}

	@Override
	public boolean validArgumentLength(int length) {
		if (length == 2) {
			return true;
		}
		return false;
	}

	@Override
	public String getHelp() {
		return "/koth scheduled <KOTH>";
	}

	@Override
	public String getPermission() {
		return "KOTH.VIEWIDS";
	}

	@Override
	public String getDescription() {
		return "View a KOTH's scheduled IDs so they can be changed or removed.";
	}
}
