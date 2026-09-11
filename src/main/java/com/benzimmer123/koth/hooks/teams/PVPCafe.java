package com.benzimmer123.koth.hooks.teams;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.benzimmer123.koth.hooks.face.TeamHook;
import com.benzimmer123.koth.util.LangUtil;
import com.google.common.collect.Lists;

import net.pvpcafe.revival.team.object.TeamObject;

public class PVPCafe implements TeamHook {

	@Override
	public List<Player> getTeamPlayers(Player p) {
		List<Player> teamPlayers = Lists.newArrayList();

		if (hasTeam(p)) {
			TeamObject team = net.pvpcafe.revival.team.Team.getTeamData(net.pvpcafe.revival.team.Team.getPlayerTeamName(p));
			for (Player cp : team.getTeamOnlinePlayers()) {
				teamPlayers.add(cp);
			}
		}

		return teamPlayers;
	}

	@Override
	public boolean hasTeam(Player p) {
		if (!net.pvpcafe.revival.team.Team.getPlayerTeamName(p).equalsIgnoreCase("None")) {
			return true;
		}
		return false;
	}

	@Override
	public String getTeamName(Player p) {
		if (hasTeam(p)) {
			return net.pvpcafe.revival.team.Team.getPlayerTeamName(p);
		}
		return LangUtil.NO_TEAM.toString();
	}

	@SuppressWarnings("deprecation")
	@Override
	public String getTeamLeader(Player p) {
		if (hasTeam(p)) {
			TeamObject team = net.pvpcafe.revival.team.Team.getTeamData(net.pvpcafe.revival.team.Team.getPlayerTeamName(p));

			if (Bukkit.getPlayer(team.getOwnerUUID()) != null) {
				return Bukkit.getPlayer(team.getOwnerUUID()).getName();
			} else {
				return Bukkit.getOfflinePlayer(team.getOwnerUUID()).getName();
			}
		}
		return null;
	}

	@Override
	public String getTeamID(Player p) {
		return null;
	}
	
	@Override
	public boolean exists(String name) {
		return true;
	}

	@Override
	public String getAPIName() {
		return "PvPCafe";
	}
}
