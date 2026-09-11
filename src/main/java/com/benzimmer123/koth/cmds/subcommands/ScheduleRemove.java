package com.benzimmer123.koth.cmds.subcommands;

import org.bukkit.command.CommandSender;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.api.objects.KOTHArena;
import com.benzimmer123.koth.api.objects.Schedule;
import com.benzimmer123.koth.cmds.rootcommand.SubCommand;
import com.benzimmer123.koth.handlers.RandomScheduleHandler;
import com.benzimmer123.koth.util.LangUtil;

public class ScheduleRemove extends SubCommand {

	public ScheduleRemove(KOTH instance) {
		super(instance, true);
		addAlias("removeschedule");
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

		int id;

		try {
			id = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			id = -1;
		}

		if (id == -1) {
			LangUtil.sendMessage(sender, LangUtil.INVALID_ID.toString());
			return false;
		}

		if (args[0].equalsIgnoreCase("-rand") || args[0].equalsIgnoreCase("-random")) {
			Schedule schedule = RandomScheduleHandler.getInstance().getSchedule().getSchedule(id);

			if (schedule == null) {
				LangUtil.sendMessage(sender, LangUtil.INVALID_ID.toString());
				return false;
			}

			RandomScheduleHandler.getInstance().getSchedule().remove(schedule);
			LangUtil.sendMessage(sender, LangUtil.REMOVED_SCHEDULE.toString().replaceAll("%id%", id + ""));
		} else {
			Schedule schedule = koth.getKOTHScheduler().getSchedule(id);

			if (schedule == null) {
				LangUtil.sendMessage(sender, LangUtil.INVALID_ID.toString());
				return false;
			}

			koth.getKOTHScheduler().remove(schedule);
			koth.save();
			LangUtil.sendMessage(sender, LangUtil.REMOVED_SCHEDULE.toString().replaceAll("%id%", id + ""));
		}
		return true;
	}

	@Override
	public boolean validArgumentLength(int length) {
		if (length == 3) {
			return true;
		}
		return false;
	}

	@Override
	public String getHelp() {
		return "/koth removeschedule <KOTH> <ID>";
	}

	@Override
	public String getPermission() {
		return "KOTH.REMOVESCHEDULE";
	}

	@Override
	public String getDescription() {
		return "Remove a currently scheduled KOTH.";
	}
}
