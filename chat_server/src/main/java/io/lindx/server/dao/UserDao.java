package io.lindx.server.dao;

import io.lindx.server.model.User;

public interface UserDao {

  User getUserbyId(final int id);

  User getUserByLoginAndPass(final String login, final String pass);

  boolean addUser(final User user);
}
