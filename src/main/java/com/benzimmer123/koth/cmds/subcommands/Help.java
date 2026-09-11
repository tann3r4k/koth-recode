package com.benzimmer123.koth.cmds.subcommands;

import java.util.List;

import org.bukkit.command.CommandSender;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.cmds.rootcommand.SubCommand;
import com.benzimmer123.koth.util.LangUtil;
import com.google.common.collect.Lists;

public class Help extends SubCommand {

	private final List<SubCommand> commands;

	public Help(KOTH instance, List<SubCommand> commands) {
		super(instance, true);
		this.commands = commands;
		addAlias("help");
	}

	private boolean commandPerPermission = plugin.getConfig().getBoolean("VIEW_COMMAND_PER_PERMISSION");

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		int pageNumber;

		if (args.length == 1) {
			try {
				pageNumber = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				pageNumber = 1;
			}
		} else {
			pageNumber = 1;
		}

		if (pageNumber < 0)
			pageNumber = 1;

		if (plugin.getConfig().getBoolean("CUSTOM_HELP_PAGE.ENABLED")) {
			sender.sendMessage(LangUtil.replaceString(plugin.getConfig().getString("CUSTOM_HELP_PAGE.TITLE")));

			if (!plugin.getConfig().isSet("CUSTOM_HELP_PAGE.PAGES." + pageNumber))
				return false;

			for (String help : plugin.getConfig().getStringList("CUSTOM_HELP_PAGE.PAGES." + pageNumber)) {
				help = KOTH.getInstance().getPlaceholderManager().parsePlaceholders(null, help);
				sender.sendMessage(LangUtil.replaceString(help));
			}
			return false;
		}

		int amountPerPage = 6;
		int maxNumber = pageNumber * amountPerPage;
		int minNumber = maxNumber - amountPerPage;

		while (commands.size() < maxNumber) {
			maxNumber--;
		}

		List<SubCommand> displayedCmds = Lists.newArrayList();
		List<SubCommand> allowedCmds = Lists.newArrayList();
		List<SubCommand> toRemove = Lists.newArrayList();

		for (SubCommand command : commands) {
			if (!command.hasPermission(sender) && commandPerPermission) {
				toRemove.add(command);
			} else {
				allowedCmds.add(command);
			}
		}

		for (int i = minNumber; i < maxNumber; i++) {
			if (allowedCmds.size() > i)
				displayedCmds.add(allowedCmds.get(i));
		}

		for (SubCommand command : toRemove) {
			displayedCmds.remove(command);
		}

		int maxPages = (int) Math.ceil((double) allowedCmds.size() / (double) amountPerPage);

		if (maxPages == 0)
			maxPages = 1;

		if (pageNumber > maxPages) {
			LangUtil.sendMessage(sender, LangUtil.NO_HELP_PAGE.toString().replaceAll("%maxpage%", maxPages + ""));
			return false;
		}

		LangUtil.sendMessage(sender, LangUtil.HELP_PAGE_TITLE.toString().replaceAll("%page%", pageNumber + "").replaceAll("%maxpage%", maxPages + ""));

		for (SubCommand command : displayedCmds) {
			LangUtil.sendMessage(sender,
					LangUtil.HELP_PAGE_ENTRY.toString().replaceAll("%cmd%", command.getHelp()).replaceAll("%desc%", command.getDescription()));
		}

		return false;
	}

	@Override
	public String getHelp() {
		return "/koth help [page]";
	}

	@Override
	public String getPermission() {
		return "KOTH.HELP";
	}

	@Override
	public String getDescription() {
		return "View the help menu.";
	}

	@Override
	public boolean validArgumentLength(int length) {
		return true;
	}

}
