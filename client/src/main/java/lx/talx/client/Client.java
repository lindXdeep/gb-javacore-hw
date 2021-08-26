package lx.talx.client;

import java.io.IOException;
import java.net.Socket;

import lx.talx.client.net.Connection;
import lx.talx.client.net.ServerAddress;
import lx.talx.client.utils.*;

public class Client {

  private ServerAddress address;
  private Connection connection;

  public Client() {
    this(new ServerAddress("127.0.0.1", 8181)); // default
  }

  public Client(final ServerAddress serverAddress) {
    this.address = serverAddress;
    this.connection = new Connection(address);
  }

  public Client connect() {

    if (connection.connect()) {
      
      byte[] bbb = connection.read();

      System.out.println(new String(bbb, 0, bbb.length));

      byte[] hello = "Hello".getBytes();

      connection.send(hello);

    }

    return this;
  }

}
