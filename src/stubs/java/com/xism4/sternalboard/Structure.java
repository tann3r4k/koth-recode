package com.xism4.sternalboard;
public class Structure {
  public static Structure getInstance() { return new Structure(); }
  public Manager getScoreboardManager() { return new Manager(); }
  public static class Manager {
    public void removeScoreboard(org.bukkit.entity.Player p) {}
    public void setScoreboard(org.bukkit.entity.Player p) {}
  }
}
