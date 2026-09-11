package com.benzimmer123.koth.hooks.teams;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.benzimmer123.koth.hooks.face.TeamHook;
import com.benzimmer123.koth.util.LangUtil;
import com.google.common.collect.Lists;

import us.forseth11.feudal.kingdoms.Kingdom;
import us.forseth11.feudal.kingdoms.Member;
import us.forseth11.feudal.user.User;

public class Feudal implements TeamHook {

	@Override
	public boolean hasTeam(Player p) {
		User user = us.forseth11.feudal.core.Feudal.getAPI().getUser(p.getUniqueId());
		Kingdom kingdom = us.forseth11.feudal.core.Feudal.getAPI().getKingdom(user);
		if (kingdom != null) {
			return true;
		}
		return false;
	}

	@Override
	public String getTeamLeader(Player p) {
		if (hasTeam(p)) {
			User user = us.forseth11.feudal.core.Feudal.getAPI().getUser(p.getUniqueId());
			Kingdom kingdom = us.forseth11.feudal.core.Feudal.getAPI().getKingdom(user);
			return kingdom.getMembersOrdered().get(0).getUser().getName();
		}
		return null;
	}

	@Override
	public String getTeamName(Player p) {
		if (hasTeam(p)) {
			User user = us.forseth11.feudal.core.Feudal.getAPI().getUser(p.getUniqueId());
			Kingdom kingdom = us.forseth11.feudal.core.Feudal.getAPI().getKingdom(user);
			return kingdom.getName();
		}
		return LangUtil.NO_TEAM.toString();
	}

	@Override
	public String getTeamID(Player p) {
		if (hasTeam(p)) {
			User user = us.forseth11.feudal.core.Feudal.getAPI().getUser(p.getName());
			Kingdom kingdom = us.forseth11.feudal.core.Feudal.getAPI().getKingdom(user);
			return kingdom.getUUID().toString();
		}
		return null;
	}

	@Override
	public List<Player> getTeamPlayers(Player p) {
		List<Player> teamPlayers = Lists.newArrayList();
		teamPlayers.add(p);

		if (hasTeam(p)) {
			User user = us.forseth11.feudal.core.Feudal.getAPI().getUser(p.getUniqueId());
			Kingdom kingdom = us.forseth11.feudal.core.Feudal.getAPI().getKingdom(user);
			for (Member member : kingdom.getMembersOrdered()) {
				if (Bukkit.getPlayer(member.getUser().getName()) != null) {
					teamPlayers.add(Bukkit.getPlayer(member.getUser().getName()));
				}
			}
		}

		return teamPlayers;
	}

	@Override
	public boolean exists(String name) {
		return true;
	}

	@Override
	public String getAPIName() {
		return "Feudal";
	}

}
