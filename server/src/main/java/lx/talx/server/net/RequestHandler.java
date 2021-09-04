package lx.talx.server.net;

import java.net.Socket;

import org.json.simple.JSONObject;

import lx.talx.server.Server;
import lx.talx.server.model.User;
import lx.talx.server.service.UserService;
import lx.talx.server.service.UserServiceImpl;
import lx.talx.server.utils.Log;
import lx.talx.server.utils.Util;

public class RequestHandler {

  private Connection connection;
  private Socket client;
  private Server server;
  private UserService userService;

  public RequestHandler(Connection connection) {
    this.connection = connection;
    this.client = connection.getClient();
    this.server = connection.getServer();

    this.userService = new UserServiceImpl();
  }

  public void authorize(byte[] buffer) {

    // first 15 bytes for command
    String command = new String(buffer, 0, (buffer.length < 15 ? buffer.length : 15));

    if (command.startsWith("/key")) {

      String key = new String(buffer, 15, buffer.length - 15);

      User user = server.getAuthProvider().authenticate(key);

      if (user != null) {
        connection.sendEncrypted("auth ok!".getBytes());
        Log.info("Login: " + user.getUserName() + " / " + user.getEmail());
      } else {
        connection.sendEncrypted(new byte[0]);
      }

    } else if (command.startsWith("/auth")) {

      Log.info("Trying login from ".concat(Util.getIp(client)));
      JSONObject tmpUser = Util.parseCredential(buffer, 15);
      connection.sendEncrypted(server.getAuthProvider().authenticate(tmpUser)); // send key or 0;
      Log.info("Auth: " + (String) tmpUser.get("username") + " / " + (String) tmpUser.get("email"));

    } else if (command.startsWith("/new")) {

      JSONObject tmpUser = Util.parseCredential(buffer, 15);
      char[] authcode = server.getAuthProvider().getAuthCodeAndSendToEmail(tmpUser);
      String note = "Authentication code sent to your email: ".concat((String) tmpUser.get("email")).concat("\n\n");
      connection.sendEncrypted(note.concat("AuthCode: ").getBytes());
      byte[] responseAuthcode = connection.readEncrypted();

      // if auth right then send [key] or [0]
      if (String.valueOf(authcode).equals(new String(responseAuthcode, 0, responseAuthcode.length))) {
        connection.sendEncrypted(server.getAuthProvider().create(tmpUser, authcode)); // send key for autologin
        Log.info("New User: " + (String) tmpUser.get("username") + " / " + (String) tmpUser.get("email"));
      } else {
        connection.sendEncrypted(new byte[0]);
      }
    }
  }
}
