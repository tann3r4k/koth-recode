package com.benzimmer123.koth.cmds.subcommands;

import org.bukkit.command.CommandSender;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.api.objects.KOTHArena;
import com.benzimmer123.koth.cmds.rootcommand.SubCommand;
import com.benzimmer123.koth.util.LangUtil;

public class ClearLoot extends SubCommand {

	public ClearLoot(KOTH instance) {
		super(instance, true);
		addAlias("clearloot");
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		KOTHArena koth = KOTH.getInstance().getKOTHManager().getKOTHFromString(args[0]);

		if (koth == null) {
			LangUtil.sendMessage(sender, LangUtil.INVALID_KOTH.toString());
			return false;
		}

		koth.getKOTHLoot().clearLootItems();
		koth.save();

		LangUtil.sendMessage(sender, LangUtil.CLEARED_LOOT.toString().replaceAll("%koth%", args[0]));
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
		return "/koth clearloot <koth>";
	}

	@Override
	public String getPermission() {
		return "KOTH.CLEARLOOT";
	}

	@Override
	public String getDescription() {
		return "Clear all KOTH loot for a specified KOTH.";
	}
}
