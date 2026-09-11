package com.benzimmer123.koth.util;

import org.bukkit.command.CommandSender;

import com.benzimmer123.koth.KOTH;

public enum LangUtil {

	PREFIX("&8[&cKOTH&8] ", false),
	ACTIVE_NOW("&a%koth% is currently active!", false),
	ACTIVE_STATUS("&aKOTH is currently active!", false),
	ADDED_AUTOSTART("&aYou created an automatic start for %koth% at %players% players, with a capture time of %captime% seconds.", true),
	ADDED_CMD("&aSuccessfully added %cmd% command to %koth%.", true),
	ADDED_LOOT("&aAdded item to %koth%'s loot with a chance of %percent%.", true),
	ADDED_LOOT_LOCATION("&aLoot location has been set.", true),
	ANNOUNCE_KOTH_ON_JOIN("&6%koth% &eis currently active on the server with &6%time% &etime remaining.", true),
	ATTEMPTING_CAPTURE("&6%player% (&a%team%&6) &ehas started controlling &6%koth% &c(%time%)", true),
	AUTOMATIC_HOSTNAME("The Console", false),
	BYPASS_MODE("&eYour bypass mode has now been %toggle%.", true),
	CANNOT_EDIT_PERCENT_ITEMS("&cYou can only edit percent loot items from the file or command.", true),
	CAPTURED_KOTH("&6%player% (&a%team%&6) &ehas captured &6%koth%", true),
	CLEARED_CMDS("&aSuccessfully cleared all reward commands for %koth%.", true),
	CLEARED_LOOT("&aCleared all loot for %koth%.", true),
	COOLDOWN("&cYou are on cooldown for another &e%timeleft% &cseconds.", true),
	CREATED_KOTH("&aYou have created the KOTH %koth%.", true),
	CREATED_REGION("&aYou have created the Region %region%.", true),
	CPU_INFO("&aCurrent Active Threads: %threads%", true),
	DAY_OF_WEEK("&8=====> &2%day% &8<=====", false),
	GUI_ACTIVE("&7This KOTH is currently active.", false),
	GUI_INACTIVE("&7This KOTH is currently NOT active.", false),
	GUI_NEXT_PAGE("&aNext Page", false),
	HELP_PAGE_ENTRY("&a%cmd% &7%desc%", false),
	HELP_PAGE_TITLE("&8========> &2KOTH Help %page%/%maxpage% &8<========", false),
	IN_GAME_ONLY("&cThis command can only be used in-game.", true),
	INVALID_CAPTIME("&cYou have entered an incorrect amount of capture time.", true),
	INVALID_CHANCE("&cYou have entered an incorrect value for the chance.", true),
	INVALID_COMPATIBILITY("&cThere is currently a compatibilty issue with OpenInv. Please use the command /koth addloot <KOTH> [PERCENT].", true),
	INVALID_DAY("&cYou have entered an incorrect value for the day you would like the KOTH to be.", true),
	INVALID_HOUR("&cYou have entered an incorrect value for the hour you would like the KOTH to be.", true),
	INVALID_ID("&cPlease enter a correct ID number. You can use /koth scheduled <KOTH> to view IDs.", true),
	INVALID_KOTH("&cThat KOTH does not currently exist.", true),
	INVALID_LENGTH("&cYou have entered an incorrect value for the length you would like the KOTH to be.", true),
	INVALID_MAXRUNTIME("&cYou have entered an incorrect value for the max run time.", true),
	INVALID_MINUTE("&cYou have entered an incorrect value for the minutes you would like the KOTH to be.", true),
	INVALID_MONTH("&cYou have entered an incorrect value for the month you would like the KOTH to be.", true),
	INVALID_NUMBER("&cYou haven't entered a correct number.", true),
	INVALID_PAGE("&cYou need to enter a correct page number.", true),
	INVALID_PLAYER("&cThat player is currently offline.", true),
	INVALID_PLAYERS("&cYou have entered an incorrect amount of players.", true),
	INVALID_REGION("&cThat region does not currently exist.", true),
	INVALID_TIME("&cYou need to define a time for the KOTH to start for example 17:00.", true),
	INVALID_TYPE("&cYou entered an invalid top type: &f%validtypes%\n&aDefaulting to TEAM type...", true),
	INVALID_WEEKDAY("&cYou have entered an invalid weekday: &f%validtypes%", true),
	INVALID_WORLD("&cKOTH locations must be specified in the same world.", true),
	INVENTORY_FULL("&cYour inventory was full, placing on the floor.", true),
	ILLEGAL_CHAR("&cYou can only use characters for the name.", true),
	KEY_GIVEN("&aYou have given the KOTH Key to %player%.", true),
	KEY_RECEIVED("&aYou have received a KOTH Key from %player%", true),
	KOTH_ALREADY_SET("&cThis KOTH is already set. If you would like to remove it please do /koth remove <kothname>.", true),
	KOTH_CURRENTLY_ACTIVE("&cThat KOTH is currently active. Please end it with /koth end <koth>.", true),
	KOTH_ENDED("&6%player% &ehas ended &6%koth%", true),
	KOTH_NEXT("&aThe next KOTH is starting at %day%/%month% at %hour%:%minute%.", false),
	KOTH_NOT_ACTIVE("&cThat KOTH is currently not active.", true),
	KOTH_STARTING("&6%player% &ehas started &6%koth% &eat &6X:%x% Y:%y% Z:%z%", true),
	KOTH_TIMER_FORMAT("&eThe KOTH %koth%, currently has %time% remaining.", true),
	KOTH_TOP_ENTRY("&c%pos%. &a%player% - %captured%", false),
	KOTH_TOP_TITLE("&8========> &2KOTH Top %page%/%maxpage% &8<========", false),
	LEADER_OFFLINE("&aFaction leader is not currently online so you will receive rewards instead.", true),
	LEFT_CLICK_BLOCK_ADD("&aPlease left click a block where would like to set the loot location.", true),
	LEFT_CLICK_BLOCK_REMOVE("&aPlease left click a chest where would like to remove the loot location.", true),
	LIST_KOTH_IDS_ENTRY("&aScheduled ID - &e%id% &ais starting at &e%hour%:%minute% &a- &e%type%", true),
	LIST_KOTH_IDS_TITLE("&8=====> &2%day% &8<=====", false),
	LIST_KOTHS_ENTRY("&7- &a%koth%", false),
	LIST_KOTHS_TITLE("&8=====> &2Current KOTHs &8<=====", false),
	LIST_REGIONS_ENTRY("&7- &a%region%", false),
	LIST_REGIONS_TITLE("&8=====> &2Current Regions &8<=====", false),
	LOST_CONTROL("&6%player% (&a%team%&6) &ehas lost control of &6%koth%", true),
	MAX_KOTHS("&cYou cannot start more than %amount% of koths at once.", true),
	MAX_RUN_TIME("&eThe KOTH &6%koth% &ehas exceeded the max run time and automatically ended.", true),
	MINIMUM_PLAYERS("&cThere needs to be at least %players% online to start the KOTH.", true),
	MONEY_ADDED("&a$%amount% has just been added to your account.", true),
	MUST_SPECIFY_MAXPOINTS("&cYou need to specify a max points because you are using a points system.", true),
	MUST_SPECIFY_RUNTIME("&cYou need to specify a max run time because you are using a points system.", true),
	NEXT_SCHEDULE("&aStarting on %day%/%month% at %hour%:%minute%.", false),
	NEXT_SCHEDULED_NAME("%koth%", false),
	NO_CAPPER("None", false),
	NO_HELP_PAGE("&cThis number does not exist, the max help page is %maxpage%.", true),
	NO_ITEM_IN_HAND("&cPlease hold the item in hand you want to add.", true),
	NO_KEY("&cYou need a key to open this chest.", true),
	NO_KOTH("None", false),
	NO_PERMISSION("&cYou do not have permission for that command", true),
	NO_SCHEDULE("&aNo scheduled KOTHs.", false),
	NO_TEAM("None", false),
	NO_WAND("&cYou need to select a region using the custom KOTH wand.", true),
	NONE_ACTIVE_STATUS("&cThere are no KOTHs active.", false),
	ONLY_ONE_KOTH("&cYou cannot start a koth when one is already active.", true),
	RANDOM_SCHEDULE("&aRandom KOTH", false),
	RECREATED_KOTH("&aYou have re-created the KOTH %koth%.", true),
	REGION_ALREADY_SET("&cThis Region is already set. If you would like to remove it please do /koth removeregion <regionname>.", true),
	REGION_SELECTED("&aYou have selected %pos% position at %x%, %y%, %z%.", true),
	RELOAD("&aSuccessfully reloaded all KOTH configuration files.", true),
	REMOVED_ALL_LOOT_LOCATIONS("&aAll loot locations have been reset for %koth%.", true),
	REMOVED_KOTH("&aThe KOTH %koth% has been removed.", true),
	REMOVED_LOOT_LOCATION("&aLoot location has been removed.", true),
	REMOVED_REGION("&aThe KOTH Region %region% has been removed.", true),
	REMOVED_SCHEDULE("&aRemoved scheduled KOTH with ID: %id%", true),
	REQUEST_TIMED_OUT("&cYour request has timed out. Please type this command again.", true),
	REWARDS_GIVEN_TO_LEADER("&eRewards have been given to team leader.", true),
	SCHEDULED_COUNTDOWN("&aStarting in %time%.", false),
	SCHEDULED_DAILY(
			"&aYou have successfully scheduled the KOTH %koth% to run at %time% everyday for %length% seconds (MaxRunTime: %maxruntime%).",
			true),
	SCHEDULED_DATE("&aYou have successfully scheduled the KOTH %koth% at %time% on %day%/%month% (MaxRunTime: %maxruntime%).", true),
	SCHEDULED_WEEKLY(
			"&aYou have successfully scheduled the KOTH %koth% to run at %time% every week for %length% seconds (MaxRunTime: %maxruntime%).",
			true),
	SETUP_INFO(
			"&8=====> &2KOTH Setup &8<=====\n&a1) &eBegin with /koth wand and select two locations.\n&a2) &eOnce selected, type /koth create <name>.\n&a3) &eStart the KOTH with /koth start <name> <time>.\n&a4) &eYou're done!\n&7&oMore info at: https://github.com/Itzzbenzz/KOTH/wiki/",
			false),
	SET_LOOT("&aThe KOTH loot chest has been set.", true),
	SET_LOOT_TYPE("&aSet reward type for %koth% to %type%.", true),
	STARTER_ITEM_GIVEN("&aYou have successfully given a KOTH starter item to %player%.", true),
	STARTER_ITEM_RECEIVED("&aYou have successfully received a KOTH starter item from %player%.", true),
	STARTS_ON("&a%koth% starts at %hour%:%minute%.", false),
	TELEPORTED("&aYou have teleported to %koth%.", true),
	TIME_ADDED("&aThe time %time% has been added for KOTH %koth%.", true),
	TIME_ALREADY_SET("&cThis time already exists for %koth%.", true),
	TIME_LEFT("&6%player% (&a%team%&6) &eis controlling &6%koth% &c(%time%)", true),
	TIME_LIST_SET("&aYour times set for %koth% currently are: %times%", true),
	TIME_NONE_SET("&cYou haven't set any times for %koth%", true),
	TIME_NOT_SET("&cThe time %time% doesn't exist for %koth%.", true),
	TIME_REMOVED("&aYou have removed the time %time% for KOTH %koth%.", true),
	TOGGLED_SCOREBOARD("&aYour scoreboard is now %toggle%.", true),
	VERSION("&eYou are currently using the KOTH version %version%. It is advised to always use the latest version.", true),
	WAND_ADDED("&eA KOTH Selection Wand has been added to your inventory.", true),
	WAND_GIVEN("&aYou have given the KOTH Wand to %player%.", true),
	WAND_RECEIVED("&aYou have received a KOTH Wand from %player%.", true);

