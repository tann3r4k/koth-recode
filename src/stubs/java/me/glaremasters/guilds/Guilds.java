package me.glaremasters.guilds;
public class Guilds {
  public static Api getApi() { return new Api(); }
  public static class Api {
    public Guild getGuild(org.bukkit.entity.Player p) { return null; }
  }
  public static class Guild {
    public Member getGuildMaster() { return new Member(); }
    public String getName() { return ""; }
    public java.util.UUID getId() { return new java.util.UUID(0, 0); }
    public java.util.Collection<org.bukkit.entity.Player> getOnlineAsPlayers() { return java.util.Collections.emptyList(); }
  }
  public static class Member {
    public String getName() { return ""; }
  }
}
