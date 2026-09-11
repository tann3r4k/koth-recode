package com.benzimmer123.koth.hooks.other;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.SirBlobman.combatlogx.api.event.PlayerUntagEvent;
import com.benzimmer123.koth.api.objects.KOTHPlayer;
import com.benzimmer123.koth.handlers.KOTHHandler;
import com.benzimmer123.koth.hooks.face.OtherHook;

public class CombatLog implements Listener, OtherHook {

	@EventHandler
	public void onUnTagEvent(PlayerUntagEvent e) {
		KOTHPlayer kothPlayer = KOTHHandler.getInstance().getKOTHPlayer(e.getPlayer());
		
		if (kothPlayer.getScoreboard() != null) {
			kothPlayer.getScoreboard().update();
		}
	}

	@Override
	public void setup() {
	}

	@Override
	public String getAPIName() {
		return "CombatLogX";
	}

}
