package com.benzimmer123.koth.cmds.subcommands;

import org.bukkit.command.CommandSender;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.api.objects.KOTHArena;
import com.benzimmer123.koth.cmds.rootcommand.SubCommand;
import com.benzimmer123.koth.util.LangUtil;

public class Rename extends SubCommand {

	public Rename(KOTH instance) {
		super(instance, true);
		addAlias("rename");
		addAlias("setname");
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		KOTHArena koth = KOTH.getInstance().getKOTHManager().getKOTHFromString(args[0]);

		if (koth == null) {
			LangUtil.sendMessage(sender, LangUtil.INVALID_KOTH.toString());
			return false;
		}

		String newName = "";

		for (int i = 1; i < args.length; i++) {
			newName = newName + args[i] + " ";
		}

		koth.setName(newName);
		koth.save();
		return true;
	}

	@Override
	public String getPermission() {
		return "KOTH.RENAME";
	}

	@Override
	public String getHelp() {
		return "/koth rename <koth> <name>";
	}

	@Override
	public String getDescription() {
		return "Rename a koth.";
	}

	@Override
	public boolean validArgumentLength(int length) {
		if (length >= 3) {
			return true;
		}
		return false;
	}

}
