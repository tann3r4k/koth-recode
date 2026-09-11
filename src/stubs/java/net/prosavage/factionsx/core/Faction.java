package net.prosavage.factionsx.core;
public class Faction {
  public boolean isSystemFaction() { return false; }
  public boolean isWilderness() { return false; }
  public boolean isSafezone() { return false; }
  public boolean isWarzone() { return false; }
  public FPlayer getLeader() { return new FPlayer(); }
  public String getTag() { return ""; }
  public long getId() { return 0; }
  public java.util.Collection<FPlayer> getOnlineMembers() { return java.util.Collections.emptyList(); }
}
