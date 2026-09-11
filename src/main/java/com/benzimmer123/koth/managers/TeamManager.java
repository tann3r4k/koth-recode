package com.benzimmer123.koth.managers;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.exceptions.TeamHookException;
import com.benzimmer123.koth.handlers.KOTHHandler;
import com.benzimmer123.koth.hooks.face.TeamHook;
import com.benzimmer123.koth.hooks.teams.ASkyblock;
import com.benzimmer123.koth.hooks.teams.AdvancedClan;
import com.benzimmer123.koth.hooks.teams.AdvancedTowny;
import com.benzimmer123.koth.hooks.teams.Bento;
import com.benzimmer123.koth.hooks.teams.BetterTeams;
import com.benzimmer123.koth.hooks.teams.Clans;
import com.benzimmer123.koth.hooks.teams.CrClans;
import com.benzimmer123.koth.hooks.teams.FabledSkyblock;
import com.benzimmer123.koth.hooks.teams.FactionsBridgeHook;
import com.benzimmer123.koth.hooks.teams.FactionsUUID;
import com.benzimmer123.koth.hooks.teams.FactionsUUIDModern;
import com.benzimmer123.koth.hooks.teams.FactionsX;
import com.benzimmer123.koth.hooks.teams.Feudal;
import com.benzimmer123.koth.hooks.teams.GalacticSkyblock;
import com.benzimmer123.koth.hooks.teams.GangsPlus;
import com.benzimmer123.koth.hooks.teams.GoliathSkyBlock;
import com.benzimmer123.koth.hooks.teams.Guild;
import com.benzimmer123.koth.hooks.teams.HCFNick;
import com.benzimmer123.koth.hooks.teams.IridiumSkyblock;
import com.benzimmer123.koth.hooks.teams.JungleSkyblock;
import com.benzimmer123.koth.hooks.teams.KingdomCraft;
import com.benzimmer123.koth.hooks.teams.Kingdoms;
import com.benzimmer123.koth.hooks.teams.Lands;
import com.benzimmer123.koth.hooks.teams.LegacyFactions;
import com.benzimmer123.koth.hooks.teams.MassiveCore;
import com.benzimmer123.koth.hooks.teams.McMMO;
import com.benzimmer123.koth.hooks.teams.SaberFactions;
import com.benzimmer123.koth.hooks.teams.SavageFactions;
import com.benzimmer123.koth.hooks.teams.SimpleClans;
import com.benzimmer123.koth.hooks.teams.SuperiorSkyblock;
import com.benzimmer123.koth.hooks.teams.UltimateClans;
import com.benzimmer123.koth.hooks.teams.UltraPrisonCore;
import com.benzimmer123.koth.util.LangUtil;
import com.benzimmer123.koth.util.LoggerUtil;
import com.benzimmer123.koth.util.ReflectionUtil;
import com.google.common.collect.Lists;

public class TeamManager {

	private TeamHook teamHook;

	public boolean exists(String name) {
		if (getAPI() == null)
			return true;

		try {
			return getAPI().exists(name);
		} catch (Throwable e) {
			throw new TeamHookException("Failed to check if team exists due to incompatible versions. Disabling " + teamHook.getAPIName() + " hook.",
					e);
		}
	}

	public String getTeamID(Player p) {
		if (getAPI() == null || p == null)
			return null;

		try {
			return getAPI().getTeamID(p);
		} catch (Throwable e) {
			throw new TeamHookException("Failed to get team members due to incompatible versions. Disabling " + teamHook.getAPIName() + " hook.", e);
		}
	}

	public String getTeamID(String playerName) {
		if (getAPI() == null || playerName == null)
			return null;
		Player online = Bukkit.getPlayer(playerName);
		if (online != null)
			return getTeamID(online);
		try {
			return getAPI().getTeamID(Bukkit.getOfflinePlayer(playerName));
		} catch (Throwable e) {
			return null;
		}
	}

	public List<Player> getTeamPlayers(Player p) {
		if (getAPI() == null || p == null)
			return Arrays.asList(p);

		List<Player> players = Lists.newArrayList();

		try {
			players = getAPI().getTeamPlayers(p);
		} catch (Throwable e) {
			throw new TeamHookException("Failed to get team members due to incompatible versions. Disabling " + teamHook.getAPIName() + " hook.", e);
		}

		if (players.isEmpty())
			players.add(p);

		return players;
	}

	public boolean hasTeam(Player p) {
		if (getAPI() == null || p == null)
			return false;

		try {
			return getAPI().hasTeam(p);
		} catch (Throwable e) {
			throw new TeamHookException("Failed to check team due to incompatible versions. Disabling " + teamHook.getAPIName() + " hook.", e);
		}
	}

