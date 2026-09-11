package com.benzimmer123.koth.cmds.subcommands;

import org.bukkit.command.CommandSender;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.api.objects.KOTHArena;
import com.benzimmer123.koth.cmds.rootcommand.SubCommand;
import com.benzimmer123.koth.util.LangUtil;

public class AutoStart extends SubCommand {

	public AutoStart(KOTH instance) {
		super(instance, true);
		addAlias("autostart");
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		int players;

		try {
			players = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			players = 0;
		}

		if (players == 0) {
			LangUtil.sendMessage(sender, LangUtil.INVALID_PLAYERS.toString());
			return false;
		}

		int capTime;

		try {
			capTime = Integer.parseInt(args[2]);
		} catch (NumberFormatException e) {
			capTime = 0;
		}

		if (capTime == 0) {
			LangUtil.sendMessage(sender, LangUtil.INVALID_CAPTIME.toString());
			return false;
		}

		KOTHArena koth = KOTH.getInstance().getKOTHManager().getKOTHFromString(args[0]);

		if (koth == null) {
			LangUtil.sendMessage(sender, LangUtil.INVALID_KOTH.toString());
			return false;
		}

		LangUtil.sendMessage(sender, LangUtil.ADDED_AUTOSTART.toString().replaceAll("%koth%", args[0]).replaceAll("%players%", players + "")
				.replaceAll("%captime%", capTime + ""));

		koth.getKOTHAutoStart().setAutoStart(players, capTime);
		koth.save();
		return true;
	}

	@Override
	public boolean validArgumentLength(int length) {
		if (length == 4) {
			return true;
		}
		return false;
	}

	@Override
	public String getHelp() {
		return "/koth autostart <KOTH> <PLAYERS> <CAPTURETIME>";
	}

	@Override
	public String getPermission() {
		return "KOTH.AUTOSTART";
	}

	@Override
	public String getDescription() {
		return "Add an automated start when a specific number of players have joined the server.";
	}
}
