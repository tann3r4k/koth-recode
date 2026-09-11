package us.forseth11.feudal.core;
public class Feudal {
  public static Api getAPI() { return new Api(); }
  public static class Api {
    public us.forseth11.feudal.user.User getUser(java.util.UUID uuid) { return new us.forseth11.feudal.user.User(); }
    public us.forseth11.feudal.user.User getUser(String name) { return new us.forseth11.feudal.user.User(); }
    public us.forseth11.feudal.kingdoms.Kingdom getKingdom(us.forseth11.feudal.user.User user) { return new us.forseth11.feudal.kingdoms.Kingdom(); }
  }
}
