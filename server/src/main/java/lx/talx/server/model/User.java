package lx.talx.server.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class User implements Serializable {
  private int id;
  private String userName;
  private String email;
  private String password;

  private int authCode;

  private String nickName;

  private static final AtomicInteger count = new AtomicInteger();

  public User() {
  } 

  public User(String userName, String email, String password, int authCode, String nickName) {
    this.userName = userName;
    this.email = email;
    this.password = password;
    this.authCode = authCode;
    this.nickName = nickName;
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUserName() {
    return this.userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getNickName() {
    return this.nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  private String getAuthCode() {
    return null;
  }

  public void setAuthCode(int authCode) {
    this.authCode = authCode;
  }

  @Override
  public int hashCode() {

    int hash = 17;
    hash *= 17 + id;
    hash *= 17 + userName.hashCode();
    hash *= 17 + nickName.hashCode();
    hash *= 17 + authCode;
    hash *= 17 + email.hashCode();
    hash *= 17 + password.hashCode();

    return hash;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (o == null || this.getClass() != o.getClass())
      return false;

    User user = (User) o;

    return Objects.equals(this.id, user.id) &&

        Objects.equals(this.userName, user.userName) &&

        Objects.equals(this.nickName, user.nickName) &&

        Objects.equals(this.authCode, user.authCode) &&

        Objects.equals(this.email, user.email) &&

        Objects.equals(this.password, user.password);
  }


  @Override
  public String toString() {
    return "{" +
      " id='" + getId() + "'" +
      ", userName='" + getUserName() + "'" +
      ", email='" + getEmail() + "'" +
      ", password='" + getPassword() + "'" +
      ", authCode='" + getAuthCode() + "'" +
      ", nickName='" + getNickName() + "'" +
      "}";
  }
}
