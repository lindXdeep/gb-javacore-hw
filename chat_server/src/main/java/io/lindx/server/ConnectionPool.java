package io.lindx.server;

import java.util.HashMap;
import java.util.Map;

public class ConnectionPool {

  private int id;

  private Map<Integer, Connection> connections = new HashMap<>();

  public synchronized int push(Connection connect) {
    connections.put(id++, connect);
    return this.id;
  }

  public synchronized Connection pop() {
    return connections.get(id--);
  }
}
