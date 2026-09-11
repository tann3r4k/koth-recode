package me.jasperjh.animatedscoreboard;
public class AnimatedScoreboard extends org.bukkit.plugin.java.JavaPlugin {
  public Handler getScoreboardHandler() { return new Handler(); }
  public static class Handler {
    public PlayerBoard getPlayer(java.util.UUID uuid) { return new PlayerBoard(); }
  }
  public static class PlayerBoard {
    public void disableScoreboard() {}
    public void enableScoreboard() {}
  }
}
