package com.benzimmer123.koth.managers;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.benzimmer123.koth.api.events.KothEndEvent;
import com.benzimmer123.koth.api.events.KothLoseCapEvent;
import com.benzimmer123.koth.api.events.KothStartCapEvent;
import com.benzimmer123.koth.api.events.KothStartEvent;
import com.benzimmer123.koth.api.events.KothWinEvent;
import com.benzimmer123.koth.api.events.ScoreboardDisableEvent;
import com.benzimmer123.koth.api.events.ScoreboardEnableEvent;
import com.benzimmer123.koth.api.events.ScoreboardUpdateEvent;
import com.benzimmer123.koth.api.objects.KOTHArena;

public class EventManager {

	public void callScoreboardDisableEvent(Player player) {
		ScoreboardDisableEvent scoreboardDisableEvent = new ScoreboardDisableEvent(player);
		Bukkit.getServer().getPluginManager().callEvent(scoreboardDisableEvent);
	}

	public boolean callScoreboardEnableEvent(Player player) {
		ScoreboardEnableEvent scoreboardEnableEvent = new ScoreboardEnableEvent(player);
		Bukkit.getServer().getPluginManager().callEvent(scoreboardEnableEvent);
		if (scoreboardEnableEvent.isCancelled())
			return false;
		return true;
	}

	public boolean callScoreboardUpdateEvent(Player player) {
		ScoreboardUpdateEvent scoreboardUpdateEvent = new ScoreboardUpdateEvent(player);
		Bukkit.getServer().getPluginManager().callEvent(scoreboardUpdateEvent);
		if (scoreboardUpdateEvent.isCancelled())
			return false;
		return true;
	}

	public boolean callKothStartEvent(KOTHArena koth, World world, int x, int y, int z) {
		KothStartEvent kothStartEvent = new KothStartEvent(koth, world, x, y, z);
		Bukkit.getServer().getPluginManager().callEvent(kothStartEvent);
		if (kothStartEvent.isCancelled())
			return false;
		return true;
	}

	public void callKothEndEvent(KOTHArena koth, World world, int x, int y, int z) {
		KothEndEvent kothEndEvent = new KothEndEvent(koth, world, x, y, z);
		Bukkit.getServer().getPluginManager().callEvent(kothEndEvent);
	}

	public boolean callKothWinEvent(KOTHArena koth, Player player, String teamName, World world, int x, int y, int z, int length) {
		KothWinEvent kothWinEvent = new KothWinEvent(koth, player, teamName, world, x, y, z, length);
		Bukkit.getServer().getPluginManager().callEvent(kothWinEvent);
		if (kothWinEvent.isCancelled())
			return false;
		return true;
	}

	public boolean callKothStartCapEvent(KOTHArena koth, Player player, String teamName, World world, int x, int y, int z) {
		KothStartCapEvent kothStartCapEvent = new KothStartCapEvent(koth, player, teamName, world, x, y, z);
		Bukkit.getServer().getPluginManager().callEvent(kothStartCapEvent);
		if (kothStartCapEvent.isCancelled())
			return false;
		return true;
	}

	public boolean callKothLoseCapEvent(KOTHArena koth, Player player, String teamName, World world, int x, int y, int z, int length) {
		KothLoseCapEvent kothLoseCapEvent = new KothLoseCapEvent(koth, player, teamName, world, x, y, z, length);
		Bukkit.getServer().getPluginManager().callEvent(kothLoseCapEvent);
		if (kothLoseCapEvent.isCancelled())
			return false;
		return true;
	}
}