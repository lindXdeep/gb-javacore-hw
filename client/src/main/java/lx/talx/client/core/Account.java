package lx.talx.client.core;

import lx.talx.client.api.Connect;
import lx.talx.client.service.IMessageProcessor;

public class Account {

  private Connect connect;
  private byte[] buf;
  private MessageAccomulator acc;
  private IMessageProcessor msgProc;

  public Account(Connect connect) {
    this.connect = connect;
    this.acc = new MessageAccomulator(connect);
    this.msgProc = new MsgProcessor();


   // acc.readMeaasges(msgProc);
   // connect.sendSecure("zopa".getBytes());


  //  buf = connect.read();
   // System.out.println(new String(buf, 0, buf.length));
  }

}
