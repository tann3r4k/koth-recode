package com.benzimmer123.koth.cmds.subcommands;

import java.util.List;

import org.bukkit.command.CommandSender;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.api.objects.KOTHArena;
import com.benzimmer123.koth.cmds.rootcommand.SubCommand;
import com.benzimmer123.koth.util.LangUtil;

public class Timer extends SubCommand {

	public Timer(KOTH instance) {
		super(instance, true);
		addAlias("timer");
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		List<KOTHArena> active = KOTH.getInstance().getKOTHManager().getActiveKOTHs();

		if (active.isEmpty()) {
			LangUtil.sendMessage(sender, LangUtil.KOTH_NOT_ACTIVE.toString());
			return false;
		}

		for (KOTHArena koth : active) {
			LangUtil.sendMessage(
					sender,
					LangUtil.KOTH_TIMER_FORMAT.toString().replaceAll("%koth%", koth.getName(true))
							.replaceAll("%time%", koth.getTimeRemainingAsString()));
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
		return "/koth timer";
	}

	@Override
	public String getPermission() {
		return "KOTH.TIMER";
	}

	@Override
	public String getDescription() {
		return "List the remaining time of the currently hosted KOTHs.";
	}
}
