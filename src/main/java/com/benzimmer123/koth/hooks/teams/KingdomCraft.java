package com.benzimmer123.koth.hooks.teams;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.benzimmer123.koth.hooks.face.TeamHook;
import com.benzimmer123.koth.util.LangUtil;
import com.google.common.collect.Lists;
import com.gufli.kingdomcraft.api.KingdomCraftProvider;
import com.gufli.kingdomcraft.api.domain.Kingdom;
import com.gufli.kingdomcraft.api.domain.User;

public class KingdomCraft implements TeamHook {

	private final com.gufli.kingdomcraft.api.KingdomCraft api = KingdomCraftProvider.get();

	@Override
	public List<Player> getTeamPlayers(Player p) {
		List<Player> teamPlayers = Lists.newArrayList();

		if (hasTeam(p)) {
			User user = api.getPlayer(p.getUniqueId()).getUser();
			Kingdom kd = user.getKingdom();
			Set<UUID> users = kd.getMembers().keySet();
			for (UUID foundUser : users) {
				if (Bukkit.getPlayer(foundUser) != null) {
					teamPlayers.add(Bukkit.getPlayer(foundUser));
				}
			}
		}

		return teamPlayers;
	}

	@Override
	public boolean hasTeam(Player p) {
		User user = api.getPlayer(p.getUniqueId()).getUser();
		return user.getKingdom() != null;
	}

	@Override
	public String getTeamName(Player p) {
		if (hasTeam(p)) {
			User user = api.getPlayer(p.getUniqueId()).getUser();
			Kingdom kd = user.getKingdom();
			return kd.getName();
		}
		return LangUtil.NO_TEAM.toString();
	}

	@Override
	public String getTeamLeader(Player p) {
		if (hasTeam(p)) {
			return p.getName();
		}
		return null;
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
		return "KingdomCraft";
	}
}
