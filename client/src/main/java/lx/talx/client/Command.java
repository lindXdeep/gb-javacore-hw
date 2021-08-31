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

    if (command.matches("^/status")) {
      client.status();
    } else if (command.matches("^/connect")) {
      client.connect();
    } else if (command.matches("^/connect\\s\\d{2,5}")) {
      client.connect(Integer.parseInt(command.split("\\s")[1]));
    } else if (command.matches("^/disconnect")) {
      client.disconnect();
    } else if (command.matches("^/reconnect")) {
      client.reconnect();
    } else if (command.matches("^/exit")) {
      client.disconnect();
      System.out.println("\nbye...\n");
      System.exit(0);
    } else if (command.matches("^/read")) {
      client.read();
    } else {
      throw new WrongCommandException();
    }

    Util.printCursor();
  }
}
