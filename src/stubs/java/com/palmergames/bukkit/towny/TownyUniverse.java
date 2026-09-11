package com.palmergames.bukkit.towny;
public class TownyUniverse {
  public static TownyUniverse getInstance() { return new TownyUniverse(); }
  public com.palmergames.bukkit.towny.object.Resident getResident(java.util.UUID uuid) { return new com.palmergames.bukkit.towny.object.Resident(); }
  public com.palmergames.bukkit.towny.object.Nation getNation(java.util.UUID uuid) { return new com.palmergames.bukkit.towny.object.Nation(); }
  public com.palmergames.bukkit.towny.object.Nation getNation(String name) { return null; }
  public com.palmergames.bukkit.towny.object.Town getTown(String name) { return null; }
}
