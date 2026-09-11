package com.songoda.skyblock.api;
public class SkyBlockAPI {
  public static com.songoda.skyblock.api.island.IslandManager getIslandManager() { return new com.songoda.skyblock.api.island.IslandManager(); }
  public static Implementation getImplementation() { return new Implementation(); }
  public static class Implementation {
    public ScoreboardManager getScoreboardManager() { return new ScoreboardManager(); }
  }
  public static class ScoreboardManager {
    public void addDisabledPlayer(org.bukkit.entity.Player p) {}
    public void removeDisabledPlayer(org.bukkit.entity.Player p) {}
  }
}
