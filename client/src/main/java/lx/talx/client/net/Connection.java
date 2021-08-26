package lx.talx.client.net;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import lx.talx.client.Client;

public class Connection extends Thread {

  private byte[] buffer;

  private DataInputStream in;
  private DataOutputStream out;
  private Socket socket;

  public Connection(ServerAddress address, Client client) throws IOException {

    this.socket = new Socket(address.getHost(), address.getPort());
    this.in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
    this.out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
  }

  @Override
  public void run() {

    buffer = new byte[10];

    try {
      in.read(buffer);
    } catch (IOException e) {
      e.printStackTrace();
    }

    System.out.println(new String(buffer, 0 , buffer.length));

    

    
  }

}
