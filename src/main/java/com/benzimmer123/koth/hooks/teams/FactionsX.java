package com.benzimmer123.koth.hooks.teams;

import java.util.List;

import org.bukkit.entity.Player;

import com.benzimmer123.koth.hooks.face.TeamHook;
import com.benzimmer123.koth.util.LangUtil;
import com.google.common.collect.Lists;

import net.prosavage.factionsx.persist.data.Factions;
import net.prosavage.factionsx.persist.data.Players;

public class FactionsX implements TeamHook {

	@Override
	public boolean hasTeam(Player p) {
		if (Players.INSTANCE.getFplayers().containsKey(p.getUniqueId().toString())) {
			net.prosavage.factionsx.core.FPlayer fp = Players.INSTANCE.getFplayers().get(p.getUniqueId().toString());

			if (fp.getFaction() != null && !fp.getFaction().isSystemFaction()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getTeamLeader(Player p) {
		if (Players.INSTANCE.getFplayers().containsKey(p.getUniqueId().toString())) {
			net.prosavage.factionsx.core.FPlayer fp = Players.INSTANCE.getFplayers().get(p.getUniqueId().toString());

			if (fp.getFaction() != null && fp.getFaction().getLeader() != null) {
				return fp.getFaction().getLeader().getName();
			}
		}
		return null;
	}

	@Override
	public String getTeamName(Player p) {
		if (hasTeam(p)) {
			net.prosavage.factionsx.core.FPlayer fp = Players.INSTANCE.getFplayers().get(p.getUniqueId().toString());
			return fp.getFaction().getTag();
		}
		return LangUtil.NO_TEAM.toString();
	}

	@Override
	public List<Player> getTeamPlayers(Player p) {
		List<Player> teamPlayers = Lists.newArrayList();
		if (hasTeam(p)) {
			net.prosavage.factionsx.core.FPlayer fp = Players.INSTANCE.getFplayers().get(p.getUniqueId().toString());

			if (fp.getFaction() != null && !fp.getFaction().isWilderness()) {
				for (net.prosavage.factionsx.core.FPlayer fplayers : fp.getFaction().getOnlineMembers()) {
					teamPlayers.add(fplayers.getPlayer());
				}
			}
		}
		return teamPlayers;
	}
	

	@Override
	public String getTeamID(Player p) {
		if (hasTeam(p)) {
			net.prosavage.factionsx.core.FPlayer fp = Players.INSTANCE.getFplayers().get(p.getUniqueId().toString());
			return fp.getFaction().getId() + "";
		}
		return null;
	}
	
	@Override
	public boolean exists(String id) {
		long longID;
		try {
			longID = Long.parseLong(id);
		} catch (NumberFormatException e) {
			return false;
		}
		net.prosavage.factionsx.core.Faction fac = Factions.INSTANCE.getFactions().get(longID);
		if (fac == null || fac.isWilderness() || fac.isSafezone() || fac.isWarzone())
			return false;
		return true;
	}
	
	@Override
	public String getAPIName() {
		return "FactionsX";
	}

}
