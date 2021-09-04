package lx.talx.server.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import org.json.simple.JSONObject;

import lx.talx.server.dao.UserDao;
import lx.talx.server.net.MailService;

public class Auth {

  private MailService mailService;

  private byte[] login;
  private byte[] pass;

  public Auth(Properties properties) {
    mailService = new MailService(properties);
  }

  public void setLogin(byte[] login) {

  }

  public void setPass(byte[] pass) {

  }

  public byte[] authenticate() {
    return new byte[0];
  }

  public char[] getAuthCodeAndSendToEmail(JSONObject tmpUser) {

    char[] authcode = getAuthCode(tmpUser);

    String[] msg = { "Authentication code for Talx messanger", String.valueOf(authcode), };

    mailService.prepareMessage((String) tmpUser.get("email"));
    mailService.sendMsg(msg);

    return authcode;
  }

  private char[] getAuthCode(JSONObject tmpUser) {

    StringBuilder sb = new StringBuilder();

    sb.append((String) tmpUser.get("usernane"));
    sb.append((String) tmpUser.get("nickname"));
    sb.append((String) tmpUser.get("email"));
    sb.append((String) tmpUser.get("password"));

    return Long.toString(generateAuthCode(sb.toString())).toCharArray();
  }

  private long generateAuthCode(String string) {
    Random random = new Random();
    List<Long> dgt = new ArrayList<>();
    long rndResult = 0;

    for (byte b : string.getBytes())
      dgt.add((long) (b + Math.abs((random.nextLong() >>> 4) / (Math.random() / 4))));

    for (Long l : dgt) {
      rndResult ^= ((l / Integer.MAX_VALUE) << 21);
      rndResult ^= ((l / Byte.MAX_VALUE) >>> 35);
      rndResult ^= ((l / (long) Double.MAX_VALUE) << 4);
    }
    return rndResult;
  }
}
