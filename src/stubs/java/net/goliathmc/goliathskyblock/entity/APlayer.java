package net.goliathmc.goliathskyblock.entity;
public class APlayer {
  public static APlayer get(java.util.UUID uuid) { return new APlayer(); }
  public Island getIsland() { return new Island(); }
  public static class Island {
    public java.util.Collection<org.bukkit.entity.Player> getOnlinePlayers() { return java.util.Collections.emptyList(); }
    public String getId() { return ""; }
  }
}
