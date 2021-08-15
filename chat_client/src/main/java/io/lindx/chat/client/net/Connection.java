package io.lindx.chat.client.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Connection {

  private final String HOST;
  private final int PORT;
  private InetAddress ADDRESS;

  private BufferedReader in;
  private PrintWriter out;

  private Socket socket;

  public Connection(String host, int port) {

    this.HOST = host;
    this.PORT = port;

    try {
      this.ADDRESS = InetAddress.getByName(HOST);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void call() {

    System.out.println("Trying connect to: " + ADDRESS);

    try {
      socket = new Socket(ADDRESS, PORT);
    } catch (IOException e1) {
      System.out.println("Cant Connect to: " + ADDRESS);
      shotdown();
      System.out.println("Server off");
      return;
    }

    System.out.println("Connect to: [" + HOST + ":" + PORT + "] succeseful!");

    try {
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void shotdown() {
    try {
      socket.close();
    } catch (IOException e) {
      System.out.println("Connection close");
    }
  }

  public void send(String string) {
    out.println(string);
  }
}
