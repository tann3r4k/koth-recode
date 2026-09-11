package com.r4g3baby.simplescore;
public class SimpleScore {
  public static class Api {
    public static Manager getManager() { return new Manager(); }
  }
  public static class Manager {
    public java.util.Map<org.bukkit.entity.Player, PlayerData> getPlayersData() { return java.util.Collections.emptyMap(); }
  }
  public static class PlayerData {
    public void disable(org.bukkit.plugin.Plugin plugin) {}
    public void enable(org.bukkit.plugin.Plugin plugin) {}
  }
}
