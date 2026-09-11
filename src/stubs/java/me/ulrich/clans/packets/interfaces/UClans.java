package me.ulrich.clans.packets.interfaces;
public interface UClans {
  PlayerAPI getPlayerAPI();
  interface PlayerAPI {
    boolean hasClan(java.util.UUID uuid);
    Clan getPlayerClan(java.util.UUID uuid);
  }
  interface Clan {
    java.util.Collection<java.util.UUID> getOnlineMembers();
    String getTag();
    java.util.UUID getLeader();
    Object getId();
  }
}
