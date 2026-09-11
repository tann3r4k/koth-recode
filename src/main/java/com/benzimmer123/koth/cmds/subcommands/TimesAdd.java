package com.benzimmer123.koth.cmds.subcommands;

import java.util.List;

import org.bukkit.command.CommandSender;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.api.objects.KOTHArena;
import com.benzimmer123.koth.cmds.rootcommand.SubCommand;
import com.benzimmer123.koth.util.LangUtil;
import com.benzimmer123.koth.util.TimeUtil;

public class TimesAdd extends SubCommand {

	public TimesAdd(KOTH instance) {
		super(instance, true);
		addAlias("addtime");
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

		if (times.contains(time)) {
			LangUtil.sendMessage(sender, LangUtil.TIME_ALREADY_SET.toString().replaceAll("%koth%", args[0]).replaceAll("%time%", formattedTime));
			return false;
		}

		times.add(time);
		koth.getKOTHDetails().setBroadcastTimes(times);
		koth.save();
		LangUtil.sendMessage(sender, LangUtil.TIME_ADDED.toString().replaceAll("%koth%", args[0]).replaceAll("%time%", formattedTime));
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
		return "/koth addtime <koth> <seconds>";
	}

	@Override
	public String getPermission() {
		return "KOTH.TIMES";
	}

	@Override
	public String getDescription() {
		return "Add a KOTH broadcast time.";
	}
}
