package com.benzimmer123.koth.hooks.teams;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.benzimmer123.koth.hooks.face.TeamHook;
import com.benzimmer123.koth.util.LangUtil;
import com.google.common.collect.Lists;
import com.wasteofplastic.askyblock.ASkyBlockAPI;

public class ASkyblock implements TeamHook {

	public boolean hasTeam(Player p) {
		if (ASkyBlockAPI.getInstance().hasIsland(p.getUniqueId()) || ASkyBlockAPI.getInstance().inTeam(p.getUniqueId())) {
			return true;
		}
		return false;
	}

	@Override
	public String getTeamLeader(Player p) {
		if (hasTeam(p)) {
			UUID leader;
			if (ASkyBlockAPI.getInstance().inTeam(p.getUniqueId())) {
				leader = ASkyBlockAPI.getInstance().getTeamLeader(p.getUniqueId());
			} else {
				leader = p.getUniqueId();
			}
			if (Bukkit.getPlayer(leader) != null) {
				return Bukkit.getPlayer(leader).getName();
			} else {
				return Bukkit.getOfflinePlayer(leader).getName();
			}
		}
		return null;
	}

	@Override
	public String getTeamName(Player p) {
		if (p != null) {
			if (hasTeam(p)) {
				if (ASkyBlockAPI.getInstance().inTeam(p.getUniqueId())) {
					return ASkyBlockAPI.getInstance().getIslandName(ASkyBlockAPI.getInstance().getTeamLeader(p.getUniqueId()));
				} else {
					return ASkyBlockAPI.getInstance().getIslandName(p.getUniqueId());
				}
			}
		}
		return LangUtil.NO_TEAM.toString().replaceAll("%player%", p.getName());
	}

	@Override
	public List<Player> getTeamPlayers(Player p) {
		List<Player> players = Lists.newArrayList();

		if (hasTeam(p)) {
			UUID leader;
			if (ASkyBlockAPI.getInstance().inTeam(p.getUniqueId())) {
				leader = ASkyBlockAPI.getInstance().getTeamLeader(p.getUniqueId());
			} else {
				leader = p.getUniqueId();
			}
			for (UUID uuid : ASkyBlockAPI.getInstance().getIslandOwnedBy(leader).getMembers()) {
				if (Bukkit.getPlayer(uuid) != null) {
					players.add(Bukkit.getPlayer(uuid));
				}
			}
		}

		return players;
	}
	
	@Override
	public boolean exists(String name) {
		 return true;
	}

	@Override
	public String getAPIName() {
		return "ASkyblock";
	}

	@Override
	public String getTeamID(Player p) {
		return null;
	}

}
