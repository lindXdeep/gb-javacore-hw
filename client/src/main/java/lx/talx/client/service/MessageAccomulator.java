package lx.talx.client.service;

import lx.talx.client.Client;

public class MessageAccomulator extends Thread {

  private Client client;

  public MessageAccomulator(Client client) {
    this.client = client;
  }

  @Override
  public void run() {
    while (!Thread.currentThread().isInterrupted()) {

      byte[] b = client.read();

      client.receive(new String(b, 0, b.length));

    }

  }

}
