package lx.talx.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import lx.talx.client.error.WrongCommandException;
import lx.talx.client.service.ICommandProcessor;
import lx.talx.client.utils.Log;
import lx.talx.client.utils.Util;

public class Command implements ICommandProcessor {

  private Client client;

  private static int minConstraint = 6;
  private static int maxConstraint = 255;
  private static BufferedReader bufIn = new BufferedReader(new InputStreamReader(System.in));

  public Command(Client client) {
    this.client = client;
  }

  @Override
  public void execute(String command) throws WrongCommandException {
    if (command.matches("^/auth")) {
      client.auth();
    } else if (command.matches("^/status")) {
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
      throw new WrongCommandException(command);
    }

    Util.printCursor();
  }

  public static byte[] dataEnter(byte[] requestMessage) {

    String str = null;

    try {
      while ((str = bufIn.readLine()).length() < minConstraint || str.length() > maxConstraint) {

        if (str.length() < minConstraint) {
          Log.error("String cannot be shorter than " + minConstraint + " characters");
        } else if (str.length() > maxConstraint) {
          Log.error("String cannot be longer than " + maxConstraint + " characters");
        }

        System.out.print(new String(requestMessage, 0, requestMessage.length));
      }

    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
    return str.getBytes();
  }
}
