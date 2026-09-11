#!/usr/bin/env python3
"""Compile-only stubs for optional plugin APIs. Not packaged in the jar."""
import os

ROOT = os.path.join(os.path.dirname(__file__), "..", "src", "stubs", "java")


def write(rel, src):
    path = os.path.join(ROOT, rel)
    os.makedirs(os.path.dirname(path), exist_ok=True)
    with open(path, "w") as f:
        f.write(src.strip() + "\n")


P = "org.bukkit.entity.Player"
U = "java.util.UUID"

write("me/clip/placeholderapi/PlaceholderAPI.java", """
package me.clip.placeholderapi;
public class PlaceholderAPI {
  public static String setPlaceholders(org.bukkit.entity.Player player, String text) { return text; }
}
""")

write("me/clip/placeholderapi/expansion/PlaceholderExpansion.java", """
package me.clip.placeholderapi.expansion;
public abstract class PlaceholderExpansion {
  public abstract String getIdentifier();
  public abstract String getAuthor();
  public abstract String getVersion();
  public boolean persist() { return false; }
  public String getPlugin() { return null; }
  public abstract String onPlaceholderRequest(org.bukkit.entity.Player player, String params);
  public boolean register() { return true; }
}
""")

write("be/maximvdw/featherboard/api/FeatherBoardAPI.java", """
package be.maximvdw.featherboard.api;
public class FeatherBoardAPI {
  public static void showScoreboard(org.bukkit.entity.Player p, String name) {}
  public static boolean isToggled(org.bukkit.entity.Player p) { return false; }
  public static void toggle(org.bukkit.entity.Player p) {}
  public static void resetDefaultScoreboard(org.bukkit.entity.Player p) {}
}
""")

write("be/maximvdw/placeholderapi/PlaceholderAPI.java", """
package be.maximvdw.placeholderapi;
public class PlaceholderAPI {
  public static void registerPlaceholder(org.bukkit.plugin.Plugin plugin, String identifier, PlaceholderReplacer replacer) {}
  public static String replacePlaceholders(org.bukkit.entity.Player player, String text) { return text; }
}
""")
write("be/maximvdw/placeholderapi/PlaceholderReplaceEvent.java", """
package be.maximvdw.placeholderapi;
public class PlaceholderReplaceEvent {
  public org.bukkit.entity.Player getPlayer() { return null; }
}
""")
write("be/maximvdw/placeholderapi/PlaceholderReplacer.java", """
package be.maximvdw.placeholderapi;
public interface PlaceholderReplacer {
  String onPlaceholderReplace(PlaceholderReplaceEvent event);
}
""")

write("de/myzelyam/api/vanish/VanishAPI.java", """
package de.myzelyam.api.vanish;
public class VanishAPI {
  public static boolean isInvisible(org.bukkit.entity.Player player) { return false; }
}
""")

write("com/SirBlobman/combatlogx/api/event/PlayerUntagEvent.java", """
package com.SirBlobman.combatlogx.api.event;
public class PlayerUntagEvent extends org.bukkit.event.Event {
  private static final org.bukkit.event.HandlerList HANDLERS = new org.bukkit.event.HandlerList();
  public org.bukkit.entity.Player getPlayer() { return null; }
  public org.bukkit.event.HandlerList getHandlers() { return HANDLERS; }
  public static org.bukkit.event.HandlerList getHandlerList() { return HANDLERS; }
}
""")

write("com/gmail/nossr50/api/PartyAPI.java", """
package com.gmail.nossr50.api;
public class PartyAPI {
  public static boolean inParty(org.bukkit.entity.Player p) { return false; }
  public static String getPartyName(org.bukkit.entity.Player p) { return null; }
  public static String getPartyLeader(String name) { return null; }
  public static java.util.List<org.bukkit.entity.Player> getOnlineMembers(org.bukkit.entity.Player p) { return java.util.Collections.emptyList(); }
}
""")

