package lx.talx.client;

import lx.talx.client.net.Connection;
import lx.talx.client.net.ServerAddress;
import lx.talx.client.service.IMessageProcessor;
import lx.talx.client.utils.Log;

public class Client {

  private ServerAddress address;
  private Connection connection;
  private IMessageProcessor msg;
  private byte[] buf;

  public Client() {
    this(new ServerAddress("127.0.0.1", 8181)); // default
  }

  public Client(final ServerAddress serverAddress) {
    this.address = serverAddress;
    this.connection = new Connection(address);
    connection.connect();
  }

  public void setMessageProcessor(IMessageProcessor msgProc) {
    this.msg = msgProc;
  }

  public void connect() {

    if (!connection.getStatus()) {

      connection.connect();

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

  public void sendMsg(String nextLine) {
    connection.send(nextLine.getBytes());
  }

  public void status() {
    if (connection.getStatus()) {
      Log.info("Connected on: " + address);
    } else {
      Log.info("disconnected");
    }
  }

  public byte[] read() {
    return connection.read();
  }

  public void receive(final String message) {
    msg.process(message);
  }
}
