package io.lindx.server.model;

public class User {
  private int id;
  private String name;
  private String pass;

  private User() {};

  public User(int id, String name, String pass) {
    this.id = id;
    this.name = name;
    this.pass = pass;
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

  @Override
  public String toString() {
    return "{" + " id='" + getId() + "'" + ", name='" + getName() + "'" + ", pass='" + getPass() + "'" + "}";
  }
}
