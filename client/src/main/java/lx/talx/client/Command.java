package lx.talx.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.json.simple.JSONObject;

import lx.talx.client.api.Client;
import lx.talx.client.error.WrongCommandException;
import lx.talx.client.service.ICommandProcessor;
import lx.talx.client.utils.Log;
import lx.talx.client.utils.Util;

public class Command implements ICommandProcessor {

  private Client client;

  private static int minConstraint = 6;
  private static int maxConstraint = 255;
  private Scanner cl = new Scanner(System.in);

  private byte[] buf;
  private static BufferedReader bufIn = new BufferedReader(new InputStreamReader(System.in));

  // regex pattern for email RFC822 compliant right format
  private static Pattern ptr = Pattern.compile(
      "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");

  public Command(Client client) {
    this.client = client;

    if (status()) {
      if (!client.enterToAccount()) {
        System.out.println("Auth key not exist. Please login\n");
        auth();
      }
    }
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
      auth();
    } else if (command.matches("^/status")) {
      status();
    } else if (command.matches("^/connect")) {
      connect();
    } else if (command.matches("^/connect\\s\\d{2,5}")) {
      connect(Integer.parseInt(command.split("\\s")[1]));
    } else if (command.matches("^/disconnect")) {
      disconnect();
    } else if (command.matches("^/reconnect")) {
      reconnect();
    } else if (command.matches("^/logout")) {
      logout();
    } else if (command.matches("^/exit")) {
      exit();
    } else if (command.matches("^/read")) {
      read();
    } else {
      throw new WrongCommandException(command);
    }

    Util.printCursor();
  }

  private void connect() {
    if (client.connect()) {
      if (!client.enterToAccount()) {
        auth();
      }
    } else {
      System.out.println("\nConnection to " + client.getAddress().getHost() + " is already open!\n");
    }
  }

  private void connect(int port) {
    client.connect(port);
  }

  private void reconnect() {

    disconnect();

    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    connect();
  }

  private void disconnect() {
    if (!client.disconnect()) {
      System.out.println("\nNo connection to server\n");
    }
  }

  private boolean status() {
    if (client.getStatus()) {
      System.out.println("Connected on: " + client.getAddress());
      return true;
    } else {
      System.out.println("disconnected");
      return false;
    }
  }

  private void exit() {
    disconnect();
    System.out.println("\nbye...\n");
    System.exit(0);
  }

  private void auth() {
    System.out.println("\n--------- Login for Talx ---------\n");

    if (!client.auth(prepareCredentionalData("Username", "Password"))) {
      signup();
    } else {
      System.out.println("Login successful!\n");
    }
  }

  private void signup() {

    System.out.println("\n--------- Sign up for Talx ---------\n");

    client.signup(prepareCredentionalData("NickName", "Username", "Email", "Password"));

    // AuthCode:
    buf = read();
    System.out.print(Util.byteToStr(buf).concat("AuthCode: "));
    if (client.authCode(Command.dataEnter(buf))) {
      System.out.println("Login!");
    } else {
      System.out.println("Authorization code is not correct");
    }
  }

  private void logout() {
    client.removeKey();
    exit();
  }

  private byte[] read() {
    return client.read();
  }

  private JSONObject prepareCredentionalData(String... parameters) {

    JSONObject user = new JSONObject();

    for (String item : parameters) {
      System.out.print(item.concat(": "));
      user.put(item.toLowerCase(), new String(dataEnter(item.concat(": ").getBytes())));
    }

    return user;
  }

  private static byte[] dataEnter(byte[] requestMessage) {

    String str = null;

    try {

      if (Util.byteToStr(requestMessage).equals("Email:\s")) {

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

}
