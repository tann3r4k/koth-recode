package com.benzimmer123.koth.cmds.rootcommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.cmds.subcommands.AddCmd;
import com.benzimmer123.koth.cmds.subcommands.AddLoot;
import com.benzimmer123.koth.cmds.subcommands.AutoStart;
import com.benzimmer123.koth.cmds.subcommands.Bypass;
import com.benzimmer123.koth.cmds.subcommands.CPU;
import com.benzimmer123.koth.cmds.subcommands.ClearCmds;
import com.benzimmer123.koth.cmds.subcommands.ClearLoot;
import com.benzimmer123.koth.cmds.subcommands.CreateKoth;
import com.benzimmer123.koth.cmds.subcommands.CreateRegion;
import com.benzimmer123.koth.cmds.subcommands.End;
import com.benzimmer123.koth.cmds.subcommands.Help;
import com.benzimmer123.koth.cmds.subcommands.Key;
import com.benzimmer123.koth.cmds.subcommands.ListKOTHs;
import com.benzimmer123.koth.cmds.subcommands.ListRegions;
import com.benzimmer123.koth.cmds.subcommands.Loot;
import com.benzimmer123.koth.cmds.subcommands.NextKoth;
import com.benzimmer123.koth.cmds.subcommands.ReCreate;
import com.benzimmer123.koth.cmds.subcommands.Reload;
import com.benzimmer123.koth.cmds.subcommands.RemoveAllLootLocations;
import com.benzimmer123.koth.cmds.subcommands.RemoveKOTH;
import com.benzimmer123.koth.cmds.subcommands.RemoveLootLocation;
import com.benzimmer123.koth.cmds.subcommands.RemoveRegion;
import com.benzimmer123.koth.cmds.subcommands.Rename;
import com.benzimmer123.koth.cmds.subcommands.ScheduleDaily;
import com.benzimmer123.koth.cmds.subcommands.ScheduleDate;
import com.benzimmer123.koth.cmds.subcommands.ScheduleRemove;
import com.benzimmer123.koth.cmds.subcommands.ScheduleTaskIDs;
import com.benzimmer123.koth.cmds.subcommands.ScheduleWeekly;
import com.benzimmer123.koth.cmds.subcommands.SetLoot;
import com.benzimmer123.koth.cmds.subcommands.SetLootLocation;
import com.benzimmer123.koth.cmds.subcommands.SetReward;
import com.benzimmer123.koth.cmds.subcommands.Setup;
import com.benzimmer123.koth.cmds.subcommands.Start;
import com.benzimmer123.koth.cmds.subcommands.StartGUI;
import com.benzimmer123.koth.cmds.subcommands.StarterItem;
import com.benzimmer123.koth.cmds.subcommands.Teleport;
import com.benzimmer123.koth.cmds.subcommands.Timer;
import com.benzimmer123.koth.cmds.subcommands.TimesAdd;
import com.benzimmer123.koth.cmds.subcommands.TimesList;
import com.benzimmer123.koth.cmds.subcommands.TimesRemove;
import com.benzimmer123.koth.cmds.subcommands.ToggleScoreboard;
import com.benzimmer123.koth.cmds.subcommands.Top;
import com.benzimmer123.koth.cmds.subcommands.Version;
import com.benzimmer123.koth.cmds.subcommands.ViewSchedule;
import com.benzimmer123.koth.cmds.subcommands.Wand;
import com.benzimmer123.koth.util.LangUtil;
import com.google.common.collect.Lists;

public class RootCommand implements CommandExecutor, TabExecutor {

	private ArrayList<SubCommand> subCommands;

