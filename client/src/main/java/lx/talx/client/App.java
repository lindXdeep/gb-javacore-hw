package lx.talx.client;

import java.util.Scanner;

import lx.talx.client.error.WrongCommandException;
import lx.talx.client.service.ICommandProcessor;
import lx.talx.client.service.IMessageProcessor;
import lx.talx.client.utils.Log;
import lx.talx.client.utils.Menu;
import lx.talx.client.utils.Util;

public class App {

  // TODO: создать нормальный класс
  private static IMessageProcessor msgProcessor = new IMessageProcessor() {
    @Override
    public void process(String message) {
      System.out.println("recive: " + message);
    }
  };

  private static Client client;
  private static Command command;

  public static void main(String... params) {

    try {
      client = Menu.setConnectParam(client, params, msgProcessor);
    } catch (RuntimeException e) {
      Log.info(e.getMessage());
    }

    command = new Command(client);

    // TODO ; УдОлить
    Scanner sc = new Scanner(System.in);
    System.out.println("------------");
    while (true) {

      Util.printCursor();

      while (sc.hasNext()) {

        try {
          command.execute(sc.nextLine());

        } catch (WrongCommandException e) {
          Log.error(e.getMessage());
          Util.printCursor();
        }
      }
    }
  }
}