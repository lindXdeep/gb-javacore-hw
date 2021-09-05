package lx.talx.client.core;

import java.nio.ByteBuffer;
import java.util.Arrays;

import org.json.simple.JSONObject;

import lx.talx.client.Command;
import lx.talx.client.error.ClientSocketExceprion;
import lx.talx.client.net.*;
import lx.talx.client.security.UserCredential;
import lx.talx.client.service.*;
import lx.talx.client.utils.*;

public class Client {

  private byte[] key;

  private ServerAddress address;
  private Connection connection;
  private IMessageProcessor msgProcessor;
  private MessageAccomulator acc;
  private byte[] buf;
  private JSONObject user;

  private Protocol protocol;

  private UserCredential credential;

  public Client(IMessageProcessor msgProcessor) {
    this(new ServerAddress("127.0.0.1", 8181), msgProcessor); // default
  }

  public Client(final ServerAddress serverAddress, IMessageProcessor msgProcessor) {
    this.msgProcessor = msgProcessor;
    this.acc = new MessageAccomulator(this);
    this.address = serverAddress;
    this.connection = new Connection(address);
    this.protocol = new Protocol(connection);
    this.credential = new UserCredential();
    connect();
  }

  public void connect(int port) {
    address.setPort(port);
    connect();
  }

  public void connect() {
    if (!connection.getStatus()) {
      if (connection.connect()) {
        try {
          // --------------------------------------
          // TODO: Connect -> KeyExchange -> Authentication -> Autorize
          // --------------------------------------
          this.protocol.executeKeyExchange();

          // Authentication
          if (!credential.isKeyexist()) {
            auth();
          } else {
            key();
          }
          // --------------------------------------
          // --------------------------------------
        } catch (ClientSocketExceprion e) {
          e.printStackTrace();
        }
      }
    } else {
      Log.info("Connection to " + address + " is already open!");
    }
  }

  private void key() {

    byte[] command = "/key".getBytes();
    key = credential.readKey();

    ByteBuffer request = ByteBuffer.allocate(15 + key.length);
    request.put(command);
    request.put(15, key);

    protocol.sendEncrypted(request.array());

    buf = protocol.readEncrypted();

    if (new String(buf, 0, buf.length).equals("/accepted")) {
      System.out.println("------aacepted----");

      // TODO: Main entry
      UserAccount account = new UserAccount(this);
      
    }
  }

  public void auth() {

    System.out.println("\n--------- Login for Talx ---------\n");

    buf = prepareCredentionalData("/auth", "Username", "Password");
    protocol.sendEncrypted(buf);

    // response to get credentials
    buf = protocol.readEncrypted();

    if (buf.length == 0) {
      signup();
    } else {
      credential.saveKey(buf);
      key();
    }
  }

  private void signup() {

    System.out.println("\n--------- Sign up for Talx ---------\n");

    buf = prepareCredentionalData("/new", "NickName", "Username", "Email", "Password");
    protocol.sendEncrypted(buf);

    // AuthCode:
    buf = protocol.readEncrypted();
    System.out.print(new String(buf, 0, buf.length));
    protocol.sendEncrypted(Command.dataEnter(buf));

    buf = protocol.readEncrypted();

    if (buf.length != 0) {
      // get autoauth key and save key on disk
      credential.saveKey(buf);
      key();
    }
  }

  public byte[] prepareCredentionalData(String command, String... parameters) {

    this.user = new JSONObject();

    for (String item : parameters) {
      System.out.print(item.concat(": "));
      user.put(item.toLowerCase(), new String(Command.dataEnter(item.concat(": ").getBytes())));
    }

    ByteBuffer request = ByteBuffer.allocate(15 + user.toJSONString().getBytes().length);
    request.put(command.getBytes());
    request.put(15, user.toJSONString().getBytes());

    return request.array();
  }

  public void disconnect() {
    connection.kill();
  }

  public void reconnect() {
    connection.kill();
    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    connection.connect();
  }

  public void sendMsg(String str) {
    connection.send(str.getBytes());
  }

  public void status() {
    if (connection.getStatus()) {
      Log.info("Connected on: " + address);
    } else {
      Log.info("disconnected");
    }
  }

  public byte[] read() {
    return protocol.readEncrypted(); // read one data packet
  }

  public void receive(final String message) {
    msgProcessor.process(message);
  }

  public byte[] readSecure() {

    buf = protocol.readEncrypted();

    int reciveKeyLength = Util.byteToInt(Arrays.copyOfRange(buf, 0, 4));
    byte[] reciveKey = Arrays.copyOfRange(buf, 4, reciveKeyLength + 4);
    if (Arrays.equals(key, reciveKey)) {
      return Arrays.copyOfRange(buf, 4 + reciveKeyLength, buf.length);
    }
    return new byte[0];
  }

  public void sendSecure(byte[] bytes) {

    ByteBuffer b = ByteBuffer.allocate(4 + key.length + bytes.length);
    b.put(Util.intToByte(key.length)); // 4
    b.put(4, key);
    b.put(4 + key.length, bytes);

    protocol.sendEncrypted(b.array());
  }
}
