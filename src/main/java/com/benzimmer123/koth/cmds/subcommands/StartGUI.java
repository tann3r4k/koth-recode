package com.benzimmer123.koth.cmds.subcommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.cmds.rootcommand.SubCommand;
import com.benzimmer123.koth.gui.OpeningGUI;

public class StartGUI extends SubCommand {

	public StartGUI(KOTH instance) {
		super(instance, false);
		addAlias("startgui");
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		new OpeningGUI(plugin).openGUI(player, true);
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
		return "/koth startgui";
	}

	@Override
	public String getPermission() {
		return "KOTH.GUI";
	}

	@Override
	public String getDescription() {
		return "Open a GUI to manage all current KOTHs.";
	}
}
