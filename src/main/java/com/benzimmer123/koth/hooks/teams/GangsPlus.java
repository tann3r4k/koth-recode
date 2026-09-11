package com.benzimmer123.koth.hooks.teams;

import java.util.List;

import org.bukkit.entity.Player;

import com.benzimmer123.koth.hooks.face.TeamHook;
import com.benzimmer123.koth.util.LangUtil;
import com.google.common.collect.Lists;

import net.brcdev.gangs.GangsPlusApi;

public class GangsPlus implements TeamHook {

	@Override
	public boolean hasTeam(Player p) {
		if (GangsPlusApi.isInGang(p)) {
			return true;
		}
		return false;
	}

	@Override
	public String getTeamLeader(Player p) {
		if (hasTeam(p)) {
			return GangsPlusApi.getPlayersGang(p).getOwner().getName();
		}
		return null;
	}

	@Override
	public String getTeamName(Player p) {
		if (hasTeam(p)) {
			return GangsPlusApi.getPlayersGang(p).getName();
		}
		return LangUtil.NO_TEAM.toString();
	}

	@Override
	public List<Player> getTeamPlayers(Player p) {
		List<Player> teamPlayers = Lists.newArrayList();
		if (hasTeam(p)) {
			teamPlayers.addAll(GangsPlusApi.getPlayersGang(p).getOnlineMembers());
		}
		return teamPlayers;
	}
	
	@Override
	public String getTeamID(Player p) {
		if (hasTeam(p)) {
			return "" + GangsPlusApi.getPlayersGang(p).getId();
		}
		return null;
	}
	
	@Override
	public boolean exists(String name) {
		return true;
	}

	@Override
	public String getAPIName() {
		return "GangsPlus";
	}

}