package com.benzimmer123.koth.hooks.teams;

import java.util.List;

import org.bukkit.entity.Player;
import org.stellardev.galacticskyblock.api.SkyBlockAPI;
import org.stellardev.galacticskyblock.coll.APlayerColl;
import org.stellardev.galacticskyblock.entity.APlayer;
import org.stellardev.galacticskyblock.entity.Island;

import com.benzimmer123.koth.hooks.face.TeamHook;
import com.benzimmer123.koth.util.LangUtil;
import com.google.common.collect.Lists;

public class GalacticSkyblock implements TeamHook {

	@Override
	public boolean hasTeam(Player p) {
		if (SkyBlockAPI.hasIsland(p)) {
			return true;
		}
		return false;
	}

	@Override
	public String getTeamLeader(Player p) {
		if (hasTeam(p)) {
			return SkyBlockAPI.getIslandLeader(p).getName();
		}
		return null;
	}

	@Override
	public String getTeamName(Player p) {
		if (hasTeam(p)) {
			return SkyBlockAPI.getIslandName(p);
		}
		return LangUtil.NO_TEAM.toString().replaceAll("%player%", p.getName());
	}

	@Override
	public List<Player> getTeamPlayers(Player p) {
		List<Player> teamPlayers = Lists.newArrayList();
		if (hasTeam(p)) {
			Island island = APlayerColl.get().getIsland(p.getUniqueId());
			island.getAPlayersWhereOnline(true).stream().forEach(aPlayer -> {
				teamPlayers.add(aPlayer.getPlayer());
			});
		}
		return teamPlayers;
	}

	@Override
	public String getTeamID(Player p) {
		if (hasTeam(p)) {
			APlayer user = APlayer.get(p.getUniqueId());
			return user.getIsland().getId();
		}
		return null;
	}
	
	@Override
	public boolean exists(String name) {
		return true;
	}

	@Override
	public String getAPIName() {
		return "GalacticSkyblock";
	}

}
