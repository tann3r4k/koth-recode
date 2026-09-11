package com.benzimmer123.koth.hooks.teams;

import java.util.List;

import org.bukkit.entity.Player;
import org.kingdoms.constants.group.Kingdom;
import org.kingdoms.constants.player.KingdomPlayer;

import com.benzimmer123.koth.hooks.face.TeamHook;
import com.benzimmer123.koth.util.LangUtil;
import com.google.common.collect.Lists;

public class Kingdoms implements TeamHook {

	@Override
	public List<Player> getTeamPlayers(Player p) {
		List<Player> teamPlayers = Lists.newArrayList();
		
		if (hasTeam(p)) {
			KingdomPlayer kingdomPlayer = KingdomPlayer.getKingdomPlayer(p);
			teamPlayers.addAll(kingdomPlayer.getKingdom().getOnlineMembers());
		}

		return teamPlayers;
	}

	@Override
	public boolean hasTeam(Player p) {
		KingdomPlayer kingdomPlayer = KingdomPlayer.getKingdomPlayer(p);
		return kingdomPlayer.hasKingdom();
	}

	@Override
	public String getTeamName(Player p) {
		if (hasTeam(p)) {
			KingdomPlayer kingdomPlayer = KingdomPlayer.getKingdomPlayer(p);
			return kingdomPlayer.getKingdom().getName();
		}
		return LangUtil.NO_TEAM.toString();
	}

	@Override
	public String getTeamLeader(Player p) {
		if (hasTeam(p)) { // Contact dev for leader info
			return p.getName();
		}
		return null;
	}
	
	@Override
	public String getTeamID(Player p) {
		if (hasTeam(p)) {
			return KingdomPlayer.getKingdomPlayer(p).getKingdomId().toString();
		}
		return null;
	}
	
	public boolean exists(String id) {
		if (id == null)
			return false;
		Kingdom kingdom = Kingdom.getKingdom(id);
		if (kingdom == null || kingdom.isHomePublic())
			return false;
		return true;
	}
	
	@Override
	public String getAPIName() {
		return "Kingdoms";
	}

}
