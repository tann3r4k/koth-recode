package com.benzimmer123.koth.listeners;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.api.objects.KOTHArena;

public class EntityDamageByEntity implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
		if (!KOTH.getInstance().getConfig().getBoolean("DISABLE_KNOCKBACK_IN_KOTH"))
			return;

		if (e.getEntityType() != EntityType.PLAYER)
			return;

		if (e.getDamager() == null)
			return;

		if (e.getDamager().getType() != EntityType.PLAYER)
			return;

		Player damager = (Player) e.getDamager();

		if (damager.getItemInHand() == null)
			return;

		if (damager.getItemInHand().getEnchantments().containsKey(Enchantment.KNOCKBACK)) {
			for (KOTHArena koth : KOTH.getInstance().getKOTHManager().getActiveKOTHs()) {
				if (koth.contains(e.getEntity().getLocation())) {
					e.setCancelled(true);
					((Damageable) e.getEntity()).damage(e.getFinalDamage());
					Vector knockbackVector = damager.getLocation().getDirection().multiply(1.15).setY(0.4);
					e.getEntity().setVelocity(knockbackVector);
					break;
				}
			}
		}
	}
}