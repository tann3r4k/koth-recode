package com.benzimmer123.koth.cmds.subcommands;

import java.util.Random;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.api.objects.KOTHArena;
import com.benzimmer123.koth.api.objects.KOTHPlayer;
import com.benzimmer123.koth.cmds.rootcommand.SubCommand;
import com.benzimmer123.koth.handlers.KOTHHandler;
import com.benzimmer123.koth.util.LangUtil;
import com.benzimmer123.koth.util.TimeUtil;

public class Start extends SubCommand {

	public Start(KOTH instance) {
		super(instance, true);
		addAlias("start");
		addAlias("begin");
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		boolean anonymous = false;

		if (KOTH.getInstance().getConfig().getBoolean("KOTH_POINTS_SYSTEM.ENABLED")) {
			if (KOTH.getInstance().getConfig().getBoolean("KOTH_POINTS_SYSTEM.MAX_POINTS") && KOTH.getInstance().getConfig().getBoolean(
					"KOTH_POINTS_SYSTEM.MAX_RUNTIME")) {
				if (args.length == 5 && args[4].equalsIgnoreCase("anon")) {
					anonymous = true;
				}
			} else {
				if (args.length == 4 && args[3].equalsIgnoreCase("anon")) {
					anonymous = true;
				}
			}
		} else {
			if ((args.length >= 4 && args[3].equalsIgnoreCase("anon")) || (args.length >= 5 && args[4] != null)) {
				anonymous = true;
			}
		}

		if (anonymous && !sender.hasPermission(getAnonPermission()) && !sender.hasPermission("KOTH.*") && !sender.isOp()) {
			LangUtil.sendMessage(sender, LangUtil.NO_PERMISSION.toString());
			return false;
		}

		KOTHArena koth;

		if (!args[0].equalsIgnoreCase("-rand") && !args[0].equalsIgnoreCase("-random")) {
			koth = KOTH.getInstance().getKOTHManager().getKOTHFromString(args[0]);

			if (koth == null) {
				LangUtil.sendMessage(sender, LangUtil.INVALID_KOTH.toString().toString());
				return false;
			}
		} else {
			if (KOTHHandler.getInstance().getKOTHS().isEmpty()) {
				LangUtil.sendMessage(sender, LangUtil.INVALID_KOTH.toString().toString());
				return false;
			}

			koth = KOTHHandler.getInstance().getKOTHS().get(new Random().nextInt(KOTHHandler.getInstance().getKOTHS().size()));
		}

		int time = 1;

		if (!KOTH.getInstance().getConfig().getBoolean("KOTH_POINTS_SYSTEM.ENABLED")) {
			try {
				time = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				time = 0;
			}

			if (time < 1) {
				LangUtil.sendMessage(sender, LangUtil.INVALID_LENGTH.toString().toString());
				return false;
			}
		}

		int maxruntime = 0;
		int maxpoints = 0;

		if (KOTH.getInstance().getConfig().getBoolean("KOTH_POINTS_SYSTEM.ENABLED")) {
			if (KOTH.getInstance().getConfig().getBoolean("KOTH_POINTS_SYSTEM.MAX_POINTS") && KOTH.getInstance().getConfig().getBoolean(
					"KOTH_POINTS_SYSTEM.MAX_RUNTIME")) {
				try {
					maxpoints = Integer.parseInt(args[1]);
				} catch (NumberFormatException e) {
					maxpoints = 0;
				}
				
				try {
					maxruntime = Integer.parseInt(args[2]);
				} catch (NumberFormatException e) {
					maxruntime = 0;
				}
			} else if (KOTH.getInstance().getConfig().getBoolean("KOTH_POINTS_SYSTEM.MAX_POINTS")) {
				try {
					maxpoints = Integer.parseInt(args[1]);
				} catch (NumberFormatException e) {
					maxpoints = 0;
				}
			} else {
				try {
					maxruntime = Integer.parseInt(args[1]);
				} catch (NumberFormatException e) {
					maxruntime = 0;
				}
			}
		} else {
			if (args.length >= 3 && !args[2].equalsIgnoreCase("anon")) {
				try {
					maxruntime = Integer.parseInt(args[2]);
				} catch (NumberFormatException e) {
					maxruntime = 0;
				}
			}
		}

		if (maxruntime == 0 && maxpoints == 0 && KOTH.getInstance().getConfig().getBoolean("KOTH_POINTS_SYSTEM.ENABLED")) {
			if (KOTH.getInstance().getConfig().getBoolean("KOTH_POINTS_SYSTEM.MAX_POINTS")) {
				LangUtil.sendMessage(sender, LangUtil.MUST_SPECIFY_MAXPOINTS.toString());
			} else {
				LangUtil.sendMessage(sender, LangUtil.MUST_SPECIFY_RUNTIME.toString());
			}
			return false;
		}

		boolean consoleSender = sender instanceof ConsoleCommandSender;

		if (KOTH.getInstance().getConfig().getBoolean("PLAYER_COOLDOWN.USE_COOLDOWN") && !consoleSender) {
			Player player = (Player) sender;
			KOTHPlayer kothPlayer = KOTHHandler.getInstance().getKOTHPlayer(player);
			if (!sender.hasPermission("KOTH.COOLDOWNBYPASS") && !sender.hasPermission("KOTH.*") && !sender.isOp()) {
				if (kothPlayer.hasKOTHCooldown()) {
					long difference = kothPlayer.getKOTHCooldown() - System.currentTimeMillis();
					int total = (int) difference / 1000;
					String cooldownTime = new TimeUtil((int) total).formatTime();
					LangUtil.sendMessage(player, LangUtil.COOLDOWN.toString().replaceAll("%timeleft%", cooldownTime));
					return false;
				} else {
					long cooldown = KOTH.getInstance().getConfig().getInt("PLAYER_COOLDOWN.COOLDOWN_TIME") * 1000;
					long endTime = System.currentTimeMillis() + cooldown;
					kothPlayer.setKOTHCooldown(endTime);
				}
			}
		}

		if (consoleSender)
			KOTH.getInstance().getKOTHManager().callTask(koth, time, null, anonymous, maxruntime, maxpoints);
		else
			KOTH.getInstance().getKOTHManager().callTask(koth, time, (Player) sender, anonymous, maxruntime, maxpoints);

		return true;
	}

