package com.benzimmer123.koth.hooks.teams;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.benzimmer123.koth.hooks.face.TeamHook;
import com.benzimmer123.koth.util.LangUtil;
import com.google.common.collect.Lists;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.Factions;
import com.massivecraft.factions.FactionsPlugin;
import com.massivecraft.factions.scoreboards.FScoreboard;
import com.massivecraft.factions.scoreboards.FSidebarProvider;
import com.massivecraft.factions.scoreboards.sidebar.FDefaultSidebar;

public class FactionsUUID implements TeamHook {

	private Faction faction(OfflinePlayer player) {
		if (player == null) {
			return null;
		}
		FPlayer fPlayer = FPlayers.getInstance().getByOfflinePlayer(player);
		if (fPlayer == null || !fPlayer.hasFaction()) {
			return null;
		}
		Faction faction = fPlayer.getFaction();
		if (faction == null || faction.isWilderness() || faction.isSafeZone() || faction.isWarZone()) {
			return null;
		}
		return faction;
	}

	@Override
	public boolean hasTeam(Player p) {
		return faction(p) != null;
	}

	@Override
	public String getTeamID(Player p) {
		return getTeamID((OfflinePlayer) p);
	}

	@Override
	public String getTeamID(OfflinePlayer p) {
		FPlayer fPlayer = p == null ? null : FPlayers.getInstance().getByOfflinePlayer(p);
		Faction faction = faction(p);
		return faction == null || fPlayer == null ? null : fPlayer.getFactionId();
	}

	@Override
	public String getTeamName(Player p) {
		Faction faction = faction(p);
		return faction == null ? LangUtil.NO_TEAM.toString() : faction.getTag();
	}

	@Override
	public String getTeamLeader(Player p) {
		Faction faction = faction(p);
		if (faction == null || faction.getFPlayerAdmin() == null) {
			return null;
		}
		return faction.getFPlayerAdmin().getName();
	}

	@Override
	public List<Player> getTeamPlayers(Player p) {
		List<Player> teamPlayers = Lists.newArrayList();
		Faction faction = faction(p);
		if (faction == null) {
			return teamPlayers;
		}
		for (FPlayer member : faction.getFPlayersWhereOnline(true)) {
			if (member != null && member.getPlayer() != null) {
				teamPlayers.add(member.getPlayer());
			}
		}
		return teamPlayers;
	}

	public void toggleScoreboard(Player p) {
		FPlayer me = FPlayers.getInstance().getByPlayer(p);
		if (me == null) {
			return;
		}

		try {
			if (!FactionsPlugin.getInstance().conf().scoreboard().constant().isEnabled()) {
				return;
			}
			FScoreboard.init(me);
			FScoreboard sb = FScoreboard.get(me);
			Method method = sb.getClass().getMethod("setDefaultSidebar", FSidebarProvider.class);
			method.invoke(sb, new FDefaultSidebar());
			sb.setSidebarVisibility(me.showScoreboard());
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| RuntimeException ignored) {
		}
	}

	@Override
	public String getAPIName() {
		return "FactionsUUID";
	}

	@Override
	public boolean exists(String id) {
		if (id == null) {
			return false;
		}
		Faction fac = Factions.getInstance().getFactionById(id);
		return fac != null && !fac.isWilderness() && !fac.isSafeZone() && !fac.isWarZone();
	}
}
