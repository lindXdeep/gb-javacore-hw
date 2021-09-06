package lx.talx.client.api;

import java.nio.ByteBuffer;

import org.json.simple.JSONObject;

import lx.talx.client.security.UserCredential;
public class Auth {

  private byte[] key;
  
  private byte[] buf;

  private JSONObject user;

  private UserCredential credential;

  private Connect connect;

  public Auth(Connect connect) {
    this.connect = connect;
    this.credential = new UserCredential();
  }

  public boolean enterToAccount() {

    if (key()) { // if key accepted then loged to account

      Thread th = new Thread(() -> {
        UserAccount account = new UserAccount(connect);
      });
      th.start();

      return true;
    }
    return false;
  }

  private boolean key() {

    if (credential.isKeyexist()) {

      byte[] command = "/key".getBytes();
      key = credential.readKey();

      ByteBuffer request = ByteBuffer.allocate(15 + key.length);
      request.put(command);
      request.put(15, key);

      connect.send(request.array());

      buf = connect.read();

      if (new String(buf, 0, buf.length).equals("/accepted")) {
        return true;
      }
      return false;
    }
    return false;
  }

  public void auth(String username, String password) {

    JSONObject user = new JSONObject();
    user.put("username", username);
    user.put("password", password);

    auth(user);
  }

  public boolean auth(JSONObject user) {

    byte[] command = "/auth".getBytes();

    ByteBuffer request = ByteBuffer.allocate(15 + user.toJSONString().getBytes().length);
    request.put(command);
    request.put(15, user.toJSONString().getBytes());

    connect.send(request.array());

    // response to get credentials
    if ((buf = connect.read()).length != 0) {
      credential.saveKey(buf);
      key();
      return true;
    }
    return false;
  }

  public void signup(String nickname, String username, String email, String password) {
    JSONObject user = new JSONObject();
    user.put("nickname", nickname);
    user.put("username", username);
    user.put("email", email);
    user.put("password", password);

    signup(user);
  }

  public void signup(JSONObject user) {

    byte[] command = "/new".getBytes();

    ByteBuffer request = ByteBuffer.allocate(15 + user.toJSONString().getBytes().length);
    request.put(command);
    request.put(15, user.toJSONString().getBytes());

    connect.send(request.array());
  }

  public boolean authCode(byte[] authcode) {

    connect.send(authcode);

    if ((buf = connect.read()).length != 0) {
      credential.saveKey(buf);
      key();
      return true;
    }
    return false;
  }

  public void removeKey() {
    if (credential.isKeyexist()) {
      credential.destroyKey();
    }
  }

  public byte[] getKey() {
    return key;
  }
}
