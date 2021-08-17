package io.lindx.server.dao;

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
public class InMemoryAuthentication implements UserDao {

  private static int userId = 4;

  private Map<Integer, User> users = new HashMap<>();

  {
    users.put(0, new User(0, "admin", "admin", "Admin"));
    users.put(1, new User(1, "user1", "pass1", "nick1"));
    users.put(2, new User(2, "user2", "pass2", "nick2"));
    users.put(3, new User(3, "user3", "pass3", "nick3"));
    users.put(4, new User(4, "user4", "pass4", "nick4"));
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
  public synchronized void addUser(final User user) {

    while (users.containsKey(++userId));
    
    user.setId(userId);
    users.put(user.getId(), user);
  }

  @Override
  public User getUserByLoginOrNick(String login, String nick) {

    Iterator<Entry<Integer, User>> it = users.entrySet().iterator();

    while (it.hasNext()) {
      User user = it.next().getValue();
      if (user.getName().equals(login) || user.getNick().equals(nick)) {
        return user;
      }
    }
    throw new UserNotFoundExeption(
        "ERROR: User not found, because login:" + login + " and " + nick + " does not exist.");
  }

  public static int getCurrentId() {
    return userId;
  }

  public Iterator<User> getAll() {
    return users.values().iterator();
  }

  public boolean isExist(User tmpUser) {
    return users.containsValue(tmpUser);
  }

}