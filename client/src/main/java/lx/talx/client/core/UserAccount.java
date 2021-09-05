package lx.talx.client.core;

public class UserAccount {

  private Client client;
  private byte[] buf;

  public UserAccount(Client client) {
    this.client = client;


    

    client.sendSecure("zopa".getBytes());
    buf = client.readSecure();




    System.out.println(new String(buf, 0, buf.length));
  }

}
