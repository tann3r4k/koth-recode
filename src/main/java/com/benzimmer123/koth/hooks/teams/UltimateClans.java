package com.benzimmer123.koth.hooks.teams;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.benzimmer123.koth.hooks.face.TeamHook;
import com.benzimmer123.koth.util.LangUtil;
import com.google.common.collect.Lists;

public class UltimateClans implements TeamHook {

	private final me.ulrich.clans.packets.interfaces.UClans clan = (me.ulrich.clans.packets.interfaces.UClans) Bukkit.getPluginManager().getPlugin(
			"UltimateClans");

	@Override
	public List<Player> getTeamPlayers(Player p) {
		List<Player> teamPlayers = Lists.newArrayList();

		if (hasTeam(p)) {
			for (UUID player : clan.getPlayerAPI().getPlayerClan(p.getUniqueId()).getOnlineMembers()) {
				if (Bukkit.getPlayer(player) != null)
					teamPlayers.add(Bukkit.getPlayer(player));
			}
		}

		return teamPlayers;
	}

	@Override
	public boolean hasTeam(Player p) {
		return clan.getPlayerAPI().hasClan(p.getUniqueId()) && clan.getPlayerAPI().getPlayerClan(p.getUniqueId()) != null;
	}

	@Override
	public String getTeamName(Player p) {
		if (hasTeam(p)) {
			return LangUtil.replaceString(clan.getPlayerAPI().getPlayerClan(p.getUniqueId()).getTag());
		}
		return LangUtil.NO_TEAM.toString();
	}

	@Override
	public String getTeamLeader(Player p) {
		if (hasTeam(p)) {
			UUID teamLeaderUUID = clan.getPlayerAPI().getPlayerClan(p.getUniqueId()).getLeader();

			if (Bukkit.getPlayer(teamLeaderUUID) != null) {
				return Bukkit.getPlayer(teamLeaderUUID).getName();
			} else {
				return Bukkit.getOfflinePlayer(teamLeaderUUID).getName();
			}
		}
		return null;
	}

	public String getTeamID(Player p) {
		if (hasTeam(p)) {
			return clan.getPlayerAPI().getPlayerClan(p.getUniqueId()).getId() + "";
		}
		return null;
	}

	@Override
	public boolean exists(String name) {
		return true;
	}

	@Override
	public String getAPIName() {
		return "UltimateClans";
	}
}
