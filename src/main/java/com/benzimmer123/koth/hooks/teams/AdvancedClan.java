package com.benzimmer123.koth.hooks.teams;

import java.util.List;

import org.bukkit.entity.Player;

import com.benzimmer123.koth.hooks.face.TeamHook;
import com.benzimmer123.koth.util.LangUtil;
import com.google.common.collect.Lists;

import me.jose.advancedclans.AdvancedClans;
import me.jose.advancedclans.objects.Clan;

public class AdvancedClan implements TeamHook {

	@Override
	public String getTeamName(Player p) {
		if (hasTeam(p)) {
			return AdvancedClans.getInstance().getPlayerManager().getClanPlayer(p.getName()).getClan().getName();
		}
		return LangUtil.NO_TEAM.toString();
	}

	@Override
	public String getTeamLeader(Player p) {
		if (hasTeam(p)) {
			Clan clan = AdvancedClans.getInstance().getPlayerManager().getClanPlayer(p.getName()).getClan();
			return clan.getLeader();
		}
		return null;
	}

	@Override
	public boolean hasTeam(Player p) {
		if (AdvancedClans.getInstance().getPlayerManager().hasClan(p)) {
			return true;
		}
		return false;
	}

	@Override
	public List<Player> getTeamPlayers(Player p) {
		List<Player> teamPlayers = Lists.newArrayList();
		if (hasTeam(p)) {
			Clan clan = AdvancedClans.getInstance().getPlayerManager().getClanPlayer(p.getName()).getClan();
			for (Player cp : clan.getOnlinePlayers()) {
				teamPlayers.add(cp);
			}
		}
		return teamPlayers;
	}

	@Override
	public String getAPIName() {
		return "AdvancedClans";
	}

	@Override
	public String getTeamID(Player p) {
		if (hasTeam(p)) {
			Clan clan = AdvancedClans.getInstance().getPlayerManager().getClanPlayer(p.getName()).getClan();
			return "" + clan.getId();
		}
		return null;
	}

	@Override
	public boolean exists(String name) {
		int id;
		try {
			id = Integer.parseInt(name);
		} catch (NumberFormatException e) {
			return false;
		}
		Clan clan = AdvancedClans.getInstance().getClanManager().getClanFromId(id);
		if (clan != null)
			return true;
		return false;
	}

}
