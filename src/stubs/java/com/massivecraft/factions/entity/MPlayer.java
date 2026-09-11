package com.massivecraft.factions.entity;
public class MPlayer {
  public static MPlayer get(org.bukkit.entity.Player p) { return new MPlayer(); }
  public Faction getFaction() { return new Faction(); }
  public org.bukkit.entity.Player getPlayer() { return null; }
  public static class Faction {
    public boolean isNormal() { return false; }
    public MPlayer getLeader() { return new MPlayer(); }
    public String getName() { return ""; }
    public String getId() { return ""; }
    public java.util.Collection<MPlayer> getMPlayersWhereOnline(boolean online) { return java.util.Collections.emptyList(); }
  }
}
