package com.benzimmer123.koth.cmds.subcommands;

import org.bukkit.command.CommandSender;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.api.objects.KOTHArena;
import com.benzimmer123.koth.cmds.rootcommand.SubCommand;
import com.benzimmer123.koth.util.LangUtil;

public class RemoveAllLootLocations extends SubCommand {

	public RemoveAllLootLocations(KOTH instance) {
		super(instance, true);
		addAlias("clearlootlocations");
		addAlias("removealllootlocations");
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		KOTHArena koth = KOTH.getInstance().getKOTHManager().getKOTHFromString(args[0]);

		if (koth == null) {
			LangUtil.sendMessage(sender, LangUtil.INVALID_KOTH.toString());
			return false;
		}

		koth.getKOTHLoot().clearLootLocations();

		LangUtil.sendMessage(sender, LangUtil.REMOVED_ALL_LOOT_LOCATIONS.toString().replaceAll("%koth%", args[0]));
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
		return "/koth clearlootlocations <KOTH>";
	}

	@Override
	public String getPermission() {
		return "KOTH.REMOVELOOTLOCATION";
	}

	@Override
	public String getDescription() {
		return "Clears all the current loot locations for a KOTH.";
	}
}
