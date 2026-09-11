package com.benzimmer123.koth.obj.koth.serial;

import java.io.Serializable;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.api.objects.KOTHTopPlayer;
import com.benzimmer123.koth.handlers.TopHandler;
import com.benzimmer123.koth.storage.GsonStorage;

public class MemoryKOTHTopPlayer implements KOTHTopPlayer, Serializable, Comparable<KOTHTopPlayer> {

	private static final long serialVersionUID = 2053975119095822870L;
	private int participations;
	private int wins;
	private String previousTeam;
	private final String playerName;
	private final String playerUUID;

	public MemoryKOTHTopPlayer(Player player) {
		playerName = player.getName();
		previousTeam = KOTH.getInstance().getTeamManager().getTeamName(player);
		playerUUID = player.getUniqueId().toString();
		save();
		TopHandler.getInstance().addPlayer(this);
	}

	public void setParticipations(int participations) {
		this.participations = participations;
		save();
	}

	public void setWins(int wins) {
		this.wins = wins;
		save();
	}

	public int getParticipations() {
		return participations;
	}

	public int getWins() {
		return wins;
	}

	public String getTeamName() {
		if (Bukkit.getPlayer(getPlayerName()) != null) {
			String teamName = KOTH.getInstance().getTeamManager().getTeamName(Bukkit.getPlayer(getPlayerName()));
			previousTeam = teamName;
		}
		return previousTeam;
	}

	public String getPlayerName() {
		return playerName;
	}

	public String getUUID() {
		return playerUUID;
	}

	@Override
	public int compareTo(KOTHTopPlayer player) {
		return (int) (this.getWins() - player.getWins());
	}

	private void save() {
		GsonStorage.serialize(this, "top/players/" + getUUID() + ".json", "player");
	}

}
