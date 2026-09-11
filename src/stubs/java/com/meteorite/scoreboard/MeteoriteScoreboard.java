package com.meteorite.scoreboard;
public class MeteoriteScoreboard {
  public static MeteoriteScoreboard getInstance() { return new MeteoriteScoreboard(); }
  public Manager getScoreboardManager() { return new Manager(); }
  public static class Manager {
    public void removePlayerScoreboard(org.bukkit.entity.Player p) {}
    public void setPlayerScoreboard(org.bukkit.entity.Player p) {}
  }
}
