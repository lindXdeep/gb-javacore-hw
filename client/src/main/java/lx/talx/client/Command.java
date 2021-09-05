package lx.talx.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.regex.Pattern;

import lx.talx.client.core.Client;
import lx.talx.client.error.WrongCommandException;
import lx.talx.client.service.ICommandProcessor;
import lx.talx.client.utils.Log;
import lx.talx.client.utils.Util;

public class Command implements ICommandProcessor {

  private Client client;

  private static int minConstraint = 6;
  private static int maxConstraint = 255;
  private Scanner cl = new Scanner(System.in);
  private static BufferedReader bufIn = new BufferedReader(new InputStreamReader(System.in));

  // regex pattern for email RFC822 compliant right format
  private static Pattern ptr = Pattern.compile(
      "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");

  public Command(Client client) {
    this.client = client;

    console();
  }

  private void console() {

    System.out.println("------------ console mode ------------");

    while (true) {

      Util.printCursor();

      while (cl.hasNext()) {

        try {
          
          execute(cl.nextLine());

        } catch (WrongCommandException e) {
          Log.error(e.getMessage());
          Util.printCursor();
        }
      }
    }
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

      if (byteToStr(requestMessage).equals("Email:\s")) {

        while (!ptr.matcher(str = bufIn.readLine()).matches()) {
          Log.error("Invalid email format");
          System.out.print(new String(requestMessage, 0, requestMessage.length));
        }

      } else {

        while ((str = bufIn.readLine()).length() < minConstraint || str.length() > maxConstraint) {

          if (str.length() < minConstraint) {
            Log.error("String cannot be shorter than " + minConstraint + " characters");
          } else if (str.length() > maxConstraint) {
            Log.error("String cannot be longer than " + maxConstraint + " characters");
          }

          System.out.print(new String(requestMessage, 0, requestMessage.length));
        }
      }

    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
    return str.getBytes();
  }

  public static String byteToStr(byte[] b) {
    return new String(b, 0, b.length);
  }
}
