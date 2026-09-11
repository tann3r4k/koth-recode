package com.benzimmer123.koth.cmds.subcommands;

import org.bukkit.command.CommandSender;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.cmds.rootcommand.SubCommand;
import com.benzimmer123.koth.util.LangUtil;

public class Setup extends SubCommand {

	public Setup(KOTH instance) {
		super(instance, true);
		addAlias("setup");
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		LangUtil.sendMessage(sender, LangUtil.SETUP_INFO.toString());
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
		return "/koth setup";
	}

	@Override
	public String getPermission() {
		return "KOTH.SETUP";
	}

	@Override
	public String getDescription() {
		return "View the steps to setup a koth successfully.";
	}
}