	public String getTeamName(Player p) {
		if (getAPI() == null || p == null)
			return LangUtil.NO_TEAM.toString();

		try {
			return getAPI().getTeamName(p);
		} catch (Throwable e) {
			throw new TeamHookException("Failed to get team name due to incompatible versions. Disabling " + teamHook.getAPIName() + " hook.", e);
		}
	}

	public String getTeamLeader(Player p) {
		if (getAPI() == null || p == null)
			return null;
		try {
			return getAPI().getTeamLeader(p);
		} catch (Throwable e) {
			throw new TeamHookException("Failed to get team leader due to incompatible versions. Disabling " + teamHook.getAPIName() + " hook.", e);
		}
	}

	public TeamHook getRawAPI() {
		if (KOTH.getInstance().getConfig().getBoolean("FORCED_HOOK.ENABLED")) {
			String name = KOTH.getInstance().getConfig().getString("FORCED_HOOK.PLUGIN");
			TeamHook teamHook = getTeamHookFromString(name);

			if (teamHook == null) {
				LoggerUtil.warning("[KOTH] Could not find plugin supported with the name " + name + ".");
				return null;
			}

			if (!Bukkit.getPluginManager().isPluginEnabled(teamHook.getAPIName())) {
				LoggerUtil.warning("[KOTH] Attempting to load " + teamHook.getAPIName()
						+ " but plugin could not be found. Continuing to load anyway...");
			}

			return teamHook;
		}

		if (Bukkit.getPluginManager().isPluginEnabled("FactionsBridge")) {
			return new FactionsBridgeHook();
		} else if (isModernFactionsUUID()) {
			return new FactionsUUIDModern();
		} else if (Bukkit.getPluginManager().isPluginEnabled("FactionsX")) {
			return new FactionsX();
		} else if (Bukkit.getPluginManager().isPluginEnabled("ASkyBlock")) {
			if (Bukkit.getServer().getPluginManager().getPlugin("ASkyBlock").getDescription().getAuthors().contains("Joseph#0278 (Discord)")) {
				return new JungleSkyblock();
			}
			return new ASkyblock();
		} else if (Bukkit.getServer().getPluginManager().isPluginEnabled("BentoBox")) {
			return new Bento();
		} else if (Bukkit.getPluginManager().isPluginEnabled("IridiumSkyblock")) {
			return new IridiumSkyblock();
		} else if (Bukkit.getPluginManager().isPluginEnabled("SuperiorSkyblock2")) {
			return new SuperiorSkyblock();
		} else if (Bukkit.getServer().getPluginManager().isPluginEnabled("Factions")) {
			if (ReflectionUtil.isPresent("com.massivecraft.factions.SavageFactions")) {
				return new SavageFactions();
			} else if (Bukkit.getServer().getPluginManager().getPlugin("Factions").getDescription().getAuthors().contains("Driftay")) {
				return new SaberFactions();
			} else if (isLegacyFactionsUUID()) {
				return new FactionsUUID();
			} else if (Bukkit.getServer().getPluginManager().isPluginEnabled("MassiveCore")) {
				return new MassiveCore();
			} else {
				return new FactionsUUID();
			}
		} else if (Bukkit.getPluginManager().isPluginEnabled("Teams")) {
			return new SaberFactions();
		} else if (Bukkit.getPluginManager().isPluginEnabled("Towny")) {
			return new AdvancedTowny();
		} else if (Bukkit.getPluginManager().isPluginEnabled("FabledSkyBlock")) {
			return new FabledSkyblock();
		} else if (Bukkit.getPluginManager().isPluginEnabled("Guilds")) {
			return new Guild();
		} else if (Bukkit.getPluginManager().isPluginEnabled("GangsPlus")) {
			return new GangsPlus();
		} else if (Bukkit.getPluginManager().isPluginEnabled("Clans")) {
			return new Clans();
		} else if (Bukkit.getPluginManager().isPluginEnabled("Feudal")) {
			return new Feudal();
		} else if (Bukkit.getPluginManager().isPluginEnabled("SimpleClans")) {
			return new SimpleClans();
		} else if (Bukkit.getServer().getPluginManager().isPluginEnabled("AdvancedClans")) {
			return new AdvancedClan();
		} else if (Bukkit.getServer().getPluginManager().isPluginEnabled("LegacyFactions")) {
			return new LegacyFactions();
		} else if (Bukkit.getServer().getPluginManager().isPluginEnabled("Lands")) {
			return new Lands();
		} else if (Bukkit.getServer().getPluginManager().isPluginEnabled("Kingdoms")) {
			return new Kingdoms();
		} else if (Bukkit.getServer().getPluginManager().isPluginEnabled("KingdomCraft")) {
			return new KingdomCraft();
		} else if (Bukkit.getServer().getPluginManager().isPluginEnabled("GoliathSkyBlock")) {
			return new GoliathSkyBlock();
		} else if (Bukkit.getPluginManager().isPluginEnabled("McMMO") && KOTH.getInstance().getConfig().getBoolean("MCMMO_USE_PARTIES")) {
			return new McMMO();
		} else if (Bukkit.getPluginManager().isPluginEnabled("UltimateClans")) {
			return new UltimateClans();
		} else if (Bukkit.getPluginManager().isPluginEnabled("GalacticSkyBlock")) {
			return new GalacticSkyblock();
		} else if (Bukkit.getPluginManager().isPluginEnabled("UltraPrisonCore")) {
			return new UltraPrisonCore();
		} else if (Bukkit.getPluginManager().isPluginEnabled("HCF")) {
			return new HCFNick();
		} else if (Bukkit.getPluginManager().isPluginEnabled("CrClans")) {
			return new CrClans();
		} else if (Bukkit.getPluginManager().isPluginEnabled("BetterTeams")) {
			return new BetterTeams();
		}
		return null;
	}

