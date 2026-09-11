package com.benzimmer123.koth.hooks.face;

import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public interface TeamHook {

	String getAPIName();
	String getTeamID(Player p);
	String getTeamName(Player p);
	String getTeamLeader(Player p);
	List<Player> getTeamPlayers(Player p);
	boolean hasTeam(Player p);
	boolean exists(String teamID);

	default String getTeamID(OfflinePlayer p) {
		return p != null && p.getPlayer() != null ? getTeamID(p.getPlayer()) : null;
	}

}
