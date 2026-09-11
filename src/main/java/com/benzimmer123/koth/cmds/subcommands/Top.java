package com.benzimmer123.koth.cmds.subcommands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.CommandSender;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.cmds.rootcommand.SubCommand;
import com.benzimmer123.koth.util.LangUtil;

public class Top extends SubCommand {

	public Top(KOTH instance) {
		super(instance, true);
		addAlias("top");
	}

	private final List<String> validTeamEntries = Arrays.asList("TEAM", "FAC", "FACTION", "ISLAND", "F", "T");
	private final List<String> validPlayerEntries = Arrays.asList("PLAYER", "P", "MEMBER", "ONLINE");

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {		
		int i;

		if (args.length > 0) {
			try {
				i = Integer.parseInt(args[0]);
			} catch (Exception e) {
				LangUtil.sendMessage(sender, LangUtil.INVALID_PAGE.toString());
				return false;
			}
		} else {
			i = 1;
		}

		String type;

		if (args.length > 1) {
			if (validTeamEntries.contains(args[1].toUpperCase()) || validPlayerEntries.contains(args[1].toUpperCase())) {
				type = args[1].toUpperCase();
			} else {
				LangUtil.sendMessage(sender, LangUtil.INVALID_TYPE.toString().replaceAll("%validtypes%", "TEAM, PLAYER"));
				type = "TEAM";
			}
		} else {
			type = plugin.getConfig().getString("KOTH_TOP.DEFAULT_TYPE");
		}

		if (type.equalsIgnoreCase("TEAM") || type.equalsIgnoreCase("T")) {
			KOTH.getInstance().getTopManager().listTeamsPage(sender, i);
		} else {
			KOTH.getInstance().getTopManager().listPlayersPage(sender, i);
		}
		
		return false;
	}

	@Override
	public boolean validArgumentLength(int length) {
		if (length == 1 || length == 2 || length == 3) {
			return true;
		}
		return false;
	}

	@Override
	public String getHelp() {
		return "/koth top [page] [PLAYER/TEAM]";
	}

	@Override
	public String getPermission() {
		return "KOTH.TOP";
	}

	@Override
	public String getDescription() {
		return "View the top cappers of KOTH.";
	}

}
