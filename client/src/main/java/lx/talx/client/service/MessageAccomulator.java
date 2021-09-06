package lx.talx.client.service;

import java.nio.file.Files;

import lx.talx.client.api.Connect;

public class MessageAccomulator {

  private Connect client;
  private Thread thread;
  private Files file;

  public MessageAccomulator(Connect client) {
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
