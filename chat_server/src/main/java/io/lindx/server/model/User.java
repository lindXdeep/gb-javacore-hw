package io.lindx.server.model;

import java.util.Objects;

public class User {
  private int id;
  private String name;
  private String pass;
  private String nick;

  private User() {};

  public User(int id, String name, String pass, String nick) {
    this.id = id;
    this.name = name;
    this.pass = pass;
    this.nick = nick;
  }

  public User(String name, String pass, String nick) {
    this.name = name;
    this.pass = pass;
    this.nick = nick;
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPass() {
    return this.pass;
  }

  public void setPass(String pass) {
    this.pass = pass;
  }

  public String getNick() {
    return this.nick;
  }

  public void setNick(String nick) {
    this.nick = nick;
  }

  @Override
  public String toString() {
    return "{" +
      " id='" + getId() + "'" +
      ", name='" + getName() + "'" +
      ", pass='" + getPass() + "'" +
      ", nick='" + getNick() + "'" +
      "}";
  }
  
  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (o == null || this.getClass() != o.getClass())
      return false;

    User user = (User) o;

    return Objects.equals(id, user.id) || 
           Objects.equals(name, user.name) || 
           Objects.equals(nick, user.nick);
  }
  
  @Override
  public int hashCode() {
    int hash = 7;
    hash = 17 * hash + (int) id;
    hash = 17 * hash + (name == null ? 0 : name.hashCode());
    hash = 17 * hash + (nick == null ? 0 : nick.hashCode());
    return hash;
  }
}
