package com.benzimmer123.koth.cmds.subcommands;

import java.io.File;

import org.bukkit.command.CommandSender;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.api.objects.KOTHArena;
import com.benzimmer123.koth.cmds.rootcommand.SubCommand;
import com.benzimmer123.koth.handlers.KOTHHandler;
import com.benzimmer123.koth.util.LangUtil;

public class Reload extends SubCommand {

	public Reload(KOTH instance) {
		super(instance, true);
		addAlias("rl");
		addAlias("reload");
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		for (KOTHArena koth : KOTHHandler.getInstance().getKOTHS()) {
			koth.save();
		}
		KOTH.getInstance().getSettingsManager().setup(plugin);
		if (!new File(plugin.getDataFolder(), "config.yml").exists()) {
			plugin.saveDefaultConfig();
		}
		plugin.reloadConfig();
		LangUtil.sendMessage(sender, LangUtil.RELOAD.toString());
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
		return "/koth reload";
	}

	@Override
	public String getPermission() {
		return "KOTH.RELOAD";
	}

	@Override
	public String getDescription() {
		return "Reload all KOTH configuration files.";
	}
}
