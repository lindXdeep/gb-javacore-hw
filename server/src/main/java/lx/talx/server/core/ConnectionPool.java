package lx.talx.server.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import lx.talx.server.security.AuthProcessor;
import lx.talx.server.utils.Util;

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

  public void sendPrivateMessage(String sender, String recipient, String message) {

    byte[] msg = "@".concat(sender.concat(" ").concat(message.trim())).getBytes();

    if (contains(recipient)) {
      Iterator<Connection> cit = connections.get(recipient).iterator();
      while (cit.hasNext())
        cit.next().sendSecure(msg);
    }
  }

  public void sendPublicMessage(String sender, String message) {
    broadcast("@".concat(sender.concat(" ").concat(message.trim())));
  }

  public void broadcast(String msg) {

    Iterator<Entry<String, List<Connection>>> allconnects = connections.entrySet().iterator();
    while (allconnects.hasNext()) {
      Iterator<Connection> it = allconnects.next().getValue().iterator();
      while (it.hasNext())
        it.next().sendSecure(msg.getBytes());
    }
  }

  public void executeSendUsersOnline(String sender, String command) {
    sendResponse(sender, command, getAllUsers());
  }

  private void sendResponse(String recipient, String command, String response) {


    String msg = command.concat(" ").concat(response);

    System.out.println("send1: " + msg);

    if (contains(recipient)) {
      Iterator<Connection> cit = connections.get(recipient).iterator();
      while (cit.hasNext())
        cit.next().sendSecure(msg.getBytes());
    }
  }

  public String getAllUsers() {

    JSONArray online = new JSONArray();

    Iterator<Entry<String, List<Connection>>> i = connections.entrySet().iterator();

    while (i.hasNext()) {
      online.add(i.next().getKey());
    }

    return online.toJSONString();
  }

}
