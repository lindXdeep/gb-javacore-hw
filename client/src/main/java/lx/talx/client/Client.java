package lx.talx.client;

import lx.talx.client.net.*;
import lx.talx.client.service.IMessageProcessor;

public class Client {

  private ServerAddress address;
  private Connection connection;
  private IMessageProcessor msg;

  public Client() {
    this(new ServerAddress("127.0.0.1", 8181)); // default
  }

  public Client(final ServerAddress serverAddress) {
    this.address = serverAddress;
    this.connection = new Connection(address);
  }

  public void setMessageProcessor(IMessageProcessor msgProc) {
    this.msg = msgProc;
  }

  public Client connect() {

    if (connection.connect()) {

      byte[] bytesFromServer = connection.read();

      msg.process(new String(bytesFromServer, 0, bytesFromServer.length));

    }
    return this;
  }

  public void sendMsg(String nextLine) {
    connection.send(nextLine.getBytes());
  }


  
  

}
