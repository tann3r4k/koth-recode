package com.benzimmer123.koth.cmds.subcommands;

import java.time.ZonedDateTime;
import java.util.List;

import org.bukkit.command.CommandSender;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.api.objects.KOTHArena;
import com.benzimmer123.koth.cmds.rootcommand.SubCommand;
import com.benzimmer123.koth.handlers.KOTHHandler;
import com.benzimmer123.koth.util.DateUtil;
import com.benzimmer123.koth.util.LangUtil;

public class NextKoth extends SubCommand {

	public NextKoth(KOTH instance) {
		super(instance, true);
		addAlias("next");
		addAlias("nextkoth");
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		List<KOTHArena> koths = KOTHHandler.getInstance().getKOTHS();

		if (koths.isEmpty())
			LangUtil.sendMessage(sender, LangUtil.NO_KOTH.toString());

		ZonedDateTime zdt = DateUtil.getNextStart();

		if (zdt == null) {
			LangUtil.sendMessage(sender, LangUtil.NO_SCHEDULE.toString());
			return false;
		}

		if (KOTH.getInstance().getConfig().getBoolean("KOTH_NEXT_COUNTDOWN")) {
			LangUtil.sendMessage(sender, DateUtil.getScheduleCountdown());
		} else {
			LangUtil.sendMessage(sender, LangUtil.KOTH_NEXT.toString().replaceAll("%month%", zdt.getMonthValue() + "").replaceAll("%day%", zdt.getDayOfMonth()
					+ "").replaceAll("%hour%", zdt.getHour() + "").replaceAll("%minute%", zdt.getMinute() + ""));
		}
		return false;
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
		return "/koth next";
	}

	@Override
	public String getPermission() {
		return "KOTH.NEXT";
	}

	@Override
	public String getDescription() {
		return "View the next upcoming KOTH.";
	}
}