	public void runTeamHookLater() {
		Bukkit.getScheduler().runTaskLater(KOTH.getInstance(), () -> {
			KOTH.getInstance().getScoreboardManager().loadScoreboards();

			setAPI(getRawAPI());

			if (teamHook != null) {
				LoggerUtil.success("[KOTH] Successfully hooked into " + teamHook.getAPIName() + ".");
			}

			KOTHHandler.getInstance().setPluginLoaded(true);
		}, 40L);
	}

	public TeamHook getAPI() {
		return teamHook;
	}

	public void setAPI(TeamHook teamHook) {
		this.teamHook = teamHook;
	}

	private boolean isModernFactionsUUID() {
		return Bukkit.getPluginManager().isPluginEnabled("FactionsUUID") || ReflectionUtil.isPresent("dev.kitteh.factions.FPlayers");
	}

	private boolean isLegacyFactionsUUID() {
		if (!Bukkit.getPluginManager().isPluginEnabled("Factions")) {
			return false;
		}
		List<String> authors = Bukkit.getPluginManager().getPlugin("Factions").getDescription().getAuthors();
		return authors.contains("mbaxter") || authors.contains("CmdrKittens") || ReflectionUtil.isPresent("com.massivecraft.factions.FactionsPlugin");
	}

	private TeamHook getTeamHookFromString(String name) {
		switch (name) {
		case "FactionsBridge":
			return new FactionsBridgeHook();
		case "FactionsUUID":
			return isModernFactionsUUID() ? new FactionsUUIDModern() : new FactionsUUID();
		case "FactionsX":
			return new FactionsX();
		case "ASkyBlock":
			return new ASkyblock();
		case "BSkyBlock":
		case "AOneBlock":
		case "BentoBox":
			return new Bento();
		case "BetterTeams":
			return new BetterTeams();
		case "CrClans":
			return new CrClans();
		case "IridiumSkyblock":
			return new IridiumSkyblock();
		case "SuperiorSkyblock2":
			return new SuperiorSkyblock();
		case "SupremeFactions":
			return new FactionsUUID();
		case "SavageFactions":
			return new SavageFactions();
		case "SaberFactions":
			return new SaberFactions();
		case "MassiveCore":
			return new MassiveCore();
		case "Teams":
			return new SaberFactions();
		case "Towny":
			return new AdvancedTowny();
		case "FabledSkyBlock":
			return new FabledSkyblock();
		case "Guilds":
			return new Guild();
		case "GangsPlus":
			return new GangsPlus();
		case "Clans":
			return new Clans();
		case "Feudal":
			return new Feudal();
		case "SimpleClans":
			return new SimpleClans();
		case "AdvancedClans":
			return new AdvancedClan();
		case "LegacyFactions":
			return new LegacyFactions();
		case "Lands":
			return new Lands();
		case "Kingdoms":
			return new Kingdoms();
		case "KingdomCraft":
			return new KingdomCraft();
		case "GoliathSkyBlock":
			return new GoliathSkyBlock();
		case "McMMO":
			return new McMMO();
		case "GalacticSkyblock":
			return new GalacticSkyblock();
		case "UClans":
		case "UltimateClans":
			return new UltimateClans();
		case "JungleSkyblock":
			return new JungleSkyblock();
		case "UltraPrisonCore":
			return new UltraPrisonCore();
		default:
			return null;
		}
	}
}
