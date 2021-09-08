package lx.talx.server.service;

import lx.talx.server.core.Server;

public class MessageService {

  private Server server;

  public MessageService(Server server) {
    this.server = server;
  }

  public void processMessage(String sender, String recipent, String message) {

    if (!server.getConnectionPool().contains(recipent)) {
      server.getConnectionPool().broadcast(recipent, "@".concat(sender).concat(" ").concat(message));
    }

  }
}
