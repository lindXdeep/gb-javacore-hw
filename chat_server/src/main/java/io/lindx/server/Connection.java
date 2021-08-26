package io.lindx.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

import io.lindx.server.model.User;
import io.lindx.server.security.error.UserNotFoundExeption;
import io.lindx.server.security.error.WrongCredentialsException;

public class Connection extends Thread {

  private int connectId;

  private Server server;
  private Socket client;
  private PrintWriter out;
  private BufferedReader in;

  private User user;

  public Connection(Socket client, Server server) {

    connectId++;

    this.server = server;
    this.client = client;

    try {
      out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
      in = new BufferedReader(new InputStreamReader(client.getInputStream()));
    } catch (IOException e) {
      e.printStackTrace();
    }
    server.log("Connection created!");
  }

  @Override
  public void run() {
    menu();
    System.out.println("ok");
  }

  private void menu() {

    sendMsg("Hello, anonymous!");
    sendMsg("Use command \"/help\" for more information about a command.");

    out.print("\n\n> ");
    out.flush();

    try {

      while (true) {

        String command = in.readLine();

        if (command.startsWith("/w ")) {
          String[] srcMsg = command.split(" ");

          String recipient = srcMsg[1];

          server.getConnectionPool().getConnectByNick(recipient)
              .sendMsg(new StringBuilder().append(Arrays.copyOfRange(srcMsg, 2, srcMsg.length)).toString());
        } else {
          switch (command) {
            case "/help":
              out.print("\nAvailable Commands:\n" +

                  "  /auth\t\t\t sign in \n" +

                  "  /new \t\t\t Create account\n" +

                  "  /end \t\t\t exit\n" +

                  "  /allusers \t\t show all users\n" +

                  "  /online \t\t show online users\n" +

                  "  /w [nick] [message] \t write private mesage for user\n" +

                  "> ");
              out.flush();
              break;
            case "/auth":
              authorize();
              break;
            case "/new":
              createAccount();
              break;
            case "/end":
              server.killConnection(this);
              break;
            case "/online":
              out.println("ConnectID\t Id\t Name\t nick");
              Iterator<Entry<Integer, Connection>> it = server.getConnectionPool().getAll().iterator();
              while (it.hasNext()) {
                Entry<Integer, Connection> c = it.next();
                User u = c.getValue().getUser();
                out.println(
                    "\t" + c.getKey() + " \t " + u.getId() + "\t " + u.getName() + "\t " + u.getNick() + "\t-online");
              }
              break;
            case "/allusers":
              Iterator<User> u = server.getAuth().getAll();
              while (u.hasNext()) {
                out.println(u.next());
              }
              break;
            case "/status":
              if (user == null) {
                out.println("\n\t OFFLINE \t\n");
              } else {
                out.println("\n\t ONLINE \t\n");
              }
              break;
            default:
              out.print("Use command \"/help\" for more information about a command.\n\n> ");
              out.flush();
          }
        }

      }
    } catch (IOException e) {
      server.log("Client from: " + client.getInetAddress() + " disconnected");
    }
  }

  private void createAccount() {

    String[] logPass = readLoginAndPass();
    String nick = null;

    User tmpUser = null;

    try {
      out.print("Nickname: ");
      out.flush();
      nick = in.readLine();

      if (!server.getAuth().isExist(tmpUser)) {
        if (logPass[0].length() > 4 || logPass[1].length() > 4 || nick.length() > 3 || !nick.equals(logPass[0])) {
          server.getAuth().addUser(new User(logPass[0], logPass[1], nick));
          server.getConnectionPool().broadcastMessage("new user --> " + tmpUser);
        } else {
          sendMsg("\n > Login and Nickname same, or something empty!");
        }
      }
    } catch (IOException e1) {
      e1.printStackTrace();
    }
  }

  private String[] readLoginAndPass() {

    String login = null;
    String pass = null;

    try {
      out.print("Login: ");
      out.flush();
      login = in.readLine();
      out.print("Password: ");
      out.flush();
      pass = in.readLine();
      out.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return new String[] { login, pass };
  }

  private void authorize() {

    String[] logPass = readLoginAndPass();

    try {
      server.log("trying from: " + client.getInetAddress());

      user = server.getAuth().getUserByLoginAndPass(logPass[0], logPass[1]);

    } catch (WrongCredentialsException e) {
      out.println(e.getMessage());
      server.log("Bad credential: `" + logPass[0] + "` / `" + logPass[1] + "`");
      server.killConnection(this);
      return;
    }

    try {
      server.getConnectionPool().isOnline(user);
    } catch (WrongCredentialsException e) {
      out.println("\n" + e.getMessage());
      server.log("Bad credential: `" + logPass[0] + "` / `" + logPass[1] + "` " + e.getMessage());
      return;
    }

    connectId = server.getConnectionPool().push(this);

    server.getConnectionPool().broadcastMessage("User: " + user.getNick() + " joined");

    server.log("User: id: " + user.getId() + " joined on " + connectId + "'th connection");

    out.println(user);
  }

  public void sendMsg(String msg) {
    out.println("\n " + msg + "\n");
    out.flush();
  }

  public int getConnectId() {
    return connectId;
  }

  public User getUser() {
    return user;
  }

  public int increment() {
    return connectId++;
  }

  public void kill() throws IOException {
    client.close();
  }
}
