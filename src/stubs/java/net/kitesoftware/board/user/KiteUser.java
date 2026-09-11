package net.kitesoftware.board.user;
public class KiteUser {
  public void setGroupOverride(net.kitesoftware.board.group.GroupType type, net.kitesoftware.board.group.Group group) {}
  public void setGroupEnabled(net.kitesoftware.board.group.GroupType type, boolean enabled) {}
  public boolean isGroupOverridden(net.kitesoftware.board.group.GroupType type) { return false; }
  public void updateGroups() {}
}
