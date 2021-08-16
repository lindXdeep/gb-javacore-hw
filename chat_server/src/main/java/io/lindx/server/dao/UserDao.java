package io.lindx.server.dao;

import java.util.Map;

import io.lindx.server.model.User;

public interface UserDao {

  User getUserbyId(final int id);

  User getUserByLoginAndPass(final String login, final String pass);
}
