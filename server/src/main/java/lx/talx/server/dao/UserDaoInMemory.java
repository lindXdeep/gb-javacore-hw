package lx.talx.server.dao;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.Map.Entry;

import lx.talx.server.model.User;

public class UserDaoInMemory implements UserDao {

  private ObjectOutputStream dbOut;
  private ObjectInputStream dbIn;

  Map<String, User> users;

  public UserDaoInMemory() {

    String db = "users.db";

    if (!Files.exists(Paths.get(db), LinkOption.NOFOLLOW_LINKS)) {
      users = new HashMap<String, User>();
    } else {
      readDbInMemory();
    }

    try {
      this.dbOut = new ObjectOutputStream(new FileOutputStream(db));
      this.dbIn = new ObjectInputStream(new FileInputStream(db));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void readDbInMemory() {
    try {
      users = (HashMap<String, User>) dbIn.readObject();
    } catch (ClassNotFoundException | IOException e) {
      e.printStackTrace();
    }
  }

  private void writeOnDisk() {
    try {
      this.dbOut.writeObject(users);
      this.dbOut.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void add(User user) {
    users.put(user.getUserName(), user);
    writeOnDisk();
  }

  @Override
  public List<User> listUsers() {
    return null;
  }

  @Override
  public User getUserByUserName(String username) {
    return users.get(username);
  }

  @Override
  public User getUserByEmail(String email) {

    Iterator<Entry<String, User>> it = users.entrySet().iterator();

    while (it.hasNext()) {

      Entry<String, User> i = it.next();

      if (i.getValue().getEmail().equals(email))
        return i.getValue();
    }
    return null;
  }

  @Override
  public void delete(User user) {

  }

}
