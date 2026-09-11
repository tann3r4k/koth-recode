package me.neznamy.tab.shared;
public class TAB {
  public static TAB getInstance() { return new TAB(); }
  public me.neznamy.tab.api.TabPlayer getPlayer(java.util.UUID uuid) { return null; }
  public ScoreboardManager getScoreboardManager() { return new ScoreboardManager(); }
  public static class ScoreboardManager {
    public void setScoreboardVisible(me.neznamy.tab.api.TabPlayer player, boolean visible, boolean extra) {}
  }
}