	public RootCommand(KOTH instance) {
		subCommands = Lists.newArrayList();
		this.subCommands.add(new Setup(instance));
		this.subCommands.add(new Start(instance));
		this.subCommands.add(new AutoStart(instance));
		this.subCommands.add(new Bypass(instance));
		this.subCommands.add(new CreateKoth(instance));
		this.subCommands.add(new CreateRegion(instance));
		this.subCommands.add(new End(instance));
		this.subCommands.add(new Key(instance));
		this.subCommands.add(new ListKOTHs(instance));
		this.subCommands.add(new ListRegions(instance));
		this.subCommands.add(new Loot(instance));
		this.subCommands.add(new ReCreate(instance));
		this.subCommands.add(new Reload(instance));
		this.subCommands.add(new RemoveAllLootLocations(instance));
		this.subCommands.add(new RemoveKOTH(instance));
		this.subCommands.add(new RemoveLootLocation(instance));
		this.subCommands.add(new RemoveRegion(instance));
		this.subCommands.add(new ScheduleDaily(instance));
		this.subCommands.add(new ScheduleDate(instance));
		this.subCommands.add(new ScheduleRemove(instance));
		this.subCommands.add(new ScheduleTaskIDs(instance));
		this.subCommands.add(new ScheduleWeekly(instance));
		this.subCommands.add(new AddLoot(instance));
		this.subCommands.add(new ClearLoot(instance));
		this.subCommands.add(new SetLoot(instance));
		this.subCommands.add(new SetLootLocation(instance));
		this.subCommands.add(new SetReward(instance));
		this.subCommands.add(new StarterItem(instance));
		this.subCommands.add(new NextKoth(instance));
		this.subCommands.add(new AddCmd(instance));
		this.subCommands.add(new ClearCmds(instance));
		this.subCommands.add(new StartGUI(instance));
		this.subCommands.add(new Teleport(instance));
		this.subCommands.add(new Timer(instance));
		this.subCommands.add(new TimesAdd(instance));
		this.subCommands.add(new TimesList(instance));
		this.subCommands.add(new TimesRemove(instance));
		this.subCommands.add(new ToggleScoreboard(instance));
		this.subCommands.add(new Top(instance));
		this.subCommands.add(new Version(instance));
		this.subCommands.add(new Rename(instance));
		this.subCommands.add(new ViewSchedule(instance));
		this.subCommands.add(new Wand(instance));
		this.subCommands.add(new CPU(instance));
		this.subCommands.add(new Help(instance, subCommands));
	}

	@Override
	public List<String> onTabComplete(CommandSender paramCommandSender, Command paramCommand, String paramString, String[] paramArrayOfString) {
		if (paramArrayOfString.length == 1) {
			return getSubCommandsAsString();
		}
		return null;
	}

	@Override
	public boolean onCommand(CommandSender paramCommandSender, Command paramCommand, String paramString, String[] paramArrayOfString) {
		if (paramArrayOfString.length >= 1) {
			SubCommand subcommand = null;
			for (SubCommand subcommand1 : this.subCommands) {
				if (subcommand1.getIdentifiers().contains(paramArrayOfString[0].toLowerCase())) {
					subcommand = subcommand1;
					break;
				}
			}
			if (subcommand != null) {
				if (!subcommand.isConsoleAllowed() && paramCommandSender instanceof ConsoleCommandSender) {
					LangUtil.sendMessage(paramCommandSender, LangUtil.IN_GAME_ONLY.toString());
					return true;
				}

				if (!subcommand.validArgumentLength(paramArrayOfString.length)) {
					subcommand.performHelp(paramCommandSender);
					return true;
				}

				if (paramCommandSender instanceof Player) {
					Player player = (Player) paramCommandSender;
					if (passChecks(subcommand, player))
						return subcommand.performCommand((CommandSender) player, Arrays.<String> copyOfRange(paramArrayOfString, 1,
								paramArrayOfString.length));
					return true;
				}
				subcommand.performCommand(paramCommandSender, Arrays.<String> copyOfRange(paramArrayOfString, 1, paramArrayOfString.length));
				return true;
			}
		}
		SubCommand subcommand = (SubCommand) Objects.requireNonNull(this.subCommands.stream().filter(paramSubcommand -> paramSubcommand
				.getIdentifiers().contains("help")).findAny().orElse(null));
		((SubCommand) subcommand).performCommand(paramCommandSender, paramArrayOfString);
		return true;
	}

	private List<String> getSubCommandsAsString() {
		List<String> commandNames = Lists.newArrayList();
		for (SubCommand subcommand : this.subCommands) {
			commandNames.addAll(subcommand.getIdentifiers());
		}
		return commandNames;
	}

	private boolean passChecks(SubCommand command, Player player) {
		boolean hasPermission = command.hasPermission(player);
		if (!hasPermission)
			LangUtil.sendMessage(player, LangUtil.NO_PERMISSION.toString());
		return hasPermission;
	}

	public int getCommandSize() {
		return subCommands.size();
	}
}