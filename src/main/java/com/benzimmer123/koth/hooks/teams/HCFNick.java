package com.benzimmer123.koth.hooks.teams;

import java.util.List;

import org.bukkit.entity.Player;

import com.benzimmer123.koth.hooks.face.TeamHook;
import com.benzimmer123.koth.util.LangUtil;
import com.google.common.collect.Lists;

import me.hulipvp.hcf.api.HCFAPI;

public class HCFNick implements TeamHook {

	@Override
	public boolean hasTeam(Player p) {
		if (HCFAPI.hasFaction(p)) {
			return true;
		}
		return false;
	}

	@Override
	public String getTeamLeader(Player p) {
		if (hasTeam(p)) {
			return HCFAPI.getFactionLeader(p);
		}
		return null;
	}

	@Override
	public String getTeamName(Player p) {
		if (hasTeam(p)) {
			return HCFAPI.getFactionName(p);
		}
		return LangUtil.NO_TEAM.toString().replaceAll("%player%", p.getName());
	}

	@Override
	public List<Player> getTeamPlayers(Player p) {
		List<Player> teamPlayers = Lists.newArrayList();
		if (hasTeam(p)) {
			teamPlayers.addAll(HCFAPI.getOnlineTeamMembers(p));
		}
		return teamPlayers;
	}

	@Override
	public String getTeamID(Player p) {
		return null;
	}

	@Override
	public String getAPIName() {
		return "HCF";
	}

	@Override
	public boolean exists(String teamID) {
		return true;
	}

}
