package io.lindx.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import io.lindx.server.dao.UserDao;
import io.lindx.server.security.InMemoryAuthentication;
import io.lindx.server.security.error.UserNotFoundExeption;
import io.lindx.server.security.error.WrongCredentialsException;

public class Connection extends Thread {

  private Server server;
  private Socket client;
  private PrintWriter out;
  private BufferedReader in;

  private InMemoryAuthentication inMemory;

  public Connection(Socket client, Server server) {

    this.server = server;
    this.client = client;
    this.inMemory = new InMemoryAuthentication();

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
  }

  private void authorize() {

    long id = server.generateId();
    out.println("Hello, you id: " + id);

    String login = null;
    String pass = null;

    while (true) {

      try {
        out.print("Login: ");
        out.flush();
        login = in.readLine();
        out.print("Password: ");
        out.flush();
        pass = in.readLine();
        out.flush();

        out.println("---------");

        try {
          out.println(inMemory.getUserByLoginAndPass(login, pass));
        } catch (WrongCredentialsException e) {
          out.println(e.getMessage());
        }
       
      } catch (IOException e) {

      }
    }
  }
}
