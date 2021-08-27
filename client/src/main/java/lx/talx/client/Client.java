package lx.talx.client;

import lx.talx.client.error.ClientSocketExceprion;
import lx.talx.client.net.Connection;
import lx.talx.client.net.Protocol;
import lx.talx.client.net.ServerAddress;
import lx.talx.client.service.IMessageProcessor;
import lx.talx.client.utils.Log;

public class Client {

  private ServerAddress address;
  private Connection connection;
  private IMessageProcessor msg;
  private byte[] buf;

  private Protocol protocol;

  public Client(IMessageProcessor msgProcessor) {
    this(new ServerAddress("127.0.0.1", 8181), msgProcessor); // default
  }

  public Client(final ServerAddress serverAddress, IMessageProcessor msgProcessor) {
    this.msg = msgProcessor;
    this.address = serverAddress;
    this.connection = new Connection(address);
    this.protocol = new Protocol(connection);
    connect();
  }

  //TODO:main point
  public void connect() {

    if (!connection.getStatus()) {

      if (connection.connect()) {


        try {
          this.protocol.executeKeyExchange();


          byte[] b = protocol.readEncrypted();

          msg.process(new String(b, 0, b.length));



           protocol.sendEncrypted("hello crypted!".getBytes());



        
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
