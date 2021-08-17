package io.lindx.server.dao;

import io.lindx.server.model.User;

public interface UserDao {

  User getUserbyId(final int id);

  User getUserByLoginOrNick(final String login, final String nick);
  User getUserByLoginAndPass(final String login, final String pass);

  void addUser(final User user);
}
