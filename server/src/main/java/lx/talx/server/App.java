package lx.talx.server;

import lx.talx.server.error.WrongPortException;

public class App {

  private static Server server;
  private final static int PORT = 8181; // default
  private static int length;

  public static void main(String... args) {

    length = args.length;

    if (length == 1) {
      int i = 0;
      char ch;
      while ((i++ < length)) {
        if (!Character.isDigit((ch = args[0].charAt(i))))
          throw new WrongPortException();

        server = new Server(Integer.parseInt(args[0]));
      }
    } else {
      server = new Server(PORT);
    }
    server.start();
  }
}