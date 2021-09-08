package lx.talx.client.core;

import java.nio.file.Files;

import lx.talx.client.api.Connect;
import lx.talx.client.service.IMessageProcessor;

public class MessageAccomulator {

  private Connect connect;
  private Thread thread;
  private Files file;

  public MessageAccomulator(Connect connect) {
    this.connect = connect;
  }

  public void readMeaasges(IMessageProcessor messageProcessor) {

    thread = new Thread(() -> {
      while (!Thread.currentThread().isInterrupted()) {

        messageProcessor.process(connect.read());

      }
    });
    thread.start();
  }
}
