package lx.talx.client;

import lx.talx.client.api.Connect;
import lx.talx.client.core.MsgProcessor;
import lx.talx.client.service.ICommandLine;
import lx.talx.client.service.IMessageProcessor;
import lx.talx.client.utils.Log;
import lx.talx.client.utils.Menu;

public class App {

  private static Connect connect;
  private static ICommandLine commandLine;
  private static IMessageProcessor msgProcessor;

  public static void main(String... params) {

    msgProcessor = new MsgProcessor();

    try {
      connect = Menu.setConnectParam(connect, params);
    } catch (RuntimeException e) {
      Log.log(e.getMessage());
    }

    // command line mode 
    commandLine = new Cli(connect);
  }
}