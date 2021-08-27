package lx.talx.server.net;

import java.nio.ByteBuffer;
import java.security.GeneralSecurityException;
import java.util.Arrays;

import lx.talx.server.security.Crypt;
import lx.talx.server.utils.Log;
import lx.talx.server.utils.Util;

public class Protocol {

  private byte[] buf;

  private Crypt crypt;

  private Connection conncetion;

  private boolean encrypted;

  public Protocol(Connection connection) {
    this.conncetion = connection;
    this.crypt = new Crypt();
  }

  public void executeKeyExchange() {

    Log.info("Waiting public key from client: ".concat(Util.getAddress(conncetion.getClient())));

    try {

      crypt.setClientPubKey(readUncrypt(conncetion.read()));

    } catch (GeneralSecurityException e) {
      Log.infoInvalidPublicKey(conncetion.getClient());
      sendUncrypt("Access denied: public key is invalid.".concat(Util.getIp(conncetion.getClient())).getBytes());
      conncetion.kill();
    }

    if (conncetion.getClient().isClosed())
      return;

    Log.info("Received client public key from" + Util.getAddress(conncetion.getClient()));
    
    sendUncrypt(crypt.getPubKeyEncoded());
    Log.info("Sent server public key to client:" + Util.getAddress(conncetion.getClient()));

    encrypted = true;
  }

  private void sendUncrypt(byte[] bytes) {

    ByteBuffer buf = null;

    buf = ByteBuffer.allocate(4 + bytes.length); // 4 + all...
    buf.put(intToByte(bytes.length)); // 4 // length
    buf.put(bytes); // 4 + all

    conncetion.send(buf.array());
  }

  /**
   * return result message.
   * 
   * [length]:[byte msg]...
   * 
   * @param msg
   * @return
   */
  private byte[] readUncrypt(byte[] msg) {
    return Arrays.copyOfRange(msg, 4,

        byteToInt(Arrays.copyOfRange(msg, 0, 4)));
  }

  private int byteToInt(byte[] bytes) {
    return (bytes != null || bytes.length == 4) ?

        (int) ((0xFF & bytes[0]) << 24 |

            (0xFF & bytes[1]) << 16 |

            (0xFF & bytes[2]) << 8 |

            (0xFF & bytes[3]) << 0

        ) : 0x0;
  }

  private byte[] intToByte(final int i) {
    return new byte[] {

        (byte) ((i >> 24) & 0xFF),

        (byte) ((i >> 16) & 0xFF),

        (byte) ((i >> 8) & 0xFF),

        (byte) ((i >> 0) & 0xFF) };
  }

}
