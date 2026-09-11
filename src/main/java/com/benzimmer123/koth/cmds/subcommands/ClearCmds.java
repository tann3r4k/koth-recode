package com.benzimmer123.koth.cmds.subcommands;

import org.bukkit.command.CommandSender;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.api.objects.KOTHArena;
import com.benzimmer123.koth.cmds.rootcommand.SubCommand;
import com.benzimmer123.koth.util.LangUtil;

public class ClearCmds extends SubCommand {

	public ClearCmds(KOTH instance) {
		super(instance, true);
		addAlias("clearcmds");
		addAlias("clearcmd");
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		KOTHArena koth = KOTH.getInstance().getKOTHManager().getKOTHFromString(args[0]);

		if (koth == null) {
			LangUtil.sendMessage(sender, LangUtil.INVALID_KOTH.toString());
			return false;
		}

		koth.getKOTHDetails().getRewardCmds().clear();
		koth.save();

		LangUtil.sendMessage(sender, LangUtil.CLEARED_CMDS.toString().replaceAll("%koth%", args[0]));
		return false;
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
		return "/koth clearcmds <koth>";
	}

	@Override
	public String getPermission() {
		return "KOTH.CLEARCMDS";
	}

	@Override
	public String getDescription() {
		return "Clear a KOTH's reward cmds.";
	}

}