write("com/massivecraft/factions/FPlayer.java", """
package com.massivecraft.factions;
public class FPlayer {
  public Faction getFaction() { return new Faction(); }
  public String getFactionId() { return ""; }
  public org.bukkit.entity.Player getPlayer() { return null; }
  public boolean isAlt() { return false; }
  public boolean showScoreboard() { return false; }
  public String getName() { return ""; }
}
""")
write("com/massivecraft/factions/Faction.java", """
package com.massivecraft.factions;
public class Faction {
  public boolean isWilderness() { return false; }
  public boolean isSafeZone() { return false; }
  public boolean isWarZone() { return false; }
  public FPlayer getFPlayerAdmin() { return new FPlayer(); }
  public String getTag() { return ""; }
  public java.util.Collection<FPlayer> getFPlayersWhereOnline(boolean online) { return java.util.Collections.emptyList(); }
}
""")
write("com/massivecraft/factions/FPlayers.java", """
package com.massivecraft.factions;
public class FPlayers {
  public static FPlayers getInstance() { return new FPlayers(); }
  public FPlayer getByPlayer(org.bukkit.entity.Player p) { return new FPlayer(); }
}
""")
write("com/massivecraft/factions/Factions.java", """
package com.massivecraft.factions;
public class Factions {
  public static Factions getInstance() { return new Factions(); }
  public Faction getFactionById(String id) { return null; }
}
""")
write("com/massivecraft/factions/FactionsPlugin.java", """
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
""")
write("com/massivecraft/factions/SavageFactions.java", """
package com.massivecraft.factions;
public class SavageFactions {
  public static SavageFactions plugin = new SavageFactions();
  public org.bukkit.configuration.file.FileConfiguration getConfig() { return new org.bukkit.configuration.file.YamlConfiguration(); }
}
""")
write("com/massivecraft/factions/scoreboards/FScoreboard.java", """
package com.massivecraft.factions.scoreboards;
public class FScoreboard {
  public static void init(com.massivecraft.factions.FPlayer player) {}
  public static FScoreboard get(com.massivecraft.factions.FPlayer player) { return new FScoreboard(); }
  public void setSidebarVisibility(boolean visible) {}
}
""")
write("com/massivecraft/factions/scoreboards/FSidebarProvider.java", """
package com.massivecraft.factions.scoreboards;
public class FSidebarProvider {}
""")
write("com/massivecraft/factions/scoreboards/sidebar/FDefaultSidebar.java", """
package com.massivecraft.factions.scoreboards.sidebar;
public class FDefaultSidebar extends com.massivecraft.factions.scoreboards.FSidebarProvider {}
""")
write("com/massivecraft/factions/entity/MPlayer.java", """
package com.massivecraft.factions.entity;
public class MPlayer {
  public static MPlayer get(org.bukkit.entity.Player p) { return new MPlayer(); }
  public Faction getFaction() { return new Faction(); }
  public org.bukkit.entity.Player getPlayer() { return null; }
  public static class Faction {
    public boolean isNormal() { return false; }
    public MPlayer getLeader() { return new MPlayer(); }
    public String getName() { return ""; }
    public String getId() { return ""; }
    public java.util.Collection<MPlayer> getMPlayersWhereOnline(boolean online) { return java.util.Collections.emptyList(); }
  }
}
""")

write("net/prosavage/factionsx/persist/data/Players.java", """
package net.prosavage.factionsx.persist.data;
public class Players {
  public static final Players INSTANCE = new Players();
  public java.util.Map<String, net.prosavage.factionsx.core.FPlayer> getFplayers() { return java.util.Collections.emptyMap(); }
}
""")
write("net/prosavage/factionsx/persist/data/Factions.java", """
package net.prosavage.factionsx.persist.data;
public class Factions {
  public static final Factions INSTANCE = new Factions();
  public java.util.Map<Long, net.prosavage.factionsx.core.Faction> getFactions() { return java.util.Collections.emptyMap(); }
}
""")
write("net/prosavage/factionsx/core/FPlayer.java", """
package net.prosavage.factionsx.core;
public class FPlayer {
  public Faction getFaction() { return new Faction(); }
  public String getName() { return ""; }
  public org.bukkit.entity.Player getPlayer() { return null; }
}
""")
write("net/prosavage/factionsx/core/Faction.java", """
package net.prosavage.factionsx.core;
public class Faction {
  public boolean isSystemFaction() { return false; }
  public boolean isWilderness() { return false; }
  public boolean isSafezone() { return false; }
  public boolean isWarzone() { return false; }
  public FPlayer getLeader() { return new FPlayer(); }
  public String getTag() { return ""; }
  public long getId() { return 0; }
  public java.util.Collection<FPlayer> getOnlineMembers() { return java.util.Collections.emptyList(); }
}
""")

write("me/neznamy/tab/api/TabPlayer.java", """
package me.neznamy.tab.api;
public interface TabPlayer {}
""")
write("me/neznamy/tab/shared/TAB.java", """
package me.neznamy.tab.shared;
public class TAB {
  public static TAB getInstance() { return new TAB(); }
  public me.neznamy.tab.api.TabPlayer getPlayer(java.util.UUID uuid) { return null; }
  public ScoreboardManager getScoreboardManager() { return new ScoreboardManager(); }
  public static class ScoreboardManager {
    public void setScoreboardVisible(me.neznamy.tab.api.TabPlayer player, boolean visible, boolean extra) {}
  }
}
""")

