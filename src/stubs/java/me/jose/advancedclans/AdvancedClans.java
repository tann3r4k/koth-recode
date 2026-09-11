package me.jose.advancedclans;
public class AdvancedClans {
  public static AdvancedClans getInstance() { return new AdvancedClans(); }
  public PlayerManager getPlayerManager() { return new PlayerManager(); }
  public ClanManager getClanManager() { return new ClanManager(); }
  public static class PlayerManager {
    public me.jose.advancedclans.objects.ClanPlayer getClanPlayer(String name) { return new me.jose.advancedclans.objects.ClanPlayer(); }
    public boolean hasClan(org.bukkit.entity.Player p) { return false; }
  }
  public static class ClanManager {
    public me.jose.advancedclans.objects.Clan getClanFromId(int id) { return null; }
  }
}
