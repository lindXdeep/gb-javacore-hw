package lx.talx.server.auth;

import org.json.simple.JSONObject;

public interface IUserAuthProvider {

  public void setLogin(byte[] login);

  public void setPass(byte[] pass);

  public byte[] authenticate();

  public void getCredentials();

  public void setLogin(String string);

  public void setUsername(String string);

  public void setEmail(String string);

  public void setPassword(String string);

  public char[] getAuthCodeAndSendToEmail(JSONObject tmpUser);
}
