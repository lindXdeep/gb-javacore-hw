package lx.talx.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
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
    this.credential = new UserCredential(this);
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

          if (!credential.isKeyexist()) {
            auth();
          } else {

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

  public void auth() {

    user = new JSONObject();

    String[] authData = { "Login/Email", "Password" };

    for (String item : authData) {
      System.out.print(item.concat(": "));
      user.put(item.toLowerCase(), new String(Command.dataEnter(item.concat(": ").getBytes())));
    }

    byte[] command = "/auth".getBytes();
    byte[] parameter = user.toJSONString().getBytes();

    ByteBuffer request = ByteBuffer.allocate(15 + parameter.length);
    request.put(command);
    request.put(15, parameter);

    protocol.sendEncrypted(request.array());

    // Login/Email
    // buf = protocol.readEncrypted();
    // System.out.print(new String(buf, 0, buf.length));
    // protocol.sendEncrypted(Command.dataEnter(buf));

    // Passworld
    // buf = protocol.readEncrypted();
    // System.out.print(new String(buf, 0, buf.length));
    // protocol.sendEncrypted(Command.dataEnter(buf));

    // response to get credentials
    if (protocol.readEncrypted().length == 0) {
      signup();
    }
  }

  private void signup() {

    System.out.println("\n--------- Sign up for Talx ---------\n");

    this.user = new JSONObject();

    // NickName, Username, Email, Password:
    String[] credentionalData = { "NickName", "Username", "Email", "Password" };

    for (String item : credentionalData) {
      System.out.print(item.concat(": "));
      user.put(item.toLowerCase(), new String(Command.dataEnter(item.concat(": ").getBytes())));
    }

    byte[] command = "/new".getBytes();
    byte[] parameter = user.toJSONString().getBytes();

    ByteBuffer request = ByteBuffer.allocate(15 + parameter.length);
    request.put(command);
    request.put(15, parameter);

    protocol.sendEncrypted(request.array());

    // AuthCode:
    buf = protocol.readEncrypted();
    System.out.print(new String(buf, 0, buf.length));
    protocol.sendEncrypted(Command.dataEnter(buf));

    // get auto auth key and save key on disk
    credential.createKey(protocol.readEncrypted());
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
