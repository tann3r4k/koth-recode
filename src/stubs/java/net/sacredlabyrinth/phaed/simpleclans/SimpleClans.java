package net.sacredlabyrinth.phaed.simpleclans;
public class SimpleClans {
  public static SimpleClans getInstance() { return new SimpleClans(); }
  public ClanManager getClanManager() { return new ClanManager(); }
  public static class ClanManager {
    public ClanPlayer getClanPlayer(org.bukkit.entity.Player p) { return new ClanPlayer(); }
  }
}
