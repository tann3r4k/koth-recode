package com.benzimmer123.koth.managers;

import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.api.enums.ProviderType;
import com.benzimmer123.koth.api.objects.KOTHPlayer;
import com.benzimmer123.koth.handlers.KOTHHandler;
import com.benzimmer123.koth.handlers.RegionHandler;
import com.benzimmer123.koth.hooks.face.ScoreboardHook;
import com.benzimmer123.koth.hooks.scoreboard.Animated;
import com.benzimmer123.koth.hooks.scoreboard.Featherboard;
import com.benzimmer123.koth.hooks.scoreboard.KiteBoard;
import com.benzimmer123.koth.hooks.scoreboard.Meteorite;
import com.benzimmer123.koth.hooks.scoreboard.QuickBoard;
import com.benzimmer123.koth.hooks.scoreboard.Revision;
import com.benzimmer123.koth.hooks.scoreboard.SimpleScoreboard;
import com.benzimmer123.koth.hooks.scoreboard.Sternal;
import com.benzimmer123.koth.hooks.scoreboard.TabPremium;
import com.benzimmer123.koth.hooks.scoreboard.TitleManager;
import com.benzimmer123.koth.hooks.teams.FabledSkyblock;
import com.benzimmer123.koth.hooks.teams.FactionsUUID;
import com.benzimmer123.koth.hooks.teams.SaberFactions;
import com.benzimmer123.koth.hooks.teams.SavageFactions;
import com.benzimmer123.koth.obj.koth.TempKOTHPlayer;
import com.benzimmer123.koth.scoreboard.PlayerScoreboard;
import com.benzimmer123.koth.scoreboard.ScoreboardProvider;
import com.benzimmer123.koth.scoreboard.ScoreboardText;
import com.benzimmer123.koth.scoreboard.providers.Default;
import com.benzimmer123.koth.util.LoggerUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ScoreboardManager {

	private Map<String, ScoreboardHook> scoreboardsLoaded;
	private boolean disabledScoreboard;
	private int maxTitleLength;
	private int maxLineLength;

	private Map<ProviderType, ScoreboardProvider> defaultProvider;

	public String getTitle(Player p, ProviderType type) {
		return this.defaultProvider.get(type).getTitle(p);
	}

	public List<ScoreboardText> getLines(Player p, ProviderType type) {
		return this.defaultProvider.get(type).getLines(p);
	}

	public ScoreboardManager() {
		defaultProvider = Maps.newHashMap();
		defaultProvider.put(ProviderType.DEFAULT, new Default());
		scoreboardsLoaded = Maps.newHashMap();
	}

	public void callScoreboard(Player player, KOTHPlayer kothPlayer) {
		if (kothPlayer == null) {
			if (KOTHHandler.getInstance().getKOTHPlayer(player) == null)
				return;
			kothPlayer = KOTHHandler.getInstance().getKOTHPlayer(player);
		}

		if (isScoreboardDisabled())
			return;

		if (kothPlayer.hasDisabledScoreboard())
			return;

		if (!isScoreboardAllowed(player)) {
			removeScoreboard(player, kothPlayer);
			return;
		}

		if (!KOTH.getInstance().getRegionManager().checkWGScoreboardRegions(player)) {
			removeScoreboard(player, kothPlayer);
			return;
		}

		if (isLoaded("TAB")) {
			if (!kothPlayer.hasFeatherboardDisplayed()) {
				((com.benzimmer123.koth.hooks.scoreboard.TabPremium) scoreboardsLoaded.get("TAB")).showKoth(player);
				kothPlayer.setFeatherboardDisplayed(true);
			}
			return;
		} else if (isLoaded("Featherboard") && KOTH.getInstance().getConfig().getBoolean("FEATHERBOARD_TRIGGER.ENABLED")) {
			if (!kothPlayer.hasFeatherboardDisplayed()) {
				new Featherboard().displayTrigger(player);
				kothPlayer.setFeatherboardDisplayed(true);
			}
		} else if (isLoaded("KiteBoard")) {
			if (!kothPlayer.hasKiteboardDisplayed()) {
				new KiteBoard().displayTrigger(player);
				kothPlayer.setKiteboardDisplayed(true);
			}
		} else {
			if (kothPlayer.getScoreboard() == null) {
				checkScoreboardHooks(player);
				kothPlayer.setScoreboard(new PlayerScoreboard(new Default(), player));
			} else {
				kothPlayer.getScoreboard().update();
			}
		}
	}

	public void disableAllScoreboards() {
		List<Player> clonedOnline = Lists.newArrayList(Bukkit.getOnlinePlayers());
		clonedOnline.stream().forEach(online -> {
			KOTHPlayer kothPlayer = KOTHHandler.getInstance().getKOTHPlayer(online);
			removeScoreboard(online, kothPlayer);
		});
	}

	public void updateScoreboards() {
		List<Player> clonedOnline = Lists.newArrayList(Bukkit.getOnlinePlayers());
		clonedOnline.stream().forEach(online -> {
			KOTHPlayer kothPlayer = KOTHHandler.getInstance().getKOTHPlayer(online);
			KOTH.getInstance().getScoreboardManager().callScoreboard(online, (TempKOTHPlayer) kothPlayer);
		});
	}

	public void loadScoreboards() {
		if (Bukkit.getPluginManager().isPluginEnabled("TitleManager")) {
			scoreboardsLoaded.put("TitleManager", new TitleManager());
		}
		if (Bukkit.getPluginManager().isPluginEnabled("QuickBoard")) {
			scoreboardsLoaded.put("QuickBoard", new QuickBoard());
		}
		if (Bukkit.getPluginManager().isPluginEnabled("AnimatedScoreboard")) {
			scoreboardsLoaded.put("AnimatedScoreboard", new Animated());
		}
		if (Bukkit.getPluginManager().isPluginEnabled("Featherboard") || Bukkit.getPluginManager().isPluginEnabled("FeatherBoard")) {
			scoreboardsLoaded.put("Featherboard", new Featherboard());
			if (KOTH.getInstance().getConfig().getBoolean("FEATHERBOARD_TRIGGER.ENABLED")) {
				LoggerUtil.success("[KOTH] Successfully loaded Featherboard trigger.");
			} else {
				LoggerUtil.warning("[KOTH] Featherboard found but hook was not enabled in the config.");
			}
		}
		if (Bukkit.getPluginManager().isPluginEnabled("KiteBoard")) {
			KiteBoard kiteBoard = new KiteBoard();
			scoreboardsLoaded.put("KiteBoard", kiteBoard);
			if (KOTH.getInstance().getConfig().getBoolean("KITEBOARD_TRIGGER.ENABLED")) {
				LoggerUtil.success("[KOTH] Successfully loaded Kiteboard trigger.");
			} else {
				LoggerUtil.warning("[KOTH] Kiteboard found but hook was not enabled in the config.");
			}
			kiteBoard.setup();
		}
		if (Bukkit.getPluginManager().isPluginEnabled("TAB")) {
			scoreboardsLoaded.put("TAB", new TabPremium());
			LoggerUtil.success("[KOTH] Using TAB scoreboard. KOTH's own sidebar stays off while TAB is loaded.");
		}
		if (Bukkit.getPluginManager().isPluginEnabled("SimpleScore")) {
			scoreboardsLoaded.put("SimpleScore", new SimpleScoreboard());
		}
		if (Bukkit.getPluginManager().isPluginEnabled("MeteoriteScoreboard")) {
			scoreboardsLoaded.put("MeteoriteScoreboard", new Meteorite());
		}
		if (Bukkit.getPluginManager().isPluginEnabled("Scoreboard-revision")) {
			scoreboardsLoaded.put("Scoreboard-revision", new Revision());
		}
		if (Bukkit.getPluginManager().isPluginEnabled("SternalBoard")) {
			scoreboardsLoaded.put("SternalBoard", new Sternal());
		}

		scoreboardsLoaded.keySet().stream().forEach(sb -> {
			LoggerUtil.success("[KOTH] Successfully hooked into " + sb + ".");
		});
	}

	public void checkScoreboardHooks(Player p) {
		Bukkit.getScheduler().runTask(KOTH.getInstance(), () -> {
			for (ScoreboardHook sbHook : scoreboardsLoaded.values()) {
				sbHook.removeScoreboard(p);
			}
			if (KOTH.getInstance().getTeamManager().getAPI() != null && KOTH.getInstance().getTeamManager().getAPI().getAPIName()
					.equalsIgnoreCase("FabledSkyblock")) {
				new FabledSkyblock().removeScoreboard(p);
			}
		});
	}

	public void displayDefaultBoard(Player p) {
		if (!scoreboardsLoaded.isEmpty()) {
			for (ScoreboardHook sbHook : scoreboardsLoaded.values()) {
				sbHook.giveScoreboard(p);
			}
		} else {
			toggleScoreboard(p);
		}
	}

	public void removeScoreboard(Player player, KOTHPlayer kothPlayer) {
		if (isLoaded("TAB")) {
			if (kothPlayer.hasFeatherboardDisplayed()) {
				scoreboardsLoaded.get("TAB").giveScoreboard(player);
				kothPlayer.setFeatherboardDisplayed(false);
			}
			return;
		} else if (isLoaded("Featherboard") && KOTH.getInstance().getConfig().getBoolean("FEATHERBOARD_TRIGGER.ENABLED")) {
			if (kothPlayer.hasFeatherboardDisplayed()) {
				new Featherboard().giveScoreboard(player);
				kothPlayer.setFeatherboardDisplayed(false);
			}
		} else if (isLoaded("KiteBoard")) {
			if (kothPlayer.hasKiteboardDisplayed()) {
				new KiteBoard().giveScoreboard(player);
				kothPlayer.setKiteboardDisplayed(false);
			}
		} else {
			if (kothPlayer.getScoreboard() != null) {
				kothPlayer.getScoreboard().disappear();
				kothPlayer.setScoreboard(null);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public boolean isScoreboardAllowed(Player player) {
		List<String> disabledWorlds;

		if (!KOTH.getInstance().getConfig().contains("SCOREBOARD.DISABLE_IN_WORLD")) {
			disabledWorlds = Lists.newArrayList();
		} else {
			disabledWorlds = (List<String>) KOTH.getInstance().getConfig().getList("SCOREBOARD.DISABLE_IN_WORLD");
		}

		boolean whitelistedWorldsEnabled = KOTH.getInstance().getConfig().getBoolean("");

		List<String> whitelistedWorlds;

		if (!KOTH.getInstance().getConfig().contains("SCOREBOARD.DISABLE_IN_WORLD")) {
			whitelistedWorlds = Lists.newArrayList();
		} else {
			whitelistedWorlds = (List<String>) KOTH.getInstance().getConfig().getList("SCOREBOARD.DISABLE_IN_WORLD");
		}

		if (!whitelistedWorldsEnabled && !disabledWorlds.contains(player.getWorld().getName())) {
			return true;
		} else if (whitelistedWorldsEnabled && whitelistedWorlds.contains(player.getWorld().getName())) {
			return true;
		}

		return false;
	}

	public void checkScoreboardChunks(Player player, Chunk chunk) {
		if (!KOTH.getInstance().getConfig().getBoolean("SCOREBOARD.SCOREBOARD_RADIUS.ENABLED"))
			return;

		if (RegionHandler.getInstance().getValidScoreboardChunks().isEmpty())
			return;

		KOTHPlayer kothPlayer = KOTHHandler.getInstance().getKOTHPlayer(player);

		if (RegionHandler.getInstance().getValidScoreboardChunks().containsKey(chunk.getX() + "," + chunk.getZ())) {
			if (RegionHandler.getInstance().getValidScoreboardChunks().get(chunk.getX() + "," + chunk.getZ()).equalsIgnoreCase(chunk.getWorld()
					.getName())) {
				if (kothPlayer.hasDisabledScoreboard()) {
					kothPlayer.setDisabledScoreboard(false);
				}
				return;
			}
		}

		if (!kothPlayer.hasDisabledScoreboard()) {
			kothPlayer.setDisabledScoreboard(true);
		}
	}

	private void toggleScoreboard(final Player p) {
		if (KOTH.getInstance().getTeamManager().getAPI() == null || p == null)
			return;
		String api = KOTH.getInstance().getTeamManager().getAPI().getAPIName();

		switch (api) {
		case "FabledSkyblock":
			new FabledSkyblock().toggleScoreboard(p);
			break;
		case "SavageFactions":
			new SavageFactions().toggleScoreboard(p);
			break;
		case "SaberFactions":
			new SaberFactions().toggleScoreboard(p);
			break;
		case "FactionsUUID":
			new FactionsUUID().toggleScoreboard(p);
			break;
		}
	}

	public Map<String, ScoreboardHook> getScoreboardsLoaded() {
		return scoreboardsLoaded;
	}

	public int getMaxTitleLength() {
		return maxTitleLength;
	}

	public int getMaxLineLength() {
		return maxLineLength;
	}

	public void setMaxTitleLength(int maxTitleLength) {
		this.maxTitleLength = maxTitleLength;
	}

	public void setMaxLineLength(int maxLineLength) {
		this.maxLineLength = maxLineLength;
	}

	public boolean isLoaded(String scoreboardPluginName) {
		return scoreboardsLoaded.containsKey(scoreboardPluginName);
	}

	public boolean isScoreboardDisabled() {
		return disabledScoreboard;
	}

	public void setScoreboardDisabled(boolean disabledScoreboard) {
		this.disabledScoreboard = disabledScoreboard;
	}
}
