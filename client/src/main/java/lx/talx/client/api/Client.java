package lx.talx.client.api;

import java.nio.ByteBuffer;

import org.json.simple.JSONObject;

import lx.talx.client.error.ClientSocketExceprion;
import lx.talx.client.net.Connection;
import lx.talx.client.net.Protocol;
import lx.talx.client.net.ServerAddress;
import lx.talx.client.security.UserCredential;
import lx.talx.client.utils.Log;
import lx.talx.client.utils.Util;

public class Client {

  private byte[] key;

  private ServerAddress address;
  private Connection connection;
  private byte[] buf;
  private JSONObject user;

  private Protocol protocol;

  private UserCredential credential;

  public Client() {
    this(new ServerAddress("127.0.0.1", 8181)); // default
  }

  public Client(final ServerAddress serverAddress) {
    this.address = serverAddress;
    this.connection = new Connection(address);
    this.protocol = new Protocol(connection);
    this.credential = new UserCredential();
    connect();
  }

  public boolean enterToAccount() {

    if (key()) { // if key accepted then loged to account

      Thread th = new Thread(() -> {
        UserAccount account = new UserAccount(this);
      });
      th.start();

      return true;
    }
    return false;
  }

  // TODO: Authentication

  private boolean key() {

    if (credential.isKeyexist()) {

      byte[] command = "/key".getBytes();
      key = credential.readKey();

      ByteBuffer request = ByteBuffer.allocate(15 + key.length);
      request.put(command);
      request.put(15, key);

      protocol.sendEncrypted(request.array());

      buf = protocol.readEncrypted();

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

    protocol.sendEncrypted(request.array());

    // response to get credentials
    if ((buf = protocol.readEncrypted()).length != 0) {
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

    protocol.sendEncrypted(request.array());
  }

  public boolean authCode(byte[] authcode) {

    protocol.sendEncrypted(authcode);

    if ((buf = protocol.readEncrypted()).length != 0) {
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

  // TODO: Connections

  public void connect(int port) {
    address.setPort(port);
    connect();
  }

  public boolean connect() {
    if (!connection.getStatus()) {
      if (connection.connect()) {
        try {
          this.protocol.executeKeyExchange();
          Log.log("Key exchange Success");
        } catch (ClientSocketExceprion e) {
          e.printStackTrace();
          return false;
        }
        return true;
      }
    }
    return false;
  }

  public boolean disconnect() {
    return connection.kill();
  }

  // TODO: Send and recive

  public void sendSecure(byte[] bytes) {

    ByteBuffer b = ByteBuffer.allocate(4 + key.length + bytes.length);
    b.put(Util.intToByte(key.length)); // 4
    b.put(4, key);
    b.put(4 + key.length, bytes);

    send(b.array());
  }

  public void send(byte[] bytes) {
    protocol.sendEncrypted(bytes);
  }

  public byte[] read() {
    return protocol.readEncrypted();
  }

  public Connection getConnection() {
    return connection;
  }

  public ServerAddress getAddress() {
    return address;
  }

  public boolean getStatus() {
    if (connection.getStatus())
      return true;
    else
      return false;
  }

}
