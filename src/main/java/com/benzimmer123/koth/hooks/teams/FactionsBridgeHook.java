package com.benzimmer123.koth.hooks.teams;

import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.benzimmer123.koth.hooks.face.TeamHook;
import com.benzimmer123.koth.util.LangUtil;
import com.google.common.collect.Lists;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FPlayer;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FactionsAPI;

public class FactionsBridgeHook implements TeamHook {

	private FactionsAPI api() {
		return FactionsBridge.getFactionsAPI();
	}

	private Faction faction(OfflinePlayer player) {
		FactionsAPI api = api();
		if (api == null || player == null) {
			return null;
		}
		FPlayer fPlayer = api.getFPlayer(player);
		if (fPlayer == null || !fPlayer.hasFaction()) {
			return null;
		}
		Faction faction = fPlayer.getFaction();
		if (faction == null || faction.isWilderness() || faction.isWarZone() || faction.isSafeZone() || faction.isServerFaction()) {
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
		return faction == null ? null : faction.getId();
	}

	@Override
	public String getTeamName(Player p) {
		Faction faction = faction(p);
		return faction == null ? LangUtil.NO_TEAM.toString() : faction.getName();
	}

	@Override
	public String getTeamLeader(Player p) {
		Faction faction = faction(p);
		if (faction == null || faction.getLeader() == null) {
			return null;
		}
		return faction.getLeader().getName();
	}

	@Override
	public List<Player> getTeamPlayers(Player p) {
		List<Player> players = Lists.newArrayList();
		Faction faction = faction(p);
		if (faction == null) {
			return players;
		}
		for (FPlayer member : faction.getOnlineMembers()) {
			if (member != null && member.getPlayer() != null) {
				players.add(member.getPlayer());
			}
		}
		return players;
	}

	@Override
	public boolean exists(String teamID) {
		FactionsAPI api = api();
		if (api == null || teamID == null) {
			return false;
		}
		Faction faction = api.getFaction(teamID);
		return faction != null && !faction.isWilderness() && !faction.isWarZone() && !faction.isSafeZone() && !faction.isServerFaction();
	}

	@Override
	public String getAPIName() {
		return "FactionsBridge";
	}
}
