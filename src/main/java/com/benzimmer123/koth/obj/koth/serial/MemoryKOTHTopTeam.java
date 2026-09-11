package com.benzimmer123.koth.obj.koth.serial;

import java.io.Serializable;

import org.bukkit.entity.Player;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.api.objects.KOTHTopTeam;
import com.benzimmer123.koth.handlers.TopHandler;
import com.benzimmer123.koth.storage.GsonStorage;

public class MemoryKOTHTopTeam implements KOTHTopTeam, Serializable, Comparable<KOTHTopTeam> {

	private static final long serialVersionUID = -1786515745837407747L;
	private int participations;
	private int wins;
	private final String teamName;
	private final String teamID;

	public MemoryKOTHTopTeam(Player player) {
		teamName = KOTH.getInstance().getTeamManager().getTeamName(player);
		teamID = KOTH.getInstance().getTeamManager().getTeamID(player);
		save();
		TopHandler.getInstance().addTeam(this);
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
		return teamName;
	}

	public String getTeamID() {
		return teamID;
	}

	@Override
	public int compareTo(KOTHTopTeam team) {
		return (int) (this.getWins() - team.getWins());
	}

	private String getFileString() {
		if (getTeamID() != null) {
			return getTeamID();
		} else {
			return getTeamName();
		}
	}

	private void save() {
		GsonStorage.serialize(this, "top/teams/" + getFileString() + ".json", "team");
	}

}
