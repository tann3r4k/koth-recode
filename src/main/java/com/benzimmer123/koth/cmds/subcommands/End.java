package com.benzimmer123.koth.cmds.subcommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.api.objects.KOTHArena;
import com.benzimmer123.koth.cmds.rootcommand.SubCommand;
import com.benzimmer123.koth.util.LangUtil;

public class End extends SubCommand {

	public End(KOTH instance) {
		super(instance, true);
		addAlias("end");
		addAlias("stop");
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		KOTHArena koth = KOTH.getInstance().getKOTHManager().getKOTHFromString(args[0]);

		if (koth == null) {
			LangUtil.sendMessage(sender, LangUtil.INVALID_KOTH.toString());
			return false;
		}

		if (!koth.isActive()) {
			LangUtil.sendMessage(sender, LangUtil.KOTH_NOT_ACTIVE.toString());

			return false;
		}

		boolean anonymous = false;

		if (args.length > 2) {
			if (sender.hasPermission(getAnonPermission()) && !sender.isOp() && !sender.hasPermission("KOTH.*")) {
				LangUtil.sendMessage(sender, LangUtil.NO_PERMISSION.toString());
				return false;
			}

			anonymous = true;
		}

		if (sender instanceof Player) {
			koth.disable(sender, !anonymous);
		} else {
			koth.disable(null, !anonymous);
		}

		return true;
	}

	@Override
	public boolean validArgumentLength(int length) {
		if (length == 2 || length == 3) {
			return true;
		}
		return false;
	}

	@Override
	public String getHelp() {
		return "/koth end <KOTH> [anon]";
	}

	@Override
	public String getPermission() {
		return "KOTH.END";
	}

	public String getAnonPermission() {
		return "KOTH.END.ANON";
	}

	@Override
	public String getDescription() {
		return "End a KOTH currently in progress.";
	}
}
