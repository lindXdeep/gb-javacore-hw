package io.lindx.server.security;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import io.lindx.server.model.User;
import io.lindx.server.security.error.UserNotFoundExeption;
import io.lindx.server.security.error.WrongCredentialsException;

/**
 * InMemoryAuthentication
 */
public class InMemoryAuthentication implements AuthPovider {

  private Map<Integer, User> users = new HashMap<>();

  {
    users.put(1, new User(1, "User1", "pass1"));
    users.put(2, new User(2, "User2", "pass2"));
    users.put(3, new User(3, "User3", "pass3"));
    users.put(4, new User(4, "User4", "pass4"));
  }

  @Override
  public User getUserbyId(final int id) {

    if (users.containsKey(id)) {
      return users.get(id);
    }
    throw new UserNotFoundExeption("ERROR: User not found, because id:" + " does not exist.");
  }

  @Override
  public User getUserByLoginAndPass(String login, String pass) {

    Iterator<Entry<Integer, User>> iterator = users.entrySet().iterator();

    while (iterator.hasNext()) {

      User tmpUser = iterator.next().getValue();

      if (tmpUser.getName().equals(login) && tmpUser.getPass().equals(pass)) {
        return tmpUser;
      }
    }
    throw new WrongCredentialsException("ERROR: Bad Credential!");
  }

  @Override
  public boolean addUser(final User user) {

    if (getUserbyId(user.getId()) != null) {
      return false;
    } else {
      users.put(user.getId(), user);
      return true;
    }
  }
}