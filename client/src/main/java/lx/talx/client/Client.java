package lx.talx.client;

import lx.talx.client.net.ServerAddress;

public class Client {

  private ServerAddress address;

  public Client() {
    this(new ServerAddress("127.0.0.1", 8181)); // default
  }


  public Client(final ServerAddress serverAddress) {
    this.address = serverAddress;
  }

  public void connect() {
    System.out.println(address);
  }
  
}
