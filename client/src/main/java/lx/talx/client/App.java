package lx.talx.client;

import lx.talx.client.api.Client;
import lx.talx.client.service.MsgProcessor;
import lx.talx.client.utils.Log;
import lx.talx.client.utils.Menu;

public class App {

  private static Client client;
  private static Command command;
  private static MsgProcessor msgProcessor;

  public static void main(String... params) {

    msgProcessor = new MsgProcessor();

    try {
      client = Menu.setConnectParam(client, params);
    } catch (RuntimeException e) {
      Log.log(e.getMessage());
    }

    // command line mode 
    command = new Command(client);
  }
}