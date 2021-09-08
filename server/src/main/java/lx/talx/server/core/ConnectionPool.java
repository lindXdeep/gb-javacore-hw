package lx.talx.server.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import lx.talx.server.security.AuthProcessor;

public class ConnectionPool {

  private AuthProcessor authProcessor;
  private Map<String, List<Connection>> connections = new HashMap<>();

  public ConnectionPool(AuthProcessor authProcessor) {
    this.authProcessor = authProcessor;
  }

  public void add(Connection connection) {

    String username = authProcessor.getCurrentUserName();

    if (!contains(username)) {
      connections.put(username, new ArrayList<Connection>());
    }

    connections.get(username).add(connection);
  }

  public void delete(String username) {
    connections.remove(username);
  }

  public boolean contains(String username) {
    return connections.containsKey(username);
  }

  public void broadcast(String msg) {

    Iterator<Entry<String, List<Connection>>> allconnects = connections.entrySet().iterator();

    while (allconnects.hasNext()) {

      Iterator<Connection> it = allconnects.next().getValue().iterator();

      while (it.hasNext()) {
        it.next().sendSecure(msg.getBytes());
      }
    }
  }

  public void broadcast(String recipent, String message) {

    if (contains(recipent)) {

      Iterator<Connection> cit = connections.get(recipent).iterator();

      while (cit.hasNext()) {
        cit.next().sendSecure(message.getBytes());
      }
    }
  }
}
