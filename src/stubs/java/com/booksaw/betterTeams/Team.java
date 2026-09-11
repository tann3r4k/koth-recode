package com.booksaw.betterTeams;
public class Team {
  public static Team getTeam(org.bukkit.entity.Player p) { return null; }
  public Members getMembers() { return new Members(); }
  public String getTag() { return ""; }
  public static class Members {
    public java.util.List<Member> getRank(PlayerRank rank) { return java.util.Collections.emptyList(); }
    public java.util.Collection<org.bukkit.entity.Player> getOnlinePlayers() { return java.util.Collections.emptyList(); }
  }
  public static class Member {
    public org.bukkit.entity.Player getPlayer() { return null; }
  }
}
