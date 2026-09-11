package com.benzimmer123.koth.cmds.subcommands;

import org.bukkit.command.CommandSender;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.cmds.rootcommand.SubCommand;
import com.benzimmer123.koth.handlers.ThreadHandler;
import com.benzimmer123.koth.util.LangUtil;

public class CPU extends SubCommand {

	public CPU(KOTH instance) {
		super(instance, true);
		addAlias("cpu");
		addAlias("lag");
		addAlias("usage");
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		int amountOfThreads = ThreadHandler.getInstance().getActiveThreadCount();
		LangUtil.sendMessage(sender, LangUtil.CPU_INFO.toString().replaceAll("%threads%", amountOfThreads + ""));
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
		return "/koth cpu";
	}

	@Override
	public String getPermission() {
		return "KOTH.CPU";
	}

	@Override
	public String getDescription() {
		return "View active threads and data used in plugin.";
	}
}
