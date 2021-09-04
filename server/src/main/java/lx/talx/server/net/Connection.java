package lx.talx.server.net;

import java.io.*;
import java.net.Socket;

import lx.talx.server.Server;
import lx.talx.server.error.*;
import lx.talx.server.utils.Log;

public class Connection extends Thread {

  private byte[] buffer;
  private int defBufSeze = 13107200; // 100 MegaBit // 12,5 MB // default

  private Socket client;
  private Server server;

  private DataInputStream in;
  private DataOutputStream out;

  private Protocol protocol;
  private RequestHandler requestHandler;

  public Connection(Socket client, Server server) throws IOException {

    this.buffer = new byte[defBufSeze];

    this.client = client;
    this.server = server;
    this.protocol = new Protocol(this);
    this.requestHandler = new RequestHandler(this);

    this.in = new DataInputStream(new BufferedInputStream(client.getInputStream()));
    this.out = new DataOutputStream(new BufferedOutputStream(client.getOutputStream()));

    Log.info("Create I/O connection with " + client.toString());
  }

  public byte[] readEncrypted() {
    return protocol.readEncrypted();
  }

  public void sendEncrypted(byte[] bytes) {
    protocol.sendEncrypted(bytes);
  }

  @Override
  public void run() {

    protocol.executeKeyExchange();

    while (true) {

      buffer = protocol.readEncrypted();

      requestHandler.authorize(buffer);

      System.out.println("все прошло!");

    }
  }
  public Socket getClient() {
    return this.client;
  }

  // Not secure
  public byte[] read() throws CantReadBytesExeption {

    allocateBuffer();

    try {
      in.read(buffer);
    } catch (IOException e) {
      throw new CantReadBytesExeption();
    }
    return buffer;
  }

  // Not secure
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

  public byte[] getBuffer() {
    return buffer;
  }

  /**
   * @param client the client to set
   */
  public void setClient(Socket client) {
    this.client = client;
  }

  /**
   * @return Server return the server
   */
  public Server getServer() {
    return server;
  }

  /**
   * @return Protocol return the protocol
   */
  public Protocol getProtocol() {
    return protocol;
  }

}
