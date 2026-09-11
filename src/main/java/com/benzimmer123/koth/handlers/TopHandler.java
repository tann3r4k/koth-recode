package com.benzimmer123.koth.handlers;

import java.io.File;
import java.util.LinkedHashMap;

import org.bukkit.entity.Player;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.api.objects.KOTHTopPlayer;
import com.benzimmer123.koth.api.objects.KOTHTopTeam;
import com.benzimmer123.koth.obj.koth.serial.MemoryKOTHTopPlayer;
import com.benzimmer123.koth.obj.koth.serial.MemoryKOTHTopTeam;
import com.benzimmer123.koth.storage.GsonStorage;
import com.google.common.collect.Maps;

public class TopHandler {

	private final static TopHandler INSTANCE;
	private LinkedHashMap<String, KOTHTopTeam> topTeams;
	private LinkedHashMap<String, KOTHTopPlayer> topPlayers;

	static {
		INSTANCE = new TopHandler();
	}

	private TopHandler() {
		topTeams = Maps.newLinkedHashMap();
		topPlayers = Maps.newLinkedHashMap();

		for (File file : new File(KOTH.getInstance().getDataFolder() + "/top/players").listFiles()) {
			KOTHTopPlayer player = GsonStorage.deserialize(MemoryKOTHTopPlayer.class, file.getPath(), "player");
			if (player != null) {
				addPlayer(player);
			}
		}

		for (File file : new File(KOTH.getInstance().getDataFolder() + "/top/teams").listFiles()) {
			KOTHTopTeam team = GsonStorage.deserialize(MemoryKOTHTopTeam.class, file.getPath(), "team");
			if (team != null) {
				addTeam(team);
			}
		}
	}

	public void setTopTeams(LinkedHashMap<String, KOTHTopTeam> topTeams) {
		this.topTeams = topTeams;
	}

	public void setTopPlayers(LinkedHashMap<String, KOTHTopPlayer> result) {
		this.topPlayers = result;
	}

	public LinkedHashMap<String, KOTHTopTeam> getTeams() {
		return topTeams;
	}

	public LinkedHashMap<String, KOTHTopPlayer> getPlayers() {
		return topPlayers;
	}

	public void addPlayer(KOTHTopPlayer player) {
		topPlayers.put(player.getUUID(), player);
	}

	public void addTeam(KOTHTopTeam team) {
		if (team.getTeamID() != null) {
			topTeams.put(team.getTeamID(), team);
		} else {
			topTeams.put(team.getTeamName(), team);
		}
	}

	public KOTHTopTeam getTeam(Player player) {
		String team;

		if (KOTH.getInstance().getTeamManager().getTeamID(player) != null) {
			team = KOTH.getInstance().getTeamManager().getTeamID(player);
		} else {
			team = KOTH.getInstance().getTeamManager().getTeamName(player);
		}

		if (topTeams.containsKey(team)) {
			return topTeams.get(team);
		}

		return new MemoryKOTHTopTeam(player);
	}

	public KOTHTopPlayer getPlayer(Player player) {
		if (topPlayers.containsKey(player.getUniqueId().toString())) {
			return topPlayers.get(player.getUniqueId().toString());
		}
		return new MemoryKOTHTopPlayer(player);
	}

	public static TopHandler getInstance() {
		return INSTANCE;
	}

}
