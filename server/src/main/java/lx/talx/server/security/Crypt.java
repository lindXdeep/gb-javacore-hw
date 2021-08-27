package lx.talx.server.security;

import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.KeyAgreement;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Crypt {

  private KeyPair keyPair;
  private SecretKeySpec keyAES;

  // Generate our public key for client
  public void setClientPubKey(byte[] publicKeyClient) throws GeneralSecurityException {
    PublicKey publicKey = KeyFactory.getInstance("DH").generatePublic(new X509EncodedKeySpec(publicKeyClient));

    DHParameterSpec dhParamFromServer = ((DHPublicKey) publicKey).getParams();

    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DH");
    keyPairGenerator.initialize(dhParamFromServer);
    keyPair = keyPairGenerator.generateKeyPair();

    generateSharedKey(publicKey);
  }

  private void generateSharedKey(PublicKey publicKey) throws GeneralSecurityException {

    KeyAgreement keyAgree = KeyAgreement.getInstance("DH");
    keyAgree.init(keyPair.getPrivate());
    keyAgree.doPhase(publicKey, true);

    byte[] sharedKeySecret = keyAgree.generateSecret();

    keyAES = new SecretKeySpec(sharedKeySecret, 0, 16, "AES");
  }

  public byte[] getPubKeyEncoded() {
    return keyPair.getPublic().getEncoded();
  }

}
