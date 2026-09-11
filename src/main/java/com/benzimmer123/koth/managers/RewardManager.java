package com.benzimmer123.koth.managers;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.api.enums.EditLootAction;
import com.benzimmer123.koth.api.enums.LootType;
import com.benzimmer123.koth.api.objects.KOTHArena;
import com.benzimmer123.koth.api.objects.KOTHLootItem;
import com.benzimmer123.koth.api.objects.KOTHPlayer;
import com.benzimmer123.koth.compatible.XMaterial;
import com.benzimmer123.koth.handlers.KOTHHandler;
import com.benzimmer123.koth.util.ItemUtil;
import com.benzimmer123.koth.util.LangUtil;
import com.benzimmer123.koth.util.WeightedRandomUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class RewardManager {

	private final Random RANDOM = new Random();

	public int getInventorySize(KOTHArena koth) {
		int chestSize = KOTH.getInstance().getConfig().getInt("REWARDS.CHEST_SIZE");

		if (koth.getKOTHLoot().getLootItemStacks().size() > chestSize) {
			return 54;
		}

		if (chestSize < 9) {
			chestSize = 9;
		}

		return chestSize;
	}

	private void runCommands(List<Player> players, KOTHArena koth, String worldname) {
		for (Player player : players) {
			runCommands(player, koth, worldname);
		}
	}

	private void rewardPlayer(List<Player> players, KOTHArena koth) {
		for (Player player : players) {
			rewardPlayer(player, koth);
		}
	}

	private void runCommands(Player p, KOTHArena koth, String worldname) {
		for (String s : koth.getKOTHDetails().getRewardCmds()) {
			s = s.replaceAll("%team%", KOTH.getInstance().getTeamManager().getTeamName(p)).replaceAll("%koth%", koth.getName(true)).replaceAll(
					"%player%", p.getName()).replaceAll("%world%", worldname).replace("/", "");
			s = KOTH.getInstance().getPlaceholderManager().parsePlaceholders(p, s);
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), s);
		}
	}

	private void rewardPlayer(Player p, KOTHArena koth) {
		if (KOTH.getInstance().getConfig().getBoolean("REWARDS.USE_REWARDS")) {
			if (koth.getKOTHLoot().getLootType().equals(LootType.KEY)) {
				ItemStack key = ItemUtil.getKey();

				if (p.getInventory().firstEmpty() != -1) {
					p.getInventory().addItem(key);
				} else {
					LangUtil.sendMessage(p, LangUtil.INVENTORY_FULL.toString());
					p.getWorld().dropItemNaturally(p.getLocation(), key);
				}
			} else if (koth.getKOTHLoot().getLootType().equals(LootType.INVENTORY)) {
				addRewardItems(p.getInventory(), p, koth);
			}
		}
	}

	private Inventory addRewardItems(Inventory inv, Player player, KOTHArena koth) {
		boolean duplicateLoot = KOTH.getInstance().getConfig().getBoolean("REWARDS.DUPLICATE_LOOT");

		if (KOTH.getInstance().getConfig().getBoolean("REWARDS.GIVE_ALL_ITEMS")) {
			List<KOTHLootItem> loot = koth.getKOTHLoot().getLootItems(duplicateLoot);
			if (loot.isEmpty())
				return inv;

			for (KOTHLootItem lootItem : koth.getKOTHLoot().getLootItems(false)) {
				ItemStack item = lootItem.toItemStack();
				selectItem(inv, item, player);
			}

			return inv;
		}

		WeightedRandomUtil<KOTHLootItem> itemDrops = new WeightedRandomUtil<>();
		List<KOTHLootItem> currentItems = Lists.newArrayList();

		for (KOTHLootItem item : koth.getKOTHLoot().getLootItems(duplicateLoot)) {
			itemDrops.addEntry(item, item.getPercent());
			currentItems.add(item);
		}

		int min = KOTH.getInstance().getConfig().getInt("REWARDS.MIN_LOOT_ITEMS");
		int max = KOTH.getInstance().getConfig().getInt("REWARDS.MAX_LOOT_ITEMS");

		if (min == 0) { // Do not need at least one element
			for (int i = 0; i < max; i++) {
				if (i >= currentItems.size())
					break;
				KOTHLootItem kothLoot = currentItems.get(i);
				if (kothLoot.getPercent() == 100) {
					selectItem(inv, kothLoot.toItemStack(), player);
				} else if (getRandomItem(kothLoot, kothLoot.getPercent())) {
					selectItem(inv, kothLoot.toItemStack(), player);
				}
			}
			return inv;
		}

		int amountOfItems;

		if (min == max) {
			amountOfItems = min;
		} else {
			amountOfItems = RANDOM.nextInt(max - min) + min;
		}

		for (int i = 0; i < amountOfItems; i++) {
			KOTHLootItem item = itemDrops.getRandom();

			if (item == null)
				continue;

			ItemStack setItem = item.toItemStack();
			selectItem(inv, setItem, player);

			if (!duplicateLoot) {
				currentItems.remove(item);
				itemDrops.removeEntry(currentItems);
			}
		}

		return inv;
	}

	private boolean getRandomItem(KOTHLootItem lootItem, int percentChance) {
		double r = RANDOM.nextInt(100);
		if (r <= percentChance) {
			return true;
		}
		return false;
	}

	private void selectItem(Inventory inv, ItemStack item, Player player) {
		if (inv.firstEmpty() != -1) {
			inv.addItem(item);
		} else {
			LangUtil.sendMessage(player, LangUtil.INVENTORY_FULL.toString());
			player.getWorld().dropItemNaturally(player.getLocation(), item);
		}
	}

	public void setRewards(Inventory inv, Player p) {
		if (!p.getOpenInventory().getTitle().contains(":")) {
			return;
		}

		String kothName = p.getOpenInventory().getTitle().split(":")[1].trim();
		KOTHArena koth = KOTH.getInstance().getKOTHManager().getKOTHFromString(kothName);

		if (koth == null) {
			LangUtil.sendMessage(p, LangUtil.INVALID_KOTH.toString());
			return;
		}

		int i = 0;
		Map<Integer, ItemStack> toAdd = Maps.newHashMap();

		for (ItemStack loot : inv.getContents()) {
			if (loot != null) {
				toAdd.put(i, loot);
			}
			i++;
		}

		koth.getKOTHLoot().clearLootItems();

		for (Integer slot : toAdd.keySet()) {
			koth.getKOTHLoot().addLootItem(toAdd.get(slot), 100, slot);
		}

		koth.save();

		LangUtil.sendMessage(p, LangUtil.SET_LOOT.toString());
	}

	public void checkLootLocations(Player p, Block block) {
		KOTHPlayer kothPlayer = KOTHHandler.getInstance().getKOTHPlayer(p);

		if (kothPlayer.getEditingKOTHLoot() == null)
			return;

		if (kothPlayer.hasEditingLootExpired()) {
			LangUtil.sendMessage(p, LangUtil.REQUEST_TIMED_OUT.toString());
			kothPlayer.setEditingKOTHLoot(null);
			kothPlayer.setEditingKOTHLootAction(null);
			kothPlayer.setEditingKOTHLootTimeout(0);
			return;
		}

		if (kothPlayer.getEditingKOTHLootAction().equals(EditLootAction.ADD)) {
			kothPlayer.getEditingKOTHLoot().getKOTHLoot().addLootLocation(block.getLocation());
			kothPlayer.getEditingKOTHLoot().save();
			LangUtil.sendMessage(p, LangUtil.ADDED_LOOT_LOCATION.toString());
		} else if (kothPlayer.getEditingKOTHLootAction().equals(EditLootAction.REMOVE)) {
			kothPlayer.getEditingKOTHLoot().getKOTHLoot().removeLootLocation(block.getLocation());
			kothPlayer.getEditingKOTHLoot().save();
			LangUtil.sendMessage(p, LangUtil.REMOVED_LOOT_LOCATION.toString());
		}

		kothPlayer.setEditingKOTHLoot(null);
		kothPlayer.setEditingKOTHLootAction(null);
		kothPlayer.setEditingKOTHLootTimeout(0);
	}

	public void rewardTeamMembers(Player capper, KOTHArena koth) {
		if (KOTH.getInstance().getConfig().getBoolean("REWARDS.GIVE_FACTION_LEADER") || KOTH.getInstance().getConfig().getBoolean(
				"REWARDS.GIVE_TEAM_LEADER")) {
			if (KOTH.getInstance().getTeamManager().getTeamLeader(capper) != null && Bukkit.getPlayer(KOTH.getInstance().getTeamManager()
					.getTeamLeader(capper)) != null) {
				LangUtil.sendMessage(capper, LangUtil.REWARDS_GIVEN_TO_LEADER.toString());
				capper = Bukkit.getPlayer(KOTH.getInstance().getTeamManager().getTeamLeader(capper));
			} else {
				LangUtil.sendMessage(capper, LangUtil.LEADER_OFFLINE.toString());
			}
		}

		if (!KOTH.getInstance().getConfig().getBoolean("REWARDS.GIVE_ALL_MEMBERS")) {
			runCommands(capper, koth, koth.getKOTHLocation().getWorld());
			rewardPlayer(capper, koth);
		} else {
			List<Player> teamMembers = KOTH.getInstance().getTeamManager().getTeamPlayers(capper);
			rewardPlayer(teamMembers, koth);
			runCommands(teamMembers, koth, koth.getKOTHLocation().getWorld());
		}
	}

	@SuppressWarnings("deprecation")
	public void openKothRewards(Player p, Action action, KOTHArena koth) {
		KOTHPlayer kothPlayer = KOTHHandler.getInstance().getKOTHPlayer(p);

		if (kothPlayer.getEditingKOTHLoot() == null) {
			Inventory inv = null;

			if (action.equals(Action.RIGHT_CLICK_BLOCK)) {
				if (!p.getItemInHand().isSimilar(ItemUtil.getKey())) {
					LangUtil.sendMessage(p, LangUtil.NO_KEY.toString());
					return;
				}

				inv = Bukkit.createInventory(p, getInventorySize(koth), LangUtil.replaceString(KOTH.getInstance().getConfig().getString(
						"REWARDS.REAL_INVENTORY_NAME")));

				inv = addRewardItems(inv, p, koth);

				int amount = p.getItemInHand().getAmount();

				if (amount > 1) {
					p.getItemInHand().setAmount(amount - 1);
					p.setItemInHand(p.getItemInHand());
				} else {
					p.setItemInHand(new ItemStack(XMaterial.AIR.parseMaterial()));
				}

				p.updateInventory();
			} else if (action.equals(Action.LEFT_CLICK_BLOCK)) {
				if (!KOTH.getInstance().getConfig().getBoolean("REWARDS.LEFT_CLICK_SHOW_LOOT"))
					return;

				if (!p.hasPermission("KOTH.VIEWLOOT") && !p.hasPermission("KOTH.*") && !p.isOp()) {
					LangUtil.sendMessage(p, LangUtil.NO_PERMISSION.toString());
					return;
				}

				inv = Bukkit.createInventory(null, getInventorySize(koth), LangUtil.replaceString(KOTH.getInstance().getConfig().getString(
						"REWARDS.VIEW_INVENTORY_NAME")));

				for (KOTHLootItem item : koth.getKOTHLoot().getLootItems(true)) {
					if (item.getSlot() < inv.getSize())
						inv.setItem(item.getSlot(), item.toItemStack());
				}
			}

			p.openInventory(inv);
		}
	}
}
