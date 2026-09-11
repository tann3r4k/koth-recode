package com.benzimmer123.koth.cmds.subcommands;

import org.bukkit.command.CommandSender;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.api.objects.KOTHRegion;
import com.benzimmer123.koth.cmds.rootcommand.SubCommand;
import com.benzimmer123.koth.handlers.RegionHandler;
import com.benzimmer123.koth.util.LangUtil;

public class ListRegions extends SubCommand {

	public ListRegions(KOTH instance) {
		super(instance, true);
		addAlias("listregions");
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		LangUtil.sendMessage(sender, LangUtil.LIST_REGIONS_TITLE.toString());

		for (KOTHRegion region : RegionHandler.getInstance().getRegions()) {
			LangUtil.sendMessage(sender, LangUtil.LIST_REGIONS_ENTRY.toString().replaceAll("%region%", region.getName()));
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
		return "/koth listregions";
	}

	@Override
	public String getPermission() {
		return "KOTH.LISTREGIONS";
	}

	@Override
	public String getDescription() {
		return "List all created custom regions.";
	}
}
