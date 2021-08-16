package io.lindx.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import io.lindx.server.dao.InMemoryAuthentication;
import io.lindx.server.dao.UserDao;
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
    authorize();

    System.out.println("ok");
  }

  private void authorize() {

    out.println("Hello, anonymous!");

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

      server.log("trying from: " + client.getInetAddress());

      user = server.getAuth().getUserByLoginAndPass(login, pass);

    } catch (WrongCredentialsException | IOException e) {
      out.println(e.getMessage());
      server.log("Bad credential: `" + login + "` / `" + pass + "`");
      server.killConnection();
      return;
    }

    connectId = server.getConnectionPool().push(this);

    server.log("User: id: " + user.getId() + " joined on " + connectId + "'th connection");

    out.println(user);
  }
}
