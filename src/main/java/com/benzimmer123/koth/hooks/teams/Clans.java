package com.benzimmer123.koth.hooks.teams;

import java.util.List;

import org.bukkit.entity.Player;

import com.benzimmer123.koth.hooks.face.TeamHook;
import com.benzimmer123.koth.util.LangUtil;
import com.google.common.collect.Lists;

import Clans.ClanConfiguration;

public class Clans implements TeamHook {

	@Override
	public boolean hasTeam(Player p) {
		if (new ClanConfiguration().getClan(p) != null) {
			return true;
		}
		return false;
	}

	@Override
	public String getTeamLeader(Player p) {
		return p.getName();
	}

	@Override
	public String getTeamName(Player p) {
		if (new ClanConfiguration().getClan(p) != null) {
			return new ClanConfiguration().getClan(p);
		}
		return LangUtil.NO_TEAM.toString();
	}

	@Override
	public List<Player> getTeamPlayers(Player p) {
		List<Player> teamPlayers = Lists.newArrayList();
		teamPlayers.add(p);
		return teamPlayers;
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
		return "Clans";
	}

}
