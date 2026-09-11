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
