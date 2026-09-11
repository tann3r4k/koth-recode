package net.goliathmc.goliathskyblock.api;
public class SkyBlockAPI {
  public static SkyBlockAPI get() { return new SkyBlockAPI(); }
  public boolean hasIsland(org.bukkit.entity.Player p) { return false; }
  public org.bukkit.OfflinePlayer getIslandLeader(org.bukkit.entity.Player p) { return null; }
  public String getIslandName(org.bukkit.entity.Player p) { return ""; }
}
