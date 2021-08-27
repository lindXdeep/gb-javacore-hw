package lx.talx.server.net;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import lx.talx.server.Server;
import lx.talx.server.utils.Log;

public class Connection extends Thread {

  private byte[] buffer;

  private Socket client;
  private Server server;

  private DataInputStream in;
  private DataOutputStream out;

  public Connection(Socket client, Server server) throws IOException {

    this.client = client;
    this.server = server;

    this.in = new DataInputStream(new BufferedInputStream(client.getInputStream()));
    this.out = new DataOutputStream(new BufferedOutputStream(client.getOutputStream()));

    Log.info("Create I/O connection with " + client.toString());
  }

  @Override
  public void run() {

    
      try {

        byte[] th = Thread.currentThread().toString().getBytes();
        byte[] he = "hello".getBytes();

        out.write(he);
        out.write(th);
        out.flush();

        buffer = new byte[123];

        //TODO: если клиент не отправляет данные то java.net.SocketException: Connection reset
        in.read(buffer);


        System.out.println(new String(buffer, 0, buffer.length));

       

      } catch (IOException e) {
        e.printStackTrace();
      }

    

  }
}
