package com.benzimmer123.koth.cmds.subcommands;

import java.util.List;

import org.bukkit.command.CommandSender;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.api.objects.KOTHArena;
import com.benzimmer123.koth.cmds.rootcommand.SubCommand;
import com.benzimmer123.koth.util.LangUtil;
import com.benzimmer123.koth.util.TimeUtil;
import com.google.common.collect.Lists;

public class TimesList extends SubCommand {

	public TimesList(KOTH instance) {
		super(instance, true);
		addAlias("listtimes");
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		KOTHArena koth = KOTH.getInstance().getKOTHManager().getKOTHFromString(args[0]);

		if (koth == null) {
			LangUtil.sendMessage(sender, LangUtil.INVALID_KOTH.toString());
			return false;
		}

		if (koth.getKOTHDetails().getBroadcastTimes().isEmpty()) {
			LangUtil.sendMessage(sender, LangUtil.TIME_NONE_SET.toString().replaceAll("%koth%", args[0]));
			return false;
		}

		List<Integer> times = koth.getKOTHDetails().getBroadcastTimes();

		String timesList = "";
		List<Integer> sortedList = Lists.newArrayList();
		List<Integer> tempList = Lists.newArrayList();

		tempList.addAll(times);

		int size = times.size();

		for (int i = 0; i < size; i++) {
			sortedList.add(getHighest(tempList));
			tempList.remove(Integer.valueOf(getHighest(tempList)));
		}

		for (int i = 0; i < sortedList.size(); i++) {
			String formattedTime = new TimeUtil(sortedList.get(i)).formatTime();
			timesList = timesList + formattedTime + ", ";
		}

		LangUtil.sendMessage(sender, LangUtil.TIME_LIST_SET.toString().replaceAll("%times%", timesList).replaceAll("%koth%", args[0]));
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
		return "/koth listtimes <koth>";
	}

	@Override
	public String getPermission() {
		return "KOTH.TIMES";
	}

	@Override
	public String getDescription() {
		return "List all broadcast times.";
	}

	private int getHighest(List<Integer> ints) {
		int highest = 0;

		for (Integer i : ints) {
			if (highest > i)
				continue;
			if (highest == i)
				continue;
			if (highest < i)
				highest = i;

		}

		return highest;
	}
}
