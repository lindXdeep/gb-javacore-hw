package lx.talx.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.json.simple.JSONObject;

import lx.talx.client.api.Auth;
import lx.talx.client.api.Connect;
import lx.talx.client.error.WrongCommandException;
import lx.talx.client.service.ICommandProcessor;
import lx.talx.client.utils.Log;
import lx.talx.client.utils.Util;

public class Cl implements ICommandProcessor {

  private Connect connect;

  private int minConstraint = 6;
  private int maxConstraint = 255;
  private Scanner cl = new Scanner(System.in);

  private byte[] buf;

  private Auth auth;
  private static BufferedReader bufIn = new BufferedReader(new InputStreamReader(System.in));

  // regex pattern for email RFC822 compliant right format
  private static Pattern ptr = Pattern.compile(
      "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");

  public Cl(Connect connect) {
    this.connect = connect;
    this.auth = connect.getAuth();
    console();
  }

  private void console() {

    if (status()) {
      if (!auth.enterToAccount()) {
        System.out.println("Auth key not exist. Please login\n");
        auth();
      }
    }

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
    if (command.matches("^/auth") || command.matches("^1")) {
      auth();
    } else if (command.matches("^/status") || command.matches("^2")) {
      status();
    } else if (command.matches("^/connect") || command.matches("^3")) {
      connect();
    } else if (command.matches("^/connect\\s\\d{2,5}") || command.matches("^4\\s\\d{2,5}")) {
      connect(Integer.parseInt(command.split("\\s")[1]));
    } else if (command.matches("^/disconnect") || command.matches("^5")) {
      disconnect();
    } else if (command.matches("^/reconnect") || command.matches("^6")) {
      reconnect();
    } else if (command.matches("^/logout") || command.matches("^7")) {
      logout();
    } else if (command.matches("^/exit") || command.matches("^8")) {
      exit();
    } else if (command.matches("^/read") || command.matches("^9")) {
      read();
    } else if (command.matches("^/help") || command.matches("^10")) {
      help();
    } else {
      throw new WrongCommandException(command);
    }

    Util.printCursor();
  }

  private void connect() {
    if (connect.connect()) {
      if (!auth.enterToAccount()) {
        auth();
      }
    } else {
      System.out.println("\nConnection to " + connect.getAddress().getHost() + " is already open!\n");
    }
  }

  private void connect(int port) {
    connect.connect(port);
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
    if (!connect.disconnect()) {
      System.out.println("\nNo connection to server\n");
    }
  }

  private boolean status() {
    if (connect.getStatus()) {
      System.out.println("Connected on: " + connect.getAddress());
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

    if (!auth.auth(prepareCredentionalData("Username", "Password"))) {
      signup();
    } else {
      System.out.println("Login successful!\n");
    }
  }

  private void signup() {

    System.out.println("\n--------- Sign up for Talx ---------\n");

    auth.signup(prepareCredentionalData("NickName", "Username", "Email", "Password"));

    // AuthCode:
    buf = read();
    System.out.print(Util.byteToStr(buf).concat("AuthCode: "));
    if (auth.authCode(dataEnter(buf))) {
      System.out.println("Login!");
    } else {
      System.out.println("Authorization code is not correct");
    }
  }

  private void logout() {
    auth.removeKey();
    exit();
  }

  private byte[] read() {
    return connect.read();
  }

  private JSONObject prepareCredentionalData(String... parameters) {

    JSONObject user = new JSONObject();

    for (String item : parameters) {
      System.out.print(item.concat(": "));
      user.put(item.toLowerCase(), new String(dataEnter(item.concat(": ").getBytes())));
    }

    return user;
  }

  private byte[] dataEnter(byte[] requestMessage) {

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

  private void help() {

    String[] help = { " 1. /auth               - Authentication", " 2. /status             - Сurrent status",
        " 3. /connect            - Try connect to the server using last address:".concat(
            connect.getAddress().toString()),
        " 4. /connect <PORT>     - Try connect to the server using custom port",
        " 5. /disconnect         - Disconnect from the Server", " 6. /reconnect          - Reconnect to the Server",
        " 7. /logout             - Logout from the user account", " 8. /exit               - Exit from the Talx",
        " 9. /recive             - Read current stream", "10. /help               - Help", };

    for (String h : help) {
      System.out.println(h);
    }
  }
}
