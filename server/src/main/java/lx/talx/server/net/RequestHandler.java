package lx.talx.server.net;

import java.net.Socket;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import lx.talx.server.Server;
import lx.talx.server.utils.Log;
import lx.talx.server.utils.Util;

public class RequestHandler {

  private Connection connection;
  private Socket client;
  private Server server;

  public RequestHandler(Connection connection) {
    this.connection = connection;
    this.client = connection.getClient();
    this.server = connection.getServer();
  }

  public void menu(byte[] buffer) {

    // first 15 bytes for command
    String command = new String(buffer, 0, (buffer.length < 15 ? buffer.length : 15));

    if (command.matches("/auth")) {

      Log.info("Trying log in from ".concat(Util.getIp(client)));

      connection.sendEncrypted("Login/Email: ".getBytes());
      server.getAuthProvider().setLogin(connection.readEncrypted());

      connection.sendEncrypted("Password: ".getBytes());
      server.getAuthProvider().setPass(connection.readEncrypted());

      connection.sendEncrypted(server.getAuthProvider().authenticate());

    } else if (command.startsWith("/new")) {

      JSONObject tmpUser = null;

      try {
        tmpUser = (JSONObject) new JSONParser().parse(new String(buffer, 15, buffer.length - 15));
      } catch (ParseException e) {
        e.printStackTrace();
      }

      char[] authcode = server.getAuthProvider().getAuthCodeAndSendToEmail(tmpUser);

      String note = "We have sent Authentication code to your email: ".concat((String) tmpUser.get("email")).concat("\n\n");
      connection.sendEncrypted(note.concat("AuthCode: ").getBytes());

      buffer = connection.readEncrypted();
      System.out.println(new String(buffer, 0, buffer.length));



      // connection.sendEncrypted("NickName: ".getBytes());
      // server.getAuthProvider().setLogin(connection.readEncrypted());

      // connection.sendEncrypted("UserName: ".getBytes());
      // server.getAuthProvider().setLogin(connection.readEncrypted());

      // connection.sendEncrypted("UserName: ".getBytes());
      // server.getAuthProvider().setLogin(connection.readEncrypted());

    }

  }
}
