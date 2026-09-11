package com.palmergames.bukkit.towny.object;
public class Resident {
  public boolean hasTown() { return false; }
  public Town getTown() throws com.palmergames.bukkit.towny.exceptions.NotRegisteredException { return new Town(); }
  public String getName() { return ""; }
}
