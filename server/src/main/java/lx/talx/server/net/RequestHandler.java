package lx.talx.server.net;

import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import lx.talx.server.Server;
import lx.talx.server.model.User;
import lx.talx.server.service.*;
import lx.talx.server.utils.*;

public class RequestHandler {

  private Connection connection;
  private Socket client;
  private Server server;
  private UserService userService;

  private MessageDigest sha1;

  public RequestHandler(Connection connection) {
    this.connection = connection;
    this.client = connection.getClient();
    this.server = connection.getServer();

    this.userService = new UserServiceImpl();

    try {
      this.sha1 = MessageDigest.getInstance("SHA-1");
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
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

      String note = "We have sent Authentication code to your email: ".concat((String) tmpUser.get("email"))
          .concat("\n\n");
      connection.sendEncrypted(note.concat("AuthCode: ").getBytes());

      byte[] responseAuthcode = connection.readEncrypted();

      if (String.valueOf(authcode).equals(new String(responseAuthcode, 0, responseAuthcode.length))) {

        if (userService.getUserByEmail((String) tmpUser.get("email")) == null
            & userService.getUserByUserName((String) tmpUser.get("username")) == null) {

          // pass to hash
          StringBuilder hPass = new StringBuilder();
          for (byte b : sha1.digest(((String) tmpUser.get("password")).getBytes()))
            hPass.append(String.format("%02X", b));

          User user = new User();
          user.setUserName((String) tmpUser.get("username"));
          user.setEmail((String) tmpUser.get("email"));
          user.setNickName((String) tmpUser.get("nickname"));
          user.setAuthCode(String.valueOf(authcode));
          user.setPassword(hPass.toString());

          userService.add(user);
        }
      }
    }
  }
}
