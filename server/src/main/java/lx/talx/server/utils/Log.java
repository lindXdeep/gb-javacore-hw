package lx.talx.server.utils;

import java.net.Socket;

public class Log {

  private static boolean chprnt;

  public static void info(String string) {
    if (!chprnt) {
      System.out.println("\n-> start.\n\t│\n\t├── " + string + "\n\t│");
    } else {
      System.out.println("\t├── " + string + "\n\t│");
    }
    chprnt = true;
  }

  public static void infoInvalidPublicKey(Socket client) {
    Log.info("Connection from:" + Util.getAddress(client) + "rejected because public key is invalid");
  }
}
