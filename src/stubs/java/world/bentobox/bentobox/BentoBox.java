package world.bentobox.bentobox;
public class BentoBox {
  public static BentoBox getInstance() { return new BentoBox(); }
  public IWM getIWM() { return new IWM(); }
  public Islands getIslands() { return new Islands(); }
  public static class IWM {
    public java.util.Set<org.bukkit.World> getWorlds() { return java.util.Collections.emptySet(); }
  }
  public static class Islands {
    public boolean hasIsland(org.bukkit.World world, world.bentobox.bentobox.api.user.User user) { return false; }
    public Island getIsland(org.bukkit.World world, world.bentobox.bentobox.api.user.User user) { return new Island(); }
  }
  public static class Island {
    public java.util.UUID getOwner() { return new java.util.UUID(0, 0); }
    public String getName() { return ""; }
    public java.util.Collection<org.bukkit.entity.Player> getPlayersOnIsland() { return java.util.Collections.emptyList(); }
    public String getUniqueId() { return ""; }
  }
}
