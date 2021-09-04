package lx.talx.client;

import java.nio.ByteBuffer;

import org.json.simple.JSONObject;

import lx.talx.client.error.ClientSocketExceprion;
import lx.talx.client.net.Connection;
import lx.talx.client.net.Protocol;
import lx.talx.client.net.ServerAddress;
import lx.talx.client.service.IMessageProcessor;
import lx.talx.client.service.MessageAccomulator;
import lx.talx.client.utils.Log;

public class Client {

  private ServerAddress address;
  private Connection connection;
  private IMessageProcessor msgProcessor;
  private MessageAccomulator acc;
  private byte[] buf;
  private JSONObject user;

  private Protocol protocol;

  private Thread thread;

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
          // TODO: main point.
          // --------------------------------------
          this.protocol.executeKeyExchange();

          System.out.println("\nключами обменялись\n");

          if (!credential.isKeyexist()) {
            auth();
          } else {
            System.out.println("key exist");
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
    byte[] parameter = credential.readKey();

    ByteBuffer request = ByteBuffer.allocate(15 + parameter.length);
    request.put(command);
    request.put(15, parameter);

    protocol.sendEncrypted(request.array());

    System.out.println(protocol.readEncrypted());
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

    // get autoauth key and save key on disk
    credential.saveKey(buf);
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
}
