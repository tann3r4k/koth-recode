package com.benzimmer123.koth.cmds.subcommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.api.objects.KOTHPlayer;
import com.benzimmer123.koth.cmds.rootcommand.SubCommand;
import com.benzimmer123.koth.handlers.KOTHHandler;
import com.benzimmer123.koth.util.LangUtil;

public class Bypass extends SubCommand {

	public Bypass(KOTH instance) {
		super(instance, false);
		addAlias("bypass");
		addAlias("admin");
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		KOTHPlayer kothPlayer = KOTHHandler.getInstance().getKOTHPlayer(player);
		boolean bypassMode = kothPlayer.inBypassMode();
		String bypass = bypassMode ? "disabled" : "enabled";

		if (bypassMode) {
			kothPlayer.setBypassMode(false);
		} else {
			kothPlayer.setBypassMode(true);
		}

		LangUtil.sendMessage(sender, LangUtil.BYPASS_MODE.toString().replaceAll("%toggle%", bypass));
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
		return "/koth bypass";
	}

	@Override
	public String getPermission() {
		return "KOTH.BYPASS";
	}

	@Override
	public String getDescription() {
		return "Enter KOTH bypass mode to not allow yourself to capture the KOTH.";
	}
}
