package lx.talx.client.security;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class UserCredential {

  private byte[] key;

  public UserCredential() {
    if (isKeyexist()) {
      this.key = readKey();
    }
    key = new byte[0];
  }

  public boolean isKeyexist() {
    return readKey() != null;
  }

  public byte[] readKey() {
    try (DataInputStream dinkey = new DataInputStream(new FileInputStream("key"))) {
      return dinkey.readAllBytes();
    } catch (IOException e) {
      System.out.println("Auth key not exist. Please login\n");
    }
    return null;
  }

  public void saveKey(byte[] key) {
    try (DataOutputStream doutkey = new DataOutputStream(new FileOutputStream("key"))) {
      doutkey.write(key);
      doutkey.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