write("me/tade/quickboard/api/QuickBoardAPI.java", """
package me.tade.quickboard.api;
public class QuickBoardAPI {
  public static void removeBoard(org.bukkit.entity.Player p) {}
  public static void createBoard(org.bukkit.entity.Player p, String name) {}
}
""")

write("me/jasperjh/animatedscoreboard/AnimatedScoreboard.java", """
package me.jasperjh.animatedscoreboard;
public class AnimatedScoreboard extends org.bukkit.plugin.java.JavaPlugin {
  public Handler getScoreboardHandler() { return new Handler(); }
  public static class Handler {
    public PlayerBoard getPlayer(java.util.UUID uuid) { return new PlayerBoard(); }
  }
  public static class PlayerBoard {
    public void disableScoreboard() {}
    public void enableScoreboard() {}
  }
}
""")

write("io/puharesource/mc/titlemanager/api/v2/TitleManagerAPI.java", """
package io.puharesource.mc.titlemanager.api.v2;
public interface TitleManagerAPI {
  void removeScoreboard(org.bukkit.entity.Player p);
  void giveDefaultScoreboard(org.bukkit.entity.Player p);
}
""")

write("com/r4g3baby/simplescore/SimpleScore.java", """
package com.r4g3baby.simplescore;
public class SimpleScore {
  public static class Api {
    public static Manager getManager() { return new Manager(); }
  }
  public static class Manager {
    public java.util.Map<org.bukkit.entity.Player, PlayerData> getPlayersData() { return java.util.Collections.emptyMap(); }
  }
  public static class PlayerData {
    public void disable(org.bukkit.plugin.Plugin plugin) {}
    public void enable(org.bukkit.plugin.Plugin plugin) {}
  }
}
""")

write("com/meteorite/scoreboard/MeteoriteScoreboard.java", """
package com.meteorite.scoreboard;
public class MeteoriteScoreboard {
  public static MeteoriteScoreboard getInstance() { return new MeteoriteScoreboard(); }
  public Manager getScoreboardManager() { return new Manager(); }
  public static class Manager {
    public void removePlayerScoreboard(org.bukkit.entity.Player p) {}
    public void setPlayerScoreboard(org.bukkit.entity.Player p) {}
  }
}
""")

write("com/xism4/sternalboard/Structure.java", """
package com.xism4.sternalboard;
public class Structure {
  public static Structure getInstance() { return new Structure(); }
  public Manager getScoreboardManager() { return new Manager(); }
  public static class Manager {
    public void removeScoreboard(org.bukkit.entity.Player p) {}
    public void setScoreboard(org.bukkit.entity.Player p) {}
  }
}
""")

write("rien/bijl/Scoreboard/r/Board/BoardPlayer.java", """
package rien.bijl.Scoreboard.r.Board;
public class BoardPlayer {
  public static BoardPlayer getBoardPlayer(org.bukkit.entity.Player p) { return new BoardPlayer(); }
  public void kill() {}
  public void attachConfigBoard(Object board) {}
}
""")
write("rien/bijl/Scoreboard/r/Plugin/Session.java", """
package rien.bijl.Scoreboard.r.Plugin;
public class Session {
  public Object defaultBoard = new Object();
  public static Session getSession() { return new Session(); }
}
""")

write("net/kitesoftware/board/KiteBoard.java", """
package net.kitesoftware.board;
public class KiteBoard {
  public net.kitesoftware.board.user.UserManager getUserManager() { return new net.kitesoftware.board.user.UserManager(); }
  public GroupManager getGroupManager() { return new GroupManager(); }
  public static class GroupManager {
    public java.util.Optional<net.kitesoftware.board.group.Group> getGroup(String name, net.kitesoftware.board.group.GroupType type) {
      return java.util.Optional.empty();
    }
  }
}
""")
write("net/kitesoftware/board/group/Group.java", """
package net.kitesoftware.board.group;
public class Group {}
""")
write("net/kitesoftware/board/group/GroupType.java", """
package net.kitesoftware.board.group;
public enum GroupType { SCOREBOARD }
""")
write("net/kitesoftware/board/user/KiteUser.java", """
package net.kitesoftware.board.user;
public class KiteUser {
  public void setGroupOverride(net.kitesoftware.board.group.GroupType type, net.kitesoftware.board.group.Group group) {}
  public void setGroupEnabled(net.kitesoftware.board.group.GroupType type, boolean enabled) {}
  public boolean isGroupOverridden(net.kitesoftware.board.group.GroupType type) { return false; }
  public void updateGroups() {}
}
""")
write("net/kitesoftware/board/user/UserManager.java", """
package net.kitesoftware.board.user;
public class UserManager {
  public KiteUser getUser(org.bukkit.entity.Player player) { return new KiteUser(); }
}
""")

