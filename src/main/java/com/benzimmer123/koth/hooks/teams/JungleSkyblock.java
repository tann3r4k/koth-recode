package com.benzimmer123.koth.hooks.teams;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.benzimmer123.koth.hooks.face.TeamHook;
import com.benzimmer123.koth.util.LangUtil;
import com.google.common.collect.Lists;
import com.wasteofplastic.askyblock.entity.Island;
import com.wasteofplastic.askyblock.entity.MPlayer;

public class JungleSkyblock implements TeamHook {

	public boolean hasTeam(Player p) {
		MPlayer mPlayer = MPlayer.get(p);
		return mPlayer != null && mPlayer.getIsland() != null;
	}

	@Override
	public String getTeamLeader(Player p) {
		if (hasTeam(p)) {
			MPlayer mPlayer = MPlayer.get(p);
			Island island = mPlayer.getIsland();
			UUID leader = island.getOwner();
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
				MPlayer mPlayer = MPlayer.get(p);
				Island island = mPlayer.getIsland();
				return island.getName();
			}
		}
		return LangUtil.NO_TEAM.toString().replaceAll("%player%", p.getName());
	}

	@Override
	public List<Player> getTeamPlayers(Player p) {
		List<Player> players = Lists.newArrayList();

		if (hasTeam(p)) {
			MPlayer mPlayer = MPlayer.get(p);
			Island island = mPlayer.getIsland();
			for (UUID uuid : island.getMembers()) {
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
		return "JungleSkyblock";
	}

	@Override
	public String getTeamID(Player p) {
		return null;
	}

}
