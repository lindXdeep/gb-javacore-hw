package lx.talx.client;

import lx.talx.client.error.WrongCommandException;
import lx.talx.client.service.ICommandProcessor;
import lx.talx.client.utils.Util;

public class Command implements ICommandProcessor {

  private Client client;

  public Command(Client client) {
    this.client = client;
  }

  @Override
  public void execute(String command) throws WrongCommandException {

    switch (command) {

      case "/read":
        client.readMsg();
        break;
      case "/status":
        client.status();
        break;
      case "/connect":
        client.connect();
        break;
      case "/disconnect":
        client.disconnect();
        break;
      case "/reconnect":
        client.reconnect();
        break;
      case "/exit":
        client.disconnect();
        System.out.println("\nbye...\n");
        System.exit(0);
        break;

      default:
        throw new WrongCommandException();

    }
    Util.printCursor();
  }
}
