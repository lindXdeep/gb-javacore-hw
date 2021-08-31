package lx.talx.server.net;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import lx.talx.server.Server;
import lx.talx.server.error.CantReadBytesExeption;
import lx.talx.server.error.CantWriteBytesExeption;
import lx.talx.server.utils.Log;
import lx.talx.server.utils.Util;

public class Connection extends Thread {

  private byte[] buffer;
  private int defBufSeze = 13107200; // 100 MegaBit // 12,5 MB // default

  private Socket client;
  private Server server;

  private DataInputStream in;
  private DataOutputStream out;

  private Protocol protocol;

  public Connection(Socket client, Server server) throws IOException {

    this.buffer = new byte[defBufSeze];

    this.client = client;
    this.server = server;
    this.protocol = new Protocol(this);

    this.in = new DataInputStream(new BufferedInputStream(client.getInputStream()));
    this.out = new DataOutputStream(new BufferedOutputStream(client.getOutputStream()));

    Log.info("Create I/O connection with " + client.toString());
  }

  @Override
  public void run() {

    protocol.executeKeyExchange();

    protocol.sendEncrypted(Util.getLogo().getBytes());
    protocol.sendEncrypted(Util.getInstruction().getBytes());

    System.out.println(protocol.readEncrypted());

    int i = 0;

    while (true) {
      
      getCredential();

      




      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      String msg = ("send -> " + i++);



      protocol.sendEncrypted(msg.getBytes());
      protocol.sendEncrypted(" hello ".getBytes());
      protocol.sendEncrypted(Thread.currentThread().toString().getBytes());
    
      System.out.println(msg);
    }
  }

  private void getCredential() {

    




  }

  public Socket getClient() {
    return this.client;
  }

  public byte[] read() throws CantReadBytesExeption {

    allocateBuffer();

    try {
      in.read(buffer);
    } catch (IOException e) {
      throw new CantReadBytesExeption();
    }
    return buffer;
  }

  public void send(byte[] bytes) {
    try {
      out.write(bytes);
      out.flush();
    } catch (IOException e) {
      throw new CantWriteBytesExeption();
    }
  }

  private void allocateBuffer() {
    allocateBuffer(defBufSeze);
  }

  public void allocateBuffer(final int size) {
    this.buffer = new byte[size];
  }

  public void kill() {
    System.out.println("kill");
  }
}
