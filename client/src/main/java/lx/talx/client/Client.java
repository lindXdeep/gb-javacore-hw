package lx.talx.client;

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

  private Protocol protocol;

  private Thread thread;

  public Client(IMessageProcessor msgProcessor) {
    this(new ServerAddress("127.0.0.1", 8181), msgProcessor); // default
  }

  public Client(final ServerAddress serverAddress, IMessageProcessor msgProcessor) {
    this.msgProcessor = msgProcessor;
    this.acc = new MessageAccomulator(this);
    this.address = serverAddress;
    this.connection = new Connection(address);
    this.protocol = new Protocol(connection);
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

          


          acc.readMeaasges(msgProcessor);

          protocol.sendEncrypted("hello crypted!".getBytes());

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
