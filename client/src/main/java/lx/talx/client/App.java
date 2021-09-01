package lx.talx.client;

import java.util.Scanner;

import lx.talx.client.error.WrongCommandException;
import lx.talx.client.service.MsgProcessor;
import lx.talx.client.utils.*;

public class App {

  private static Client client;
  private static Command command;
  private static MsgProcessor msgProcessor;

  public static void main(String... params) {

    msgProcessor = new MsgProcessor();

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