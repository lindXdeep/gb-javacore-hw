package lx.talx.server.net;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import lx.talx.server.Server;
import lx.talx.server.utils.Log;

public class Connection extends Thread {

  private Socket client;
  private Server server;

  private BufferedInputStream in;
  private BufferedOutputStream out;

  public Connection(Socket client, Server server) throws IOException {

    this.client = client;
    this.server = server;

    this.in = new BufferedInputStream(client.getInputStream());
    this.out = new BufferedOutputStream(client.getOutputStream());

    Log.info("Create I/O connection with " + client.toString());
  }

  @Override
  public void run() {

    while (true) {

      try {

        byte[] th = Thread.currentThread().toString().getBytes();
        byte[] he = "hello".getBytes();

        out.write(he);
        out.write(th);
        out.flush();

        in.read();

      } catch (IOException e) {
        e.printStackTrace();
      }
      
    }
    
  }
}
