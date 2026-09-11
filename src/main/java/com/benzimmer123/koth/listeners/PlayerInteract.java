package com.benzimmer123.koth.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.api.objects.KOTHArena;
import com.benzimmer123.koth.api.objects.KOTHPlayer;
import com.benzimmer123.koth.compatible.XMaterial;
import com.benzimmer123.koth.gui.OpeningGUI;
import com.benzimmer123.koth.handlers.KOTHHandler;
import com.benzimmer123.koth.util.ItemUtil;
import com.benzimmer123.koth.util.LangUtil;
import com.benzimmer123.koth.util.ServerVersionUtil;
import com.benzimmer123.koth.util.TimeUtil;
import com.benzimmer123.koth.versionspecific.Minecraft1_9;

public class PlayerInteract implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerInteract(PlayerInteractEvent e) {
		if (e.getAction() == Action.LEFT_CLICK_AIR)
			return;

		ItemStack item = e.getPlayer().getItemInHand();

		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getClickedBlock() == null) {
			if (!item.equals(ItemUtil.getStarterItem()))
				return;

			e.setCancelled(true);

			if (!e.getPlayer().hasPermission("KOTH.USESTARTERITEM") && !e.getPlayer().hasPermission("KOTH.*") && !e.getPlayer().isOp()) {
				LangUtil.sendMessage(e.getPlayer(), LangUtil.NO_PERMISSION.toString());
				return;
			}

			if (KOTH.getInstance().getKOTHManager().isActiveKOTH() && KOTH.getInstance().getConfig().getBoolean(
					"KOTH_STARTER_ITEM.ONE_KOTH_ACTIVE")) {
				LangUtil.sendMessage(e.getPlayer(), LangUtil.ONLY_ONE_KOTH.toString());
				return;
			}

			KOTHPlayer kothPlayer = KOTHHandler.getInstance().getKOTHPlayer(e.getPlayer());

			if (KOTH.getInstance().getConfig().getBoolean("PLAYER_COOLDOWN.USE_COOLDOWN")) {
				if (!e.getPlayer().hasPermission("KOTH.COOLDOWNBYPASS") && !e.getPlayer().hasPermission("KOTH.*") && !e.getPlayer().isOp()) {
					if (kothPlayer.hasKOTHCooldown()) {
						long difference = kothPlayer.getKOTHCooldown() - System.currentTimeMillis();
						int total = (int) difference / 1000;
						String cooldownTime = new TimeUtil((int) total).formatTime();
						LangUtil.sendMessage(e.getPlayer(), LangUtil.COOLDOWN.toString().replaceAll("%timeleft%", cooldownTime));
						return;
					} else {
						long cooldown = KOTH.getInstance().getConfig().getInt("PLAYER_COOLDOWN.COOLDOWN_TIME") * 1000;
						long endTime = System.currentTimeMillis() + cooldown;
						kothPlayer.setKOTHCooldown(endTime);
					}
				}
			}

			if (KOTH.getInstance().getConfig().isSet("KOTH_STARTER_ITEM.MINIMUM_PLAYERS")) {
				int minimumPlayers = KOTH.getInstance().getConfig().getInt("KOTH_STARTER_ITEM.MINIMUM_PLAYERS");

				if (Bukkit.getOnlinePlayers().size() < minimumPlayers) {
					LangUtil.sendMessage(e.getPlayer(), LangUtil.MINIMUM_PLAYERS.toString().replaceAll("%players%", minimumPlayers + ""));
					return;
				}
			}

			new OpeningGUI(KOTH.getInstance()).openGUI(e.getPlayer(), false);

			if (item.getAmount() > 1)
				item.setAmount(item.getAmount() - 1);
			else
				e.getPlayer().setItemInHand(null);

			e.getPlayer().updateInventory();
		} else if (e.getClickedBlock() != null) {
			if (e.getClickedBlock().getType() == XMaterial.CHEST.parseMaterial()) {
				if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
					KOTH.getInstance().getRewardManager().checkLootLocations(e.getPlayer(), e.getClickedBlock());
				}

				Location loc = new Location(e.getClickedBlock().getLocation().getWorld(), e.getClickedBlock().getLocation().getBlockX(), e
						.getClickedBlock().getLocation().getBlockY(), e.getClickedBlock().getLocation().getBlockZ());
				KOTHArena koth = null;

				for (KOTHArena koths : KOTHHandler.getInstance().getKOTHS()) {
					if (koths.getKOTHLoot().getLootLocations().contains(loc)) {
						koth = koths;
					}
				}

				if (koth == null)
					return;

				e.setCancelled(true);
				KOTH.getInstance().getRewardManager().openKothRewards(e.getPlayer(), e.getAction(), koth);
				return;
			} else if (item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null && item.getItemMeta().getDisplayName()
					.equalsIgnoreCase(ChatColor.GREEN + "KOTH Selection Wand")) {
				if (e.getClickedBlock() == null)
					return;

				if (ServerVersionUtil.isAboveVersion(ServerVersionUtil.v1_9_R1) && Minecraft1_9.isUsingOffHand(e.getHand())) {
					return;
				}

				String pos = "1st";
				KOTHPlayer kothPlayer = KOTHHandler.getInstance().getKOTHPlayer(e.getPlayer());

				if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
					e.setCancelled(true);
					kothPlayer.addWandLocation(1, e.getClickedBlock().getLocation());
				} else if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
					e.setCancelled(true);
					kothPlayer.addWandLocation(2, e.getClickedBlock().getLocation());
					pos = "2nd";
				}

				LangUtil.sendMessage(e.getPlayer(), LangUtil.REGION_SELECTED.toString().replaceAll("%z%", e.getClickedBlock().getLocation()
						.getBlockZ() + "").replaceAll("%y%", e.getClickedBlock().getLocation().getBlockY() + "").replaceAll("%x%", e.getClickedBlock()
								.getLocation().getBlockX() + "").replaceAll("%pos%", pos));
			}
		}
	}
}
