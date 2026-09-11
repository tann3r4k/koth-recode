package net.redstoneore.legacyfactions.entity;
public class Faction {
  public boolean isWilderness() { return false; }
  public boolean isSafeZone() { return false; }
  public boolean isWarZone() { return false; }
  public FPlayer getOwner() { return new FPlayer(); }
  public String getTag() { return ""; }
  public String getId() { return ""; }
  public java.util.Collection<FPlayer> getMembers() { return java.util.Collections.emptyList(); }
}
