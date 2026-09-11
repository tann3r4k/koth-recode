package com.SirBlobman.combatlogx.api.event;
public class PlayerUntagEvent extends org.bukkit.event.Event {
  private static final org.bukkit.event.HandlerList HANDLERS = new org.bukkit.event.HandlerList();
  public org.bukkit.entity.Player getPlayer() { return null; }
  public org.bukkit.event.HandlerList getHandlers() { return HANDLERS; }
  public static org.bukkit.event.HandlerList getHandlerList() { return HANDLERS; }
}
