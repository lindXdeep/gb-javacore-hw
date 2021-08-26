package lx.talx.client;

import lx.talx.client.utils.Log;
import lx.talx.client.utils.Menu;

public class App {

  private static Client client;

  public static void main(String... params) {

    try {
      client = Menu.setConnectParam(client, params).connect();
    } catch (RuntimeException e) {
      Log.info(e.getMessage());
    }


  }
}