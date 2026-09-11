package org.kingdoms.constants.player;
public class KingdomPlayer {
  public static KingdomPlayer getKingdomPlayer(org.bukkit.entity.Player p) { return new KingdomPlayer(); }
  public boolean hasKingdom() { return false; }
  public org.kingdoms.constants.group.Kingdom getKingdom() { return new org.kingdoms.constants.group.Kingdom(); }
  public java.util.UUID getKingdomId() { return new java.util.UUID(0, 0); }
}