write("com/palmergames/bukkit/towny/TownyUniverse.java", """
package com.palmergames.bukkit.towny;
public class TownyUniverse {
  public static TownyUniverse getInstance() { return new TownyUniverse(); }
  public com.palmergames.bukkit.towny.object.Resident getResident(java.util.UUID uuid) { return new com.palmergames.bukkit.towny.object.Resident(); }
  public com.palmergames.bukkit.towny.object.Nation getNation(java.util.UUID uuid) { return new com.palmergames.bukkit.towny.object.Nation(); }
  public com.palmergames.bukkit.towny.object.Nation getNation(String name) { return null; }
  public com.palmergames.bukkit.towny.object.Town getTown(String name) { return null; }
}
""")
write("com/palmergames/bukkit/towny/exceptions/NotRegisteredException.java", """
package com.palmergames.bukkit.towny.exceptions;
public class NotRegisteredException extends Exception {}
""")
write("com/palmergames/bukkit/towny/object/Resident.java", """
package com.palmergames.bukkit.towny.object;
public class Resident {
  public boolean hasTown() { return false; }
  public Town getTown() throws com.palmergames.bukkit.towny.exceptions.NotRegisteredException { return new Town(); }
  public String getName() { return ""; }
}
""")
write("com/palmergames/bukkit/towny/object/Town.java", """
package com.palmergames.bukkit.towny.object;
public class Town {
  public boolean hasNation() { return false; }
  public Nation getNation() throws com.palmergames.bukkit.towny.exceptions.NotRegisteredException { return new Nation(); }
  public Resident getMayor() { return new Resident(); }
  public String getName() { return ""; }
  public java.util.Collection<Resident> getResidents() { return java.util.Collections.emptyList(); }
}
""")
write("com/palmergames/bukkit/towny/object/Nation.java", """
package com.palmergames.bukkit.towny.object;
public class Nation {
  public Town getCapital() { return new Town(); }
  public String getName() { return ""; }
  public java.util.Collection<Town> getTowns() { return java.util.Collections.emptyList(); }
}
""")

write("me/angeschossen/lands/api/integration/LandsIntegration.java", """
package me.angeschossen.lands.api.integration;
public class LandsIntegration {
  public LandsIntegration(org.bukkit.plugin.Plugin plugin) {}
  public me.angeschossen.lands.api.player.LandPlayer getLandPlayer(java.util.UUID uuid) { return new me.angeschossen.lands.api.player.LandPlayer(); }
}
""")
write("me/angeschossen/lands/api/player/LandPlayer.java", """
package me.angeschossen.lands.api.player;
public class LandPlayer {
  public boolean ownsLand() { return false; }
  public me.angeschossen.lands.api.land.Land getEditLand() { return new me.angeschossen.lands.api.land.Land(); }
}
""")
write("me/angeschossen/lands/api/land/Land.java", """
package me.angeschossen.lands.api.land;
public class Land {
  public java.util.UUID getOwnerUID() { return new java.util.UUID(0, 0); }
  public String getName() { return ""; }
  public int getId() { return 0; }
  public java.util.Collection<org.bukkit.entity.Player> getOnlinePlayers() { return java.util.Collections.emptyList(); }
}
""")

write("com/booksaw/betterTeams/Team.java", """
package com.booksaw.betterTeams;
public class Team {
  public static Team getTeam(org.bukkit.entity.Player p) { return null; }
  public Members getMembers() { return new Members(); }
  public String getTag() { return ""; }
  public static class Members {
    public java.util.List<Member> getRank(PlayerRank rank) { return java.util.Collections.emptyList(); }
    public java.util.Collection<org.bukkit.entity.Player> getOnlinePlayers() { return java.util.Collections.emptyList(); }
  }
  public static class Member {
    public org.bukkit.entity.Player getPlayer() { return null; }
  }
}
""")
write("com/booksaw/betterTeams/PlayerRank.java", """
package com.booksaw.betterTeams;
public enum PlayerRank { OWNER }
""")

write("world/bentobox/bentobox/BentoBox.java", """
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
""")
write("world/bentobox/bentobox/api/user/User.java", """
package world.bentobox.bentobox.api.user;
public class User {
  public static User getInstance(org.bukkit.entity.Player p) { return new User(); }
}
""")

