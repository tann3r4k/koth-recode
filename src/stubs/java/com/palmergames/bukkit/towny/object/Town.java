package com.palmergames.bukkit.towny.object;
public class Town {
  public boolean hasNation() { return false; }
  public Nation getNation() throws com.palmergames.bukkit.towny.exceptions.NotRegisteredException { return new Nation(); }
  public Resident getMayor() { return new Resident(); }
  public String getName() { return ""; }
  public java.util.Collection<Resident> getResidents() { return java.util.Collections.emptyList(); }
}
