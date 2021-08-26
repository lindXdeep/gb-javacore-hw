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

    try {
      this.connection = new Connection(address, this);
    } catch (IOException e) {
      Log.error(e);
    }
  }

  public Client connect() {

    Log.info("Trying to connect to " + address);

    this.connection.start();

    Log.info("Connection with" + address + "established!");

    return this;
  }

}