write("com/wasteofplastic/askyblock/ASkyBlockAPI.java", """
package com.wasteofplastic.askyblock;
public class ASkyBlockAPI {
  public static ASkyBlockAPI getInstance() { return new ASkyBlockAPI(); }
  public boolean hasIsland(java.util.UUID uuid) { return false; }
  public boolean inTeam(java.util.UUID uuid) { return false; }
  public java.util.UUID getTeamLeader(java.util.UUID uuid) { return uuid; }
  public String getIslandName(java.util.UUID uuid) { return ""; }
  public Island getIslandOwnedBy(java.util.UUID uuid) { return new Island(); }
  public static class Island {
    public java.util.List<java.util.UUID> getMembers() { return java.util.Collections.emptyList(); }
  }
}
""")
write("com/wasteofplastic/askyblock/entity/MPlayer.java", """
package com.wasteofplastic.askyblock.entity;
public class MPlayer {
  public static MPlayer get(org.bukkit.entity.Player p) { return new MPlayer(); }
  public Island getIsland() { return new Island(); }
}
""")
write("com/wasteofplastic/askyblock/entity/Island.java", """
package com.wasteofplastic.askyblock.entity;
public class Island {
  public java.util.UUID getOwner() { return new java.util.UUID(0, 0); }
  public String getName() { return ""; }
  public java.util.List<java.util.UUID> getMembers() { return java.util.Collections.emptyList(); }
}
""")

write("com/songoda/skyblock/api/SkyBlockAPI.java", """
package com.songoda.skyblock.api;
public class SkyBlockAPI {
  public static com.songoda.skyblock.api.island.IslandManager getIslandManager() { return new com.songoda.skyblock.api.island.IslandManager(); }
  public static Implementation getImplementation() { return new Implementation(); }
  public static class Implementation {
    public ScoreboardManager getScoreboardManager() { return new ScoreboardManager(); }
  }
  public static class ScoreboardManager {
    public void addDisabledPlayer(org.bukkit.entity.Player p) {}
    public void removeDisabledPlayer(org.bukkit.entity.Player p) {}
  }
}
""")
write("com/songoda/skyblock/api/island/IslandManager.java", """
package com.songoda.skyblock.api.island;
public class IslandManager {
  public static boolean hasIsland(org.bukkit.entity.Player p) { return false; }
  public Island getIsland(org.bukkit.entity.Player p) { return new Island(); }
  public java.util.List<java.util.UUID> getMembersOnline(Island island) { return java.util.Collections.emptyList(); }
  public static class Island {
    public java.util.UUID getOwnerUUID() { return new java.util.UUID(0, 0); }
    public java.util.UUID getIslandUUID() { return new java.util.UUID(0, 0); }
  }
}
""")

write("com/iridium/iridiumskyblock/api/IridiumSkyblockAPI.java", """
package com.iridium.iridiumskyblock.api;
public class IridiumSkyblockAPI {
  public static IridiumSkyblockAPI getInstance() { return new IridiumSkyblockAPI(); }
  public com.iridium.iridiumskyblock.database.User getUser(org.bukkit.entity.Player p) { return new com.iridium.iridiumskyblock.database.User(); }
}
""")
write("com/iridium/iridiumskyblock/database/User.java", """
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
""")

write("com/bgsoftware/superiorskyblock/api/SuperiorSkyblockAPI.java", """
package com.bgsoftware.superiorskyblock.api;
public class SuperiorSkyblockAPI {
  public static com.bgsoftware.superiorskyblock.api.wrappers.SuperiorPlayer getPlayer(org.bukkit.entity.Player p) { return new com.bgsoftware.superiorskyblock.api.wrappers.SuperiorPlayer(); }
}
""")
write("com/bgsoftware/superiorskyblock/api/wrappers/SuperiorPlayer.java", """
package com.bgsoftware.superiorskyblock.api.wrappers;
public class SuperiorPlayer {
  public Island getIsland() { return new Island(); }
  public String getName() { return ""; }
  public java.util.UUID getUniqueId() { return new java.util.UUID(0, 0); }
  public org.bukkit.entity.Player asPlayer() { return null; }
  public static class Island {
    public SuperiorPlayer getOwner() { return new SuperiorPlayer(); }
    public String getName() { return ""; }
    public java.util.UUID getUniqueId() { return new java.util.UUID(0, 0); }
    public java.util.List<SuperiorPlayer> getIslandMembers(boolean includeOwner) { return java.util.Collections.emptyList(); }
  }
}
""")

write("me/glaremasters/guilds/Guilds.java", """
package me.glaremasters.guilds;
public class Guilds {
  public static Api getApi() { return new Api(); }
  public static class Api {
    public Guild getGuild(org.bukkit.entity.Player p) { return null; }
  }
  public static class Guild {
    public Member getGuildMaster() { return new Member(); }
    public String getName() { return ""; }
    public java.util.UUID getId() { return new java.util.UUID(0, 0); }
    public java.util.Collection<org.bukkit.entity.Player> getOnlineAsPlayers() { return java.util.Collections.emptyList(); }
  }
  public static class Member {
    public String getName() { return ""; }
  }
}
""")

