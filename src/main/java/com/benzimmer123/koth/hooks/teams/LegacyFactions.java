package com.benzimmer123.koth.hooks.teams;

import java.util.List;

import org.bukkit.entity.Player;

import com.benzimmer123.koth.hooks.face.TeamHook;
import com.benzimmer123.koth.util.LangUtil;
import com.google.common.collect.Lists;

import net.redstoneore.legacyfactions.entity.FPlayer;
import net.redstoneore.legacyfactions.entity.Faction;
import net.redstoneore.legacyfactions.entity.FactionColl;

public class LegacyFactions implements TeamHook {

	public boolean hasTeam(Player p) {
		Faction fac = FactionColl.get(p);
		if (fac != null && !fac.isWilderness()) {
			return true;
		}
		return false;
	}

	public String getTeamLeader(Player p) {
		if (FactionColl.get(p) != null) {
			return FactionColl.get(p).getOwner().getName();
		}
		return null;
	}

	public String getTeamName(Player p) {
		Faction fac = FactionColl.get(p);
		if (fac != null && !fac.isWilderness()) {
			return fac.getTag();
		} else {
			return LangUtil.NO_TEAM.toString();
		}
	}

	@Override
	public List<Player> getTeamPlayers(Player p) {
		List<Player> teamPlayers = Lists.newArrayList();
		Faction fac = FactionColl.get(p);
		if (fac != null && !fac.isWilderness()) {
			for (FPlayer fp : fac.getMembers()) {
				teamPlayers.add(fp.getPlayer());
			}
		}
		return teamPlayers;
	}
	
	@Override
	public String getTeamID(Player p) {
		if (hasTeam(p)) {
			return FactionColl.get(p).getId();
		}
		return null;
	}
	
	@Override
	public boolean exists(String id) {
		if (id == null)
			return false;
		Faction fac = FactionColl.get().getFactionById(id);
		if (fac == null || fac.isWilderness() || fac.isSafeZone() || fac.isWarZone())
			return false;
		return true;
	}

	@Override
	public String getAPIName() {
		return "LegacyFactions";
	}
	
}
