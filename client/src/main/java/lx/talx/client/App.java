package lx.talx.client;

import java.util.Scanner;

import lx.talx.client.service.IMessageProcessor;
import lx.talx.client.utils.Log;
import lx.talx.client.utils.Menu;

public class App {

  private static Client client;

  // TODO: создать нормальный класс
  private static IMessageProcessor msgProcessor = new IMessageProcessor() {
    @Override
    public void process(String message) {
      sout(message);
    }
  };

  public static void main(String... params) {

    try {
      client = Menu.setConnectParam(client, params).connect();
      client.setMessageProcessor(msgProcessor);
    } catch (RuntimeException e) {
      Log.info(e.getMessage());
    }

    // TODO ; УдОлить
    Scanner sc = new Scanner(System.in);
    while (sc.hasNext()) {
      client.sendMsg(sc.nextLine());
    }

  }

  // TODO ; удОлить
  public static void sout(String s) {
    System.out.println(s);
  }

}