write("net/brcdev/gangs/GangsPlusApi.java", """
package net.brcdev.gangs;
public class GangsPlusApi {
  public static boolean isInGang(org.bukkit.entity.Player p) { return false; }
  public static Gang getPlayersGang(org.bukkit.entity.Player p) { return new Gang(); }
  public static class Gang {
    public org.bukkit.OfflinePlayer getOwner() { return null; }
    public String getName() { return ""; }
    public int getId() { return 0; }
    public java.util.Collection<org.bukkit.entity.Player> getOnlineMembers() { return java.util.Collections.emptyList(); }
  }
}
""")

write("net/sacredlabyrinth/phaed/simpleclans/SimpleClans.java", """
package net.sacredlabyrinth.phaed.simpleclans;
public class SimpleClans {
  public static SimpleClans getInstance() { return new SimpleClans(); }
  public ClanManager getClanManager() { return new ClanManager(); }
  public static class ClanManager {
    public ClanPlayer getClanPlayer(org.bukkit.entity.Player p) { return new ClanPlayer(); }
  }
}
""")
write("net/sacredlabyrinth/phaed/simpleclans/ClanPlayer.java", """
package net.sacredlabyrinth.phaed.simpleclans;
public class ClanPlayer {
  public Clan getClan() { return new Clan(); }
  public org.bukkit.entity.Player toPlayer() { return null; }
  public String getCleanName() { return ""; }
  public static class Clan {
    public java.util.List<ClanPlayer> getLeaders() { return java.util.Collections.emptyList(); }
    public String getName() { return ""; }
    public java.util.List<ClanPlayer> getMembers() { return java.util.Collections.emptyList(); }
  }
}
""")

write("org/kingdoms/constants/player/KingdomPlayer.java", """
package org.kingdoms.constants.player;
public class KingdomPlayer {
  public static KingdomPlayer getKingdomPlayer(org.bukkit.entity.Player p) { return new KingdomPlayer(); }
  public boolean hasKingdom() { return false; }
  public org.kingdoms.constants.group.Kingdom getKingdom() { return new org.kingdoms.constants.group.Kingdom(); }
  public java.util.UUID getKingdomId() { return new java.util.UUID(0, 0); }
}
""")
write("org/kingdoms/constants/group/Kingdom.java", """
package org.kingdoms.constants.group;
public class Kingdom {
  public static Kingdom getKingdom(String id) { return null; }
  public java.util.Collection<org.bukkit.entity.Player> getOnlineMembers() { return java.util.Collections.emptyList(); }
  public String getName() { return ""; }
  public boolean isHomePublic() { return false; }
}
""")

write("com/gufli/kingdomcraft/api/KingdomCraftProvider.java", """
package com.gufli.kingdomcraft.api;
public class KingdomCraftProvider {
  public static KingdomCraft get() { return new KingdomCraft(); }
}
""")
write("com/gufli/kingdomcraft/api/KingdomCraft.java", """
package com.gufli.kingdomcraft.api;
public class KingdomCraft {
  public PlayerWrapper getPlayer(java.util.UUID uuid) { return new PlayerWrapper(); }
  public static class PlayerWrapper {
    public com.gufli.kingdomcraft.api.domain.User getUser() { return new com.gufli.kingdomcraft.api.domain.User(); }
  }
}
""")
write("com/gufli/kingdomcraft/api/domain/User.java", """
package com.gufli.kingdomcraft.api.domain;
public class User {
  public Kingdom getKingdom() { return null; }
}
""")
write("com/gufli/kingdomcraft/api/domain/Kingdom.java", """
package com.gufli.kingdomcraft.api.domain;
public class Kingdom {
  public java.util.Map<java.util.UUID, Object> getMembers() { return java.util.Collections.emptyMap(); }
  public String getName() { return ""; }
}
""")

write("net/cosmogrp/crclans/ClansAPI.java", """
package net.cosmogrp.crclans;
public interface ClansAPI {
  <T> net.cosmogrp.crclans.clan.service.ClanService<T> getService(Class<T> type);
  UserService getUserService();
  interface UserService {
    net.cosmogrp.crclans.user.User getUser(java.util.UUID uuid);
  }
}
""")
write("net/cosmogrp/crclans/user/User.java", """
package net.cosmogrp.crclans.user;
public interface User {
  boolean hasClan();
  String getClanTag();
}
""")
write("net/cosmogrp/crclans/clan/service/ClanService.java", """
package net.cosmogrp.crclans.clan.service;
public interface ClanService<T> {
  T getData(String tag);
}
""")
write("net/cosmogrp/crclans/clan/member/ClanMemberData.java", """
package net.cosmogrp.crclans.clan.member;
public class ClanMemberData {
  public Owner getOwner() { return new Owner(); }
  public java.util.Collection<java.util.UUID> getOnlineIdMembers() { return java.util.Collections.emptyList(); }
  public static class Owner {
    public String getPlayerName() { return ""; }
  }
}
""")

