package com.benzimmer123.koth.managers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.api.objects.KOTHTopPlayer;
import com.benzimmer123.koth.api.objects.KOTHTopTeam;
import com.benzimmer123.koth.handlers.TopHandler;
import com.benzimmer123.koth.util.LangUtil;

public class TopManager {

	public void addTopWin(Player capper) {
		if (KOTH.getInstance().getConfig().getBoolean("KOTH_TOP.ENABLED")) {
			KOTHTopPlayer player = TopHandler.getInstance().getPlayer(capper);
			player.setWins(player.getWins() + 1);

			if (KOTH.getInstance().getTeamManager().hasTeam(capper)) {
				KOTHTopTeam team = TopHandler.getInstance().getTeam(capper);
				team.setWins(team.getWins() + 1);
			}
		}
	}

	public void addTopParticiaption(Player found) {
		if (KOTH.getInstance().getConfig().getBoolean("KOTH_TOP.ENABLED")) {
			KOTHTopPlayer player = TopHandler.getInstance().getPlayer(found);
			player.setParticipations(player.getParticipations() + 1);

			if (KOTH.getInstance().getTeamManager().hasTeam(found)) {
				KOTHTopTeam team = TopHandler.getInstance().getTeam(found);
				team.setParticipations(team.getParticipations() + 1);
			}
		}
	}

	public void listTeamsPage(CommandSender sender, int pageNumber) {
		Map<String, KOTHTopTeam> kothMap = TopHandler.getInstance().getTeams();
		List<KOTHTopTeam> kothTeams = kothMap.values().stream().collect(Collectors.toList());

		final int pageheight = 10;
		int pagecount = (kothTeams.size() / pageheight) + 1;
		if (pageNumber > pagecount)
			pageNumber = pagecount;
		else if (pageNumber < 1)
			pageNumber = 1;
		int start = (pageNumber - 1) * pageheight;
		int end = start + pageheight;
		if (end > kothTeams.size())
			end = kothTeams.size();

		LangUtil.sendMessage(sender, LangUtil.KOTH_TOP_TITLE.toString().replaceAll("%page%", pageNumber + "").replaceAll("%maxpage%", pagecount
				+ ""));

		for (KOTHTopTeam players : kothTeams.subList(start, end)) {
			int position = kothTeams.indexOf(players) + 1;

			LangUtil.sendMessage(sender, LangUtil.KOTH_TOP_ENTRY.toString().replaceAll("%pos%", position + "").replaceAll("%captured%", players
					.getWins() + "").replaceAll("%player%", players.getTeamName()).replaceAll("%team%", players.getTeamName()).replaceAll(
							"%participations%", "" + players.getParticipations()));
		}
	}

	public void listPlayersPage(CommandSender sender, int pageNumber) {
		LinkedHashMap<String, KOTHTopPlayer> kothMap = TopHandler.getInstance().getPlayers();
		List<KOTHTopPlayer> kothPlayers = kothMap.values().stream().collect(Collectors.toList());

		final int pageheight = 10;
		int pagecount = (kothPlayers.size() / pageheight) + 1;
		if (pageNumber > pagecount)
			pageNumber = pagecount;
		else if (pageNumber < 1)
			pageNumber = 1;
		int start = (pageNumber - 1) * pageheight;
		int end = start + pageheight;
		if (end > kothPlayers.size())
			end = kothPlayers.size();

		LangUtil.sendMessage(sender, LangUtil.KOTH_TOP_TITLE.toString().replaceAll("%page%", pageNumber + "").replaceAll("%maxpage%", pagecount
				+ ""));

		for (KOTHTopPlayer players : kothPlayers.subList(start, end)) {
			int position = kothPlayers.indexOf(players) + 1;

			LangUtil.sendMessage(sender, LangUtil.KOTH_TOP_ENTRY.toString().replaceAll("%pos%", position + "").replaceAll("%captured%", players
					.getWins() + "").replaceAll("%team%", players.getPlayerName()).replaceAll("%player%", players.getPlayerName()).replaceAll(
							"%participations%", "" + players.getParticipations()));
		}
	}

	public void sortPlayerList() {
		LinkedHashMap<String, KOTHTopPlayer> treeMap = TopHandler.getInstance().getPlayers();
		treeMap = treeMap.entrySet().stream().sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue())).collect(Collectors.toMap(Map.Entry::getKey,
				Map.Entry::getValue, (oldV, newV) -> oldV, LinkedHashMap::new));
		TopHandler.getInstance().setTopPlayers(treeMap);
	}

	public void sortTeamList() {
		LinkedHashMap<String, KOTHTopTeam> treeMap = TopHandler.getInstance().getTeams();
		treeMap = treeMap.entrySet().stream().sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue())).collect(Collectors.toMap(Map.Entry::getKey,
				Map.Entry::getValue, (oldV, newV) -> oldV, LinkedHashMap::new));
		TopHandler.getInstance().setTopTeams(treeMap);
	}
}
