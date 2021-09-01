package lx.talx.server.net;

import java.net.Socket;
import java.util.Arrays;

import lx.talx.server.utils.Log;
import lx.talx.server.utils.Util;


public class RequestHandler {

  private Connection connection;
  private Socket client;

  public RequestHandler(Connection connection) {
    this.connection = connection;
    this.client = connection.getClient();
  }

  public void menu(byte[] buffer) {

    // first 15 bytes for commands
    String command = new String(new String(Arrays.copyOfRange(buffer, 0, 15), 0, buffer.length));

    if (command.matches("/auth")) {

      Log.info("Trying log in from ".concat(Util.getIp(client)));

      while (true) {

        connection.sendEncrypted("Login/Email: ".getBytes());
        
        buffer = connection.readEncrypted();

        System.out.println(new String(buffer, 0, buffer.length));

        connection.sendEncrypted("Password: ".getBytes());
        
        buffer = connection.readEncrypted();

        System.out.println(new String(buffer, 0, buffer.length));

        

      }

    } else if (command.matches("/key")) {

    }

  }
}
