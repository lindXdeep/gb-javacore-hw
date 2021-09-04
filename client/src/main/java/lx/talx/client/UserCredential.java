package lx.talx.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class UserCredential {

  private static DataInputStream din;

  public UserCredential(Client client) {
  }

  public boolean isKeyexist() {
    return getKey() != null;
  }

  public byte[] getKey() {
    try (DataInputStream dinkey = new DataInputStream(new FileInputStream("key"))) {
      return dinkey.readAllBytes();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public void createKey(byte[] key) {
    try (DataOutputStream doutkey = new DataOutputStream(new FileOutputStream("key"))) {
      doutkey.write(key);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
