package lx.talx.client.api;

import java.nio.ByteBuffer;

import org.json.simple.JSONObject;

import lx.talx.client.security.UserCredential;

public class Auth {

  private byte[] key;
  
  private byte[] buf;

  private JSONObject user;

  private UserCredential credential;

  private Client client;

  public Auth(Client client) {
    this.client = client;
    this.credential = new UserCredential();
  }

  public boolean enterToAccount() {

    if (key()) { // if key accepted then loged to account

      Thread th = new Thread(() -> {
        UserAccount account = new UserAccount(client);
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

      client.send(request.array());

      buf = client.read();

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

    client.send(request.array());

    // response to get credentials
    if ((buf = client.read()).length != 0) {
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

    client.send(request.array());
  }

  public boolean authCode(byte[] authcode) {

    client.send(authcode);

    if ((buf = client.read()).length != 0) {
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
