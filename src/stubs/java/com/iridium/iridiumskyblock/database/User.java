package com.iridium.iridiumskyblock.database;
public class User {
  public java.util.Optional<Island> getIsland() { return java.util.Optional.empty(); }
  public java.util.UUID getUuid() { return new java.util.UUID(0, 0); }
  public static class Island {
    public User getOwner() { return new User(); }
    public String getName() { return ""; }
    public int getId() { return 0; }
    public java.util.List<User> getMembers() { return java.util.Collections.emptyList(); }
  }
}
