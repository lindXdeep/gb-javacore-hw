package lx.talx.server.core;

import java.nio.ByteBuffer;
import java.util.Arrays;

import lx.talx.server.security.Auth;
import lx.talx.server.utils.Util;

public class CoreProcess {

  private Auth auth;
  private byte[] buf;

  public CoreProcess(Auth auth) {
    this.auth = auth;

    System.out.println("--------in account--------");

  //  int i = 0;
  //  while (true) {

     

    //  execute(auth.readSecure());


      
   // }
    
    // sendMessage("/user", "@Jopov", "@Pidr", "hello ".concat(Integer.toString(i++)));
    // byte[] b = auth.readSecure();

    // authPoint.sendSecure(b);

  }

  private void execute(byte[] recive) {
    byte[][] msg = parseMessage(recive);

    System.out.println("~~~~~~~~~~~~~~~~`");

    System.out.println(Util.byteToStr(msg[2]));

  }

  private void sendMessage(final String connamd, final String sender, final String recipient, final String message) {

    ByteBuffer b = ByteBuffer.allocate(64 + 64 + 20 + 4 + message.length());
    b.put(0, Util.strToByte(sender)); // 0 - 64
    b.put(255, Util.strToByte(recipient)); // 255 - 510
    b.put(510, Util.strToByte(connamd)); // 510 - 530
    b.put(530, Util.intToByte(message.length())); // 530 - 534
    b.put(534, Util.strToByte(message)); // 534 - len

    byte[] msg = b.array();

    System.out.println("send: \n" +

        Util.byteToStr(parseMessage(msg)[0]) + "\n" +

        Util.byteToStr(parseMessage(msg)[1]) + "\n" +

        Util.byteToStr(parseMessage(msg)[2]) + "\n" +

        Util.byteToStr(parseMessage(msg)[3]) + "\n");

    auth.sendSecure(msg);
  }

  public void readReciveMessage() {
    byte[] msg = auth.readSecure();
    System.out.println("recive:");
    System.out.printf("10%s: %s \n", "sender", Util.byteToStr(parseMessage(msg)[0]));
    System.out.printf("10%s: %s \n", "recipient", Util.byteToStr(parseMessage(msg)[0]));
    System.out.printf("10%s: %s \n", "command", Util.byteToStr(parseMessage(msg)[0]));
    System.out.printf("10%s: %s \n", "message", Util.byteToStr(parseMessage(msg)[0]));
  }

  private byte[][] parseMessage(byte[] recive) {

    int lengthMsg = Util.byteToInt(Arrays.copyOfRange(recive, 510, 514));

    return new byte[][] {
        // sender
        Arrays.copyOfRange(recive, 0, 255),
        // recipient
        Arrays.copyOfRange(recive, 255, 510),
        // command
        Arrays.copyOfRange(recive, 510, 530),
        // message
        Arrays.copyOfRange(recive, 534, lengthMsg + 534) };
  }
}