	@Override
	public boolean validArgumentLength(int length) {
		if (KOTH.getInstance().getConfig().getBoolean("KOTH_POINTS_SYSTEM.ENABLED")) {
			if (KOTH.getInstance().getConfig().getBoolean("KOTH_POINTS_SYSTEM.MAX_POINTS") && KOTH.getInstance().getConfig().getBoolean(
					"KOTH_POINTS_SYSTEM.MAX_RUNTIME")) {
				if (length == 4 || length == 5) {
					return true;
				}
				return false;
			} else {
				if (length == 3 || length == 4) {
					return true;
				}
			}
			return false;
		}
		if (length >= 3 && length <= 5) {
			return true;
		}
		return false;
	}

	@Override
	public String getHelp() {
		if (KOTH.getInstance().getConfig().getBoolean("KOTH_POINTS_SYSTEM.ENABLED")) {
			if (KOTH.getInstance().getConfig().getBoolean("KOTH_POINTS_SYSTEM.MAX_POINTS") && KOTH.getInstance().getConfig().getBoolean(
					"KOTH_POINTS_SYSTEM.MAX_RUNTIME")) {
				return "/koth start <koth> <MAX_POINTS> <MAX_RUN_TIME> [anon]";
			} else if (KOTH.getInstance().getConfig().getBoolean("KOTH_POINTS_SYSTEM.MAX_POINTS")) {
				return "/koth start <koth> <MAX_POINTS> [anon]";
			}
			return "/koth start <KOTH> <MAX_RUN_TIME> [anon]";
		}
		return "/koth start <KOTH> <SECONDS> [maxruntime] [anon]";
	}

	@Override
	public String getPermission() {
		return "KOTH.START";
	}

	public String getAnonPermission() {
		return "KOTH.START.ANON";
	}

	@Override
	public String getDescription() {
		return "Start a created KOTH.";
	}
}
