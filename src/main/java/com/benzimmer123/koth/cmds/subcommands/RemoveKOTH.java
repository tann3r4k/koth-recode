package com.benzimmer123.koth.cmds.subcommands;

import java.io.File;

import org.bukkit.command.CommandSender;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.api.objects.KOTHArena;
import com.benzimmer123.koth.cmds.rootcommand.SubCommand;
import com.benzimmer123.koth.handlers.KOTHHandler;
import com.benzimmer123.koth.handlers.ThreadHandler;
import com.benzimmer123.koth.obj.koth.serial.MemoryKOTHArena;
import com.benzimmer123.koth.storage.GsonStorage;
import com.benzimmer123.koth.util.LangUtil;

public class RemoveKOTH extends SubCommand {

	public RemoveKOTH(KOTH instance) {
		super(instance, true);
		addAlias("delete");
		addAlias("remove");
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		KOTHArena koth = KOTH.getInstance().getKOTHManager().getKOTHFromString(args[0]);

		if (koth == null) {
			LangUtil.sendMessage(sender, LangUtil.INVALID_KOTH.toString());
			return false;
		}

		if (koth.isActive()) {
			LangUtil.sendMessage(sender, LangUtil.KOTH_CURRENTLY_ACTIVE.toString());
			return false;
		}

		File kothFile = new File(plugin.getDataFolder() + "/koths/" + koth.getName(false).toLowerCase() + ".json");

		if (kothFile.exists()) {
			koth = GsonStorage.deserialize(MemoryKOTHArena.class, kothFile.getPath(), "koth");
			kothFile.delete();
		}

		if (koth != null) {
			if (ThreadHandler.getInstance().getThreadByID(koth.getKOTHDetails().getAsyncTaskID()) != null) {
				ThreadHandler.getInstance().getThreadByID(koth.getKOTHDetails().getAsyncTaskID()).cancel();
			}

			KOTHHandler.getInstance().removeKOTH(koth.getName(false).toLowerCase());
		}

		LangUtil.sendMessage(sender, LangUtil.REMOVED_KOTH.toString().replaceAll("%koth%", args[0]));
		return true;
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
		return "/koth remove <KOTH>";
	}

	@Override
	public String getPermission() {
		return "KOTH.REMOVE";
	}

	@Override
	public String getDescription() {
		return "Remove a currently created KOTH zone.";
	}
}
