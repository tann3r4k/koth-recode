package com.benzimmer123.koth.hooks.teams;

import java.util.List;

import org.bukkit.entity.Player;

import com.benzimmer123.koth.hooks.face.TeamHook;
import com.benzimmer123.koth.util.LangUtil;
import com.gmail.nossr50.api.PartyAPI;
import com.google.common.collect.Lists;

public class McMMO implements TeamHook {

	@Override
	public String getTeamName(Player p) {
		if (hasTeam(p)) {
			return PartyAPI.getPartyName(p);
		}
		return LangUtil.NO_TEAM.toString();
	}

	@Override
	public String getTeamLeader(Player p) {
		if (hasTeam(p)) {
			String name = getTeamName(p);
			return PartyAPI.getPartyLeader(name);
		}
		return null;
	}

	@Override
	public boolean hasTeam(Player p) {
		if (PartyAPI.inParty(p)) {
			return true;
		}
		return false;
	}

	@Override
	public List<Player> getTeamPlayers(Player p) {
		List<Player> teamPlayers = Lists.newArrayList();
		if (hasTeam(p)) {
			for (Player cp : PartyAPI.getOnlineMembers(p)) {
				teamPlayers.add(cp);
			}
		}
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
		return "McMMO";
	}
	
}
