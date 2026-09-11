package com.benzimmer123.koth.cmds.subcommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.api.objects.KOTHArena;
import com.benzimmer123.koth.cmds.rootcommand.SubCommand;
import com.benzimmer123.koth.util.LangUtil;

public class Teleport extends SubCommand {

	public Teleport(KOTH instance) {
		super(instance, false);
		addAlias("teleport");
		addAlias("tp");
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		KOTHArena koth = KOTH.getInstance().getKOTHManager().getKOTHFromString(args[0]);

		if (koth == null) {
			LangUtil.sendMessage(sender, LangUtil.INVALID_KOTH.toString());
			return false;
		}

		player.teleport(koth.getKOTHLocation().getLocation1().add(0, 2, 0));
		LangUtil.sendMessage(sender, LangUtil.TELEPORTED.toString().replaceAll("%koth%", args[0]));
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
		return "/koth tp <KOTH>";
	}

	@Override
	public String getPermission() {
		return "KOTH.TP";
	}

	@Override
	public String getDescription() {
		return "Teleport to a KOTH zone.";
	}
}
