package com.benzimmer123.koth.hooks.teams;

import java.util.List;

import org.bukkit.entity.Player;

import com.benzimmer123.koth.hooks.face.TeamHook;
import com.benzimmer123.koth.util.LangUtil;
import com.google.common.collect.Lists;

import net.goliathmc.goliathskyblock.api.SkyBlockAPI;
import net.goliathmc.goliathskyblock.entity.APlayer;

public class GoliathSkyBlock implements TeamHook {

	@Override
	public boolean hasTeam(Player p) {
		if (SkyBlockAPI.get().hasIsland(p)) {
			return true;
		}
		return false;
	}

	@Override
	public String getTeamLeader(Player p) {
		if (hasTeam(p)) {
			return SkyBlockAPI.get().getIslandLeader(p).getName();
		}
		return null;
	}

	@Override
	public String getTeamName(Player p) {
		if (hasTeam(p)) {
			return SkyBlockAPI.get().getIslandName(p);
		}
		return LangUtil.NO_TEAM.toString().replaceAll("%player%", p.getName());
	}

	@Override
	public List<Player> getTeamPlayers(Player p) {
		List<Player> teamPlayers = Lists.newArrayList();
		if (hasTeam(p)) {
			APlayer user = APlayer.get(p.getUniqueId());
			teamPlayers.addAll(user.getIsland().getOnlinePlayers());
		}
		return teamPlayers;
	}

	@Override
	public String getTeamID(Player p) {
		if (hasTeam(p)) {
			APlayer user = APlayer.get(p.getUniqueId());
			return user.getIsland().getId();
		}
		return null;
	}

	@Override
	public boolean exists(String name) {
		return true;
	}
	
	@Override
	public String getAPIName() {
		return "GoliathSkyBlock";
	}

}
