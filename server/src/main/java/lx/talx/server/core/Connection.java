package lx.talx.server.core;

import java.io.*;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lx.talx.server.error.*;
import lx.talx.server.security.Auth;
import lx.talx.server.security.CryptProtocol;
import lx.talx.server.service.MessageService;
import lx.talx.server.utils.Log;
import lx.talx.server.utils.Util;

public class Connection extends Thread {

  private byte[] buffer;
  private int defBufSeze = 13107200; // 100 MegaBit // 12,5 MB // default

  private Socket client;
  private Server server;

  private DataInputStream in;
  private DataOutputStream out;

  private CryptProtocol protocol;
  private Auth auth;

  private MessageService msgServ;

  // regex pattern recipient user
  private Pattern pUser = Pattern.compile("^@[a-zA-Z]{0,255}\\s");
  private Pattern pMsg = Pattern.compile("\\s.{0,4096}");

  public Connection(Socket client, Server server) throws IOException {

    this.buffer = new byte[defBufSeze];

    this.client = client;
    this.server = server;
    this.protocol = new CryptProtocol(this);
    this.auth = new Auth(this);

    this.msgServ = new MessageService(server);

    this.in = new DataInputStream(new BufferedInputStream(client.getInputStream()));
    this.out = new DataOutputStream(new BufferedOutputStream(client.getOutputStream()));

    Log.info("Create I/O connection with " + client.toString());
  }

  @Override
  public void run() {

    protocol.executeKeyExchange();

    while (!auth.authorize(protocol.readEncrypted()))
      ;

    if (auth.isRevoke()) {
      Log.info("Auth passed. " + client);
      server.getConnectionPool().add(this);
    }

    // new Thread(() -> {
    // try {
    // Thread.sleep(5000);
    // } catch (InterruptedException e) {
    // e.printStackTrace();
    // }

    // kill();
    // }).start();

    try {

      String msg = null;

      while (auth.isRevoke() && (buffer = auth.readSecure()).length != 0) {

        if ((msg = Util.byteToStr(buffer)).matches("^@[a-zA-Z]{3,64}\\s.{0,4096}")) {

          Matcher m;
          String sender = server.getAuthProcessor().getCurrentUserName();
          String recipent = null;
          String message = null;

          if ((m = pUser.matcher(msg)).find())
            recipent = msg.substring(m.start(), m.end());

          if ((m = pMsg.matcher(msg)).find())
            message = msg.substring(m.start(), m.end()).trim();

          msgServ.processMessage(sender, recipent, message);

        }

        System.out.print(":::" + Util.byteToStr(buffer));
      }

    } catch (RuntimeException e) {
      System.out.println("самоубился");
    }

  }

  public byte[] readEncrypted() {
    return protocol.readEncrypted();
  }

  public void sendEncrypted(byte[] bytes) {
    protocol.sendEncrypted(bytes);
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
    try {
      String user = server.getAuthProcessor().getCurrentUserName();
      server.getConnectionPool().delete(user);
      server.getConnectionPool().broadcast("", "/status" + user + "offline");
      client.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void setClient(Socket client) {
    this.client = client;
  }

  public byte[] getBuffer() {
    return buffer;
  }

  public Socket getClient() {
    return this.client;
  }

  public CryptProtocol getProtocol() {
    return protocol;
  }

  public Server getServer() {
    return server;
  }

  public void sendSecure(byte[] bytes) {
    auth.sendSecure(bytes);
  }
}
