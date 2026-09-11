package net.cosmogrp.crclans;
public interface ClansAPI {
  <T> net.cosmogrp.crclans.clan.service.ClanService<T> getService(Class<T> type);
  UserService getUserService();
  interface UserService {
    net.cosmogrp.crclans.user.User getUser(java.util.UUID uuid);
  }
}
