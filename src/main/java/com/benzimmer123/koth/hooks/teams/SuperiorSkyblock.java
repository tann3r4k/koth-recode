package com.benzimmer123.koth.hooks.teams;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.benzimmer123.koth.hooks.face.TeamHook;
import com.benzimmer123.koth.util.LangUtil;
import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.wrappers.SuperiorPlayer;
import com.google.common.collect.Lists;

public class SuperiorSkyblock implements TeamHook {

	@Override
	public boolean hasTeam(Player p) {
		if (SuperiorSkyblockAPI.getPlayer(p) != null) {
			if (SuperiorSkyblockAPI.getPlayer(p).getIsland() != null) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getTeamLeader(Player p) {
		if (hasTeam(p)) {
			if (SuperiorSkyblockAPI.getPlayer(p).getIsland().getOwner().asPlayer() != null) {
				return SuperiorSkyblockAPI.getPlayer(p).getIsland().getOwner().asPlayer().getName();
			}
		}
		return null;
	}

	@Override
	public String getTeamName(Player p) {
		if (hasTeam(p)) {
			return SuperiorSkyblockAPI.getPlayer(p).getIsland().getName().isEmpty() ? SuperiorSkyblockAPI.getPlayer(p).getIsland().getOwner()
					.getName() : SuperiorSkyblockAPI.getPlayer(p).getIsland().getName();
		}
		return LangUtil.NO_TEAM.toString();
	}

	@Override
	public List<Player> getTeamPlayers(Player p) {
		List<Player> teamPlayers = Lists.newArrayList();
		if (hasTeam(p)) {
			for (SuperiorPlayer sp : SuperiorSkyblockAPI.getPlayer(p).getIsland().getIslandMembers(true)) {
				if (Bukkit.getPlayer(sp.getUniqueId()) != null)
					teamPlayers.add(Bukkit.getPlayer(sp.getUniqueId()));
			}
		}
		return teamPlayers;
	}

	@Override
	public String getTeamID(Player p) {
		if (hasTeam(p)) {
			return SuperiorSkyblockAPI.getPlayer(p).getIsland().getUniqueId().toString();
		}
		return null;
	}
	
	@Override
	public boolean exists(String name) {
		return true;
	}

	@Override
	public String getAPIName() {
		return "SuperiorSkyblock";
	}
}
