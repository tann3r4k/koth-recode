package com.benzimmer123.koth.hooks.teams;

import java.util.List;

import org.bukkit.entity.Player;

import com.benzimmer123.koth.hooks.face.TeamHook;
import com.benzimmer123.koth.util.LangUtil;
import com.google.common.collect.Lists;

import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;

public class SimpleClans implements TeamHook {

	@Override
	public boolean hasTeam(Player p) {
		ClanPlayer clanPlayer = net.sacredlabyrinth.phaed.simpleclans.SimpleClans.getInstance().getClanManager().getClanPlayer(p);
		if (clanPlayer != null && clanPlayer.getClan() != null) {
			return true;
		}
		return false;
	}

	@Override
	public String getTeamLeader(Player p) {
		if (hasTeam(p)) {
			ClanPlayer clanPlayer = net.sacredlabyrinth.phaed.simpleclans.SimpleClans.getInstance().getClanManager().getClanPlayer(p);
			return clanPlayer.getClan().getLeaders().get(0).getCleanName();
		}
		return null;
	}

	@Override
	public String getTeamName(Player p) {
		if (hasTeam(p)) {
			ClanPlayer clanPlayer = net.sacredlabyrinth.phaed.simpleclans.SimpleClans.getInstance().getClanManager().getClanPlayer(p);
			return clanPlayer.getClan().getName();
		}
		return LangUtil.NO_TEAM.toString();
	}

	@Override
	public List<Player> getTeamPlayers(Player p) {
		List<Player> teamPlayers = Lists.newArrayList();

		if (hasTeam(p)) {
			ClanPlayer clanPlayer = net.sacredlabyrinth.phaed.simpleclans.SimpleClans.getInstance().getClanManager().getClanPlayer(p);
			for (ClanPlayer player : clanPlayer.getClan().getMembers()) {
				teamPlayers.add(player.toPlayer());
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
		return "SimpleClans";
	}
}