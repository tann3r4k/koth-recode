package net.sacredlabyrinth.phaed.simpleclans;
public class ClanPlayer {
  public Clan getClan() { return new Clan(); }
  public org.bukkit.entity.Player toPlayer() { return null; }
  public String getCleanName() { return ""; }
  public static class Clan {
    public java.util.List<ClanPlayer> getLeaders() { return java.util.Collections.emptyList(); }
    public String getName() { return ""; }
    public java.util.List<ClanPlayer> getMembers() { return java.util.Collections.emptyList(); }
  }
}
