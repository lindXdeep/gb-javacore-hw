package lx.talx.server.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lx.talx.server.core.Server;

public class MessageService {

  // regex pattern recipient user
  private Pattern pUsr = Pattern.compile("^@[a-zA-Z]{0,255}\\s");
  private Pattern pMsg = Pattern.compile("\\s.{0,4096}");

  private Server server;

  public MessageService(Server server) {
    this.server = server;
  }

  public void processMessage(String msg) {

    Matcher m;
    String sender = server.getAuthProcessor().getCurrentUserName();
    String recipent = null;
    String message = null;

    if (msg.matches("^@[a-zA-Z]{3,64}\\s.{0,4096}")) {

      if ((m = pUsr.matcher(msg)).find())
        recipent = msg.substring(m.start(), m.end()).toLowerCase();

      if ((m = pMsg.matcher(msg)).find())
        message = msg.substring(m.start(), m.end()).toLowerCase();

      if (recipent.matches("^@all\\s")) {
        server.getConnectionPool().sendPublicMessage(sender, message);
      } else if (recipent.matches("^@[a-zA-Z]{0,64}\\s")) {

        server.getConnectionPool().sendPrivateMessage(sender, recipent.substring(1).trim(), message);
      }
    }
  }
}