write("me/hulipvp/hcf/api/HCFAPI.java", """
package me.hulipvp.hcf.api;
public class HCFAPI {
  public static boolean hasFaction(org.bukkit.entity.Player p) { return false; }
  public static String getFactionLeader(org.bukkit.entity.Player p) { return null; }
  public static String getFactionName(org.bukkit.entity.Player p) { return null; }
  public static java.util.Collection<org.bukkit.entity.Player> getOnlineTeamMembers(org.bukkit.entity.Player p) { return java.util.Collections.emptyList(); }
}
""")

write("me/jose/advancedclans/AdvancedClans.java", """
package me.jose.advancedclans;
public class AdvancedClans {
  public static AdvancedClans getInstance() { return new AdvancedClans(); }
  public PlayerManager getPlayerManager() { return new PlayerManager(); }
  public ClanManager getClanManager() { return new ClanManager(); }
  public static class PlayerManager {
    public me.jose.advancedclans.objects.ClanPlayer getClanPlayer(String name) { return new me.jose.advancedclans.objects.ClanPlayer(); }
    public boolean hasClan(org.bukkit.entity.Player p) { return false; }
  }
  public static class ClanManager {
    public me.jose.advancedclans.objects.Clan getClanFromId(int id) { return null; }
  }
}
""")
write("me/jose/advancedclans/objects/Clan.java", """
package me.jose.advancedclans.objects;
public class Clan {
  public String getName() { return ""; }
  public String getLeader() { return ""; }
  public java.util.Collection<org.bukkit.entity.Player> getOnlinePlayers() { return java.util.Collections.emptyList(); }
  public int getId() { return 0; }
}
""")
write("me/jose/advancedclans/objects/ClanPlayer.java", """
package me.jose.advancedclans.objects;
public class ClanPlayer {
  public Clan getClan() { return new Clan(); }
}
""")

write("us/forseth11/feudal/core/Feudal.java", """
package us.forseth11.feudal.core;
public class Feudal {
  public static Api getAPI() { return new Api(); }
  public static class Api {
    public us.forseth11.feudal.user.User getUser(java.util.UUID uuid) { return new us.forseth11.feudal.user.User(); }
    public us.forseth11.feudal.user.User getUser(String name) { return new us.forseth11.feudal.user.User(); }
    public us.forseth11.feudal.kingdoms.Kingdom getKingdom(us.forseth11.feudal.user.User user) { return new us.forseth11.feudal.kingdoms.Kingdom(); }
  }
}
""")
write("us/forseth11/feudal/user/User.java", """
package us.forseth11.feudal.user;
public class User {
  public String getName() { return ""; }
}
""")
write("us/forseth11/feudal/kingdoms/Kingdom.java", """
package us.forseth11.feudal.kingdoms;
public class Kingdom {
  public java.util.List<Member> getMembersOrdered() { return java.util.Collections.emptyList(); }
  public String getName() { return ""; }
  public java.util.UUID getUUID() { return new java.util.UUID(0, 0); }
}
""")
write("us/forseth11/feudal/kingdoms/Member.java", """
package us.forseth11.feudal.kingdoms;
public class Member {
  public us.forseth11.feudal.user.User getUser() { return new us.forseth11.feudal.user.User(); }
}
""")

write("org/stellardev/galacticskyblock/api/SkyBlockAPI.java", """
package org.stellardev.galacticskyblock.api;
public class SkyBlockAPI {
  public static boolean hasIsland(org.bukkit.entity.Player p) { return false; }
  public static org.bukkit.OfflinePlayer getIslandLeader(org.bukkit.entity.Player p) { return null; }
  public static String getIslandName(org.bukkit.entity.Player p) { return ""; }
}
""")
write("org/stellardev/galacticskyblock/coll/APlayerColl.java", """
package org.stellardev.galacticskyblock.coll;
public class APlayerColl {
  public static APlayerColl get() { return new APlayerColl(); }
  public org.stellardev.galacticskyblock.entity.Island getIsland(java.util.UUID uuid) { return new org.stellardev.galacticskyblock.entity.Island(); }
}
""")
write("org/stellardev/galacticskyblock/entity/APlayer.java", """
package org.stellardev.galacticskyblock.entity;
public class APlayer {
  public static APlayer get(java.util.UUID uuid) { return new APlayer(); }
  public Island getIsland() { return new Island(); }
  public org.bukkit.entity.Player getPlayer() { return null; }
}
""")
write("org/stellardev/galacticskyblock/entity/Island.java", """
package org.stellardev.galacticskyblock.entity;
public class Island {
  public java.util.Collection<APlayer> getAPlayersWhereOnline(boolean online) { return java.util.Collections.emptyList(); }
  public String getId() { return ""; }
}
""")

