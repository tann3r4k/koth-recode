package com.benzimmer123.koth.cmds.subcommands;

import org.bukkit.command.CommandSender;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.api.objects.KOTHArena;
import com.benzimmer123.koth.cmds.rootcommand.SubCommand;
import com.benzimmer123.koth.handlers.KOTHHandler;
import com.benzimmer123.koth.util.LangUtil;

public class ListKOTHs extends SubCommand {

	public ListKOTHs(KOTH instance) {
		super(instance, true);
		addAlias("list");
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		LangUtil.sendMessage(sender, LangUtil.LIST_KOTHS_TITLE.toString());

		for (KOTHArena koth : KOTHHandler.getInstance().getKOTHS()) {
			LangUtil.sendMessage(sender, LangUtil.LIST_KOTHS_ENTRY.toString().replaceAll("%koth%", koth.getName(true)));
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
		return "/koth list";
	}

	@Override
	public String getPermission() {
		return "KOTH.LISTKOTHS";
	}

	@Override
	public String getDescription() {
		return "View all the currently created KOTH zones.";
	}
}
