package com.benzimmer123.koth.hooks.teams;

import java.util.List;

import org.bukkit.entity.Player;

import com.benzimmer123.koth.hooks.face.TeamHook;
import com.benzimmer123.koth.util.LangUtil;
import com.booksaw.betterTeams.PlayerRank;
import com.booksaw.betterTeams.Team;
import com.google.common.collect.Lists;

public class BetterTeams implements TeamHook {

	@Override
	public boolean hasTeam(Player p) {
		if (Team.getTeam(p) != null) {
			return true;
		}
		return false;
	}

	@Override
	public String getTeamLeader(Player p) {
		if (hasTeam(p)) {
			if (Team.getTeam(p).getMembers() != null && Team.getTeam(p).getMembers().getRank(PlayerRank.OWNER) != null && !Team.getTeam(p)
					.getMembers().getRank(PlayerRank.OWNER).isEmpty() && Team.getTeam(p).getMembers().getRank(PlayerRank.OWNER).get(0)
							.getPlayer() != null) {
				return Team.getTeam(p).getMembers().getRank(PlayerRank.OWNER).get(0).getPlayer().getName();
			}
		}
		return null;
	}

	@Override
	public String getTeamName(Player p) {
		if (hasTeam(p)) {
			return Team.getTeam(p).getTag();
		}
		return LangUtil.NO_TEAM.toString();
	}

	@Override
	public List<Player> getTeamPlayers(Player p) {
		List<Player> teamPlayers = Lists.newArrayList();
		if (hasTeam(p)) {
			teamPlayers.addAll(Team.getTeam(p).getMembers().getOnlinePlayers());
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
		return "BetterTeams";
	}

}
