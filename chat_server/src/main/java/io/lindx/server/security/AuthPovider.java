package io.lindx.server.security;

import io.lindx.server.model.User;

/**
 * AuthPovider
 */
public interface AuthPovider {

  User getUserbyId(final int id);

  User getUserByLoginAndPass(final String login, final String pass);

  boolean addUser(final User user);
}