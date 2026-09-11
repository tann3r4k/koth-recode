package com.benzimmer123.koth.hooks.teams;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.bukkit.entity.Player;

import com.benzimmer123.koth.hooks.face.TeamHook;
import com.benzimmer123.koth.util.LangUtil;
import com.google.common.collect.Lists;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Factions;
import com.massivecraft.factions.scoreboards.FScoreboard;
import com.massivecraft.factions.scoreboards.FSidebarProvider;
import com.massivecraft.factions.scoreboards.sidebar.FDefaultSidebar;

public class SavageFactions implements TeamHook {

	@Override
	public boolean hasTeam(Player p) {
		if (FPlayers.getInstance().getByPlayer(p) != null) {
			FPlayer fp = FPlayers.getInstance().getByPlayer(p);

			if (fp.getFaction() != null && !fp.getFaction().isWilderness()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getTeamLeader(Player p) {
		if (FPlayers.getInstance().getByPlayer(p) != null) {
			FPlayer fp = FPlayers.getInstance().getByPlayer(p);

			if (fp.getFaction() != null && fp.getFaction().getFPlayerAdmin() != null) {
				return fp.getFaction().getFPlayerAdmin().getName();
			}
		}
		return null;
	}

	@Override
	public String getTeamName(Player p) {
		if (FPlayers.getInstance().getByPlayer(p) != null) {
			FPlayer fp = FPlayers.getInstance().getByPlayer(p);

			if (fp.getFaction() != null && !fp.getFaction().isWilderness()) {
				return fp.getFaction().getTag();
			}
		}
		return LangUtil.NO_TEAM.toString();
	}

	@Override
	public List<Player> getTeamPlayers(Player p) {
		List<Player> teamPlayers = Lists.newArrayList();
		if (FPlayers.getInstance().getByPlayer(p) != null) {
			FPlayer fp = FPlayers.getInstance().getByPlayer(p);

			if (fp.getFaction() != null && !fp.getFaction().isWilderness()) {
				for (FPlayer fplayers : fp.getFaction().getFPlayersWhereOnline(true)) {
					if (!fplayers.isAlt())
						teamPlayers.add(fplayers.getPlayer());
				}
			}
		}
		return teamPlayers;
	}

	public void toggleScoreboard(Player p){
		FPlayer me = FPlayers.getInstance().getByPlayer(p);
		FScoreboard.init(me);
		FScoreboard sb = FScoreboard.get(me);

		Method method;

		try {
			method = sb.getClass().getMethod("setDefaultSidebar", FSidebarProvider.class, int.class);
			method.invoke(sb, new FDefaultSidebar(), com.massivecraft.factions.SavageFactions.plugin.getConfig().getInt("default-update-interval", 20));
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}

		sb.setSidebarVisibility(me.showScoreboard());
	}
	
	@Override
	public String getTeamID(Player p) {
		if (hasTeam(p)) {
			return com.massivecraft.factions.FPlayers.getInstance().getByPlayer(p).getFactionId();
		}
		return null;
	}
	
	@Override
	public boolean exists(String id) {
		com.massivecraft.factions.Faction fac = Factions.getInstance().getFactionById(id);
		if (fac == null || fac.isWilderness() || fac.isSafeZone() || fac.isWarZone())
			return false;
		return true;
	}
	
	@Override
	public String getAPIName() {
		return "SavageFactions";
	}
}