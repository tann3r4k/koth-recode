package com.benzimmer123.koth.listeners;

import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.api.objects.KOTHArena;
import com.benzimmer123.koth.handlers.KOTHHandler;
import com.benzimmer123.koth.obj.koth.TempInventory;
import com.benzimmer123.koth.obj.koth.TempKOTHPlayer;
import com.benzimmer123.koth.util.ReflectionUtil;

public class PlayerDeath implements Listener {

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		KOTHArena koth = KOTH.getInstance().getKOTHManager().isCapping(e.getEntity());

		if (koth != null) {
			koth.setCapper(null, true);
		}

		if (KOTH.getInstance().getConfig().getBoolean("KEEP_INVENTORY_WHILE_ACTIVE", false) || KOTH.getInstance().getConfig().getBoolean(
				"KEEP_INVENTORY_WHILE_ACTIVE.ENABLED", false)) {
			List<String> worlds = KOTH.getInstance().getConfig().getStringList("KEEP_INVENTORY_WHILE_ACTIVE.DISABLE_IN_WORLD");

			if (worlds.contains(e.getEntity().getLocation().getWorld().getName()))
				return;

			if (!KOTH.getInstance().getRegionManager().checkInventoryRestoreRegions(e.getEntity()))
				return;

			if (!KOTH.getInstance().getConfig().getBoolean("KEEP_INVENTORY_WHILE_ACTIVE.ONLY_IN_REGION.ENABLED")) {
				if (!KOTH.getInstance().getKOTHManager().isActiveKOTH())
					return;
			}

			if (!ReflectionUtil.exists("org.bukkit.event.entity.PlayerDeathEvent", "setKeepInventory")) {
				TempKOTHPlayer player = (TempKOTHPlayer) KOTHHandler.getInstance().getKOTHPlayer(e.getEntity());
				TempInventory tempInventory = new TempInventory(e.getEntity());
				player.setStoredInventory(tempInventory);
				e.getDrops().clear();
			} else {
				e.setKeepInventory(true);
				e.getDrops().clear();
			}

			e.setKeepLevel(true);
			e.setDroppedExp(0);
		}
	}
}
