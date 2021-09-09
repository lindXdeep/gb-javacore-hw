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

  public void sendPrivateMessage(String sender, String recipent, String message) {

    byte[] msg = "@".concat(sender.concat(" ").concat(message.trim())).getBytes();

    if (contains(recipent)) {
      Iterator<Connection> cit = connections.get(recipent).iterator();
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

  public void commandSendUsersOnline() {
   
    JSONParser p = new JSONParser();

    try {
      JSONArray a = (JSONArray) p.parse(getAllUsers());


      for (int i = 0; i < a.size(); i++) {
        System.out.println(a.get(i));
      }



    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }


  }

  private String getAllUsers() {

    JSONArray online = new JSONArray();

    Iterator<Entry<String, List<Connection>>> i = connections.entrySet().iterator();

    while (i.hasNext()) {
      online.add(i.next().getKey());
    }

    return online.toJSONString();
  }
}
