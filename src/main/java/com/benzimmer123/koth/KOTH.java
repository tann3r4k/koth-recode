package com.benzimmer123.koth;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.benzimmer123.koth.api.objects.KOTHArena;
import com.benzimmer123.koth.cmds.rootcommand.RootCommand;
import com.benzimmer123.koth.exceptions.NotUniqueInventoryNameException;
import com.benzimmer123.koth.handlers.KOTHHandler;
import com.benzimmer123.koth.handlers.RegionHandler;
import com.benzimmer123.koth.handlers.ThreadHandler;
import com.benzimmer123.koth.hooks.other.BossBar;
import com.benzimmer123.koth.listeners.EntityDamageByEntity;
import com.benzimmer123.koth.listeners.InventoryClick;
import com.benzimmer123.koth.listeners.InventoryClose;
import com.benzimmer123.koth.listeners.PlayerChangedWorld;
import com.benzimmer123.koth.listeners.PlayerDeath;
import com.benzimmer123.koth.listeners.PlayerInteract;
import com.benzimmer123.koth.listeners.PlayerJoin;
import com.benzimmer123.koth.listeners.PlayerMove;
import com.benzimmer123.koth.listeners.PlayerQuit;
import com.benzimmer123.koth.listeners.PlayerRespawn;
import com.benzimmer123.koth.listeners.PlayerTeleport;
import com.benzimmer123.koth.managers.APIManager;
import com.benzimmer123.koth.managers.EventManager;
import com.benzimmer123.koth.managers.KOTHManager;
import com.benzimmer123.koth.managers.PlaceholderManager;
import com.benzimmer123.koth.managers.RegionManager;
import com.benzimmer123.koth.managers.RewardManager;
import com.benzimmer123.koth.managers.ScoreboardManager;
import com.benzimmer123.koth.managers.SettingsManager;
import com.benzimmer123.koth.managers.TeamManager;
import com.benzimmer123.koth.managers.TopManager;
import com.benzimmer123.koth.tasks.ScheduledTask;
import com.benzimmer123.koth.tasks.ScoreboardTask;
import com.benzimmer123.koth.tasks.SortNextDayTask;
import com.benzimmer123.koth.tasks.TopPlayersTask;
import com.benzimmer123.koth.tasks.TopTeamsTask;
import com.benzimmer123.koth.util.LoggerUtil;
import com.benzimmer123.koth.util.MetricsUtil;
import com.benzimmer123.koth.util.ServerVersionUtil;
import com.benzimmer123.koth.util.UpdateCheckerUtil;

public class KOTH extends JavaPlugin {

	private static KOTH INSTANCE;

	private final int PLUGIN_ID = 6832;
	private final int METRICS_ID = 15282;

	private TeamManager teamManager;
	private KOTHManager kothManager;
	private RewardManager rewardManager;
	private ScoreboardManager sbManager;
	private RegionManager regionManager;
	private PlaceholderManager placeholderManager;
	private TopManager topManager;
	private EventManager eventManager;
	private SettingsManager settingsManager;
	private APIManager apiManager;

