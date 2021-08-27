package lx.talx.client.net;

import java.nio.ByteBuffer;
import java.util.Arrays;

import lx.talx.client.error.ClientSocketExceprion;
import lx.talx.client.security.Crypt;
import lx.talx.client.utils.Log;

public class Protocol {

  private Connection connection;
  private Crypt crypt;

  public Protocol(Connection connection) {
    this.connection = connection;
    this.crypt = new Crypt();
  }

  public void executeKeyExchange() throws ClientSocketExceprion {

    Log.info("Generate public key");
    crypt.generatePublicKey();

    Log.info("Sending public key to server");
    sendUncrypt(crypt.getPubKeyEncoded());

    crypt.setServerPubKey(readUncrypt(connection.read()));
    Log.info("Public key from server received");
  }

  private void sendUncrypt(byte[] bytes) {

    ByteBuffer buf = null;

    buf = ByteBuffer.allocate(4 + bytes.length); // 4 + all...
    buf.put(intToByte(bytes.length)); // 4 // length
    buf.put(bytes); // 4 + all

    connection.send(buf.array());
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
    int msgLength =  byteToInt(Arrays.copyOfRange(msg, 0, 4));
    return Arrays.copyOfRange(msg, 4, msgLength + 4);
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
