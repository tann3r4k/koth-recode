package com.benzimmer123.koth.hooks.teams;

import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.benzimmer123.koth.hooks.face.TeamHook;
import com.benzimmer123.koth.util.LangUtil;
import com.google.common.collect.Lists;

import dev.kitteh.factions.FPlayer;
import dev.kitteh.factions.FPlayers;
import dev.kitteh.factions.Faction;
import dev.kitteh.factions.Factions;

public class FactionsUUIDModern implements TeamHook {

	private Faction faction(OfflinePlayer player) {
		if (player == null || FPlayers.fPlayers() == null) {
			return null;
		}
		FPlayer fPlayer = FPlayers.fPlayers().get(player);
		if (fPlayer == null || !fPlayer.hasFaction()) {
			return null;
		}
		Faction faction = fPlayer.faction();
		if (faction == null || faction.isWilderness() || faction.isSafeZone() || faction.isWarZone()) {
			return null;
		}
		return faction;
	}

	@Override
	public boolean hasTeam(Player p) {
		return faction(p) != null;
	}

	@Override
	public String getTeamID(Player p) {
		return getTeamID((OfflinePlayer) p);
	}

	@Override
	public String getTeamID(OfflinePlayer p) {
		Faction faction = faction(p);
		return faction == null ? null : Integer.toString(faction.id());
	}

	@Override
	public String getTeamName(Player p) {
		Faction faction = faction(p);
		return faction == null ? LangUtil.NO_TEAM.toString() : faction.tag();
	}

	@Override
	public String getTeamLeader(Player p) {
		Faction faction = faction(p);
		if (faction == null || faction.admin() == null) {
			return null;
		}
		return faction.admin().name();
	}

	@Override
	public List<Player> getTeamPlayers(Player p) {
		List<Player> teamPlayers = Lists.newArrayList();
		Faction faction = faction(p);
		if (faction == null) {
			return teamPlayers;
		}
		List<Player> online = faction.membersOnlineAsPlayers();
		if (online != null) {
			teamPlayers.addAll(online);
		}
		return teamPlayers;
	}

	@Override
	public boolean exists(String teamID) {
		if (teamID == null || Factions.factions() == null) {
			return false;
		}
		Faction faction;
		try {
			faction = Factions.factions().get(Integer.parseInt(teamID));
		} catch (NumberFormatException ignored) {
			faction = Factions.factions().get(teamID);
		}
		return faction != null && !faction.isWilderness() && !faction.isSafeZone() && !faction.isWarZone();
	}

	@Override
	public String getAPIName() {
		return "FactionsUUID";
	}
}
