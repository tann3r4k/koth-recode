package com.benzimmer123.koth.cmds.subcommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.api.objects.KOTHPlayer;
import com.benzimmer123.koth.cmds.rootcommand.SubCommand;
import com.benzimmer123.koth.handlers.KOTHHandler;
import com.benzimmer123.koth.util.LangUtil;

public class ToggleScoreboard extends SubCommand {

	public ToggleScoreboard(KOTH instance) {
		super(instance, false);
		addAlias("sb");
		addAlias("scoreboard");
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		KOTHPlayer kothPlayer = KOTHHandler.getInstance().getKOTHPlayer(player);
		boolean toggled = kothPlayer.hasDisabledScoreboard();

		if (toggled) {
			kothPlayer.setDisabledScoreboard(false);
		} else {
			kothPlayer.setDisabledScoreboard(true);
		}

		String toggle = toggled ? "enabled" : "disabled";

		LangUtil.sendMessage(sender, LangUtil.TOGGLED_SCOREBOARD.toString().replaceAll("%toggle%", toggle));
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
		return "/koth sb";
	}

	@Override
	public String getPermission() {
		return "KOTH.TOGGLESB";
	}

	@Override
	public String getDescription() {
		return "Toggle your KOTH scoreboard.";
	}

}
