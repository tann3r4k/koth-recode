package com.massivecraft.factions;
public class FactionsPlugin {
  public static FactionsPlugin getInstance() { return new FactionsPlugin(); }
  public org.bukkit.configuration.file.FileConfiguration getConfig() { return new org.bukkit.configuration.file.YamlConfiguration(); }
  public Conf conf() { return new Conf(); }
  public static class Conf {
    public Scoreboard scoreboard() { return new Scoreboard(); }
  }
  public static class Scoreboard {
    public Constant constant() { return new Constant(); }
  }
  public static class Constant {
    public boolean isEnabled() { return false; }
  }
}
