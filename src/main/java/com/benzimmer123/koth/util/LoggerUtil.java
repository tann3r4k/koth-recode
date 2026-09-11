package com.benzimmer123.koth.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.handlers.KOTHHandler;

public class LoggerUtil {

	private static final CommandSender LOGGER = Bukkit.getServer().getConsoleSender();

	public LoggerUtil(String hasFoundUpdate, int amountOfCommands, long startTimeStamp) {
		String PLUGIN_NAME = "KOTH";
		String PLUGIN_VERSION = KOTH.getInstance().getDescription().getVersion();
		String PLUGIN_AUTHOR = "Benzimmer";
		String PLUGIN_DISCORD = "https://discord.gg/fuffySb";

		boolean CHECK_FOR_UPDATES = KOTH.getInstance().getConfig().getBoolean("SEARCH_FOR_UPDATES", true);

		ServerVersionUtil SPIGOT_VERSION = ServerVersionUtil.getServerVersion();

		String LINE_SEPARTATOR = ChatColor.BLUE + "===================================" + ChatColor.GRAY;
		String CHAR_SEPARTATOR = ChatColor.GRAY + ": " + ChatColor.WHITE;
		String PREFIX = ChatColor.GRAY + "- " + ChatColor.BLUE;
		String LINE_END = ChatColor.GRAY + "";

		long START_UP_TIME = System.currentTimeMillis() - startTimeStamp;

		LOGGER.sendMessage(LINE_SEPARTATOR);
		LOGGER.sendMessage("");
		LOGGER.sendMessage(PREFIX + "Plugin" + CHAR_SEPARTATOR + PLUGIN_NAME + LINE_END);
		LOGGER.sendMessage(PREFIX + "Version" + CHAR_SEPARTATOR + PLUGIN_VERSION + LINE_END);
		LOGGER.sendMessage(PREFIX + "Server Version" + CHAR_SEPARTATOR + SPIGOT_VERSION + LINE_END);
		LOGGER.sendMessage(PREFIX + "Author" + CHAR_SEPARTATOR + PLUGIN_AUTHOR + LINE_END);
		LOGGER.sendMessage(PREFIX + "Discord" + CHAR_SEPARTATOR + PLUGIN_DISCORD + LINE_END);
		LOGGER.sendMessage("");
		LOGGER.sendMessage(PREFIX + "Searching for plugin updates..." + LINE_END);

		if (CHECK_FOR_UPDATES) {
			LOGGER.sendMessage(hasFoundUpdate != null ? CHAR_SEPARTATOR + ChatColor.GREEN + "A new update has been found for this plugin - "
					+ hasFoundUpdate + LINE_END : CHAR_SEPARTATOR + ChatColor.GREEN + "Latest plugin version found." + LINE_END);
		}

		if (Bukkit.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
			LOGGER.sendMessage("");
			LOGGER.sendMessage(PREFIX + "Successfully registered placeholders with PlaceholderAPI." + LINE_END);
		}

		if (Bukkit.getServer().getPluginManager().isPluginEnabled("MVdWPlaceholderAPI")) {
			LOGGER.sendMessage("");
			LOGGER.sendMessage(PREFIX + "Successfully registered placeholders with MVdWPlaceholderAPI." + LINE_END);
		}

		LOGGER.sendMessage("");
		LOGGER.sendMessage(PREFIX + "Loaded " + ChatColor.GREEN + KOTHHandler.getInstance().getKOTHS().size() + ChatColor.BLUE + " koths." + LINE_END);
		LOGGER.sendMessage(PREFIX + "Loaded " + ChatColor.GREEN + amountOfCommands + ChatColor.BLUE + " commands." + LINE_END);
		LOGGER.sendMessage("");
		LOGGER.sendMessage(PREFIX + "Enabled plugin in " + ChatColor.GREEN + START_UP_TIME + ChatColor.BLUE + " ms" + LINE_END);
		LOGGER.sendMessage("");
		LOGGER.sendMessage(LINE_SEPARTATOR);
	}

	public static void info(String message) {
		LOGGER.sendMessage(message + ChatColor.GRAY);
	}

	public static void success(String message) {
		LOGGER.sendMessage(ChatColor.GREEN + message + ChatColor.GRAY);
	}

	public static void warning(String message) {
		LOGGER.sendMessage(ChatColor.RED + message + ChatColor.GRAY);
	}
}