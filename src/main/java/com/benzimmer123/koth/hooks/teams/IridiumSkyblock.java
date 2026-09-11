package com.benzimmer123.koth.hooks.teams;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.benzimmer123.koth.hooks.face.TeamHook;
import com.benzimmer123.koth.util.LangUtil;
import com.google.common.collect.Lists;
import com.iridium.iridiumskyblock.api.IridiumSkyblockAPI;
import com.iridium.iridiumskyblock.database.User;

public class IridiumSkyblock implements TeamHook {

	@Override
	public boolean hasTeam(Player p) {
		if (IridiumSkyblockAPI.getInstance().getUser(p) != null && IridiumSkyblockAPI.getInstance().getUser(p).getIsland().isPresent()) {
			return true;
		}
		return false;
	}

	@Override
	public String getTeamLeader(Player p) {
		if (hasTeam(p)) {
			if (Bukkit.getPlayer(IridiumSkyblockAPI.getInstance().getUser(p).getIsland().get().getOwner().getUuid()) != null) {
				return Bukkit.getPlayer(IridiumSkyblockAPI.getInstance().getUser(p).getIsland().get().getOwner().getUuid()).getName();
			} else {
				return Bukkit.getOfflinePlayer(IridiumSkyblockAPI.getInstance().getUser(p).getIsland().get().getOwner().getUuid()).getName();
			}
		}
		return null;
	}

	@Override
	public String getTeamName(Player p) {
		if (hasTeam(p)) {
			return IridiumSkyblockAPI.getInstance().getUser(p).getIsland().get().getName();
		}
		return LangUtil.NO_TEAM.toString().replaceAll("%player%", p.getName());
	}

	@Override
	public List<Player> getTeamPlayers(Player p) {
		List<Player> teamPlayers = Lists.newArrayList();
		if (hasTeam(p)) {
			for (User sPlayer : IridiumSkyblockAPI.getInstance().getUser(p).getIsland().get().getMembers()) {
				if (Bukkit.getPlayer(sPlayer.getUuid()) != null) {
					teamPlayers.add(Bukkit.getPlayer(sPlayer.getUuid()));
				}
			}
		}
		return teamPlayers;
	}

	@Override
	public String getTeamID(Player p) {
		if (hasTeam(p)) {
			return IridiumSkyblockAPI.getInstance().getUser(p).getIsland().get().getId() + "";
		}
		return null;
	}

	@Override
	public boolean exists(String name) {
		return true;
	}
	
	@Override
	public String getAPIName() {
		return "IridiumSkyblock";
	}

}
