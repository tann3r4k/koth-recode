package com.benzimmer123.koth.cmds.subcommands;

import org.bukkit.command.CommandSender;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.api.objects.KOTHArena;
import com.benzimmer123.koth.cmds.rootcommand.SubCommand;
import com.benzimmer123.koth.util.LangUtil;

public class AddCmd extends SubCommand {

	public AddCmd(KOTH instance) {
		super(instance, true);
		addAlias("addcmd");
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		KOTHArena koth = KOTH.getInstance().getKOTHManager().getKOTHFromString(args[0]);

		if (koth == null) {
			LangUtil.sendMessage(sender, LangUtil.INVALID_KOTH.toString());
			return false;
		}

		String cmd = "";

		for (int i = 1; i < args.length; i++) {
			cmd = cmd + args[i] + " ";
		}

		koth.getKOTHDetails().addRewardCmd(cmd);
		koth.save();

		LangUtil.sendMessage(sender, LangUtil.ADDED_CMD.toString().replaceAll("%koth%", args[0]).replaceAll("%cmd%", cmd));
		return false;
	}

	@Override
	public boolean validArgumentLength(int length) {
		if (length >= 3) {
			return true;
		}
		return false;
	}

	@Override
	public String getHelp() {
		return "/koth addcmd <koth> <cmd>";
	}

	@Override
	public String getPermission() {
		return "KOTH.ADDCMD";
	}

	@Override
	public String getDescription() {
		return "Add a KOTH reward command.";
	}

}
