package net.brcdev.gangs;
public class GangsPlusApi {
  public static boolean isInGang(org.bukkit.entity.Player p) { return false; }
  public static Gang getPlayersGang(org.bukkit.entity.Player p) { return new Gang(); }
  public static class Gang {
    public org.bukkit.OfflinePlayer getOwner() { return null; }
    public String getName() { return ""; }
    public int getId() { return 0; }
    public java.util.Collection<org.bukkit.entity.Player> getOnlineMembers() { return java.util.Collections.emptyList(); }
  }
}