	public void onEnable() {
		INSTANCE = this;

		long startTime = System.currentTimeMillis();

		teamManager = new TeamManager();
		kothManager = new KOTHManager();
		rewardManager = new RewardManager();
		sbManager = new ScoreboardManager();
		regionManager = new RegionManager();
		placeholderManager = new PlaceholderManager();
		topManager = new TopManager();
		settingsManager = new SettingsManager();
		eventManager = new EventManager();
		apiManager = new APIManager();

		saveDefaultConfig();
		getSettingsManager().setup(this);

		RootCommand rootCmd = new RootCommand(this);
		getCommand("koth").setExecutor(rootCmd);
		getCommand("koth").setTabCompleter(rootCmd);

		getServer().getPluginManager().registerEvents(new InventoryClick(), this);
		getServer().getPluginManager().registerEvents(new InventoryClose(), this);
		getServer().getPluginManager().registerEvents(new PlayerChangedWorld(), this);
		getServer().getPluginManager().registerEvents(new PlayerDeath(), this);
		getServer().getPluginManager().registerEvents(new PlayerInteract(), this);
		getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
		getServer().getPluginManager().registerEvents(new PlayerQuit(), this);
		getServer().getPluginManager().registerEvents(new PlayerTeleport(), this);
		getServer().getPluginManager().registerEvents(new PlayerRespawn(), this);
		getServer().getPluginManager().registerEvents(new EntityDamageByEntity(), this);
		getServer().getPluginManager().registerEvents(new PlayerMove(), this);

		String[] files = new String[] { getDataFolder() + "/koths", getDataFolder() + "/regions", getDataFolder() + "/top", getDataFolder()
				+ "/top/players", getDataFolder() + "/top/teams", getDataFolder() + "/schedules" };

		for (String fileName : files) {
			File file = new File(fileName);
			if (!file.exists()) {
				file.mkdirs();
			}
		}

		getAPIManager().setupAllSupportedPlugins();
		getTeamManager().runTeamHookLater();
		getPlaceholderManager().setupPlaceholderAPI();

		TopPlayersTask topPlayersTask = new TopPlayersTask();
		topPlayersTask.submitRepeatingScheduledTask(TimeUnit.SECONDS, getConfig().getInt("KOTH_TOP.SORT_DELAY"));

		TopTeamsTask topTeamsTask = new TopTeamsTask();
		topTeamsTask.submitRepeatingScheduledTask(TimeUnit.SECONDS, getConfig().getInt("KOTH_TOP.SORT_DELAY"));

		if (getConfig().getBoolean("SCOREBOARD.USE_SCOREBOARD") || getConfig().getBoolean("KITEBOARD_TRIGGER.ENABLED") || getConfig().getBoolean(
				"FEATHERBOARD_TRIGGER.ENABLED")) {
			double updateSeconds = (double) (KOTH.getInstance().getConfig().getInt("SCOREBOARD.UPDATE_TICKS") / 20.0);
			int delayInSeconds = (int) Math.ceil(updateSeconds);
			ScoreboardTask scoreboardTask = new ScoreboardTask();
			scoreboardTask.submitRepeatingScheduledTask(TimeUnit.SECONDS, delayInSeconds);
		}

		ScheduledTask schedulerTask = new ScheduledTask();
		schedulerTask.submitRepeatingScheduledTask(TimeUnit.MINUTES, 1);

		SortNextDayTask sortNextDayTask = new SortNextDayTask();
		sortNextDayTask.submitTask();
		sortNextDayTask.submitRepeatingScheduledTask(TimeUnit.DAYS, 1);

		RegionHandler.getInstance().loadValidScoreboardChunks();

		String adminName = getConfig().getString("KOTH_ADMIN_GUI.NAME");
		String playerName = getConfig().getString("KOTH_PLAYER_GUI.NAME");
		String realLootName = getConfig().getString("REWARDS.REAL_INVENTORY_NAME");
		String viewLootName = getConfig().getString("REWARDS.VIEW_INVENTORY_NAME");

		if (adminName.equalsIgnoreCase(playerName) || realLootName.equalsIgnoreCase(viewLootName) || realLootName.equalsIgnoreCase(playerName)
				|| realLootName.equalsIgnoreCase(adminName) || viewLootName.equalsIgnoreCase(playerName) || viewLootName.equalsIgnoreCase(
						adminName)) {
			throw new NotUniqueInventoryNameException("Inventory names in the config must ALL be unique. Plugin disabling...");
		}

		if (ServerVersionUtil.isAboveVersion(ServerVersionUtil.v1_13_R1)) {
			getScoreboardManager().setMaxTitleLength(128);
			getScoreboardManager().setMaxLineLength(128);
		} else {
			getScoreboardManager().setMaxLineLength(32);
			getScoreboardManager().setMaxTitleLength(32);
		}

		String latestVersion = new UpdateCheckerUtil(getDescription().getVersion(), PLUGIN_ID).getLatestVersion();
		new LoggerUtil(latestVersion, rootCmd.getCommandSize(), startTime);
		new MetricsUtil(INSTANCE, METRICS_ID);
	}

	public void onDisable() {
		for (KOTHArena koth : KOTHHandler.getInstance().getKOTHS()) {
			koth.save();
		}

		Bukkit.getOnlinePlayers().stream().forEach(x -> BossBar.removeBossBar(x));
		ThreadHandler.getInstance().cancelAll();
	}

	public TopManager getTopManager() {
		return topManager;
	}

	public RewardManager getRewardManager() {
		return rewardManager;
	}

	public ScoreboardManager getScoreboardManager() {
		return sbManager;
	}

	public RegionManager getRegionManager() {
		return regionManager;
	}

	public PlaceholderManager getPlaceholderManager() {
		return placeholderManager;
	}

	public KOTHManager getKOTHManager() {
		return kothManager;
	}

	public TeamManager getTeamManager() {
		return teamManager;
	}

	public EventManager getEventManager() {
		return eventManager;
	}

	public SettingsManager getSettingsManager() {
		return settingsManager;
	}

	public APIManager getAPIManager() {
		return apiManager;
	}

	public static KOTH getInstance() {
		return INSTANCE;
	}
}