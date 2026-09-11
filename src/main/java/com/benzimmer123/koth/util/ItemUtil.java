package com.benzimmer123.koth.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.compatible.XMaterial;
import com.benzimmer123.koth.versionspecific.Minecraft1_8;
import com.google.common.collect.Lists;

public class ItemUtil {

	public static ItemStack getWand() {
		ItemStack kothWand = new ItemStack(XMaterial.STICK.parseMaterial());
		ItemMeta kothWandMeta = kothWand.getItemMeta();
		kothWandMeta.setDisplayName(ChatColor.GREEN + "KOTH Selection Wand");
		kothWand.setItemMeta(kothWandMeta);
		return kothWand;
	}

	public static ItemStack getStarterItem() {
		ItemStack item = new ItemStack(XMaterial.matchXMaterial(KOTH.getInstance().getConfig().getString("KOTH_STARTER_ITEM.MATERIAL")).get()
				.parseMaterial());
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(LangUtil.replaceString(KOTH.getInstance().getConfig().getString("KOTH_STARTER_ITEM.NAME")));
		List<String> lores = KOTH.getInstance().getConfig().getStringList("KOTH_STARTER_ITEM.LORE");
		List<String> replacedlores = Lists.newArrayList();
		for (String s : lores) {
			replacedlores.add(LangUtil.replaceString(s));
		}
		im.setLore(replacedlores);
		item.setItemMeta(im);
		return item;
	}

	public static ItemStack getStarterItem(int amount, String type) {
		ItemStack item = new ItemStack(XMaterial.PAPER.parseMaterial());
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(ChatColor.GREEN + "" + amount + " " + type);
		item.setItemMeta(itemMeta);
		return item;
	}

	public static ItemStack getKey() {
		int amount;
		String material;
		String name;
		List<String> lore = KOTH.getInstance().getConfig().getStringList("REWARDS.KEY.ITEM_DISPLAY_LORE");
		material = KOTH.getInstance().getConfig().getString("REWARDS.KEY.ITEM_MATERIAL", "TRIPWIRE_HOOK");
		amount = KOTH.getInstance().getConfig().getInt("REWARDS.KEY.ITEM_AMOUNT", 1);
		name = KOTH.getInstance().getConfig().getString("REWARDS.KEY.ITEM_DISPLAY_NAME", "&aKOTH Key");
		ItemStack key = new ItemStack(XMaterial.matchXMaterial(material).get().parseMaterial(), amount);
		ItemMeta keymeta = key.getItemMeta();
		keymeta.setDisplayName(LangUtil.replaceString(name));
		List<String> lores = lore;
		List<String> replacedLores = Lists.newArrayList();

		for (String s : lores) {
			replacedLores.add(LangUtil.replaceString(s));
		}

		keymeta.setLore(replacedLores);
		key.setItemMeta(keymeta);

		if (ServerVersionUtil.isAboveVersion(ServerVersionUtil.v1_8_R1) && KOTH.getInstance().getConfig().getBoolean("REWARDS.KEY.GLOW")) {
			key = Minecraft1_8.addGlow(key);
		}

		return key;
	}

	public static ItemStack fromBase64(String data) throws IOException {
		try {
			ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64.getMimeDecoder().decode(data));
			BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
			ItemStack item = (ItemStack) dataInput.readObject();
			dataInput.close();
			return item;
		} catch (ClassNotFoundException e) {
			throw new IOException("Unable to decode class type.", e);
		}
	}

	public static String itemStackToBase64(ItemStack item) throws IllegalStateException {
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
			dataOutput.writeObject(item);
			dataOutput.close();
			return Base64.getMimeEncoder().encodeToString(outputStream.toByteArray());
		} catch (Exception e) {
			throw new IllegalStateException("Unable to save item stacks.", e);
		}
	}

}
