package com.wasteofplastic.askyblock;
public class ASkyBlockAPI {
  public static ASkyBlockAPI getInstance() { return new ASkyBlockAPI(); }
  public boolean hasIsland(java.util.UUID uuid) { return false; }
  public boolean inTeam(java.util.UUID uuid) { return false; }
  public java.util.UUID getTeamLeader(java.util.UUID uuid) { return uuid; }
  public String getIslandName(java.util.UUID uuid) { return ""; }
  public Island getIslandOwnedBy(java.util.UUID uuid) { return new Island(); }
  public static class Island {
    public java.util.List<java.util.UUID> getMembers() { return java.util.Collections.emptyList(); }
  }
}