	private String defaultValue;
	private boolean usePrefix;

	LangUtil(String defaultValue, boolean usePrefix) {
		this.defaultValue = defaultValue;
		this.usePrefix = usePrefix;
	}

	public String getDefault() {
		return this.defaultValue;
	}

	public String getPath() {
		return this.name();
	}

	public boolean usePrefix() {
		return this.usePrefix;
	}

	public static String replaceString(String s) {
		return s.replaceAll("(&([a-f0-9]))", "\u00A7$2").replaceAll("&l", "\u00A7l").replaceAll("&o", "\u00A7o").replaceAll("&k", "\u00A7k")
				.replaceAll("&r", "\u00A7r").replaceAll("&n", "\u00A7n").replaceAll("&m", "\u00A7m");
	}

	@Override
	public String toString() {
		boolean usePrefix = false;
		String prefix = null;

		if (KOTH.getInstance().getSettingsManager().getLang().isSet(LangUtil.PREFIX.getPath())) {
			if (!KOTH.getInstance().getSettingsManager().getLang().getString(LangUtil.PREFIX.getPath()).equalsIgnoreCase("") && KOTH.getInstance()
					.getSettingsManager().getLang().getString(LangUtil.PREFIX.getPath()) != null && this.usePrefix()) {
				usePrefix = true;
				prefix = replaceString(KOTH.getInstance().getSettingsManager().getLang().getString(LangUtil.PREFIX.getPath()));
			}
		}

		if (KOTH.getInstance().getSettingsManager().getLang().isSet(getPath())) {
			if (KOTH.getInstance().getSettingsManager().getLang().getString(getPath()).equalsIgnoreCase("") || KOTH.getInstance().getSettingsManager()
					.getLang().getString(getPath()) == null) {
				return "";
			}
			return usePrefix ? prefix + replaceString(KOTH.getInstance().getSettingsManager().getLang().getString(getPath()))
					: replaceString(KOTH.getInstance().getSettingsManager().getLang().getString(getPath()));
		}

		return usePrefix ? prefix + replaceString(this.defaultValue) : replaceString(this.defaultValue);
	}

	public static void sendMessage(CommandSender sender, String replacedString) {
		if (replacedString.equalsIgnoreCase(""))
			return;

		String[] message = replacedString.split("\\\\n");

		for (String msg : message) {
			if (KOTH.getInstance().getPlaceholderManager().isClipPlaceholderAPIHooked())
				msg = KOTH.getInstance().getPlaceholderManager().parsePlaceholders(null, msg);
			sender.sendMessage(msg.replace("\\n", ""));
		}
	}
}
