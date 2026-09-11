package dev.drawethree.ultraprisoncore;
public class UltraPrisonCore {
  public static UltraPrisonCore getInstance() { return new UltraPrisonCore(); }
  public Gangs getGangs() { return new Gangs(); }
  public static class Gangs {
    public Api getApi() { return new Api(); }
  }
  public static class Api {
    public java.util.Optional<Gang> getPlayerGang(org.bukkit.entity.Player p) { return java.util.Optional.empty(); }
    public java.util.Optional<Gang> getByName(String name) { return java.util.Optional.empty(); }
  }
  public static class Gang {
    public java.util.UUID getGangOwner() { return new java.util.UUID(0, 0); }
    public String getName() { return ""; }
    public java.util.Collection<org.bukkit.entity.Player> getOnlinePlayers() { return java.util.Collections.emptyList(); }
  }
}
