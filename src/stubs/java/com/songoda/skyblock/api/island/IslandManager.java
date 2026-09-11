package com.songoda.skyblock.api.island;
public class IslandManager {
  public static boolean hasIsland(org.bukkit.entity.Player p) { return false; }
  public Island getIsland(org.bukkit.entity.Player p) { return new Island(); }
  public java.util.List<java.util.UUID> getMembersOnline(Island island) { return java.util.Collections.emptyList(); }
  public static class Island {
    public java.util.UUID getOwnerUUID() { return new java.util.UUID(0, 0); }
    public java.util.UUID getIslandUUID() { return new java.util.UUID(0, 0); }
  }
}
