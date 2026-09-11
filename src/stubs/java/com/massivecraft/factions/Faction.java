package com.massivecraft.factions;
public class Faction {
  public boolean isWilderness() { return false; }
  public boolean isSafeZone() { return false; }
  public boolean isWarZone() { return false; }
  public FPlayer getFPlayerAdmin() { return new FPlayer(); }
  public String getTag() { return ""; }
  public java.util.Collection<FPlayer> getFPlayersWhereOnline(boolean online) { return java.util.Collections.emptyList(); }
}
