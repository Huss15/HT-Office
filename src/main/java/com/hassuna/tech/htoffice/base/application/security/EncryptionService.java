package com.hassuna.tech.htoffice.base.application.security;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EncryptionService {

  private static final String ALGORITHM = "AES/GCM/NoPadding";
  private static final int GCM_IV_LENGTH = 12; // Empfohlene Länge für GCM
  private static final int GCM_TAG_LENGTH = 128; // Authentifizierungstag in Bits
  private static final String KEY_ALGORITHM = "AES";

  @Value("${ht.office.secret.key:}") // Lade aus application.properties oder Env (z.B.
  // APP_ENCRYPTION_KEY)
  private String keyBase64;

  private SecretKey secretKey;

  @PostConstruct
  public void init() {
    if (keyBase64 == null || keyBase64.trim().isEmpty()) {
      throw new IllegalArgumentException("Encryption key must be provided");
    }
    try {
      byte[] keyBytes = Base64.getDecoder().decode(keyBase64);
      if (keyBytes.length != 32) { // AES-256 erfordert 32 Bytes
        throw new IllegalArgumentException("Key must be 256 bits (32 bytes) long");
      }
      this.secretKey = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid encryption key format: " + e.getMessage(), e);
    }
  }

  public String encrypt(String plainText) {
    if (plainText == null) {
      return null;
    }

    try {
      Cipher cipher = Cipher.getInstance(ALGORITHM);

      byte[] iv = new byte[GCM_IV_LENGTH];
      new SecureRandom().nextBytes(iv); // Zufälliger IV pro Verschlüsselung
      GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
      cipher.init(Cipher.ENCRYPT_MODE, secretKey, spec);
      byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
      byte[] combined = new byte[iv.length + encrypted.length];
      System.arraycopy(iv, 0, combined, 0, iv.length);
      System.arraycopy(encrypted, 0, combined, iv.length, encrypted.length);
      return Base64.getEncoder().encodeToString(combined);

    } catch (NoSuchAlgorithmException
        | NoSuchPaddingException
        | InvalidKeyException
        | InvalidAlgorithmParameterException
        | IllegalBlockSizeException
        | BadPaddingException e) {
      log.error("Error during encryption: {}", e.getMessage(), e);
      return null;
    }
  }

  public String decrypt(String encryptedText) throws Exception {
    if (encryptedText == null) {
      return null;
    }
    Cipher cipher = Cipher.getInstance(ALGORITHM);
    byte[] decoded = Base64.getDecoder().decode(encryptedText);
    if (decoded.length < GCM_IV_LENGTH) {
      throw new IllegalArgumentException("Invalid encrypted text: Too short");
    }
    byte[] iv = new byte[GCM_IV_LENGTH];
    System.arraycopy(decoded, 0, iv, 0, iv.length);
    byte[] encrypted = new byte[decoded.length - iv.length];
    System.arraycopy(decoded, iv.length, encrypted, 0, encrypted.length);
    GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
    cipher.init(Cipher.DECRYPT_MODE, secretKey, spec);
    byte[] decrypted = cipher.doFinal(encrypted);
    return new String(decrypted, StandardCharsets.UTF_8);
  }

  public String generateKeyBase64() throws Exception {
    javax.crypto.KeyGenerator keyGen = javax.crypto.KeyGenerator.getInstance(KEY_ALGORITHM);
    keyGen.init(256, new SecureRandom()); // AES-256
    SecretKey key = keyGen.generateKey();
    return Base64.getEncoder().encodeToString(key.getEncoded());
  }
}
