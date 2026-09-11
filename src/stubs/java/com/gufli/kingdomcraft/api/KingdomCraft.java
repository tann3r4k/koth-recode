package com.gufli.kingdomcraft.api;
public class KingdomCraft {
  public PlayerWrapper getPlayer(java.util.UUID uuid) { return new PlayerWrapper(); }
  public static class PlayerWrapper {
    public com.gufli.kingdomcraft.api.domain.User getUser() { return new com.gufli.kingdomcraft.api.domain.User(); }
  }
}
