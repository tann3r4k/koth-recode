package com.bgsoftware.superiorskyblock.api.wrappers;
public class SuperiorPlayer {
  public Island getIsland() { return new Island(); }
  public String getName() { return ""; }
  public java.util.UUID getUniqueId() { return new java.util.UUID(0, 0); }
  public org.bukkit.entity.Player asPlayer() { return null; }
  public static class Island {
    public SuperiorPlayer getOwner() { return new SuperiorPlayer(); }
    public String getName() { return ""; }
    public java.util.UUID getUniqueId() { return new java.util.UUID(0, 0); }
    public java.util.List<SuperiorPlayer> getIslandMembers(boolean includeOwner) { return java.util.Collections.emptyList(); }
  }
}
