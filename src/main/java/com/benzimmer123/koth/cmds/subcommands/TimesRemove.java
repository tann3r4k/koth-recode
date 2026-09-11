package com.benzimmer123.koth.cmds.subcommands;

import java.util.List;

import org.bukkit.command.CommandSender;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.api.objects.KOTHArena;
import com.benzimmer123.koth.cmds.rootcommand.SubCommand;
import com.benzimmer123.koth.util.LangUtil;
import com.benzimmer123.koth.util.TimeUtil;

public class TimesRemove extends SubCommand {

	public TimesRemove(KOTH instance) {
		super(instance, true);
		addAlias("removetime");
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		KOTHArena koth = KOTH.getInstance().getKOTHManager().getKOTHFromString(args[0]);

		if (koth == null) {
			LangUtil.sendMessage(sender, LangUtil.INVALID_KOTH.toString());
			return false;
		}

		int time;

		try {
			time = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			time = 0;
		}

		if (time == 0) {
			LangUtil.sendMessage(sender, LangUtil.INVALID_NUMBER.toString());
			return false;
		}

		List<Integer> times = koth.getKOTHDetails().getBroadcastTimes();

		String formattedTime = new TimeUtil(time).formatTime();

		if (!times.contains(time)) {
			LangUtil.sendMessage(sender, LangUtil.TIME_NOT_SET.toString().replaceAll("%time%", formattedTime).replaceAll("%koth%", args[0]));
			return false;
		}

		times.remove(Integer.valueOf(time));
		koth.getKOTHDetails().setBroadcastTimes(times);
		koth.save();
		LangUtil.sendMessage(sender, LangUtil.TIME_REMOVED.toString().replaceAll("%time%", formattedTime).replaceAll("%koth%", args[0]));
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
		return "/koth removetime <koth> <seconds>";
	}

	@Override
	public String getPermission() {
		return "KOTH.TIMES";
	}

	@Override
	public String getDescription() {
		return "Remove a broadcast time.";
	}
}