write("dev/drawethree/ultraprisoncore/UltraPrisonCore.java", """
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
""")
write("dev/drawethree/ultraprisoncore/gangs/UltraPrisonGangs.java", """
package dev.drawethree.ultraprisoncore.gangs;
public class UltraPrisonGangs {
  public static UltraPrisonGangs getInstance() { return new UltraPrisonGangs(); }
  public dev.drawethree.ultraprisoncore.UltraPrisonCore.Api getApi() { return new dev.drawethree.ultraprisoncore.UltraPrisonCore.Api(); }
}
""")

write("net/goliathmc/goliathskyblock/api/SkyBlockAPI.java", """
package net.goliathmc.goliathskyblock.api;
public class SkyBlockAPI {
  public static SkyBlockAPI get() { return new SkyBlockAPI(); }
  public boolean hasIsland(org.bukkit.entity.Player p) { return false; }
  public org.bukkit.OfflinePlayer getIslandLeader(org.bukkit.entity.Player p) { return null; }
  public String getIslandName(org.bukkit.entity.Player p) { return ""; }
}
""")
write("net/goliathmc/goliathskyblock/entity/APlayer.java", """
package net.goliathmc.goliathskyblock.entity;
public class APlayer {
  public static APlayer get(java.util.UUID uuid) { return new APlayer(); }
  public Island getIsland() { return new Island(); }
  public static class Island {
    public java.util.Collection<org.bukkit.entity.Player> getOnlinePlayers() { return java.util.Collections.emptyList(); }
    public String getId() { return ""; }
  }
}
""")

write("net/redstoneore/legacyfactions/entity/FactionColl.java", """
package net.redstoneore.legacyfactions.entity;
public class FactionColl {
  public static Faction get(org.bukkit.entity.Player p) { return new Faction(); }
  public static FactionColl get() { return new FactionColl(); }
  public Faction getFactionById(String id) { return null; }
}
""")
write("net/redstoneore/legacyfactions/entity/Faction.java", """
package net.redstoneore.legacyfactions.entity;
public class Faction {
  public boolean isWilderness() { return false; }
  public boolean isSafeZone() { return false; }
  public boolean isWarZone() { return false; }
  public FPlayer getOwner() { return new FPlayer(); }
  public String getTag() { return ""; }
  public String getId() { return ""; }
  public java.util.Collection<FPlayer> getMembers() { return java.util.Collections.emptyList(); }
}
""")
write("net/redstoneore/legacyfactions/entity/FPlayer.java", """
package net.redstoneore.legacyfactions.entity;
public class FPlayer {
  public String getName() { return ""; }
  public org.bukkit.entity.Player getPlayer() { return null; }
}
""")

write("net/pvpcafe/revival/team/Team.java", """
package net.pvpcafe.revival.team;
public class Team {
  public static String getPlayerTeamName(org.bukkit.entity.Player p) { return "None"; }
  public static net.pvpcafe.revival.team.object.TeamObject getTeamData(String name) { return new net.pvpcafe.revival.team.object.TeamObject(); }
}
""")
write("net/pvpcafe/revival/team/object/TeamObject.java", """
package net.pvpcafe.revival.team.object;
public class TeamObject {
  public java.util.Collection<org.bukkit.entity.Player> getTeamOnlinePlayers() { return java.util.Collections.emptyList(); }
  public java.util.UUID getOwnerUUID() { return new java.util.UUID(0, 0); }
}
""")

write("me/ulrich/clans/packets/interfaces/UClans.java", """
package me.ulrich.clans.packets.interfaces;
public interface UClans {
  PlayerAPI getPlayerAPI();
  interface PlayerAPI {
    boolean hasClan(java.util.UUID uuid);
    Clan getPlayerClan(java.util.UUID uuid);
  }
  interface Clan {
    java.util.Collection<java.util.UUID> getOnlineMembers();
    String getTag();
    java.util.UUID getLeader();
    Object getId();
  }
}
""")

write("Clans/ClanConfiguration.java", """
package Clans;
public class ClanConfiguration {
  public String getClan(org.bukkit.entity.Player p) { return null; }
}
""")

print("wrote stubs to", os.path.abspath(ROOT))
