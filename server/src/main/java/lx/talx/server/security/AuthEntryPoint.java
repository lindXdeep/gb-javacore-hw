package lx.talx.server.security;

import java.net.Socket;
import java.util.Arrays;

import org.json.simple.JSONObject;

import lx.talx.server.Server;
import lx.talx.server.core.CoreProcess;
import lx.talx.server.net.Connection;
import lx.talx.server.utils.Log;
import lx.talx.server.utils.Util;

public class AuthEntryPoint {

  private CoreProcess core;

  private Connection connection;
  private Socket client;
  private Server server;

  private byte[] buf;

  public AuthEntryPoint(Connection connection) {
    this.connection = connection;
    this.client = connection.getClient();
    this.server = connection.getServer();
  }

  public void authorize(byte[] buffer) {

    // first 15 bytes for command
    String command = new String(buffer, 0, (buffer.length < 15 ? buffer.length : 15));

    if (command.startsWith("/key")) {

      String key = new String(buffer, 15, buffer.length - 15);

      if (server.getAuthProvider().enable(key)) {
        connection.sendEncrypted("/accepted".getBytes());
        // --------------------------------------
        // TODO: Main Entry point
        // --------------------------------------

        core = new CoreProcess(this, server.getAuthProvider());

        // --------------------------------------
        // --------------------------------------
      } else {
        connection.sendEncrypted(new byte[0]);
      }

    } else if (command.startsWith("/auth")) {

      Log.info("Trying login from ".concat(Util.getIp(client)));
      JSONObject tmpUser = Util.parseCredential(buffer, 15);
      connection.sendEncrypted(server.getAuthProvider().authenticate(tmpUser)); // send [key] or [0]
      Log.info("Auth: " + (String) tmpUser.get("username") + " / " + (String) tmpUser.get("email"));

    } else if (command.startsWith("/new")) {

      JSONObject tmpUser = Util.parseCredential(buffer, 15);
      char[] authcode = server.getAuthProvider().getAuthCodeAndSendToEmail(tmpUser);
      String note = "Authentication code sent to your email: ".concat((String) tmpUser.get("email")).concat("\n\n");
      connection.sendEncrypted(note.getBytes());
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

  public byte[] readSecure() {

    buf = connection.readEncrypted();

    int reciveKeyLength = Util.byteToInt(Arrays.copyOfRange(buf, 0, 4));
    byte[] reciveKey = Arrays.copyOfRange(buf, 4, reciveKeyLength + 4);
    if (server.getAuthProvider().isKeyEquals(reciveKey)) {
      return Arrays.copyOfRange(buf, 4 + reciveKeyLength, buf.length);
    }
    return new byte[0];
  }

  public void sendSecure(byte[] bytes) {

    if (server.getAuthProvider().getKeyIsEnabled().length != 0) {
      connection.sendEncrypted(bytes);
    } else {
      connection.sendEncrypted(new byte[0]);
    }
  }
}
