package io.lindx.server;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import io.lindx.server.model.User;
import io.lindx.server.security.error.BadRequestException;
import io.lindx.server.security.error.WrongCredentialsException;

public class ConnectionPool {

  private int id;

  private Map<Integer, Connection> connections = new HashMap<>();

  public synchronized int push(Connection connect) {

    while (connections.containsKey(connect.increment()))
      ;
    id = connect.getConnectId();
    connections.put(id, connect);
    return this.id;
  }

  public synchronized Connection pop() {

    Connection connect = connections.get(id);
    connections.remove(id);
    id--;
    return connect;
  }

  public void broadcastMessage(String msg) {

    Iterator<Entry<Integer, Connection>> it = connections.entrySet().iterator();

    while (it.hasNext()) {
      it.next().getValue().sendMsg(msg);
    }
  }

  public int getId() {
    return id;
  }

  public boolean isOnline(User user) {

    Iterator<Connection> it = connections.values().iterator();

    while (it.hasNext()) {
      if (it.next().getUser().equals(user)) {
        throw new WrongCredentialsException("User '" + user.getNick() + "'' is already online");
      }
    }
    return false;
  }

  public void remove(int connectId) {

    connections.remove(connectId);

    Iterator<Connection> it = connections.values().iterator();

    while (it.hasNext()) {
      if (it.next().getConnectId() == connectId) {
        it.remove();
      }
    }
  }

  public Set<Entry<Integer, Connection>> getAll() {
    return connections.entrySet();
  }

  public Connection getConnectByNick(String recipient) {

    Iterator<Connection> it = connections.values().iterator();

    while (it.hasNext()) {
      Connection con = it.next();
      if (con.getUser().getNick().equals(recipient))
        return con;
    }
    throw new BadRequestException("Connection is not exist");
  }
}
