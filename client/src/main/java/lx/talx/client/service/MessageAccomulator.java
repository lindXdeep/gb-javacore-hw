package lx.talx.client.service;

import java.nio.file.Files;

import lx.talx.client.core.Client;

public class MessageAccomulator {

  private Client client;
  private Thread thread;
  private Files file;

  public MessageAccomulator(Client client) {
    this.client = client;
  }

  public void readMeaasges(IMessageProcessor messageProcessor) {
    thread = new Thread(() -> {
      while (!Thread.currentThread().isInterrupted()) {

        byte[] b = client.read();

        messageProcessor.process(new String(b, 0, b.length));
      }
    });
    thread.start();
  }
}
