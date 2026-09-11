package com.benzimmer123.koth.cmds.subcommands;

import org.bukkit.command.CommandSender;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.cmds.rootcommand.SubCommand;
import com.benzimmer123.koth.util.LangUtil;

public class Version extends SubCommand {

	public Version(KOTH instance) {
		super(instance, true);
		addAlias("v");
		addAlias("version");
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		LangUtil.sendMessage(sender, LangUtil.VERSION.toString().replaceAll("%version%", plugin.getDescription().getVersion()));
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
		return "/koth version";
	}

	@Override
	public String getPermission() {
		return "KOTH.VERSION";
	}

	@Override
	public String getDescription() {
		return "View your current KOTH version.";
	}
}
