package lx.talx.client.net;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import lx.talx.client.Client;
import lx.talx.client.error.CantReadBytesExeption;
import lx.talx.client.error.CantWriteBytesExeption;
import lx.talx.client.utils.Log;

public class Connection extends Thread {

  private boolean connected;

  private byte[] buffer;
  private int defBufSeze = 13107200; // 100 MegaBit // 12,5 MB // default

  private DataInputStream in;
  private DataOutputStream out;
  private Socket socket;

  private ServerAddress address;

  public Connection(ServerAddress address) {

    this.address = address;
    this.buffer = new byte[defBufSeze];
  }

  public boolean connect() {

    Log.info("Trying to connect to " + address);

    int i = 10;

    while (i-- > 0 & socket == null) {

      try {
        Thread.sleep(1000);
        this.socket = new Socket(address.getHost(), address.getPort());

        connected = true;

        if (connected) {
          Log.info("Connection with" + address + "established!");
          this.in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
          this.out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        }

      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (IOException e2) {
        System.out.printf(" %s", ".");
      }
    }
    return connected;
  }

  public byte[] getBuffer() {
    return buffer;
  }

  public byte[] read() {

    allocateBuffer();

    try {
      in.read(buffer);
    } catch (IOException e) {
      throw new CantReadBytesExeption();
    }
    return buffer;
  }

  private void allocateBuffer() {
    allocateBuffer(defBufSeze);
  }

  public void allocateBuffer(final int size) {
    this.buffer = new byte[size];
  }

  public void send(byte[] bytes) {
    try {
      out.write(bytes);
      out.flush();
    } catch (IOException e) {
      throw new CantWriteBytesExeption();
    }
  }
}
