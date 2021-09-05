package lx.talx.server.core;

import lx.talx.server.security.AuthEntryPoint;
import lx.talx.server.security.AuthProcessor;

public class CoreProcess {

  private AuthEntryPoint authPoint;
  private AuthProcessor authProcessor;

  public CoreProcess(AuthEntryPoint authPoint, AuthProcessor authProcessor) {
    this.authPoint = authPoint;
    this.authProcessor = authProcessor;

    System.out.println("--------in account--------");

    
     
      byte[] b = authPoint.readSecure();
      
      System.out.println(new String(b, 0, b.length));



      authPoint.sendSecure("zopagovno".getBytes());

  



   // authPoint.sendSecure(b);

  }
}
